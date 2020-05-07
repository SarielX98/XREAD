<template>
	<view>
		<uni-status-bar></uni-status-bar>
		<view>
			<view class="iconfont iconclose flex align-center justify-center font-lg"
			style="width: 100rpx;height: 100rpx;" hover-class="bg-light" 
			@click="back"></view>
		</view>
		
		
		<view class="text-center" style="padding-top: 130rpx;padding-bottom: 70rpx;font-size: 55rpx;">{{status ? '用户注册' : '账号密码登录'}}</view>
		
		<view class="px-2">
			
			<template v-if="!status">
				<view class="mb-2">
					<input type="text" v-model="username" placeholder="请输入账号" class="border-bottom p-2"/>
				</view>
				<view class="mb-2 flex align-stretch">
					<input type="text" v-model="password" placeholder="请输入密码" class="border-bottom p-2 flex-1"/>
					<view class="border-bottom flex align-center justify-center font text-muted" style="width: 150rpx;">忘记密码？</view>
				</view>
			</template>
			
			<template v-else>
				<view class="mb-2 flex align-stretch">
					<input type="text" v-model="username" placeholder="请输入账号" class="border-bottom p-2 flex-1"/>
				</view>
				<view class="mb-2 flex align-stretch">
					<input type="text" v-model="password" placeholder="请输入密码" class="border-bottom p-2 flex-1"/>
				</view>
			</template>
			
			<!-- <template v-else>
				<view class="mb-2 flex align-stretch">
					<view class="border-bottom flex align-center justify-center font px-2">+86</view>
					<input type="text" v-model="phone" placeholder="手机号" class="border-bottom p-2 flex-1"/>
				</view>
				<view class="mb-2 flex align-stretch">
					<input type="text" v-model="code" placeholder="请输入验证码" class="border-bottom p-2 flex-1"/>
					<view class="border-bottom flex align-center justify-center font-sm text-white rounded"
					 :class="codeTime > 0 ? 'bg-main-disabled' : 'bg-pink'"
					style="width: 180rpx;" @click="getCode">{{codeTime > 0 ? codeTime + 's' : '获取验证码'}}</view>
				</view>
			</template> -->
			
			
		</view>
		
		<view v-if="!status" class="py-2 px-3">
			<button class="text-white" style="border-radius: 50rpx;border: 0;" type="primary"
			:disabled="disabled" :class="disabled ? 'bg-main-disabled' : 'bg-pink'"
			@click="submit" :loading="loading">{{loading ? '登录中...' : '登录'}}</button>
		</view>
		<view v-else class="py-2 px-3">
			<button class="text-white" style="border-radius: 50rpx;border: 0;" type="primary"
			:disabled="disabled" :class="disabled ? 'bg-main-disabled' : 'bg-pink'"
			@click="register" :loading="loading">{{loading ? '注册中...' : '注册'}}</button>
		</view>
		
		<view class="flex align-center justify-center pt-2 pb-4">
			<view class="text-primary font-sm" @click="changeStatus">{{status ? '账号密码登录' : '用户注册'}}</view>
			<text class="text-muted mx-2">|</text>
			<view class="text-primary font-sm">登录遇到问题</view>
		</view>
		
		<!-- <view class="flex align-center justify-center">
			<view style="height: 1rpx;background-color: #DDDDDD;width: 100rpx;"></view>
			<view class="mx-2 text-muted">社交账号登录</view>
			<view style="height: 1rpx;background-color: #DDDDDD;width: 100rpx;"></view>
		</view>
		
		<other-login></other-login> -->
		
		<view class="flex align-center justify-center text-muted">
			注册即代表同意<text class="text-primary">《xxx协议》</text>
		</view>
		
	</view>
</template>

<script>
	import uniStatusBar from '@/components/uni-ui/uni-status-bar/uni-status-bar.vue';
	import otherLogin from '@/components/common/other-login.vue';
	export default {
		components:{
			uniStatusBar,
			otherLogin
		},
		data() {
			return {
				status:false,
				username:"",
				password:"",
				loading:false
				// phone:"",
				// code:"",
				// codeTime:0
			}
		},
		onLoad() {
			
		},
		computed: {
			disabled() {
				if (this.username === '' || this.password === '') {
					return true
				}
				return false
			}
		},
		methods: {
			back(){
				uni.navigateBack({
					delta: 1
				});
			},
			// 初始化表单
			initForm(){
				this.username=''
				this.password=''
				// this.phone=''
				// this.code=''
			},
			// 切换登录方式
			changeStatus(){
				// 初始化表单
				this.initForm()
				this.status = !this.status
			},
			// 获取验证码
			// getCode(){
			// 	// 防止重复获取
			// 	if (this.codeTime > 0) {
			// 		return;
			// 	}
			// 	// 验证手机号
			// 	if (!this.validate()) return;
			// 	// 倒计时
			// 	this.codeTime = 60
			// 	let timer = setInterval(()=>{
			// 		if (this.codeTime >= 1) {
			// 			this.codeTime--
			// 		} else {
			// 			this.codeTime = 0
			// 			clearInterval(timer)
			// 		}
					
			// 	},1000)
			// },
			// 表单验证
			validate(){
				// 用户账号
				if(this.username.length < 6 || this.username.length > 11){
					uni.showToast({
						title:'用户名应为6~11个字符或数字',
						icon:'none'
					});
					return false
				}
				// 用户密码
				if(this.password.length < 6 || this.password.length > 16){
					uni.showToast({
						title:'密码应为6~16个字符或数字',
						icon:'none'
					});
					return false
				}
				//手机号正则
				// var mPattern = /^1[34578]\d{9}$/;
				// if (!mPattern.test(this.phone)) {
				// 	uni.showToast({
				// 		title: '手机号格式不正确',
				// 		icon: 'none'
				// 	});
				// 	return false
				// }
				// ...更多验证
				return true
			},
			// 提交
			submit(){
				// 表单验证
				if (!this.validate()) return;
				// 提交后端
				this.loading = true
				this.$H.post('/user/login',{
					username:this.username,
					password:this.password
				},{
					header:{
						"content-type":"application/json"
					},
				}).then(res=>{
					this.loading = false
					// 修改vuex的state,持久化存储
					this.$store.commit('login',res.user)
					// 提示和跳转
					uni.switchTab({
						url:'../index/index'
					});
					uni.showToast({
						title: '登录成功',
						icon: 'none'
					});
				}).catch(err=>{
					// 登录失败
					this.loading = false
				})
			},
			// 注册
			register(){
				// 表单验证
				if (!this.validate()) return;
				// 提交后端
				this.loading = true
				this.$H.post('/user/register',{
					username:this.username,
					password:this.password
				},{
					header:{
						"content-type":"application/json"
					},
				}).then(res=>{
					this.loading = false
					this.status = !this.status
					uni.showToast({
						title: '注册成功',
						icon: 'none'
					});
				}).catch(err=>{
					this.loading = false
				})
			}
		}
	}
</script>

<style>

</style>
