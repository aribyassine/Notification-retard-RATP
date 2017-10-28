<template>
  <div class="container">
    <div class="row">
      <editable :fuse="fuse" v-model="fromServeur"></editable>
    </div>
  </div>
</template>

<script>
  import Editable from './Editable.vue'
  import Fuse from 'fuse.js'

  export default {
    name: 'edit',
    components: {
      'editable': Editable
    },
    data () {
      return {
        fromServeur: {
          ligne: {
            directions: 'yolo',
            name: 'hey'
          },
          interval: ['06:00', '08:00'],
          days: {
            lun: true,
            mar: true,
            mer: true,
            jeu: false,
            ven: false,
            sam: false,
            dim: false
          }
        },
        lines: [],
        fuse: null
      }
    },
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
        for (let type in res) {
          res[type].forEach((ligne) => this.lines.push(ligne))
        }
        this.fuse = new Fuse(this.lines, options)
      })
    }
  }
</script>
