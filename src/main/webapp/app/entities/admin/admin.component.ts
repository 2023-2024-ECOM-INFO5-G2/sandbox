import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import AdminService from './admin.service';
import { type IAdmin } from '@/shared/model/admin.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Admin',
  setup() {
    const { t: t$ } = useI18n();
    const adminService = inject('adminService', () => new AdminService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const admins: Ref<IAdmin[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveAdmins = async () => {
      isFetching.value = true;
      try {
        const res = await adminService().retrieve();
        admins.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveAdmins();
    };

    onMounted(async () => {
      await retrieveAdmins();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IAdmin) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeAdmin = async () => {
      try {
        await adminService().delete(removeId.value);
        const message = t$('ecom02App.admin.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveAdmins();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      admins,
      handleSyncList,
      isFetching,
      retrieveAdmins,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeAdmin,
      t$,
    };
  },
});
