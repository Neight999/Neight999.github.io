import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import AdminView from '../views/AdminView.vue'
import LayoutView from '../views/LayoutView.vue'
import RegisterView from '../views/RegisterView.vue'
import BookView from '../views/BookView.vue'
import TypeView from '../views/TypeView.vue'
import AuditView from '../views/AuditView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/register',
    name: 'register',
    component: RegisterView
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/',
    name: 'Layout',
    component: LayoutView,
    children: [
      {
        path: '',
        name: 'home',
        component: HomeView
      },
      {
        path: 'admin',
        name: 'admin',
        component: AdminView
      },
      {
        path: 'book',
        name: 'book',
        component: BookView
      },
      {
        path: 'type',
        name: 'type',
        component: TypeView
      },
      {
        path: 'audit',
        name: 'audit',
        component: AuditView
      }
    ]
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to,from,next) =>{
  if(to.path === '/login' || to.path === '/register'){
    next();
  }
  const user = localStorage.getItem('user');
  if(!user && to.path !== '/login' && to.path !== '/register'){
    return next('/login');
  }
  next();
});


export default router
