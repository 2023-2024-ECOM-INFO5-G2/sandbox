/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import AdminUpdate from './admin-update.vue';
import AdminService from './admin.service';
import AlertService from '@/shared/alert/alert.service';

type AdminUpdateComponentType = InstanceType<typeof AdminUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const adminSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<AdminUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Admin Management Update Component', () => {
    let comp: AdminUpdateComponentType;
    let adminServiceStub: SinonStubbedInstance<AdminService>;

    beforeEach(() => {
      route = {};
      adminServiceStub = sinon.createStubInstance<AdminService>(AdminService);
      adminServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          adminService: () => adminServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(AdminUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.admin = adminSample;
        adminServiceStub.update.resolves(adminSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(adminServiceStub.update.calledWith(adminSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        adminServiceStub.create.resolves(entity);
        const wrapper = shallowMount(AdminUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.admin = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(adminServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        adminServiceStub.find.resolves(adminSample);
        adminServiceStub.retrieve.resolves([adminSample]);

        // WHEN
        route = {
          params: {
            adminId: '' + adminSample.id,
          },
        };
        const wrapper = shallowMount(AdminUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.admin).toMatchObject(adminSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        adminServiceStub.find.resolves(adminSample);
        const wrapper = shallowMount(AdminUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
