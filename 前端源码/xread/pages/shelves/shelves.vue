<template>
	<view>
		
		<!-- 书架列表 -->
		<scroll-view scroll-y :style="'height:'+scrollH+'px;'">
			<block v-for="item in collectedArr" :key="item.id">
				<shelves-list :bookID="item.id" :bookImgUrl="item.imgurl" :bookName="item.name" :bookDesc="item.synopsis" @getID="getID" @showCancelCol="showCancelCol"></shelves-list>
			</block>
			
		</scroll-view>
		
		<!-- 取消收藏界面 -->
		<uni-popup type="bottom" ref="popup" @change="changeStatus">
			<view class="bg-white">
				<view class="flex align-center" style="height: 100rpx;" @click="cancelCollect">
					<view class="iconfont iconfavorite text-danger px-3"></view>
					<text class="font">取消收藏</text>
				</view>
				<view class="bg-hover-light" style="height: 15rpx;"></view>
				<view class="text-center font-md" style="height: 110rpx;line-height: 110rpx;" @click="showCancelCol(false)">取消</view>
			</view>
		</uni-popup>
	</view>
</template>

<script>
	import {books} from '@/common/book';
	import shelvesList from '@/components/shelves/shelves-list.vue';
	import uniPopup from '@/components/uni-ui/uni-popup/uni-popup.vue';
	export default {
		components: {
			shelvesList,
			uniPopup
		},
		data() {
			return {
				// 列表高度
				scrollH: 600,
				showCancelColStatus: false,
				// 书架列表
				collectedArr: [],
				cancelColID: 0
			}
		},
		onLoad() {
			uni.getSystemInfo({
				success:res=>{
					// px
					this.scrollH = res.windowHeight;
				}
			})
			this.initData()
		},
		watch: {
			showCancelColStatus(newVal) {
				newVal ? uni.hideTabBar({animation: true}) : uni.showTabBar({animation: true})
			}
		},
		methods: {
			// 模拟请求
			initData(){
				this.collectedArr = getApp().globalData.collectedBooks
			},
			// 拿取消收藏目标的id
			getID(id){
				this.cancelColID = id
			},
			// 根据id找index
			IDtoIndex(id){
				return this.collectedArr.findIndex(item => item.id === id)
			},
			// 取消收藏
			cancelCollect(){
				let index = this.IDtoIndex(this.cancelColID);
				this.collectedArr.splice(index, 1)
				this.showCancelCol(false)
			},
			showCancelCol(bol){
				bol ? this.$refs.popup.open() : this.$refs.popup.close()
			},
			changeStatus(e){
				this.showCancelColStatus = e.show
			}
		}
	}
</script>

<style>

</style>
