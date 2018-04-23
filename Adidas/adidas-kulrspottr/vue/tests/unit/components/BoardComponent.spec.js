import { mount, shallow} from '@vue/test-utils'

import BoardComponent from '../../../src/components/BoardComponent.vue';

describe('Components: BoardComponent', () => {

    it('should create the component', () => {
        expect(BoardComponent.data().level).toEqual(1);
    });

    it('should get a BoardConfiguration with level 1', () => {
        const wrapper = mount(BoardComponent);
        expect(wrapper.vm.boardConfig.boxesPerAxis).toEqual(2);
        expect(wrapper.vm.boardConfig.totalBoxes).toEqual(4);
    });

    it('should render a level 1 board', () => {
        const wrapper = mount(BoardComponent);
        expect(wrapper.find('h6').text()).toEqual('Level 1');
        expect(wrapper.findAll('.row').length).toEqual(2);
    });

    it('should click the special box at level 1', () => {
        const wrapper = mount(BoardComponent);
        wrapper.vm.clickBox(wrapper.vm.boardConfig.specialBox);
        expect(wrapper.vm.level).toEqual(2);
        expect(wrapper.vm.boardConfig.boxesPerAxis).toEqual(3);
        expect(wrapper.vm.boardConfig.totalBoxes).toEqual(9);
    });

    it('should click a wrong box', () => {
        const $router = { push:() => { }};
        spyOn($router, 'push').and.returnValue({});
        const wrapper = shallow(BoardComponent, {
            mocks: {
                $router
            }
        });
        wrapper.vm.clickBox(-1);
        expect($router.push).toHaveBeenCalledWith({name: 'hallOfFame', params: {level: 1}});
    });
});

import {  } from '@vue/test-utils'

