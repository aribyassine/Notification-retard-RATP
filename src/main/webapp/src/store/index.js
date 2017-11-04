import Vue from 'vue'
import Vuex from 'vuex'
import _ from 'lodash'

Vue.use(Vuex)

function intervals () {
  let hours = _.range(24)
  let minutes = _.range(0, 60, 10)
  let res = []
  hours.forEach((h) => minutes.forEach((m) => res.push((h < 10 ? '0' : '') + h + ':' + (m < 10 ? '0' : '') + m)))
  return res
}

const state = {
  serveur: [{
    ligne: {
      code: 1,
      name: 'Métro 1',
      directions: 'La Defense / Chateau de Vincennes',
      id: 62
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
  }],
  lines: null,
  fuse: null,
  intervals: intervals()
}

export default new Vuex.Store({
  state,
  strict: true,
  mutations: {
    setLines (state, payload) {
      state.lines = payload
    },
    setFuse (state, payload) {
      state.fuse = payload
    }
  },
  getters: {
    itemByIndex: (state) => (i) => {
      return state.serveur[i]
    },
    size: (state) => state.serveur.length,
    intervals: (state) => state.intervals
  },
  actions: {}
})
