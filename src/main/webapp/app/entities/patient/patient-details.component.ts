import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import { Line } from 'vue-chartjs';
import { BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, Title, Tooltip, PointElement, LineElement } from 'chart.js';
import PatientService from './patient.service';
import MesureService from '../mesure/mesure.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { type IPatient } from '@/shared/model/patient.model';
import { useAlertService } from '@/shared/alert/alert.service';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faArrowsUpDown, faCakeCandles, faDoorOpen, faGenderless, faLocationDot } from '@fortawesome/free-solid-svg-icons';

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, PointElement, LineElement);

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PatientDetails',
  components: { Line },
  setup() {
    library.add(faLocationDot);
    library.add(faCakeCandles);
    library.add(faGenderless);
    library.add(faArrowsUpDown);
    library.add(faDoorOpen);
    const patientService = inject('patientService', () => new PatientService());
    const mesureService = inject('mesureService', () => new MesureService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dataUtils = useDataUtils();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const patient: Ref<IPatient> = ref({});
    const poidsPatient: Ref<Array<Object>> = ref([]);
    const EPAPatient: Ref<Array<Object>> = ref([]);
    const patientIMC: Ref<Number> = ref(0);

    const retrievePatient = async patientId => {
      try {
        const res = await patientService().find(patientId);
        patient.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    const calculIMC = (patientHeight: string, patientWeight: string) => {
      return Math.round(Number(patientWeight) / (((Number(patientHeight) / 100) * Number(patientHeight)) / 100));
    };

    const retrievePatientWeights = async patientId => {
      try {
        const res = await mesureService().retrieve();
        const patientMesures = res.data.filter(o => o.patient !== null && o.patient.id === Number(patientId));

        poidsPatient.value = patientMesures.filter(o => o.nomValeur === 'poids');
        EPAPatient.value = patientMesures.filter(o => o.nomValeur === 'EPA');
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.patientId) {
      retrievePatient(route.params.patientId).then(() =>
        retrievePatientWeights(route.params.patientId).then(() => {
          patientIMC.value = calculIMC(patient.value.taille, poidsPatient.value[poidsPatient.value.length - 1].valeur);
        }),
      );
    }

    return {
      alertService,
      patient,
      poidsPatient,
      EPAPatient,
      patientIMC,
      chartData: {
        labels: ['January', 'February', 'March'],
        datasets: [{ data: [40, 20, 12] }],
      },
      chartOptions: {
        responsive: true,
      },

      ...dataUtils,

      previousState,
      t$: useI18n().t,
    };
  },
});
