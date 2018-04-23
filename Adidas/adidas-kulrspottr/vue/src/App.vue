<template>
  <div id="app" class="container">
    <router-view></router-view>
  </div>
</template>

<script>
import Home from './views/Home.vue';
import Game from './views/Game.vue';
import HallOfFame from './views/HallOfFame.vue';

export default {
  name: 'app',
  components: {
    Home,
    Game,
    HallOfFame
  }
}
</script>
<style lang="scss">
.container {
  min-height: 55vh;
  display: flex;
}
</style>

