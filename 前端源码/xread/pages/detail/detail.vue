<template>
	<view>
		<!-- 帖子详情页详细信息 -->
		<common-list :item="info" isDetail 
		@follow="follow"
		@doSupport="doSupport"
		@doComment="doComment" @doShare="doShare">
			<view>{{info.content}}</view>
			<view>
				<image v-for="(item,index) in images" :key="index" @click="preview(index)"
				:src="item.url" class="w-100" mode="widthFix"></image>
			</view>
		</common-list>
		
		<divider></divider>
		<view class="p-2 font-md font-weight-bold">
			最新评论 {{comments.length}}
		</view>
		<view class="px-2">
			
			
			<view class="uni-comment-list" v-for="(item,index) in comments" :key="index">
				<view v-if="item.fid" style="width: 60rpx;"></view>
				<view class="flex w-100" :class="item.fid ? 'bg-light rounded p-2' : ''">
					<view><image class="rounded-circle mr-2" :src="item.userpic" style="width: 55rpx;height: 55rpx;"></image></view>
					<view class="uni-comment-body">
						<view class="uni-comment-top">
							<text>{{item.username}}</text>
						</view>
						<view class="uni-comment-content"
						@click="reply(item.id)">{{item.data}}</view>
						<view class="uni-comment-date">
							<view>{{item.time}}</view>
						</view>
					</view>
				</view>
			</view>
			
			
		</view>
		
		
		<!-- 占位 -->
		<view style="height: 100rpx;"></view>
		<bottom-input :focus="focus" @blur="blur" @submit="submit"></bottom-input>
		
		<more-share ref="share"></more-share>
		
	</view>
</template>

<script>
	import commonList from '@/components/common/common-list.vue';
	import bottomInput from '@/components/common/bottom-input.vue';
	import moreShare from '@/components/common/more-share.vue';
	import $T from '@/common/time.js';
	export default {
		components:{
			commonList,
			bottomInput,
			moreShare
		},
		data() {
			return {
				// 当前帖子信息
				info:{
					id:0,
					user_id:0,
					username: "昵称",
					userpic: "",
					newstime: 0,
					isFollow: false,
					title: "",
					titlepic: "",
					support: {
						type: "support",
						support_count: 0,
						unsupport_count: 0
					},
					comment_count: 0,
					share_count: 0,
					content:""
				},
				images:[],
				comments:[],
				focus:false,
				reply_id:0
			}
		},
		onLoad(e) {
			// 初始化
			if (e.detail) {
				console.log(JSON.parse(e.detail));
				this.__init(JSON.parse(e.detail))
			}
			// 监听关注和顶踩操作
			uni.$on('updateFollowOrSupport',(e)=>{
				switch (e.type){
					case 'follow': // 关注
					this.follow(e.data.user_id)
						break;
					case 'support': // 顶踩
					this.doSupport(e.data)
						break;
				}
			})
		},
		onUnload() {
			uni.$off('updateFollowOrSupport',(e)=>{})
		},
		computed: {
			imageList(){
				return this.images.map(item=>item.url)
			}
		},
		onNavigationBarButtonTap() {
			this.$refs.share.open({
				title: this.info.title,
				shareText: this.info.title,
				href:"https://uniapp.dcloud.io",
				image: this.info.titlepic
			})
		},
		onBackPress() {
			this.$refs.share.close()
		},
		methods: {
			__init(data){
				// 修改标题
				uni.setNavigationBarTitle({
					title:data.title
				})
				this.info = data
				this.info.content = ''
				// 请求api
				this.$H.get('/post/getpostdetail?id='+this.info.id).then(res=>{
					console.log(res)
					this.info.content = res.post.content
					this.images = res.post.imageList
				})
				this.getComments()
			},
			// 点击评论
			doComment(){
				
			},
			// 点击分享
			doShare(){
				
			},
			// 关注
			follow(){
				this.info.isFollow = true
				uni.showToast({
					title: '关注成功'
				});
			},
			// 顶踩操作
			doSupport(e){
				// 之前操作过
				if (this.info.support.type === e.type) {
					return uni.showToast({
						title:'你已经操作过了',
						icon:'none'
					})
				}
				let msg = e.type === 'support' ? '顶' : '踩'
				// 判断之前有没有操作过
				if (this.info.support.type === '') {
					this.info.support[e.type+'_count']++
				}else if (this.info.support.type === 'support' && e.type === 'unsupport'){
					// 顶 - 1
					this.info.support.support_count--;
					// 踩 + 1
					this.info.support.unsupport_count++;
				}else if (this.info.support.type === 'unsupport' && e.type === 'support'){
					// 顶 + 1
					this.info.support.support_count++;
					// 踩 - 1
					this.info.support.unsupport_count--;
				}
				this.info.support.type = e.type
				uni.showToast({ title: msg + '成功' });
			},
			// 预览图片
			preview(index){
				uni.previewImage({
					current: index,
					urls: this.imageList
				});
			},
			// 提交评论
			submit(data){
				if(data === ''){
					return uni.showToast({
						title: '评论不能为空',
						icon: 'none'
					});
				}
				uni.showLoading({
					title: '评论中...',
					mask: false
				});
				this.$H.post('/comment/docomment',{
					fid:this.reply_id,
					data:data,
					postId:this.info.id
				},{
					header:{
						"content-type":"application/json"
					},
					token:true
				}).then(res=>{
					uni.hideLoading()
					this.getComments()
					console.log(res);
				}).catch(err=>{
					uni.hideLoading()
				})
			},
			// 获取评论列表
			getComments(){
				this.$H.get('/comment/getcomment?postId='+this.info.id).then(res=>{
					this.comments = this.__ArrSort(res.success)
					this.info.comment_count = this.comments.length
					uni.$emit('updateCommentsCount',{
						id:this.info.id,
						count:this.info.comment_count
					})
					// console.log(JSON.stringify(this.comments));
				})
			},
			// 重新整理评论格式
			__ArrSort(arr,id = 0){
				var temp = [],lev=0;
				var forFn = function(arr, id, lev){
					for (var i = 0; i < arr.length; i++) {
						var item = arr[i];
						if (item.fid == id) {
							item.lev = lev;
							temp.push({
								id:item.id,
								fid:item.fid,
								userid:item.user.id,
								userpic:item.user.userpic,
								username:item.user.username,
								time:$T.gettime(item.createTime),
								data:item.data,
							});
							forFn(arr,item.id,lev+1);
						}
					}
				};
				forFn(arr,id,lev);
				return temp;
			},
			// 回复评论
			reply(id){
				this.reply_id = id
				this.focus = true
			},
			// 输入框失去焦点
			blur(){
				this.reply_id = 0
				this.focus = false
			}
		}
	}
</script>

<style>

</style>
