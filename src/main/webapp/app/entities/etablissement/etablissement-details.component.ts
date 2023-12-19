import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import EtablissementService from './etablissement.service';
import { type IEtablissement } from '@/shared/model/etablissement.model';
import { type IPatient } from '@/shared/model/patient.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EtablissementDetails',
  setup() {
    const etablissementService = inject('etablissementService', () => new EtablissementService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const etablissement: Ref<IEtablissement> = ref({});
    const patientsEtablissement: ref<IPatient[]> = ref({});
    const retrieveEtablissement = async etablissementId => {
      try {
        const res = await etablissementService().find(etablissementId);
        etablissement.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    const retrievePatientEtablissement = async etablissementId => {
      try {
        console.log('avant requete dans le try');
        const res = await etablissementService().getPatientEtablissement(etablissementId);
        console.log('apres la requête');
        console.log(res);
        patientsEtablissement.value = res;
      } catch (err) {
        console.log(err);
        alertService.showHttpError(err.response);
      }
    };

    if (route.params?.etablissementId) {
      retrieveEtablissement(route.params.etablissementId);
      console.log('avant');
      retrievePatientEtablissement(route.params.etablissementId);
      console.log('après');
    }

    return {
      alertService,
      etablissement,
      retrievePatientEtablissement,
      patientsEtablissement,
      previousState,
      t$: useI18n().t,
    };
  },
});
