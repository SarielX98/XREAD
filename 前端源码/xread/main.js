import Vue from 'vue'
import App from './App'
import store from './store/index.js';

Vue.prototype.$store = store

Vue.config.productionTip = false

// 引入全局组件
import divider from './components/common/divider.vue';
Vue.component('divider',divider)
import noThing from './components/common/no-thing.vue';
Vue.component('no-thing',noThing)

// 引入配置文件
import $C from './common/config.js';
Vue.prototype.$C = $C
// 挂载助手函数库
import $U from './common/util.js';
Vue.prototype.$U = $U
// 引入社区模块请求库
import $H from './common/request-community.js';
Vue.prototype.$H = $H
// 引入交互函数库
import request from '@/common/request';
Vue.prototype.$http = request;
// 引入小说模块请求库
import $B from './common/request-book.js';
Vue.prototype.$B = $B

// 权限验证操作
Vue.prototype.checkAuth = (callback) => {
	// 权限验证
	if (!store.state.loginStatus) {
		uni.showToast({
			title: '请先登录',
			icon: 'none'
		});
		return uni.navigateTo({
			url: '/pages/login/login',
		});
	}
	callback()
}
// 权限验证跳转
Vue.prototype.navigateTo = (options) => {
	// 权限验证
	if (!store.state.loginStatus) {
		uni.showToast({
			title: '请先登录',
			icon: 'none'
		});
		return uni.navigateTo({
			url: '/pages/login/login',
		});
	}
	uni.navigateTo(options);
}

App.mpType = 'app'

const app = new Vue({
	store,
    ...App
})
app.$mount()
