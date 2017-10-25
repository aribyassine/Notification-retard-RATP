<template>
  <v-autocomplete :items="linesAutocomplete" :get-label="getLabel"
                  @update-items="updateAutocomplete" @item-selected="itemSelected"
                  :component-item='template' :min-len='1'></v-autocomplete>
</template>

<script>
  import ligneTemplate from './AutocompleteLigneTemplate.vue'
  import Autocomplete from 'v-autocomplete'
  import Fuse from 'fuse.js'

  export default {
    name: 'Autocomplete',
    components: {'v-autocomplete': Autocomplete},
    data () {
      return {
        lines: [],
        linesAutocomplete: [],
        template: ligneTemplate,
        fuse: null
      }
    },
    mounted: function () {
      // `this` est une référence à l'instance de vm
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
    },
    methods: {
      itemSelected (item) {
        this.$emit('update', item)
      },
      getLabel (item) {
        return item.name
      },
      updateAutocomplete (text) {
        this.linesAutocomplete = this.fuse.search(text).slice(0, 15)
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="stylus">
  .v-autocomplete
    .v-autocomplete-input-group
      .v-autocomplete-input
        font-size 1.5em
        padding 10px 15px
        box-shadow none
        border 1px solid #157977
        width 100%
        outline none
        background-color #eee
      &.v-autocomplete-selected
        .v-autocomplete-input
          color green
          background-color #f2fff2
    .v-autocomplete-list
      display block
      position absolute
      width 100%
      text-align left
      border none
      border-top none
      max-height 380px
      overflow-y auto
      border-bottom 1px solid #157977
      .v-autocomplete-list-item
        cursor pointer
        background-color #fff
        padding 10px
        border-bottom 1px solid #157977
        border-left 1px solid #157977
        border-right 1px solid #157977
        &:last-child
          border-bottom none
        &:hover
          background-color #eee
        abbr
          opacity 0.8
          font-size 0.8em
          display block
          font-family sans-serif

</style>
