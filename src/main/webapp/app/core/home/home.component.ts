import { type ComputedRef, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
// import retrievePatients from '../../entities/patient/patient.component'
import type LoginService from '@/account/login.service';
import PatientService from '../../entities/patient/patient.service';
// import { type IPatient } from '@/shared/model/patient.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  setup() {
    const loginService = inject<LoginService>('loginService');

    const authenticated = inject<ComputedRef<boolean>>('authenticated');
    const username = inject<ComputedRef<string>>('currentUsername');
    const patientService = inject('patientService', () => new PatientService());

    const data = ref([]);

    const openLogin = () => {
      loginService.openLogin();
    };

    const action = async () => {
      try {
        const res = await patientService().retrieve();
        data.value = res.data;
        console.log(data);
      } catch (err) {
        // alertService.showHttpError(err.response);
      } finally {
        // isFetching.value = false;
      }
    };

    return {
      authenticated,
      data,
      username,
      openLogin,
      action,
      t$: useI18n().t,
    };
  },
});
