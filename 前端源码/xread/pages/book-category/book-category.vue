<template>
	<view>
		<view class="cal" style="height: 20rpx;"></view>
		
		
		<view class="flex" >
			<!-- 左侧 -->
			<scroll-view scroll-y class="font text-light-black" style="width: 180rpx;padding: 0 20rpx;" :style="{height: `${calHeight}rpx`}">
				<block v-for="(item, index) in sortList" :key="index">
					<view class="animated faster" :class="leftIndex === index ? 'selected pulse font-weight-bold' : ''" style="height: 150rpx;line-height: 150rpx;" @click="leftToright(index)">{{item.name}}</view>
				</block>
			</scroll-view>
			
			<!-- 右侧 -->
			<scroll-view scroll-y :scroll-into-view="rightIndex" scroll-with-animation :style="{height: `${calHeight}rpx`}" @scroll="rightToleft">
				<block v-for="(item, index) in sortList" :key="index">
					<view class="sort" style="margin-bottom: 70rpx;" :id="`right${index}`">
						<!-- 顶部标题 -->
						<view class="flex align-center justify-center" style="height: 80rpx;line-height: 80rpx;">
							<text class="mr-1">{{item.name}}</text>
							<view class="iconfont iconmore" style="font-size: 40rpx;"></view>
						</view>
						<!-- 底部内容 -->
						<view class="flex flex-wrap rounded font">
							<block v-for="(item2, index2) in item.data" :key="index2">
								<view class="flex align-center justify-between mr-3 mb-3" style="width: 250rpx;background-color: #f0f3f8;"
								@click="openCatDetail(item2)">
									<view class="flex flex-column align-center">
										<view class="flex-1 pl-1">{{item2.name}}</view>
										<view class="flex-1 pl-1 font-sm text-light-muted">{{item2.bookCount | bookCount}}</view>
									</view>
									<image :src="item2.coverImg" mode="" style="width: 75rpx; height: 100rpx;"></image>
								</view>
							</block>
						</view>
					</view>
				</block>
				<!-- 占位符 -->
				<view :style="{height: `${calHeight - 800}rpx`}"></view>
			</scroll-view>
		</view>
	</view>
</template>

<script>
	import {sortResources as resources} from './sortResources';
	import $N from '@/common/unit';
	export default {
		data() {
			return {
				calHeight: 0,
				leftIndex: 0,
				rightIndex: `right${0}`,
				sortList:[]
			}
		},
		computed: {
			// 左侧数据
			leftListRes() {
				let res = resources.map(item => item.text)
				return res
			},
			// 右侧数据
			rightListRes() {
				return resources
			}
		},
		filters: {
			bookCount(value) {
				return value > 10000 ? (value / 10000).toFixed(2) + '万' : value;
			}
		},
		onLoad() {
			
			this.getAllSortList()
		},
		methods: {
			// 左联动右
			leftToright(i){
				this.rightIndex = `right${i}`
			},
			// 右联动左
			rightToleft(e){
				let curScrollTop = e.detail.scrollTop,
					standardVal = $N.Topx(1000),
					curIndex = Math.round(curScrollTop / standardVal);
				this.leftIndex = curIndex;
			},
			// 获取小说分类
			getAllSortList(){
				this.$B.get('/cats/lv2/statistics').then(data=>{
					if(data['ok']){
						let list = [];
						for(let key in data){
							if(key!='ok'){
								data[key].forEach((item)=>{
									item.coverImg = decodeURIComponent(item.bookCover[0]).replace('/agent/', '');
								});
								let tmpObj = {
									key: key,
									data: data[key]
								};
								let name;
								switch(key){
									case 'male':
										name = '男生';  break;
									case 'female':
										name = '女生';  break;
									case 'picture':
										name = '漫画';  break;
									case 'press':
										name = '出版';  break;
									default: 
										name = '其它';  break;
								}
								tmpObj.name = name;
								list.push(tmpObj);
							}
						}
						this.sortList = list;
					} else{
						uni.showToast({
							title: '请求出错',
							icon: 'none'
						});
					}
				})
			},
			// 点击进入分类详情
			openCatDetail(item){
				let gender = this.sortList[this.leftIndex].key;
				let major = item.name;
				uni.navigateTo({
					url: `/pages/book-catdetail/book-catdetail?gender=${gender}&major=${major}`
				});
			}
		},
		mounted() {
			// 动态获取高度
			$N.calSurplusHeight({
				pageID: this,
				pos: 'cal',
				isTabBarPage: true,
				success: val => this.calHeight = val 
			})
		}
	}
</script>

<style>
	.selected{
		color: #F7646D;
	}
</style>
