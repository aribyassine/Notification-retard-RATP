import Vue from 'vue'
import Router from 'vue-router'
import Edit from '@/components/Edit'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/edit',
      name: 'Edit',
      component: Edit
    }
  ]
})
