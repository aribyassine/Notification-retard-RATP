<template>
  <div class="container">
    <div class="row">
      <editable v-for="(item, index) in server" :key="index" :ligne="item.ligne" :days="item.days"
                :interval="item.interval" :index="index"></editable>
      <button class="btn btn-default btn-block" @click="newItem">
        <span class="glyphicon glyphicon-menu-down" aria-hidden="true"></span> Ajouter <span
        class="glyphicon glyphicon-menu-down" aria-hidden="true"></span>
      </button>
    </div>
  </div>
</template>

<script>
  import Editable from './Editable.vue'
  import { mapGetters, mapMutations } from 'vuex'

  export default {
    name: 'edit',
    components: {
      'editable': Editable
    },
    methods: {
      ...mapMutations({ newItem: 'addNewItem' })
    },
    computed: {
      // rajouter les accesseurs dans `computed` avec l'opérateur de décomposition
      ...mapGetters(['server'])
    }
  }
</script>
