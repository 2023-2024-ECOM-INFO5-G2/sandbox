import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import PatientService from './patient.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import MedecinService from '@/entities/medecin/medecin.service';
import { type IMedecin } from '@/shared/model/medecin.model';
import { type IPatient, Patient } from '@/shared/model/patient.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PatientUpdate',
  setup() {
    const patientService = inject('patientService', () => new PatientService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const patient: Ref<IPatient> = ref(new Patient());

    const medecinService = inject('medecinService', () => new MedecinService());

    const medecins: Ref<IMedecin[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrievePatient = async patientId => {
      try {
        const res = await patientService().find(patientId);
        patient.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.patientId) {
      retrievePatient(route.params.patientId);
    }

    const initRelationships = () => {
      medecinService()
        .retrieve()
        .then(res => {
          medecins.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      idPatient: {
        required: validations.required(t$('entity.validation.required').toString()),
        numeric: validations.numeric(t$('entity.validation.number').toString()),
      },
      prenom: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      nom: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      sexe: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      dateDeNaissance: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      chambre: {
        required: validations.required(t$('entity.validation.required').toString()),
        numeric: validations.numeric(t$('entity.validation.number').toString()),
      },
      poids: {},
      dateArrivee: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      idMedecin: {},
      idPatients: {},
      idPatients: {},
      idPatients: {},
    };
    const v$ = useVuelidate(validationRules, patient as any);
    v$.value.$validate();

    return {
      patientService,
      alertService,
      patient,
      previousState,
      isSaving,
      currentLanguage,
      medecins,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.patient.id) {
        this.patientService()
          .update(this.patient)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('ecom02App.patient.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.patientService()
          .create(this.patient)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('ecom02App.patient.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
