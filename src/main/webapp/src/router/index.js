import Vue from 'vue'
import Router from 'vue-router'
import Edit from '@/components/Edit'
// import Login from '@/components/login/Login'
// import Register from '@/components/login/Register'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Edit',
      component: Edit
    }
    /*
    ,{
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    }
    */
  ]
})
