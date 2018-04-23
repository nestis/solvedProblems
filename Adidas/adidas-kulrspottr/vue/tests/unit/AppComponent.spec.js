import AppComponent from '../../src/App.vue'

describe('AppComponent', () => {

    it('should have components definitions', () => {
        expect(AppComponent.components).not.toBeUndefined();
    });
});