<template>
	<view>
		
		<input class="uni-input" type="text" 
		placeholder="输入旧密码" v-model="oldpassword"/>
		<input class="uni-input" type="text" 
		placeholder="输入新密码" v-model="newpassword"/>
		<input class="uni-input" type="text" 
		placeholder="输入确认密码" v-model="renewpassword"/>
		
		<view class="py-2 px-3">
			<button class="bg-pink text-white" style="border-radius: 50rpx;border: 0;" 
			type="primary" :disabled="disabled" :class="disabled ? 'bg-main-disabled' : ''"
			@click="submit">设置</button>
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				oldpassword:"",
				newpassword:"",
				renewpassword:""
			}
		},
		computed: {
			disabled() {
				return this.oldpassword == '' || this.newpassword == '' || this.renewpassword == '' 
			}
		},
		methods: {
			// 验证
			check(){
				if (this.newpassword !== this.renewpassword) {
					uni.showToast({
						title: '两次密码不一致',
						icon: 'none'
					});
					return false
				}
				return true
			},
			// 提交
			submit(){
				if (!this.check()) {
					return;
				}
				this.$H.post('/user/repassword',{
					password:this.oldpassword,
					newPassword:this.newpassword,
					reNewPassword:this.renewpassword
				},{
					token:true
				}).then(res=>{
					this.$store.commit('logout')
					uni.showToast({
						title: '修改成功，请重新登录!',
						icon: 'none'
					});
					uni.navigateTo({
						url: '../login/login',
					});
				})
			}
		}
	}
</script>

<style>

</style>
