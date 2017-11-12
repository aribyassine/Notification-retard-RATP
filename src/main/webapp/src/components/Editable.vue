<template>
  <div class="panel panel-default">
    <div class="panel-body">
      <div class="row">
        <div class="col-xs-10">
          <div class="col-xs-12">
            <autocomplete @update="updated" v-model="ligneCopy"></autocomplete>
          </div>
          <div class="col-xs-12 slider">
            <vue-slider v-model="intervalCopy" :data="intervals"></vue-slider>
          </div>
          <div class="col-xs-12 days">
            <switches v-model="everyDay" label="Tous les jours" theme="custom" color="green"></switches>
            <switches v-model="exceptWeekEnd" label="Du lundi au vendredi" theme="custom" color="green"></switches>
            <switches v-model="daysCopy[0]" label="lundi" theme="custom" color="blue"></switches>
            <switches v-model="daysCopy[1]" label="mardi" theme="custom" color="blue"></switches>
            <switches v-model="daysCopy[2]" label="mercredi" theme="custom" color="blue"></switches>
            <switches v-model="daysCopy[3]" label="jeudi" theme="custom" color="blue"></switches>
            <switches v-model="daysCopy[4]" label="vendredi" theme="custom" color="blue"></switches>
            <switches v-model="daysCopy[5]" label="samedi" theme="custom" color="blue"></switches>
            <switches v-model="daysCopy[6]" label="dimanche" theme="custom" color="blue"></switches>
          </div>
        </div>
        <div class="col-xs-2">
          <div class="aligner">

            <button type="button" class="btn btn-danger aligner-item" @click="remove()" v-if="saved && isValid"><span
              class="glyphicon glyphicon-remove"></span>
              remove
            </button>


            <button type="button" class="btn btn-success aligner-item" v-if="!saved && isValid"
                    @click="updateStore(daysCopy, intervalCopy, ligneCopy, index)">
              <span class="glyphicon glyphicon-saved"></span> save
            </button>

          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Autocomplete from './Autocomplete.vue'
  import vueSlider from 'vue-slider-component'
  import _ from 'lodash'
  import Switches from 'vue-switches'

  import { mapGetters, mapMutations } from 'vuex'

  export default {
    name: 'Editable',

    components: {
      'autocomplete': Autocomplete,
      'vue-slider': vueSlider,
      'switches': Switches
    },
    props: {
      ligne: {required: true},
      days: {required: true},
      interval: {required: true},
      index: {required: true}
    },
    data () {
      return {
        daysCopy: _.values(this.days),
        intervalCopy: this.interval,
        ligneCopy: this.ligne
      }
    },
    methods: {
      ...mapMutations({rm: 'remove', dup: 'duplicate'}),
      updateStore (days, interval, ligne, index) {
        days = this.daysObject(days)
        this.$store.commit('updateStore', {days, interval, ligne, index})
      },
      updated (ligne) {
        this.ligneCopy = ligne
      },
      daysObject (days) {
        return {lun: days[0], mar: days[1], mer: days[2], jeu: days[3], ven: days[4], sam: days[5], dim: days[6]}
      },
      remove () {
        this.rm({index: this.index})
      }
    },
    computed: {
      ...mapGetters(['intervals', 'itemByIndex']),
      isValid: function () {
        return this.ligneCopy.id !== 0 && this.daysCopy.some((day) => day)
      },
      saved: function () {
        let store = this.itemByIndex(this.index)
        return _.isEqual(_.valuesIn(this.daysCopy), _.valuesIn(store.days)) &&
          _.isEqual(this.intervalCopy, store.interval) &&
          _.isEqual(this.ligneCopy.id, store.ligne.id)
      },
      everyDay: {
        get: function () { return _.values(this.daysCopy).reduce((acc, day) => day && acc, true) },
        set: function (v) { this.daysCopy = _.map(this.daysCopy, (day) => v) }
      },
      exceptWeekEnd: {
        get: function () { return _.take(_.values(this.daysCopy), 5).reduce((acc, day) => day && acc, true) },
        set: function (v) { this.daysCopy = _.map(this.daysCopy, (value, key) => (key === 5 || key === 6) ? value : v) }
      }
    }
  }
</script>

<style lang="scss">
  .vue-switcher-theme--custom {
    &.vue-switcher-color--blue {
      div {
        background-color: lighten(#1997d9, 30%);

        &:after {
          background-color: #1997d9;
        }
      }
      &.vue-switcher--unchecked {
        div {
          background-color: lighten(#9797ae, 30%);

          &:after {
            background-color: lighten(#9797ae, 10%);
          }
        }
      }
    }
    &.vue-switcher-color--green {
      div {
        background-color: lighten(#3ed97a, 30%);

        &:after {
          background-color: #3ed97a;
        }
      }
      &.vue-switcher--unchecked {
        div {
          background-color: lighten(#97c8ae, 20%);

          &:after {
            background-color: #97c8ae;
          }
        }
      }
    }
  }
</style>

<style lang="stylus">
  .slider
    padding-top 50px

  .days
    padding 20px
    display flex
    justify-content space-around

</style>

<style>
  .aligner {
    height: 203px;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
  }

  .fade-enter-active, .fade-leave-active {
    transition: opacity .5s
  }

  .fade-enter, .fade-leave-to {
    opacity: 0
  }
</style>
