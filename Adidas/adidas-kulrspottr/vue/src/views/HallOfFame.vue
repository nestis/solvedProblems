<template>
    <div class="hallFame row">
        <div class="col s12 m12">
            <div class="card blue-grey darken-1">
                <div class="card-content white-text">
                    <span class="card-title">Game Over!</span>
                    <p>List of winners</p>
                    <ul>
                        <li v-for="winner in hallFame" :key="winner.pos">
                            <span v-if="!winner.save">{{winner.name}}</span>
                            <input text v-if="winner.save" v-model="winner.name" placeholder="Insert your name"
                                v-on:keyup:enter="submitUser(winner)"
                                v-on:blur="submitUser(winner)"/>
                            <span>{{winner.level}}</span>
                        </li>
                    </ul>
                </div>
                <div class="card-action">
                    <router-link to="game" class="waves-effect waves-light btn-large teal lighten-1">
                        Start Again
                    </router-link>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
  name: "HallOfFame",

  data: function() {
      return {
          hallFame: []
      }
  },

  mounted: function() {
      if(this.$route.params.level === undefined) {
        this.$router.push('/home');
      }
    const hallFame = JSON.parse(window.localStorage.getItem('adidas.kulrSpottr.hall'));
    if (hallFame !== null) {
        hallFame.push({
            name: '',
            level: this.$route.params.level,
            save: true
        });
        hallFame.sort((a, b) => b.level - a.level);
        this.hallFame = hallFame.slice(0, 10);
    } else {
        this.hallFame.push({
            name: '',
            level: this.$route.params.level,
            save: true
        });
    }
  },

  methods: {
      submitUser(winner) {
            winner.save = false;
            window.localStorage.setItem('adidas.kulrSpottr.hall', JSON.stringify(this.hallFame));
      }
  }
};
</script>

<style lang="scss">
.hallFame {
  li {
    display: flex;
    justify-content: space-between;

    input {
        color: white;
        font-size: 15px;
        height: 20px;
    }
  }
}
</style>

