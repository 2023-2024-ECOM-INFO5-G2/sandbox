import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import PatientService from './patient.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { type IPatient } from '@/shared/model/patient.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PatientDetails',
  setup() {
    const patientService = inject('patientService', () => new PatientService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dataUtils = useDataUtils();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const patient: Ref<IPatient> = ref({});

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

    return {
      alertService,
      patient,

      ...dataUtils,

      previousState,
      t$: useI18n().t,
    };
  },
});
