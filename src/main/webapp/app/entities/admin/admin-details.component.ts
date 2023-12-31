import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import AdminService from './admin.service';
import { type IAdmin } from '@/shared/model/admin.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AdminDetails',
  setup() {
    const adminService = inject('adminService', () => new AdminService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const admin: Ref<IAdmin> = ref({});

    const retrieveAdmin = async adminId => {
      try {
        const res = await adminService().find(adminId);
        admin.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.adminId) {
      retrieveAdmin(route.params.adminId);
    }

    return {
      alertService,
      admin,

      previousState,
      t$: useI18n().t,
    };
  },
});
