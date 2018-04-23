<template>
    <div class="row">
        <div class="col s12 m12">
            <div class="card blue-grey darken-1">
                <div class="card-content white-text">
                    <span class="card-title">Adidas KulrSpottr</span>
                    <p>
                        Simple game where you have to choose the colour 
                        that is different from the others.
                    </p>
                </div>
                <div class="card-action">
                    <router-link to="game" class="waves-effect waves-light btn-large teal lighten-1">
                        Start
                    </router-link>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: 'Home'
}
</script>
<style lang="scss">
.card-action {
    text-align: center;
}
</style>
