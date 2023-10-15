/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import AdminDetails from './admin-details.vue';
import AdminService from './admin.service';
import AlertService from '@/shared/alert/alert.service';

type AdminDetailsComponentType = InstanceType<typeof AdminDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const adminSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Admin Management Detail Component', () => {
    let adminServiceStub: SinonStubbedInstance<AdminService>;
    let mountOptions: MountingOptions<AdminDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      adminServiceStub = sinon.createStubInstance<AdminService>(AdminService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          adminService: () => adminServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        adminServiceStub.find.resolves(adminSample);
        route = {
          params: {
            adminId: '' + 123,
          },
        };
        const wrapper = shallowMount(AdminDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.admin).toMatchObject(adminSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        adminServiceStub.find.resolves(adminSample);
        const wrapper = shallowMount(AdminDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
