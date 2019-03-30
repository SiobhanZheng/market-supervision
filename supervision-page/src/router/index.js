import Vue from 'vue'
import Router from 'vue-router'

// 登录
import login from '@/page/login'
// 首页
import index from '@/page/index'
//500
import errorBug from '@/page/500'

Vue.use(Router)
const router = new Router({
  routes: [
    {
      path: '/500',
      name: '500',
      component: errorBug,
      meta: {
        title: '500程序错误',
      },
    },
    {
      path: '/',
      redirect: {
        name: 'login'
      }
    },
    {
      path: '/login',
      name: 'login',
      component: login,
      meta: {
        title: '张家港市场监督管理局乐余分局',
      },
    },
    {
      path: '/index',
      name: 'index',
      component: index,
      meta: {
        title: '张家港市场监督管理局乐余分局',
        requiresAuth:true,// 添加该字段，表示进入这个路由是需要登录的
      },
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (to.meta.title) {//如果设置标题，拦截后设置标题
    document.title = to.meta.title
  }
  next()
})
export default router
