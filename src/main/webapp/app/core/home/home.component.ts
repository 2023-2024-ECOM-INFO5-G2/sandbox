import { type ComputedRef, defineComponent, onMounted, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import EtablissementService from '../../entities/etablissement/etablissement.service';
import type { IEtablissement } from '../../shared/model/etablissement.model';
import type LoginService from '../../account/login.service';
import { useAlertService } from '../../shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  setup() {
    const loginService = inject<LoginService>('loginService');
    const etablissementService = inject('etablissementService', () => new EtablissementService());
    const selectedetablissement = ref({});
    const etablissements: Ref<IEtablissement[]> = ref([]);
    const alertService = inject('alertService', () => useAlertService(), true);
    const isFetching = ref(false);

    const authenticated = inject<ComputedRef<boolean>>('authenticated');
    const username = inject<ComputedRef<string>>('currentUsername');
    const retrieveEtablissements = async () => {
      isFetching.value = true;
      try {
        const res = await etablissementService().retrieve();
        etablissements.value = res.data;
        selectedetablissement.value = etablissements.value[0];
      } catch (err: any) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    onMounted(async () => {
      await retrieveEtablissements(); //FIXME : à supprimer ???
    });
    const openLogin = () => {
      loginService.openLogin();
    };

    return {
      authenticated,
      username,
      etablissements,
      isFetching,
      selectedetablissement,
      openLogin,
      t$: useI18n().t,
    };
  },
});
