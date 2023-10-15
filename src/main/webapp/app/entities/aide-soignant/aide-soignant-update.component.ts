import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import AideSoignantService from './aide-soignant.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import PatientService from '@/entities/patient/patient.service';
import { type IPatient } from '@/shared/model/patient.model';
import { type IAideSoignant, AideSoignant } from '@/shared/model/aide-soignant.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AideSoignantUpdate',
  setup() {
    const aideSoignantService = inject('aideSoignantService', () => new AideSoignantService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const aideSoignant: Ref<IAideSoignant> = ref(new AideSoignant());

    const patientService = inject('patientService', () => new PatientService());

    const patients: Ref<IPatient[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveAideSoignant = async aideSoignantId => {
      try {
        const res = await aideSoignantService().find(aideSoignantId);
        aideSoignant.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.aideSoignantId) {
      retrieveAideSoignant(route.params.aideSoignantId);
    }

    const initRelationships = () => {
      patientService()
        .retrieve()
        .then(res => {
          patients.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      idSoignant: {
        required: validations.required(t$('entity.validation.required').toString()),
        numeric: validations.numeric(t$('entity.validation.number').toString()),
      },
      prenom: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      nom: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      idSoignants: {},
    };
    const v$ = useVuelidate(validationRules, aideSoignant as any);
    v$.value.$validate();

    return {
      aideSoignantService,
      alertService,
      aideSoignant,
      previousState,
      isSaving,
      currentLanguage,
      patients,
      v$,
      t$,
    };
  },
  created(): void {
    this.aideSoignant.idSoignants = [];
  },
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.aideSoignant.id) {
        this.aideSoignantService()
          .update(this.aideSoignant)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('ecom02App.aideSoignant.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.aideSoignantService()
          .create(this.aideSoignant)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('ecom02App.aideSoignant.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },

    getSelected(selectedVals, option): any {
      if (selectedVals) {
        return selectedVals.find(value => option.id === value.id) ?? option;
      }
      return option;
    },
  },
});
