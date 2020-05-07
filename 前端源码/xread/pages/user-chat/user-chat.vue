<template>
	<view>
		
		<!-- 聊天列表 -->
		<scroll-view scroll-y="true" style="position: absolute;left: 0;top: 0;right: 0;bottom: 100rpx;" :scroll-into-view="scrollInto" scroll-with-animation>
			
			<block v-for="(item,index) in list" :key="index">
				<view :id="'chat'+index">
					<user-chat-list :item="item" :index="index" 
					:pretime="index > 0 ? list[index-1].create_time : 0"
					></user-chat-list>
				</view>
			</block>
			
		</scroll-view>
		
		<!-- 底部操作条 -->
		<bottom-input @submit="submit"></bottom-input>
		<!-- <view style="height: 100rpx;"
		class="fixed-bottom flex align-center border-top bg-white">
		
			<input type="text" v-model="content" class="flex-1 rounded bg-light ml-2" style="padding: 5rpx;" placeholder="文明发言" @confirm="submit"/>
			
			<view class="iconfont iconfasong flex align-center justify-center font-lg animated" hover-class="jello text-main" style="width: 100rpx;" @click="submit"></view>
			
		</view> -->
		
	</view>
</template>

<script>
	import userChatList from '@/components/user-chat/user-chat-list.vue';
	import bottomInput from '@/components/common/bottom-input.vue';
	export default {
		components: {
			userChatList,
			bottomInput
		},
		data() {
			return {
				scrollInto:"",
				list:[{
					user_id:2,
					avatar:"/static/default.png",
					username:"昵称",
					data:"你好啊",
					type:"text", 
					create_time:1570783530
				},{
					user_id:1,
					avatar:"/static/default.png",
					username:"昵称",
					data:"你好啊",
					type:"text", 
					create_time:1570783530
				},{
					user_id:2,
					avatar:"/static/default.png",
					username:"昵称",
					data:"你好啊",
					type:"text", 
					create_time:1570783530
				},{
					user_id:1,
					avatar:"/static/default.png",
					username:"昵称",
					data:"你好啊",
					type:"text", 
					create_time:1570783530
				},{
					user_id:2,
					avatar:"/static/default.png",
					username:"昵称",
					data:"你好啊",
					type:"text", 
					create_time:1570783530
				},{
					user_id:1,
					avatar:"/static/default.png",
					username:"昵称",
					data:"你好啊",
					type:"text", 
					create_time:1570783530
				},{
					user_id:2,
					avatar:"/static/default.png",
					username:"昵称",
					data:"你好啊",
					type:"text", 
					create_time:1570783530
				},{
					user_id:1,
					avatar:"/static/default.png",
					username:"昵称",
					data:"你好啊",
					type:"text", 
					create_time:1570783530
				},{
					user_id:2,
					avatar:"/static/default.png",
					username:"昵称",
					data:"你好啊",
					type:"text", 
					create_time:1570783530
				},{
					user_id:1,
					avatar:"/static/default.png",
					username:"昵称",
					data:"你好啊",
					type:"text", 
					create_time:1570783530
				}]
			}
		},
		// 页面加载完成的时候
		onReady() {
			this.pageToBottom()
		},
		methods: {
			// 发送
			submit(data){
				let obj = {
					user_id:1,
					avatar:"/static/default.png",
					username:"昵称",
					type:"text", 
					data:data,
					create_time:(new Date()).getTime()
				}
				this.list.push(obj)
				// 滚动到底部
				this.pageToBottom()
			},
			// 滚动到底部
			pageToBottom(){
				let lastIndex = this.list.length - 1
				if (lastIndex < 0) return;
				this.$nextTick(()=>{
					this.scrollInto  = 'chat'+lastIndex
				})
			}
		}
	}
</script>

<style>

</style>
