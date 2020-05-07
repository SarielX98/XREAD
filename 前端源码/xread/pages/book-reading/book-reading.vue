<template>
	<view>
		<uni-status-bar :class="curTheme" class="cal"></uni-status-bar>
		
		<!-- 设置开始 -->
		<!-- 设置头部部分 -->
		<view :class="curTheme" class="fixed-top shadow animated slideInDown" v-if="setStatus">
			<uni-status-bar></uni-status-bar>
			<view class="flex align-center" style="height: 80rpx;">
				<!-- #ifndef MP-WEIXIN -->
				<view class="iconfont iconback pl-2" style="font-size: 60rpx;" @click="quit"></view>
				<!-- #endif -->
				
				<text class="font-md pl-2">{{novalName}}</text>
				<text class="flex-1 px-2 font-sm text-ellipsis">章节:{{curChapterTitle}}</text>
			</view>
		</view>
		
		<!-- 设置底部部分 -->
		<view :class="curTheme" class="flex align-center fixed-bottom shadow font animated slideInUp" style="height: 150rpx;" v-if="setStatus">
			<view class="flex-1 flex flex-column align-center" @click="changeCatalogStatus(true)">
				<view class="iconfont iconmulu" style="font-size: 55rpx;"></view>
				<view>目录</view>
			</view>
			<view class="flex-1 flex flex-column align-center" @click="isNight">
				<view class="iconfont iconbrowse" style="font-size: 55rpx;"></view>
				<view>夜间模式</view>
			</view>
			<view class="flex-1 flex flex-column align-center" @click="changeTypeFaceStatus(true)">
				<view class="iconfont iconziti" style="font-size: 55rpx;"></view>
				<view>字体</view>
			</view>
			<view class="flex-1 flex flex-column align-center" @click="changeMoreStatus(true)">
				<view class="iconfont iconearth" style="font-size: 55rpx;"></view>
				<view>更多</view>
			</view>
		</view>
		<!-- 设置结束 -->
		
		<!-- 目录开始 -->
		<uni-drawer :visible="catalogStatus" @close="changeCatalogStatus(false)">
			<uni-status-bar></uni-status-bar>
			<view class="flex align-center justify-center" style="height: 80rpx;">章节选择</view>
			<scroll-view scroll-y :style="{height: `${calHeight - 80}rpx`}">
				<block v-for="item in chapterCatalog" :key="item.id">
					<view class="px-1 py-2 font text-ellipsis border-bottom rounded" :class="chapterIndex + 1 === item.id ? 'curChapter' : ''" @click="toPointChapter(item.id)">{{item.title}}</view>
				</block>
			</scroll-view>
		</uni-drawer>
		<!-- 目录结束 -->
		
		<!-- 字体设置开始 -->
		<view :class="curTheme" class="fixed-bottom shadow px-3 pt-2 font animated slideInUp" style="height: 180rpx;" v-if="typeFaceStatus">
			<view class="flex">
				字体:<slider class="flex-1" :value="myFontSize" min="20" max="50" block-size="16" activeColor="#34495E" backgroundColor="#ECF1F0" @change="changeFontSize" @changing="changeFontSize"/>
			</view>
			<view class="flex">
				间距:<slider class="flex-1" :value="myLineHeight" min="20" max="100" block-size="16" activeColor="#34495E" backgroundColor="#ECF1F0" @change="changeLineHeight" @changing="changeLineHeight"/>
			</view>
		</view>
		<!-- 字体设置结束 -->
		
		<!-- 更多设置开始 -->
		<view :class="curTheme" class="fixed-bottom flex flex-column justify-center shadow px-3 font animated slideInUp" 
		style="height: 250rpx;" v-if="moreStatus">
			<!-- #ifndef H5 -->
			<view class="flex align-center">
				亮度: <slider class="flex-1" :value="brightNess" min="0" max="100" block-size="16" 
				activeColor="#34495E" backgroundColor="#ECF1F0" @change="setBrightNess"/>
			</view>
			<!-- #endif -->
			<view class="flex font text-light-black" style="justify-content: space-between;">
				<block v-for="item in themes" :key="item.id">
					<view class="flex-1">
						<view :class="item.id" class="border rounded mx-1" style="height: 80rpx;" 
						@click="changeThemeIndex(item.id)"></view>
						<view class="text-center">{{item.name}}</view>
					</view>
				</block>
			</view>
		</view>
		<!-- 更多设置结束 -->
		
		<!-- 文本开始 -->
		<swiper :class="curTheme" :style="{height: `${calHeight}rpx`, fontSize: `${myFontSize}px`, lineHeight: `${myLineHeight}px`}" class="px-2" @click="changeSetStatus" @change="swiperChangeChapter" :current="chapterIndex">
			<swiper-item v-for="item in LoadedChapters" :key="item.id">
				<scroll-view scroll-y :style="{height: `${calHeight}rpx`}">
					<uni-load-more status="loading" v-if="!item.text"></uni-load-more>
					<rich-text :nodes="item.text"></rich-text>
				</scroll-view>
			</swiper-item>
		</swiper>
		
		<!-- 文本结束 -->
	</view>
</template>

<script>
	let testContent;
	import $N from '@/common/unit';
	import htmlParser from '@/common/html-parser';
	import uniLoadMore from '@/components/uni-ui/uni-load-more/uni-load-more.vue';
	import uniStatusBar from '@/components/uni-ui/uni-status-bar/uni-status-bar.vue';
	import uniDrawer from '@/components/uni-ui/uni-drawer/uni-drawer.vue';
	export default {
		components: {
			uniStatusBar,
			uniLoadMore,
			uniDrawer
		},
		data() {
			return {
				calHeight: 0,
				// 各种状态
				setStatus: false,
				catalogStatus: false,
				typeFaceStatus: false,
				moreStatus: false,
				
				chapterIndex: 0, // 当前章节标识
				novalName: '请稍后', // 小说名称
				
				// 小说目录
				chapterCatalog: [{
					id: 1,
					title: ''
				}], 
				// 已经加载的章节
				LoadedChapters: [{
					id: 1,
					title: ''
				}], 
				myFontSize: uni.getStorageSync('myFontSize') || 20,
				myLineHeight: uni.getStorageSync('myLineHeight') || 45,
				brightNess: 0, // 亮度值
				themes:[
					{
						id: 'blueTheme',
						name: '天蓝'
					},
					{
						id: 'eyeHelpTheme',
						name: '护眼'
					},
					{
						id: 'lightGretTheme',
						name: '淡灰'
					},
					{
						id: 'morningTheme',
						name: '早晨'
					},
					{
						id: 'nightTheme',
						name: '夜间'
					}
				],
				themeIndex: uni.getStorageSync('themeIndex'),
			}
		},
		computed: {
			// 当前章节标题
			curChapterTitle() {
				return this.chapterCatalog[this.chapterIndex].title || '加载中'
			},
			// 当前主题
			curTheme(){
				let index = this.themeIndex;
				if(!uni.getStorageSync('themeIndex') && uni.getStorageSync('themeIndex') !== 0){
					index = 3
				}
				return this.themes[index].id
			}
		},
		methods: {
			// 退出
			quit(){
				uni.navigateBack({
					delta: 1
				});
			},
			// 改变设置部分状态
			changeSetStatus(){
				if (!this.setStatus) {
					if (this.typeFaceStatus || this.moreStatus) {
						this.changeTypeFaceStatus(false);
						this.changeMoreStatus(false);
						return
					}
				}
				this.setStatus = !this.setStatus
			},
			// 改变目录部分状态
			changeCatalogStatus(Bol){
				this.catalogStatus = Bol;
				if (this.catalogStatus) this.changeSetStatus()
			},
			// 改变字体设置部分状态
			changeTypeFaceStatus(Bol){
				this.typeFaceStatus = Bol;
				if (this.typeFaceStatus) this.changeSetStatus()
			},
			// 改变更多设置部分状态
			changeMoreStatus(Bol){
				this.moreStatus = Bol;
				if (this.moreStatus) this.changeSetStatus()
			},
			
			// 初始化
			async init(id) {
				// 获取数据
				let {content} = await this.$http.get('/testContent');
				testContent = content;
				let res = await this.$http.get('/testSynopsis');
				this.novalName = res.name;
				this.chapterCatalog = res.chapterCatalog;
				
				
				let curIndex = this.chapterCatalog.findIndex(item => item.id === Number(id));
				this.changeIndex(curIndex);
				this.PreLoad(); // 预加载
				this.DeplyLoad() // 延迟加载
			},
			// 预加载
			PreLoad() {
				this.LoadedChapters.shift();
				this.chapterCatalog.forEach(item => this.LoadedChapters.push({
					id: item.id,
					title: item.title,
					text: ''
				}))
			},
			// 延迟加载
			DeplyLoad() {
				let index = this.chapterIndex,
					text = testContent[index].text;
				if (this.LoadedChapters[index].text === '') {
					setTimeout(() => this.LoadedChapters[index].text = htmlParser(text), 1000)
				}
			},
			
			
			// 改变当前章节标识
			changeIndex(index){
				this.chapterIndex = index
			},
			// 滑动改变章节标识
			swiperChangeChapter(e){
				this.changeIndex(e.detail.current)
				this.DeplyLoad()
			},
			// 点击目录章节跳转对应章节
			toPointChapter(id){
				let curIndex = this.chapterCatalog.findIndex(item => item.id === id);
				if (this.chapterIndex === curIndex) return;
				this.changeIndex(curIndex);
				this.DeplyLoad()
			},
			// 改变字体大小
			changeFontSize(e){
				this.myFontSize = e.detail.value;
				uni.setStorageSync('myFontSize', this.myFontSize)
			},
			// 改变行文间距
			changeLineHeight(e){
				this.myLineHeight = e.detail.value;
				uni.setStorageSync('myLineHeight', this.myLineHeight)
			},
			// 获取亮度
			getBrightNess(){
				uni.getScreenBrightness({
					success: e => this.brightNess = Math.floor(e.value) / 16 * 100
				})
			},
			// 设置亮度
			setBrightNess(e){
				let newVal = e.detail.value;
				this.brightNess = newVal;
				uni.setScreenBrightness({
					value: newVal * 16 / 100
				})
			},
			// 更改主题标识
			changeThemeIndex(id){
				let curIndex = this.themes.findIndex(theme => theme.id === id);
				this.themeIndex = curIndex
				
				// 写入主题缓存
				uni.setStorageSync('themeIndex',curIndex)
			},
			// 切换夜间模式
			isNight(){
				if (this.themeIndex !== 4) {
					this.changeThemeIndex('nightTheme')
				} else {
					this.changeThemeIndex('morningTheme')
				}
			}
		},
		onLoad(e) {
			uni.getSystemInfo({
				success:res=>{
					// px
					this.calHeight = $N.Torpx(res.windowHeight - res.statusBarHeight);
				}
			})
			// console.log(e)
			let id = e.chapterID;
			this.init(id)
		},
		created() {
			// #ifndef H5
			// 获取屏幕亮度
			this.getBrightNess()
			// #endif
		},
	}
</script>

<style>
	.curChapter{
		background-color: #8395A7;
		color: #FFFFFF;
	}
</style>
