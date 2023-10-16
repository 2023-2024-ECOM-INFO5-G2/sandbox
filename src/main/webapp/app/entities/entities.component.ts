import { defineComponent, provide } from 'vue';

import PatientService from './patient/patient.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('patientService', () => new PatientService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
