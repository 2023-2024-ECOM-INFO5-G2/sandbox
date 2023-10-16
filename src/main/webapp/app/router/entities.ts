import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Patient = () => import('@/entities/patient/patient.vue');
const PatientUpdate = () => import('@/entities/patient/patient-update.vue');
const PatientDetails = () => import('@/entities/patient/patient-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'patient',
      name: 'Patient',
      component: Patient,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'patient/new',
      name: 'PatientCreate',
      component: PatientUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'patient/:patientId/edit',
      name: 'PatientEdit',
      component: PatientUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'patient/:patientId/view',
      name: 'PatientView',
      component: PatientDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
