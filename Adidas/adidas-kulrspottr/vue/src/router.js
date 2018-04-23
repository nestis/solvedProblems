import Vue from 'vue';
import Router from 'vue-router';

import Home from './views/Home.vue';
import Game from './views/Game.vue';
import HallOfFame from './views/HallOfFame.vue';

Vue.use(Router);

export default new Router({
  routes: [
    { path: '/', redirect: '/home' },
    { path: '/home', component: Home },
    { path: '/game', component: Game },
    { name: 'hallOfFame', path: '/hallOfFame', component: HallOfFame }
  ]
});
