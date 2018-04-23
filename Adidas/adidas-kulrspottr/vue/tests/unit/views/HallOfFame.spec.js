import { shallow } from '@vue/test-utils';

import HallOfFame from '../../../src/views/HallOfFame.vue';

describe('Views: HallOfFame', () => {

    it('should create the component', () => {
        expect(HallOfFame.data().hallFame).toBeDefined();
    });

    it('should mount the component with params and no winners', () => {
        window.localStorage = {
            getItem: (key) => { return null }
        }
        const $route = {
            params: {
                level: 10
            }
        };
        const wrapper = shallow(HallOfFame, {
            mocks: { $route },
            stubs: ['router-link']
        });

        expect(wrapper.vm.hallFame.length).toEqual(1);
        expect(wrapper.vm.hallFame[0].level).toEqual(10);
    });

    it('should mount the component with params and  winners', () => {
        window.localStorage = {
            getItem: (key) => { return '[{ "name": "Test1", "level": 10}, { "name": "Test2", "level": 5}]' }
        }
        const $route = {
            params: {
                level: 10
            }
        };
        const wrapper = shallow(HallOfFame, {
            mocks: { $route },
            stubs: ['router-link']
        });

        expect(wrapper.vm.hallFame.length).toEqual(3);
        expect(wrapper.vm.hallFame[0].level).toEqual(10);
        expect(wrapper.vm.hallFame[1].level).toEqual(10);
        expect(wrapper.vm.hallFame[2].level).toEqual(5);
    });

    it('should mount the component with no params', () => {
        const $router = { push: () => { }}
        spyOn($router, 'push').and.returnValue({});
        const $route = {
            params: {
            }
        };

        const wrapper = shallow(HallOfFame, {
            mocks: { $route, $router },
            stubs: ['router-link']
        });

        expect($router.push).toHaveBeenCalledWith('/home');
    });

    it('should submit a winner', () => {
        window.localStorage = {
            getItem: (key) => { return '[{ "name": "Test1", "level": 10}, { "name": "Test2", "level": 5}]' },
            setItem: (key, value) => { return '' }
        }
        spyOn(window.localStorage, 'setItem').and.callThrough();
        
        const $route = {
            params: {
                level: 10
            }
        };
        const wrapper = shallow(HallOfFame, {
            mocks: { $route },
            stubs: ['router-link']
        });

        const winner = { name: 'Test', level: 10};
        wrapper.vm.submitUser(winner);
        expect(winner.save).toBeFalsy();
        expect(window.localStorage.setItem).toHaveBeenCalled();
    });
});
