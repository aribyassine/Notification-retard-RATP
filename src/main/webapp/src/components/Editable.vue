<template>

  <div class="panel panel-default">
    <div class="panel-body">
      <div class="row">
        <div class="col-md-12">
          <autocomplete @update="updated" :fuse="fuse"></autocomplete>
        </div>
        <div class="col-md-12 slider">
          <vue-slider v-model="interval" :data="intervals"></vue-slider>
        </div>
        <div class="col-md-12 days">
          <switches v-model="days.lun" label="lundi" theme="custom" color="blue"></switches>
          <switches v-model="days.mar" label="mardi" theme="custom" color="blue"></switches>
          <switches v-model="days.mer" label="mercredi" theme="custom" color="blue"></switches>
          <switches v-model="days.jeu" label="jeudi" theme="custom" color="blue"></switches>
          <switches v-model="days.ven" label="vendredi" theme="custom" color="blue"></switches>
          <switches v-model="days.sam" label="samedi" theme="custom" color="blue"></switches>
          <switches v-model="days.dim" label="dimanche" theme="custom" color="blue"></switches>
          <switches v-model="everyDay" label="Tous les jours" theme="custom" color="green"></switches>
          <switches v-model="exceptWeekEnd" label="De lundi à vendredi" theme="custom" color="green"></switches>
        </div>
      </div>
    </div>

    <ul>
      <li>{{ligne.name}}</li>
      <li>{{interval}}</li>
      <li>{{value.days}}</li>
      <li>{{days}}</li>
      <li v-for="(v, k) in days" v-if="v">{{k}}</li>
    </ul>
  </div>
</template>

<script>
  import Autocomplete from './Autocomplete.vue'
  import vueSlider from 'vue-slider-component'
  import _ from 'lodash'
  import Switches from 'vue-switches'

  export default {
    name: 'Editable',
    props: {
      fuse: {required: true},
      value: {required: true}
    },
    components: {
      'autocomplete': Autocomplete,
      'vue-slider': vueSlider,
      'switches': Switches
    },
    data () {
      return {
        ligne: this.value.ligne,
        interval: this.value.interval,
        intervals: null,
        days: this.value.days
      }
    },
    created: function () {
      let hours = _.range(24)
      let minutes = _.range(0, 60, 10)
      let res = []
      hours.forEach((h) => minutes.forEach((m) => res.push((h < 10 ? '0' : '') + h + ':' + (m < 10 ? '0' : '') + m)))
      this.intervals = res
    },
    computed: {
      everyDay: {
        get: function () { return _.values(this.days).reduce((acc, day) => day && acc, true) },
        set: function (v) { this.days = _.mapValues(this.days, (day) => v) }
      },
      exceptWeekEnd: {
        get: function () {
          let acc = true
          _.each(this.days, function (v, k) {
            if (k !== 'dim' && k !== 'sam') {
              acc = acc && v
            }
          })
          return acc
        },
        set: function (value) {
          let days = {}
          _.each(this.days, function (v, k) {
            if (k !== 'dim' && k !== 'sam') {
              days[k] = value
            } else {
              days[k] = v
            }
          })
          this.days = days
        }
      }
    },
    methods: {
      updated (item) {
        this.ligne = item
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
