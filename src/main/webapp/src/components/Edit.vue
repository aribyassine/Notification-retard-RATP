<template>
  <div class="container">
    <div class="row">
      <autocomplete @update="updated"></autocomplete>
      <ul>
        <li v-for="test in tests">{{test}}</li>
      </ul>
    </div>
  </div>
</template>

<script>
  import Autocomplete from './Autocomplete.vue'

  export default {
    components: {'autocomplete': Autocomplete},
    data () {
      return {
        tests: ['test']
      }
    },
    methods: {
      updated (item) {
        this.tests.push(item.name)
      }
    }
  }
</script>

<style>

</style>
