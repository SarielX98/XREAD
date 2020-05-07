<template>
	<!-- 列表样式 -->
	<view class="p-2 animated fast fadeIn">
		<!-- 头像昵称 | 关注按钮 -->
		<view class="flex align-center justify-between">
			<view class="flex align-center">
				<!-- 头像 -->
				<image class="rounded-circle mr-2" 
				:src="item.userpic" @click="openSpace(item.user_id)" 
				style="width: 55rpx;height: 55rpx;" 
				lazy-load></image>
				<!-- 昵称 -->
				<view>
					<view class="font">{{item.username}}</view>
				</view>
			</view>
			<!-- 按钮 -->
			<view @click="follow" v-if="!item.isFollow"
			class="flex align-center justify-center rounded text-white bg-pink animated faster"
			style="width: 80rpx;height: 40rpx;"
			hover-class="jello">
				关注
			</view>
		</view>
		<!-- 标题 -->
		<view class="font-md my-1" @click="openDetail">{{item.title}}</view>
		<!-- 帖子详情 -->
		<slot>
			<!-- 图片 -->
			<image v-if="item.titlepic" class="rounded w-100" @click="openDetail"
			:src="item.titlepic" style="height: 350rpx;"></image>
		</slot>
		<!-- 发布时间|图标按钮 -->
		<view class="flex align-center justify-between">
			<!-- 发布时间 -->
			<text class="flex-1 font-sm text-light-muted">{{item.newstime|formatTime}}</text>
			<view class="flex align-center">
				<!-- 顶 -->
				<view class="flex align-center justify-center mr-2 animated faster"
				hover-class="jello text-pink" @click="doSupport('support')"
				:class="item.support.type === 'support' ? 'supprot-active' : ' '">
					<text class="iconfont icongood mr-2"></text>
					<text>{{item.support.support_count > 0 ? item.support.support_count : '顶'}}</text>
				</view>
				<!-- 踩 -->
				<view class="flex align-center justify-center mr-2 animated faster"
				hover-class="jello text-pink" @click="doSupport('unsupport')"
				:class="item.support.type === 'unsupport' ? 'supprot-active' : ' '">
					<text class="iconfont iconbad mr-2"></text>
					<text>{{item.support.unsupport_count > 0 ? item.support.unsupport_count : '踩'}}</text>
				</view>
				<!-- 评论 -->
				<view class="flex align-center justify-center mr-2 animated faster" 
				hover-class="jello text-pink" @click="doComment">
					<text class="iconfont iconcomments mr-2"></text>
					<text>{{item.comment_count > 0 ? item.comment_count : '评论'}}</text>
				</view>
				<!-- 分享 -->
				<view class="flex align-center justify-center animated faster" 
				hover-class="jello text-pink" @click="doShare">
					<text class="iconfont iconskip mr-2"></text>
					<text>{{item.share_count > 0 ? item.share_count : '分享'}}</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import $T from '@/common/time.js';
	export default {
		props: {
			item: Object,
			index: {
				type:Number,
				default:-1
			},
			isDetail:{
				type:Boolean,
				default:false
			}
		},
		filters: {
			formatTime(value) {
				return $T.gettime(value);
			}
		},
		methods:{
			// 打开个人空间
			openSpace(user_id){
				uni.navigateTo({
					url: '/pages/user-space/user-space?user_id='+user_id,
				});
			},
			// 关注
			follow(){
				this.checkAuth(()=>{
					console.log(this.item)
					this.$H.post('/follow/dofollow',{
						followId:this.item.user_id
					},{
						token:true
					}).then(res=>{
						// 通知更新
						uni.$emit('updateFollowOrSupport',{
							type:"follow",
							data:{
								user_id:this.item.user_id
							}
						})
					})
				})
			},
			// 顶踩操作
			doSupport(type){
				this.checkAuth(()=>{
					this.$H.post('/support/dosupport',{
						postId:this.item.id,
						type:type === 'support' ? 0 : 1
					},{
						token:true,
						header:{
							"content-type":"application/json"
						}
					}).then(res=>{
						console.log(res)
						if(res.code){
							return uni.showToast({
								title: res.msg,
								icon: 'none'
							});
						}
						// this.$emit('doSupport',{
						// 	type:type,
						// 	index:this.index
						// });
						uni.$emit('updateFollowOrSupport',{
							type:"support",
							data:{
								type:type,
								id:this.item.id
							}
						})
					})
				})
			},
			// 评论
			doComment(){
				this.checkAuth(()=>{
					if (!this.isDetail) {
						return this.openDetail()
					}
					this.$emit('doComment')
				})
			},
			// 分享
			doShare(){
				if (!this.isDetail) {
					return this.openDetail()
				}
				this.$emit('doShare')
			},
			// 进入详情页
			openDetail(){
				// 如果处在详情页
				if (this.isDetail) return;
				uni.navigateTo({
					url: '../../pages/detail/detail?detail='+JSON.stringify(this.item),
				});
			}
		}
	}
</script>

<style>
	.supprot-active{
		color: #FF4A6A;
	}
</style>
