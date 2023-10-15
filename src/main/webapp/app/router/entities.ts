import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Patient = () => import('@/entities/patient/patient.vue');
const PatientUpdate = () => import('@/entities/patient/patient-update.vue');
const PatientDetails = () => import('@/entities/patient/patient-details.vue');

const AideSoignant = () => import('@/entities/aide-soignant/aide-soignant.vue');
const AideSoignantUpdate = () => import('@/entities/aide-soignant/aide-soignant-update.vue');
const AideSoignantDetails = () => import('@/entities/aide-soignant/aide-soignant-details.vue');

const Infirmiere = () => import('@/entities/infirmiere/infirmiere.vue');
const InfirmiereUpdate = () => import('@/entities/infirmiere/infirmiere-update.vue');
const InfirmiereDetails = () => import('@/entities/infirmiere/infirmiere-details.vue');

const Admin = () => import('@/entities/admin/admin.vue');
const AdminUpdate = () => import('@/entities/admin/admin-update.vue');
const AdminDetails = () => import('@/entities/admin/admin-details.vue');

const Repas = () => import('@/entities/repas/repas.vue');
const RepasUpdate = () => import('@/entities/repas/repas-update.vue');
const RepasDetails = () => import('@/entities/repas/repas-details.vue');

const Medecin = () => import('@/entities/medecin/medecin.vue');
const MedecinUpdate = () => import('@/entities/medecin/medecin-update.vue');
const MedecinDetails = () => import('@/entities/medecin/medecin-details.vue');

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
    {
      path: 'aide-soignant',
      name: 'AideSoignant',
      component: AideSoignant,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'aide-soignant/new',
      name: 'AideSoignantCreate',
      component: AideSoignantUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'aide-soignant/:aideSoignantId/edit',
      name: 'AideSoignantEdit',
      component: AideSoignantUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'aide-soignant/:aideSoignantId/view',
      name: 'AideSoignantView',
      component: AideSoignantDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'infirmiere',
      name: 'Infirmiere',
      component: Infirmiere,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'infirmiere/new',
      name: 'InfirmiereCreate',
      component: InfirmiereUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'infirmiere/:infirmiereId/edit',
      name: 'InfirmiereEdit',
      component: InfirmiereUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'infirmiere/:infirmiereId/view',
      name: 'InfirmiereView',
      component: InfirmiereDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'admin',
      name: 'Admin',
      component: Admin,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'admin/new',
      name: 'AdminCreate',
      component: AdminUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'admin/:adminId/edit',
      name: 'AdminEdit',
      component: AdminUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'admin/:adminId/view',
      name: 'AdminView',
      component: AdminDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'repas',
      name: 'Repas',
      component: Repas,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'repas/new',
      name: 'RepasCreate',
      component: RepasUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'repas/:repasId/edit',
      name: 'RepasEdit',
      component: RepasUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'repas/:repasId/view',
      name: 'RepasView',
      component: RepasDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'medecin',
      name: 'Medecin',
      component: Medecin,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'medecin/new',
      name: 'MedecinCreate',
      component: MedecinUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'medecin/:medecinId/edit',
      name: 'MedecinEdit',
      component: MedecinUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'medecin/:medecinId/view',
      name: 'MedecinView',
      component: MedecinDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
