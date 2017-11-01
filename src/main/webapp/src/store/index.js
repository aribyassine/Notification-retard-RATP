import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

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
  fuse: null
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
  getters: {},
  actions: {}
})
