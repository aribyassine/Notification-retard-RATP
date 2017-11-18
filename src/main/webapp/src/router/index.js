import Vue from 'vue'
import Router from 'vue-router'
import Edit from '@/components/Edit'
import Notification from '@/components/Notification'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Edit',
      component: Edit
    }, {
      path: '/notification',
      name: 'Notification',
      component: Notification
    }
  ]
})
