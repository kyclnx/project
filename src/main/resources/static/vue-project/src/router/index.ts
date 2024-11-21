import {createRouter, createWebHistory} from 'vue-router'
import LoginView from '@/view/LoginView.vue'
import HomeView from '@/view/home/HomeView.vue'
// import home from '@/view/home/AppHeader.vue'
export const router = createRouter(
  {
    history: createWebHistory(import.meta.env.BASE_URL),
    routes:[
      {
        path: '/',
        name: '/login',
        component: LoginView
      },
      {
        path: '/home',
        name: 'home',
        component: HomeView
      },
    ]
  }
)
export default router
