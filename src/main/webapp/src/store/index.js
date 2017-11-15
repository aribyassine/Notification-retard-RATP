import Vue from 'vue'
import Vuex from 'vuex'
import _ from 'lodash'
import jquery from 'jquery'

Vue.use(Vuex)

function intervals () {
  let hours = _.range(24)
  let minutes = _.range(0, 60, 10)
  let res = []
  hours.forEach((h) => minutes.forEach((m) => res.push((h < 10 ? '0' : '') + h + ':' + (m < 10 ? '0' : '') + m)))
  return res
}

function fromServeur () {
  jquery.get('/getscheduledlines').then((response) => console.log(response.content), (err) => console.log(err))
  return [
    {
      ligne: {code: 1, name: 'Métro 1', directions: 'La Defense / Chateau de Vincennes', id: 62, type: 'metros'},
      interval: ['06:00', '08:00'],
      days: {lun: true, mar: true, mer: false, jeu: false, ven: false, sam: false, dim: false}
    }
  ]
}

const state = {
  server: fromServeur(),
  lines: null,
  fuse: null,
  intervals: intervals()
}

function pushToServer (item) {
  jquery.post('/addscheduledline', {
    lineName: item.ligne.name,
    type: item.ligne.type,
    interval: item.interval,
    days: [item.days.lun, item.days.mar, item.days.mer, item.days.jeu, item.days.ven, item.days.sam, item.days.dim]
  }, (response) => console.log(response))
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
    },
    updateStore (state, payload) {
      if (!isNaN(parseInt(payload.ligne.code))) {
        payload.ligne.code = parseInt(payload.ligne.code)
      }
      if (!isNaN(parseInt(payload.ligne.id))) {
        payload.ligne.id = parseInt(payload.ligne.id)
      }
      state.server[payload.index].ligne = payload.ligne
      state.server[payload.index].interval = payload.interval
      state.server[payload.index].days = payload.days
      pushToServer(state.server[payload.index])
    },
    setLigneAt (state, payload) {
      state.server[payload.index].ligne = payload.ligne
    },
    addNewItem (state) {
      state.server.push({
        ligne: {code: 0, name: '', directions: '', id: 0},
        interval: ['06:00', '08:00'],
        days: {lun: false, mar: false, mer: false, jeu: false, ven: false, sam: false, dim: false}
      })
    },
    remove (state, payload) {
      Vue.delete(state.server, payload.index)
      // console.log(_.without(state.server, state.server[payload.index]), payload.index)
      // state.server = _.without(state.server, state.server[payload.index])
    }
  },
  getters: {
    itemByIndex: (state) => (i) => {
      return state.server[i]
    },
    items: (state) => state.server,
    server: (state) => state.server,
    size: (state) => state.server.length,
    intervals: (state) => state.intervals,
    fuse: (state) => state.fuse
  }
})
