<template>
	<view>
		
		<!-- 导航 -->
		<!-- <uni-nav-bar :border="false" :fixed="true" :statusBar="true" @clickRight="openAddInput">
			<view class="flex align-center justify-center font-weight-bold w-100">
				<view class="font-lg text-pink mx-1">关注</view>
				<view class="mx-1" @click="changeTab(index)"
				v-for="(item,index) in tabBars" :key="index" 
				:class="tabIndex === index ? 'font-lg text-pink' : 'font-md text-light-muted'">
					{{item.name}}
				</view>
			</view>
			<text slot="right" class="iconfont iconbianjiqianbixieshuru"></text>
		</uni-nav-bar> -->
		<!-- <uni-nav-bar :border="false" :fixed="true" :statusBar="true" @clickRight="openAddInput">
			<view class="flex align-center justify-center w-100">
				<view class="font-md">艾克斯社区</view>
			</view>
			<text slot="right" class="iconfont iconbianjiqianbixieshuru"></text>
		</uni-nav-bar> -->
		<!-- :style="'height:'+scrollH+'px;'" -->
		<scroll-view scroll-y="true" >
			
			<!-- 热门分类 -->
			<hot-cate :hotCate="hotCate"></hot-cate>
			<!-- 搜索框 -->
			<!-- <view class="p-2">
				<view class="bg-light rounded flex align-center justify-center py-2 text-secondary" @click="openSearch">
					<text class="iconfont iconsearch mr-2"></text>
					搜索话题
				</view>
			</view> -->
			<!-- 轮播图 -->
			<rotation-chat :imgArr="swiperImages"></rotation-chat>
			<!-- 最近更新 -->
			<view class="p-2 font-md">最近更新</view>
			<!-- 话题列表组件 -->
			<block  v-for="(item,index) in topicList" :key="index">
				<topic-list :item="item" :index="index"></topic-list>
			</block>
		</scroll-view>
		
		<!-- <swiper :current="tabIndex" :duration="150" :style="'height:'+scrollH+'px;'" @change="onChangeTab">
			关注
			<swiper-item>
				<scroll-view scroll-y="true" :style="'height:'+scrollH+'px;'"
				@scrolltolower="loadmoreEvent">
					<block v-for="(item,index) in list" :key="index">
						<common-list :item="item" :index="index" @doSupport="doSupport"></common-list>
						<divider></divider>
					</block>
					<load-more :loadmore="loadmore"></load-more>
				</scroll-view>
			</swiper-item>
			话题
			<swiper-item>
				<scroll-view scroll-y="true" :style="'height:'+scrollH+'px;'">
					
					热门分类
					<hot-cate :hotCate="hotCate"></hot-cate>
					搜索框
					<view class="p-2">
						<view class="bg-light rounded flex align-center justify-center py-2 text-secondary" @click="openSearch">
							<text class="iconfont iconsearch mr-2"></text>
							搜索话题
						</view>
					</view>
					轮播图
					<rotation-chat :imgArr="swiperImages"></rotation-chat>
					最近更新
					<view class="p-2 font-md">最近更新</view>
					话题列表组件
					<block  v-for="(item,index) in topicList" :key="index">
						<topic-list :item="item" :index="index"></topic-list>
					</block>
				</scroll-view>
			</swiper-item>
		</swiper> -->



	</view>
</template>

<script>
	// const demo = [{
	// 		username: "昵称",
	// 		userpic: "/static/default.png",
	// 		newstime: 1587638900,
	// 		isFollow: true,
	// 		title: "我是标题",
	// 		titlepic: "/static/demo/1.jpg",
	// 		support: {
	// 			type: "support",
	// 			support_count: 1,
	// 			unsupport_count: 2
	// 		},
	// 		comment_count: 2,
	// 		share_count: 2
	// 	},
	// 	{
	// 		username: "昵称",
	// 		userpic: "/static/default.png",
	// 		newstime: 1587638900,
	// 		isFollow: true,
	// 		title: "我是标题",
	// 		titlepic: "",
	// 		support: {
	// 			type: "unsupport",
	// 			support_count: 1,
	// 			unsupport_count: 2
	// 		},
	// 		comment_count: 2,
	// 		share_count: 2
	// 	},
	// 	{
	// 		username: "昵称",
	// 		userpic: "/static/default.png",
	// 		newstime: 1587638900,
	// 		isFollow: true,
	// 		title: "我是标题",
	// 		titlepic: "/static/demo/1.jpg",
	// 		support: {
	// 			type: "",
	// 			support_count: 1,
	// 			unsupport_count: 2
	// 		},
	// 		comment_count: 2,
	// 		share_count: 2
	// 	}			
	// ];
	// import uniNavBar from '@/components/uni-ui/uni-nav-bar/uni-nav-bar.vue';
	// import commonList from '@/components/common/common-list.vue';
	// import loadMore from '@/components/common/load-more.vue';
	import hotCate from '@/components/community/hot-cate.vue';
	import rotationChat from '@/components/common/rotation-chat.vue';
	import topicList from '@/components/community/topic-list.vue';
	export default {
		components: {
			// uniNavBar,
			// commonList,
			// loadMore,
			hotCate,
			rotationChat,
			topicList
		},
		data() {
			return {
				// 列表高度
				// scrollH:600,
				// 顶部选项卡
				// scrollInto:"",
				// tabIndex:0,
				// tabBars: [{
				// 	name: "关注"
				// },{
				// 	name: "话题"
				// }],
				//关注列表
				// list: [],
				// 1.上拉加载更多  2.加载中... 3.没有更多了
				// loadmore:"上拉加载更多",
				
				hotCate:[],
				// 轮播图列表
				swiperImages:[{
					src:"http://static.zongheng.com/upload/recommend/3a/39/3a397facf33b8bcfc94cb80de31ed283.jpeg"
				},{
					src:"http://static.zongheng.com/upload/recommend/29/51/29514bfe4a23e4cc19939ffe9fb98f4c.jpeg"
				},{
					src:"http://static.zongheng.com/upload/recommend/ba/16/ba163be4c0cf79953397646760779678.jpeg"
				},{
					src:"http://static.zongheng.com/upload/recommend/22/04/2204ca0d10fd374e96d02402954fa140.jpeg"
				},{
					src:"http://static.zongheng.com/upload/recommend/1f/4b/1f4b87cd2c6590df9e49c15b527a4822.jpeg"
				}],
				// 话题列表
				topicList:[]
			}
		},
		// 监听点击导航栏搜索框
		onNavigationBarSearchInputClicked() {
			uni.navigateTo({
				url: '../search/search?type=topic',
			});
		},
		// 监听点击导航栏按钮
		onNavigationBarButtonTap() {
			this.navigateTo({
				url: '../add-input/add-input',
			});
		},
		onLoad() {
			uni.getSystemInfo({
				success:res=>{
					this.scrollH = res.windowHeight - res.statusBarHeight - 44
				}
			})
			// 加载测试数据
			// this.list = demo
			this.getTopicNav()
			this.getHotTopic()
		},
		methods: {
			// 获取分类
			getTopicNav(){
				this.$H.get('/topicclass/listtopicclass').then(res => {
					this.hotCate = res.topiclassList.map(item=>{
						return {
							id:item.id,
							name:item.classname
						}
					})
				})
			},
			// 获取最近话题
			getHotTopic(){
				this.$H.get('/topic/tentoptopic').then(res => {
					this.topicList = res.listTenTopTopic.map(item=>{
						return{
							id:item.id,
							cover:item.titlepic,
							title:item.title,
							desc:item.desc
						}
					})
				})
			},
			// 打开发布页
			// openAddInput(){
			// 	this.navigateTo({
			// 		url: '../add-input/add-input',
			// 	});
			// },
			// 切换选项卡
			// changeTab(index){
			// 	this.tabIndex = index
			// },
			// 滑动
			// onChangeTab(e){
			// 	this.tabIndex = e.detail.current
			// },
			// 顶踩操作
			// doSupport(e){
			// 	// 拿到当前对象
			// 	let item = this.list[e.index]
			// 	let msg = e.type === 'support' ? '顶' : '踩'
			// 	// 之前没有操作过
			// 	if (item.support.type === '') {
			// 		item.support[e.type+'_count']++
			// 	} else if (item.support.type ==='support' && e.type === 'unsupport') {
			// 		// 顶 - 1
			// 		item.support.support_count--;
			// 		// 踩 + 1
			// 		item.support.unsupport_count++;
			// 	} else if(item.support.type ==='unsupport' && e.type === 'support'){ 					// 之前踩了
			// 		// 顶 + 1
			// 		item.support.support_count++;
			// 		// 踩 - 1
			// 		item.support.unsupport_count--;
			// 	}
			// 	item.support.type = e.type
			// 	uni.showToast({ title: msg + '成功' });
			// },
			// 上拉加载
			// loadmoreEvent(){
			// 	// 验证当前是否处于可加载状态
			// 	if (this.loadmore !== '上拉加载更多') return;
			// 	// 设置加载状态
			// 	this.loadmore = '加载中...'
			// 	// 模拟请求数据
			// 	setTimeout(()=>{
			// 		// 加载数据
			// 		this.list = [...this.list,...this.list]
			// 		// 设置加载状态
			// 		this.loadmore = '上拉加载更多'
			// 	},2000)
			// },
			// 打开搜索页
			// openSearch(){
			// 	uni.navigateTo({
			// 		url: '../search/search?type=topic',
			// 	});
			// }
		}
	}
</script>

<style>

</style>
