import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import EtablissementService from './etablissement.service';
import { type IEtablissement } from '@/shared/model/etablissement.model';
import { type IPatient } from '../../shared/model/patient.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Etablissement',
  setup() {
    const { t: t$ } = useI18n();
    const etablissementService = inject('etablissementService', () => new EtablissementService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const etablissements: Ref<IEtablissement[]> = ref([]);
    const patientsEtablissement: Ref<IPatient[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveEtablissements = async () => {
      isFetching.value = true;
      try {
        const res = await etablissementService().retrieve();
        etablissements.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const etablissementId: Ref<number> = ref(1); // valeur qu'il faudra mettre à jour
    let tmp = 0;
    const retrievePatientEtablissement = async () => {
      isFetching.value = true;
      try {
        console.log('avant requete dans le try');
        const res = await etablissementService().getPatientEtablissement(etablissementId.value);
        console.log('apres la requête');
        patientsEtablissement.value = res;
      } catch (err) {
        console.log(err);
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = async () => {
      await retrieveEtablissements();
      await retrievePatientEtablissement();
    };

    onMounted(async () => {
      await retrieveEtablissements();
      await retrievePatientEtablissement();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IEtablissement) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeEtablissement = async () => {
      try {
        await etablissementService().delete(removeId.value);
        const message = t$('g2EcomApp.etablissement.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveEtablissements();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      etablissements,
      patientsEtablissement,
      handleSyncList,
      isFetching,
      retrieveEtablissements,
      retrievePatientEtablissement,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeEtablissement,
      tmp,
      t$,
    };
  },
});
