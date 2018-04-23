<template>
  <div class="box" v-bind:style="{ backgroundColor: color }"
    v-on:click="clickBox">
  </div>
</template>
<script>
export default {
  name: 'BoxComponent',

  props: {
    color: String,
    boxId: Number
  },

  methods: {
    clickBox: function() {
      this.$emit('click', this.boxId);
    }
  }
}
</script>
<style lang="scss">
.box {
    flex-grow: 1;
    cursor: pointer;
    height: 100%;
    margin: 2px 2px 2px 2px;
    border-radius: 4px 4px 4px 4px;
    position: relative;
}
</style>
