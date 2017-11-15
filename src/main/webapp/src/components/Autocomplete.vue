<template>
    <v-autocomplete :items="linesAutocomplete" v-model="item" :get-label="getLabel"
                    @update-items="updateAutocomplete" @item-selected="itemSelected"
                    :component-item='template' :min-len='1' :placeholder="value.name.toString()"></v-autocomplete>
</template>

<script>
  import ligneTemplate from './AutocompleteLigneTemplate.vue'
  import Autocomplete from 'v-autocomplete'
  import { mapGetters } from 'vuex'

  export default {
    name: 'Autocomplete',
    components: {'v-autocomplete': Autocomplete},
    props: {
      value: {
        required: true
      }
    },
    data () {
      return {
        item: this.value,
        linesAutocomplete: [],
        template: ligneTemplate
      }
    },
    watch: {
      value: function (ligne) { this.item = ligne }
    },
    computed: {
      ...mapGetters(['fuse'])
    },
    methods: {
      itemSelected (item) {
        this.$emit('update', item)
      },
      getLabel (item) {
        return item.name + ' ' + item.directions
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
        border 1px solid #1997d9
        width 100%
        outline none
        background-color #eee
      &.v-autocomplete-selected
        .v-autocomplete-input
          color #1997d9
          background-color #eee
    .v-autocomplete-list
      display block
      position absolute
      z-index 4
      width calc(100% - 30px)
      text-align left
      border none
      border-top none
      max-height 380px
      overflow-y auto
      border-bottom 1px solid #1997d9
      .v-autocomplete-list-item
        cursor pointer
        background-color #fff
        padding 10px
        border-bottom 1px solid #1997d9
        border-left 1px solid #1997d9
        border-right 1px solid #1997d9
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
