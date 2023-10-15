import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import MedecinService from './medecin.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IMedecin, Medecin } from '@/shared/model/medecin.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MedecinUpdate',
  setup() {
    const medecinService = inject('medecinService', () => new MedecinService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const medecin: Ref<IMedecin> = ref(new Medecin());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveMedecin = async medecinId => {
      try {
        const res = await medecinService().find(medecinId);
        medecin.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.medecinId) {
      retrieveMedecin(route.params.medecinId);
    }

    const initRelationships = () => {};

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      idMedecin: {
        required: validations.required(t$('entity.validation.required').toString()),
        numeric: validations.numeric(t$('entity.validation.number').toString()),
      },
      firstName: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      lastName: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      idMedecins: {},
    };
    const v$ = useVuelidate(validationRules, medecin as any);
    v$.value.$validate();

    return {
      medecinService,
      alertService,
      medecin,
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
      if (this.medecin.id) {
        this.medecinService()
          .update(this.medecin)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('ecom02App.medecin.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.medecinService()
          .create(this.medecin)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('ecom02App.medecin.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
