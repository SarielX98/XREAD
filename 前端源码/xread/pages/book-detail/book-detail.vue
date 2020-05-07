<template>
	<view>
		<view class="flex align-center py-2" style="height: 250rpx;background-color: #A8B0C3;">
			<image :src="dataObj.imgurl" style="width: 150rpx;height: 240rpx;" lazy-load class="flex-1 mx-2 rounded"></image>
			<view class="flex-2 mx-2">
				<view class="font-lg">{{dataObj.name}}</view>
				<view class="font mt-1">作者：{{dataObj.author}}</view>
				<view class="flex align-center mt-2">
					<button class="flex-1 mx-1">分享</button>
					<button class="flex-1 mx-1" @click="collectThisBook"> {{collectStatus ? '已收藏' : '收藏'}} </button>
				</view>
			</view>
		</view>
	
		<view class="shadow">
			<tab-top :tabArr="['详情','目录']" :tabIndex="tabIndex" @changeTab="changeTab"></tab-top>
			
			<!-- 具体内容 -->
			<swiper :style="'height:'+scrollH+'px;'" :current="tabIndex" @change="swiperChangeIndex">
				
				<!-- 详情 -->
				<swiper-item>
					<scroll-view scroll-y :style="'height:'+scrollH+'px;'">
						<view>
							<view class="py-2 font-md flex justify-center text-light-black">——简介——</view>
							<view class="px-2 font-md" style="line-height: 80rpx;">{{dataObj.synopsis}}</view>
						</view>
					</scroll-view>
				</swiper-item>
				
				<!-- 目录 -->
				<swiper-item>
					<scroll-view scroll-y :style="'height:'+scrollH+'px;'">
						<view v-if="chaptersObj">
							<block v-for="(item,index) in chaptersObj" :key="index">
								<view class="p-2 text-ellipsis border-bottom" hover-class="bg-light" @click="toReadingPage(index)">{{item.title}}</view>
							</block>
						</view>
					</scroll-view>
				</swiper-item>
			</swiper>
		</view>
	</view>
</template>

<script>
	let currentBookID,
		collectedBooks = getApp().globalData.collectedBooks;
	import tabTop from '@/components/common/tab-top.vue';
	export default {
		components: {
			tabTop
		},
		data() {
			return {
				tabIndex: 0,
				scrollH: 600,
				collectStatus: false,
				currentBook:[{
					name:'',
					author:'',
					synopsis:'',
					imgurl:''
				}],
				chapterCatalog: [],
				id:'',// 小说id
				dataObj: [],
				chaptersObj:[]
			}
		},
		async onLoad(e) {
			uni.getSystemInfo({
				success:res=>{
					// px
					this.scrollH = res.windowHeight - uni.upx2px(375);
				}
			});
			currentBookID = e.bookID;
			this.id = e.bookID
			let {chapterCatalog} = await this.$http.get('/testSynopsis');
			this.chapterCatalog = chapterCatalog;
			let currentBook = collectedBooks.find(item => item.id === currentBookID)
			if (currentBook) {
				this.collectStatus = true;
				return this.currentBook = currentBook
			} 
			
			let {list} = await this.$http.get('/book');
			
			this.currentBook = list.find(item => item.id === currentBookID);
			
			this.getData()
			this.getChapters()
			
		},
		methods: {
			// 获取小说信息
			getData(){
				this.$B.get('/book/'+this.id).then(res=>{
					res.cover = decodeURIComponent(res.cover).replace('/agent/', '');
					let book = {
						id:res._id,
						name:res.title,
						author:res.author,
						synopsis:res.longIntro,
						imgurl:res.cover
					}
					this.dataObj = book
				})
			},
			// 获取小说章节
			getChapters(){
				this.$B.get('/mix-atoc/'+this.id+'?view=chapters').then(res=>{
					this.chaptersObj = res.mixToc.chapters
				})
			},
			changeTab(tabIndex){
				this.tabIndex = tabIndex;
			},
			swiperChangeIndex(e){
				this.tabIndex = e.detail.current
			},
			toReadingPage(id){
				uni.navigateTo({
					url: `/pages/book-reading/book-reading?chapterID=${id}`,
				});
			},
			collectThisBook(){
				this.collectStatus = !this.collectStatus;
				uni.showToast({
					icon: 'none',
					title: this.collectStatus ? '已收藏' : '取消收藏成功'
				})
				let index = collectedBooks.findIndex(item => item.id === this.id);
				
				this.collectStatus ? collectedBooks.push(this.dataObj) : collectedBooks.splice(index, 1);
			}
		}
	}
</script>

<style>

</style>
