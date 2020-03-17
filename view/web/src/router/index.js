import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'login-1',
      component: ()=> import("./../views/Login.vue")
    },
    {
      path: '/useOnline',
      name: 'useOnline',
      component: ()=> import("./../views/useOnline/UseOnline.vue"),
      redirect:'/useOnline/sliderFixed',
      children:[
        {path:'sliderPop',name:"sliderPop",component:()=>import("./../views/useOnline/SliderPop.vue")},
        {path:'sliderFixed',name:"sliderFixed",component:()=>import("./../views/useOnline/SliderFixed.vue")},
        {path:'pointPop',name:"pointPop",component:()=>import("./../views/useOnline/PointPop.vue")},
        {path:'pointFixed',name:"pointFixed",component:()=>import("./../views/useOnline/PointFixed.vue")},
      ]
    },
    {
      path: '/helpCenter',
      name: 'helpCenter',
      component: ()=> import("./../views/helpCenter/HelpCenter.vue"),
      redirect:'/helpCenter/desc',
      children:[
        {path:'admin',name:"helpAdmin",component:()=>import("./../views/helpCenter/HelpAdmin.vue")},
        {path:'web',name:"helpWeb",component:()=>import("./../views/helpCenter/HelpWeb.vue")},
        {path:'desc',name:"helpDesc",component:()=>import("./../views/helpCenter/HelpDesc.vue")},
        {path:'process',name:"helpProcess",component:()=>import("./../views/helpCenter/HelpProcess.vue")},
        {path:'question',name:"question",component:()=>import("./../views/helpCenter/Question.vue")},
      ]
    },
    {
      path: '/login',
      name: 'login-2',
      component: ()=> import("./../views/Login.vue")
    },
    {
      path: '*',
      name: 'login-3',
      component: ()=> import("./../views/Login.vue")
    }
  ]
})
