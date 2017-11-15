<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
  import Fuse from 'fuse.js'
  import { mapMutations } from 'vuex'

  export default {
    name: 'app',
    mounted: function () {
      this.$http.get('lines').then(response => {
        let options = {
          shouldSort: true,
          tokenize: true,
          keys: [{
            name: 'directions',
            weight: 0.2
          }, {
            name: 'name',
            weight: 0.8
          }]
        }
        let res = response.body.result
        let lines = []
        for (let type in res) {
          res[type].forEach((ligne) => {
            ligne.type = type
            lines.push(ligne)
          })
        }
        this.setLines(lines)
        this.setFuse(new Fuse(lines, options))
      })
    },
    methods: {
      ...mapMutations([
        'setFuse',
        'setLines'
        // attacher `this.setLines()` à `this.$store.commit('setLines', param)`
      ])
    }
  }
</script>

<style lang="scss">
  @import "assets/scss/app.scss";
  body{padding-top: 20px}
</style>
