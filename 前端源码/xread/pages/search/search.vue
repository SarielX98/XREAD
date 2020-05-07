<template>
	<view>
		<template v-if="searchList.length === 0">
			<!-- 搜索历史 -->
			<view class="py-2 font-md px-2">搜索历史</view>
			<view class="flex flex-wrap">
				<view class="border rounded font mx-2 my-1 px-2" 
				v-for="(item,index) in list" :key="index"
				hover-class=" bg-light"
				@click="clickSearchHistory(item)">{{item}}</view>
			</view>
		</template>
		<template v-else>
			<!-- 数据列表 -->
			<!-- 小说 -->
			<book-list v-if="type === 'book'" :bookList="searchList"></book-list>
			<block v-else v-for="(item,index) in searchList" :key="index">
				
				<template v-if="type === 'topic'" >
					<!-- 话题 -->
					<topic-list :item="item" :index="index"></topic-list>
				</template>
				<template v-else>
					<!-- 用户 -->
					<user-list :item="item" :index="index"></user-list>
				</template>
				
			</block>
			
			<!-- 上拉加载 -->
			<load-more :loadmore="loadmore"></load-more>
			
		</template>
		
	</view>
</template>

<script>
	// 测试数据
	const demo1 = [{
			username: "昵称",
			userpic: "/static/default.png",
			newstime: "2020-01-21 下午15:51",
			isFollow: false,
			title: "我是标题",
			titlepic: "/static/demo/1.jpg",
			support: {
				type: "support",
				support_count: 1,
				unsupport_count: 2
			},
			comment_count: 2,
			share_count: 2
		},
		{
			username: "昵称",
			userpic: "/static/default.png",
			newstime: "2020-01-21 下午15:51",
			isFollow: false,
			title: "我是标题",
			titlepic: "",
			support: {
				type: "unsupport",
				support_count: 1,
				unsupport_count: 2
			},
			comment_count: 2,
			share_count: 2
		},
		{
			username: "昵称",
			userpic: "/static/default.png",
			newstime: "2020-01-21 下午15:51",
			isFollow: false,
			title: "我是标题",
			titlepic: "/static/demo/1.jpg",
			support: {
				type: "",
				support_count: 1,
				unsupport_count: 2
			},
			comment_count: 2,
			share_count: 2
		}			
	];
	
	const demo2 = [{
					cover:"/static/demo/topicpic1.jpg",
					title:"话题名称",
					desc:"话题描述",
					today_count:0,
					news_count:10
				},{
					cover:"/static/demo/topicpic1.jpg",
					title:"话题名称",
					desc:"话题描述",
					today_count:0,
					news_count:10
				},{
					cover:"/static/demo/topicpic1.jpg",
					title:"话题名称",
					desc:"话题描述",
					today_count:0,
					news_count:10
				},{
					cover:"/static/demo/topicpic1.jpg",
					title:"话题名称",
					desc:"话题描述",
					today_count:0,
					news_count:10
				},{
					cover:"/static/demo/topicpic1.jpg",
					title:"话题名称",
					desc:"话题描述",
					today_count:0,
					news_count:10
				},{
					cover:"/static/demo/topicpic1.jpg",
					title:"话题名称",
					desc:"话题描述",
					today_count:0,
					news_count:10
				},{
					cover:"/static/demo/topicpic1.jpg",
					title:"话题名称",
					desc:"话题描述",
					today_count:0,
					news_count:10
				},{
					cover:"/static/demo/topicpic1.jpg",
					title:"话题名称",
					desc:"话题描述",
					today_count:0,
					news_count:10
				},{
					cover:"/static/demo/topicpic1.jpg",
					title:"话题名称",
					desc:"话题描述",
					today_count:0,
					news_count:10
				},{
					cover:"/static/demo/topicpic1.jpg",
					title:"话题名称",
					desc:"话题描述",
					today_count:0,
					news_count:10
				},			
	];
	
	const demo3 = [{
		avatar:"/static/default.png",
		username:"昵称",
		sex:2, // 0未知 ，1女性，2男性
		age:24,
		isFollow:true
	},{
		avatar:"/static/default.png",
		username:"昵称",
		sex:1, // 0未知 ，1女性，2男性
		age:24,
		isFollow:false
	}];
	import commonList from '@/components/common/common-list.vue';
	import topicList from '@/components/community/topic-list.vue';
	import userList from '@/components/user-list/user-list.vue';
	import loadMore from '@/components/common/load-more.vue';
	import bookList from '@/components/common/book-list.vue';
	export default {
		components:{
			commonList,
			topicList,
			userList,
			loadMore,
			bookList
		},
		data() {
			return {
				searchText:"",
				list:[],
				// 搜索结果
				searchList:[],
				// 当前搜索类型
				type:"book",
				loadmore:"上拉加载更多",
				page:1
			}
		},
		// 监听导航输入
		onNavigationBarSearchInputChanged(e) {
			this.searchText = e.text;
		},
		// 监听点击导航搜索按钮
		onNavigationBarButtonTap(e) {
			if (e.index === 0) {
				this.searchEvent()
			}
		},
		onLoad(e) {
			if (e.type) {
				this.type = e.type
			}
			let pageTitle = '小说'
			switch (this.type){
				case 'book':
				pageTitle = '小说'
					break;
				case 'topic':
				pageTitle = '话题'
					break;
				case 'user':
				pageTitle = '用户'
					break;
			}
			// 修改搜索占位
			// #ifdef APP-PLUS
			let currentWebView = this.$mp.page.$getAppWebview();
			let tn = currentWebView.getStyle().titleNView;
			tn.searchInput.placeholder = '搜索'+pageTitle;
			currentWebView.setStyle({
				titleNView: tn
			})
			// #endif
			// 取出搜索历史
			let list = uni.getStorageSync('historySearchText')
			if(list){
				this.list = JSON.parse(list)
			}
		},
		// 监听下拉刷新
		onPullDownRefresh() {
			if(this.searchText === ''){
				return uni.stopPullDownRefresh()
			}
			this.getData(true,()=>{
				// 关闭下拉刷新状态
				uni.stopPullDownRefresh()
			})
		},
		// 监听上拉加载
		onReachBottom(){
			if(this.loadmore !== '上拉加载更多'){
				return;
			}
			this.loadmore = "加载中..."
			this.getData(false)
		},
		methods: {
			// 点击搜索历史
			clickSearchHistory(text){
				this.searchText = text
				this.searchEvent()
			},
			// 搜索事件
			searchEvent(){
				// 收起键盘
				uni.hideKeyboard()
				// 加入搜索历史
				let index = this.list.findIndex(v => v===this.searchText)
				if(index !== -1){
					this.$U.__toFirst(this.list,index)
				}else{
					this.list.unshift(this.searchText)
				}
				uni.setStorageSync('historySearchText',JSON.stringify(this.list))
				// 请求搜索
				this.getData()
			},
			// 获取数据
			getData(isrefresh = true,callback =false){
				// 处于loading状态
				uni.showLoading({
					title: '加载中',
					mask: false
				})
				// 请求搜索
				this.page = isrefresh ? 1 : (this.page + 1)
				if (this.type==='book'){
					
					this.$B.get('/book/fuzzy-search',{
						query: this.searchText
					}).then(res=>{
						if(res.ok){
							let list = res.books.map(v=>{
								return{
									id:v._id,
									name:v.title,
									imgurl:v.cover,
									synopsis:v.shortIntro,
								}
							});
							list.forEach((item)=>{
								item.imgurl = decodeURIComponent(item.imgurl).replace('/agent/', '');
							});
							// 渲染页面
							this.searchList = list
							// 加载情况
							this.loadmore =  '没有更多了' 
							// 隐藏loading
							uni.hideLoading()
							if(typeof callback === 'function'){
								callback()
							}
						}else{
							showToast('请求出错');
						}
					})
				}else{
					this.$H.post('/'+this.type+'/search'+this.type,{
						keyword:this.searchText,
						page:this.page
					}).then(res=>{
						// 整理格式
						let list = []
						switch (this.type){
							case 'topic':
							list = res.listTopic.list.map(item=>{
								return {
									id:item.id,
									cover:item.titlepic,
									title:item.title,
									desc:item.desc
								}
							})
								break;
							case 'user':
							list = res.listUser.list.map(v=>{
								return {
									id:v.id,
									avatar:v.userpic,
									username:v.username,
									sex:0,
									age:0,
									isFollow:true
								}
							})
								break;
						}
						
						// 渲染页面
						this.searchList = isrefresh ? [...list] : [...this.searchList,...list]
						
						// 加载情况
						this.loadmore = list.length < 10 ? '没有更多了' : '上拉加载更多'
						
						// 隐藏loading
						uni.hideLoading()
						if(typeof callback === 'function'){
							callback()
						}
					}).catch(err=>{
						this.page--
						// 隐藏loading
						uni.hideLoading()
						if(typeof callback === 'function'){
							callback()
						}
					})
				}
				
			}
		}
	}
</script>

<style>

</style>
