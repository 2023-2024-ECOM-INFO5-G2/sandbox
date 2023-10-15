import { defineComponent, provide } from 'vue';

import PatientService from './patient/patient.service';
import AideSoignantService from './aide-soignant/aide-soignant.service';
import InfirmiereService from './infirmiere/infirmiere.service';
import AdminService from './admin/admin.service';
import RepasService from './repas/repas.service';
import MedecinService from './medecin/medecin.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('patientService', () => new PatientService());
    provide('aideSoignantService', () => new AideSoignantService());
    provide('infirmiereService', () => new InfirmiereService());
    provide('adminService', () => new AdminService());
    provide('repasService', () => new RepasService());
    provide('medecinService', () => new MedecinService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
