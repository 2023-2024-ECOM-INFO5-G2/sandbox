import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import AdminService from './admin.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IAdmin, Admin } from '@/shared/model/admin.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AdminUpdate',
  setup() {
    const adminService = inject('adminService', () => new AdminService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const admin: Ref<IAdmin> = ref(new Admin());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      idAdmin: {
        required: validations.required(t$('entity.validation.required').toString()),
        numeric: validations.numeric(t$('entity.validation.number').toString()),
      },
      prenom: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      nom: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
    };
    const v$ = useVuelidate(validationRules, admin as any);
    v$.value.$validate();

    return {
      adminService,
      alertService,
      admin,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.admin.id) {
        this.adminService()
          .update(this.admin)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('ecom02App.admin.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.adminService()
          .create(this.admin)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('ecom02App.admin.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
