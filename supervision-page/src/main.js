// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import iView from 'iview'
import 'iview/dist/styles/iview.css'
import VueResource from 'vue-resource'
import Distpicker from 'v-distpicker'
Vue.component('v-distpicker', Distpicker)

Vue.config.productionTip = false

Vue.use(iView)
Vue.use(VueResource)
// 引入VueResource的interceptors add by sionhan.zheng at 20190328
Vue.http.interceptors.push(function(request, next) {
  next(function (response) {
    // 前端对登陆过期的拦截，并跳转到login页面
    // debugger;
    if (response.body.code == "000") {
      this.$Modal.info({
        title: '提示',
        content: '未登录或者登录过期，请先登录',
        okText:'知道了',
        onOk: () => {
          this.$router.push({
            path:"/login",
          });
        },
      });
    }
    // 前端对后台系统内部异常的拦截，并跳转到500页面
    if(response.body.code == "500"){
      this.$router.push({
        path:"/500",
      });
    }

  })
})
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})
