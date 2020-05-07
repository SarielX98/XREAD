<template>
	<view>
		<!-- 未登录 -->
		<view v-if="!loginStatus" class="flex align-center p-2" hover-class="bg-light" @click="openLogin">
			<image src="/static/default.png"
			style="width: 100rpx; height: 100rpx;"
			class="rounded-circle"></image>
			<view class="flex flex-column flex-1 px-2">
				<text class="font-lg font-weight-bold">点击登录</text>
			</view>
			<text class="iconfont iconmore"></text>
		</view>
		
		<!-- 已登录 -->
		<view v-else class="flex align-center p-2" hover-class="bg-light">
			<image :src="avatar" 
			style="width: 100rpx; height: 100rpx;"
			class="rounded-circle"></image>
			<view class="flex flex-column flex-1 px-2">
				<text class="font-lg font-weight-bold">{{user.username}}</text>
				<!-- <text>总帖子10  今日发帖0</text> -->
			</view>
			<text class="iconfont iconmore"></text>
		</view>
		
		<view class="flex align-center px-3 py-2">
			<view class="flex flex-1 flex-column align-center justify-center"
			v-for="(item,index) in myData" :key="index">
				<text class="font-lg font-weight-bold">{{item.num}}</text>
				<text class="font text-muted">{{item.name}}</text>
			</view>
		</view>
		<!-- 
		<view class="px-3 py-2">
			<image src="/static/demo/banner2.jpg" mode="aspectFill"
			style="height: 170rpx;width: 100%;" class="rounded"></image>
		</view>
		
		<uni-list-item title="浏览历史" showExtraIcon>
			<text slot="icon" class="iconfont iconbrowse"></text>
		</uni-list-item>
		<uni-list-item title="社区认证" showExtraIcon>
			<text slot="icon" class="iconfont iconjewelry"></text>
		</uni-list-item>
		<uni-list-item title="审核帖子" showExtraIcon>
			<text slot="icon" class="iconfont icontemplatedefault"></text>
		</uni-list-item>
		 -->
	</view>
</template>

<script>
	import uniListItem from '@/components/uni-ui/uni-list-item/uni-list-item.vue';
	import { mapState } from 'vuex'
	export default {
		components:{
			uniListItem
		},
		data() {
			return {
				myData:[{
					name:"书单",
					num:0
				},{
					name:"帖子",
					num:0
				},{
					name:"评论",
					num:0
				},{
					name:"粉丝",
					num:0
				}]
			}
		},
		onNavigationBarButtonTap() {
			uni.navigateTo({
				url: '../user-set/user-set',
			});
		},
		computed: {
			...mapState({
				loginStatus:state=>state.loginStatus,
				user:state=>state.user
			}),
			// 用户头像
			avatar(){
				return this.user.userpic ? this.user.userpic : '/static/default.png'
			}
		},
		onShow(){
			if(this.loginStatus){
				this.getCounts()
			}
		},
		watch: {
			loginStatus(newValue, oldValue) {
				if(newValue){
					this.getCounts()
				}else{
					this.myData.forEach(item=>{
						item.num = 0
					})
				}
			}
		},
		methods: {
			// 获取用户相关数据
			getCounts(){
				this.$H.get('/user/getcounts?userId='+this.user.id,{},{
					token:true,
					notoast:true
				}).then(res=>{
					this.myData[1].num = res.data.post_count
					this.myData[2].num = res.data.comments_count
					this.myData[3].num = res.data.withfen_count
				})
			},
			// 打开登录页
			openLogin(){
				uni.navigateTo({
					url: '../login/login',
				});
			}
		}
	}
</script>

<style>

</style>
