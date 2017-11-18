<template>
  <div id="app">
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <a class="navbar-brand" href="/">Notifieur</a>
        <ul class="nav navbar-nav navbar-right">
          <li>
            <router-link to="/">Accueil</router-link>
          </li>
          <li>
            <router-link to="/notification">Notifications</router-link>
          </li>
        </ul>
      </div>
    </nav>
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

  body {
    padding-top: 70px
  }
</style>
