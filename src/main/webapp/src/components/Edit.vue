<template>
  <div class="container">
    <div class="row">
      <editable v-model="fromServeur"></editable>
    </div>
  </div>
</template>

<script>
  import Editable from './Editable.vue'

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
