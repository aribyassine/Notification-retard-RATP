<template>
  <div class="container">
    <div class="row">
      <editable v-for="i in size" :i="i - 1" :key="i -1"></editable>
    </div>
  </div>
</template>

<script>
  import Editable from './Editable.vue'
  import { mapGetters } from 'vuex'

  export default {
    name: 'edit',
    components: {
      'editable': Editable
    },
    computed: {
      // rajouter les accesseurs dans `computed` avec l'opérateur de décomposition
      ...mapGetters(['size'])
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
            mer: false,
            jeu: false,
            ven: false,
            sam: false,
            dim: false
          }
        }
      }
    }
  }
</script>
