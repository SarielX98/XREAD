import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
	state:{
		// 登录
		loginStatus: false,
		token:false,
		user:{
			// "id": 24,
			// "username": "sariel999",
			// "userpic": "http://q9jczboph.bkt.clouddn.com//images/testImg1.jpgtId1588143766384",
			// "password": "$2a$10$ZEHmysKpzQ2KeiFgnVkG..pMWCxHLMIIVOwJTfPHk1Eupukpa8McS",
			// "phone": null,
			// "email": null,
			// "status": 1,
			// "createTime": 1587623013,
			// "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyNCIsImV4cCI6MTU4ODIzMjg5Nn0.NCU9YTAv83j9IZSdpVbFRLXccJQwRzNbBR7MZP1OSlU",
			// "userInfo": {
			// 	"id": 1,
			// 	"userId": 24,
			// 	"age": 0,
			// 	"sex": 0,
			// 	"qg": 0,
			// 	"job": "IT",
			// 	"path": "广东汕头",
			// 	"birthday": "1998-04-29"
			// }
		}
	},
	mutations:{
		// 登录
		login(state,user){
			state.loginStatus = true
			state.user = user
			state.token = state.user.token
			uni.setStorageSync('user',JSON.stringify(user));
		},
		// 初始化登录状态
		initUser(state){
			let user = uni.getStorageSync('user');
			if(user){
				state.user = JSON.parse(user)
				state.loginStatus = true
				state.token = state.user.token
			}
		},
		// 退出登录
		logout(state){
			state.loginStatus = false
			state.user = {}
			state.token = false
			uni.removeStorageSync('user');
		},
		// 修改用户信息
		editUserInfo(state,{key,value}){
			state.user[key] = value
			uni.setStorageSync('user',JSON.stringify(state.user));
		},
		// 修改资料
		editUserUserInfo(state,obj){
			state.user.userInfo.sex = obj.sex
			state.user.userInfo.qg = obj.qg
			state.user.userInfo.job = obj.job
			state.user.userInfo.path = obj.path
			state.user.userInfo.birthday = obj.birthday
			uni.setStorageSync('user',JSON.stringify(state.user));
		}
	}
})