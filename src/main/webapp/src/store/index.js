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
  // Todo remove global async
  jquery.ajaxSetup({async: false})
  let res = null
  jquery.get('/getscheduledlines').then(function (response) {
    res = response // JSON.parse('[{"lineScheduleId":6,"line":{"lineId":2,"lineName":"RER C","lineType":"rer"},"beginTime":"6:0","endTime":"8:40","day":"tuesday"},{"lineScheduleId":8,"line":{"lineId":2,"lineName":"RER C","lineType":"rer"},"beginTime":"18:50","endTime":"20:40","day":"sunday"},{"lineScheduleId":5,"line":{"lineId":2,"lineName":"RER C","lineType":"rer"},"beginTime":"6:0","endTime":"8:40","day":"monday"},{"lineScheduleId":7,"line":{"lineId":2,"lineName":"RER C","lineType":"rer"},"beginTime":"18:50","endTime":"20:40","day":"saturday"},{"lineScheduleId":4,"line":{"lineId":1,"lineName":"Metro 1","lineType":"metro"},"beginTime":"6:0","endTime":"8:40","day":"tuesday"},{"lineScheduleId":3,"line":{"lineId":1,"lineName":"Metro 1","lineType":"metro"},"beginTime":"6:0","endTime":"8:40","day":"monday"},{"lineScheduleId":1,"line":{"lineId":1,"lineName":"Metro 1","lineType":"metro"},"beginTime":"6:0","endTime":"10:10","day":"monday"},{"lineScheduleId":2,"line":{"lineId":1,"lineName":"Metro 1","lineType":"metro"},"beginTime":"6:0","endTime":"10:10","day":"tuesday"}]')
    let lines = []
    jquery.get('/lines')
      .then(
        function (response) {
          response = response.result
          for (let type in response) {
            response[type].forEach((ligne) => {
              ligne.type = type
              lines.push(ligne)
            })
          }
          res = res.map(function (e) {
            return {
              id: e.lineScheduleId,
              ligne: _.find(lines, (line) => e.line.lineName === line.name),
              interval: [
                e.beginTime.split(':').map((time) => (parseInt(time) < 10 ? '0' : '') + parseInt(time)).join(':'),
                e.endTime.split(':').map((time) => (parseInt(time) < 10 ? '0' : '') + parseInt(time)).join(':')
              ],
              days: e.day
            }
          })
          res = _.flatMap(_.values(_.groupBy(res, 'ligne.name')).map(elem => _.values(_.groupBy(elem, 'interval'))))
          res = res.map(elem =>
            elem.reduce(function (acc, val) {
              acc.ids.push(val.id)
              acc.ligne = val.ligne
              acc.interval = val.interval
              acc.days.push(val.days)
              return acc
            }, {ids: [], days: []})
          )
          res.forEach(function (item) {
            item.days = {
              lun: item.days.includes('monday'),
              mar: item.days.includes('tuesday'),
              mer: item.days.includes('wednesday'),
              jeu: item.days.includes('thursday'),
              ven: item.days.includes('friday'),
              sam: item.days.includes('saturday'),
              dim: item.days.includes('sunday')
            }
          })
        },
        (err) => console.log(err)
      )
  }, (err) => console.log(err))
  return res
}

function pushToServer (item, index) {
  if (_.has(item, 'ids')) {
    jquery.post('/updatescheduledline', {
      ids: item.ids,
      lineName: item.ligne.name,
      type: item.ligne.type,
      interval: item.interval,
      days: [item.days.lun, item.days.mar, item.days.mer, item.days.jeu, item.days.ven, item.days.sam, item.days.dim]
    }, function (response) { state.server[index].ids = response.ids })
  } else {
    jquery.post('/addscheduledline', {
      lineName: item.ligne.name,
      type: item.ligne.type,
      interval: item.interval,
      days: [item.days.lun, item.days.mar, item.days.mer, item.days.jeu, item.days.ven, item.days.sam, item.days.dim]
    }, (response) => console.log(response))
  }
}

const state = {
  server: fromServeur(),
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
    },
    setServerData (state, payload) {
      state.server = payload
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
      pushToServer(state.server[payload.index], payload.index)
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
      if (_.has(state.server[payload.index], 'ids')) {
        jquery.post('/deletescheduledline', {ids: state.server[payload.index].ids}, (response) => console.log(response))
      }
      Vue.delete(state.server, payload.index)
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
