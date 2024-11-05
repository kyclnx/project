import { createRouter, createWebHistory } from "vue-router";
import LoginView from '@/view/LoginView.vue';
import HomeView from '@/view/HomeView.vue'; // 假设已有主页组件

const routes = [
    { path: '/', component: LoginView },
    { path: '/home', component: HomeView }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
