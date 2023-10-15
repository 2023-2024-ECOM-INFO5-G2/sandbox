/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Admin from './admin.vue';
import AdminService from './admin.service';
import AlertService from '@/shared/alert/alert.service';

type AdminComponentType = InstanceType<typeof Admin>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Admin Management Component', () => {
    let adminServiceStub: SinonStubbedInstance<AdminService>;
    let mountOptions: MountingOptions<AdminComponentType>['global'];

    beforeEach(() => {
      adminServiceStub = sinon.createStubInstance<AdminService>(AdminService);
      adminServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          adminService: () => adminServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        adminServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Admin, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(adminServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.admins[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: AdminComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Admin, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        adminServiceStub.retrieve.reset();
        adminServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        adminServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeAdmin();
        await comp.$nextTick(); // clear components

        // THEN
        expect(adminServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(adminServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
