import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import { Line } from 'vue-chartjs';
import { BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, LineElement, PointElement, Title, Tooltip } from 'chart.js';
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
    const weightChartData: Ref<Object> = ref({});
    const EPAChartData: Ref<Object> = ref({});
    const chartOptions: Ref<Object> = ref({
      responsive: true,
    });
    const weightChartLoaded: Ref<Boolean> = ref(false);
    const EPAChartLoaded: Ref<Boolean> = ref(false);
    const newEPAValue: Ref<String> = ref('');
    const newWeightValue: Ref<String> = ref('');

    const retrievePatient = async patientId => {
      try {
        const res = await patientService().find(patientId);
        patient.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    const addPoidsValue = async () => {
      try {
        if (newWeightValue.value !== null) {
          // Create a new Poids entry object
          const newPoidsEntry = {
            date: new Date().toISOString(),
            valeur: Number(newWeightValue.value),
            nomValeur: 'poids',
            patient: patient.value,
          };

          // Add the new Poids entry to the poidsPatient array
          poidsPatient.value.push(newPoidsEntry);

          // Save the new Poids entry to the server
          await mesureService().create(newPoidsEntry);
          await retrievePatientMesures(patient.value.id);
        }
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    const addEPAValue = async () => {
      try {
        if (newEPAValue.value !== null && newEPAValue.value !== '') {
          // Create a new EPA entry object
          const newEPAEntry = {
            date: new Date().toISOString(),
            valeur: Number(newEPAValue.value),
            nomValeur: 'EPA',
            patient: patient.value,
          };

          // Add the new EPA entry to the EPAPatient array
          EPAPatient.value.push(newEPAEntry);

          // Save the new EPA entry to the server
          await mesureService().create(newEPAEntry);
          await retrievePatientMesures(patient.value.id);
        }
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    const calculIMC = (patientHeight: string, patientWeight: string) => {
      return Math.round(Number(patientWeight) / (((Number(patientHeight) / 100) * Number(patientHeight)) / 100));
    };

    const retrievePatientMesures = async patientId => {
      try {
        const res = await mesureService().retrieve();
        const patientMesures = res.data.filter(o => o.patient !== null && o.patient.id === Number(patientId));

        poidsPatient.value = patientMesures.filter(o => o.nomValeur === 'poids');
        poidsPatient.value.sort((a, b) => new Date(a.date) - new Date(b.date));
        EPAPatient.value = patientMesures.filter(o => o.nomValeur === 'EPA');
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.patientId) {
      retrievePatient(route.params.patientId).then(() =>
        retrievePatientMesures(route.params.patientId).then(() => {
          patientIMC.value = calculIMC(patient.value.taille, poidsPatient.value[poidsPatient.value.length - 1].valeur);
          const weightValues = [];
          const EPAValues = [];
          for (const weightEntry of poidsPatient.value) {
            weightValues.push({
              x: weightEntry.date,
              y: weightEntry.valeur,
            });
          }
          for (const EPAEntry of EPAPatient.value) {
            EPAValues.push({
              x: EPAEntry.date,
              y: EPAEntry.valeur,
            });
          }
          weightChartData.value = {
            datasets: [
              {
                label: 'poids (kg)',
                data: weightValues,
                borderColor: 'rgb(255, 125, 100)',
              },
            ],
          };
          EPAChartData.value = {
            datasets: [
              {
                label: 'EPA',
                data: EPAValues,
                borderColor: 'rgb(45, 80, 255)',
              },
            ],
          };
          weightChartLoaded.value = true;
          EPAChartLoaded.value = true;
        }),
      );
    }

    return {
      alertService,
      patient,
      poidsPatient,
      EPAPatient,
      patientIMC,
      weightChartData,
      EPAChartData,
      chartOptions,
      weightChartLoaded,
      EPAChartLoaded,
      newEPAValue,
      newWeightValue,

      ...dataUtils,

      previousState,
      t$: useI18n().t,
      addEPAValue,
      addPoidsValue,
    };
  },
});
