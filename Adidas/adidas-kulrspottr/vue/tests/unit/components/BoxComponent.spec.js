import { mount } from '@vue/test-utils'

import BoxComponent from '../../../src/components/BoxComponent.vue';

describe('Components: BoxComponents', () => {

    it('should create the component', () => {
        const wrapper = mount(BoxComponent)
        expect(wrapper.vm).not.toBeUndefined();
    });

    it('should pass props to the component', () => {
        const wrapper = mount(BoxComponent, {
          propsData: {
            color: 'red',
            boxId: 1
          }
        })
        expect(wrapper.vm.color).toEqual('red');
        expect(wrapper.vm.boxId).toEqual(1);
    });

    it('should execute click method and emit event', () => {
        const wrapper = mount(BoxComponent, {
          propsData: {
            color: 'red',
            boxId: 1
          }
        });
        wrapper.vm.clickBox();
        expect(wrapper.vm.color).toEqual('red');
        expect(wrapper.emitted().click).toBeTruthy();
        expect(wrapper.emitted().click[0]).toEqual([1]);
    });
});