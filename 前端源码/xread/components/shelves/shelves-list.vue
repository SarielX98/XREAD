<template>
	
	<view class="flex align-center p-1 border-bottom" style="height: 250rpx;">
		<image :src="bookImgUrl" mode="aspectFill" class="rounded mx-2" style="width: 150rpx;height: 200rpx;"></image>
		<view class="flex-1">
			<view class="flex align-center justify-between">
				<view class="py-1 font-md font-weight-bold">{{bookName}}</view>
				<view class="iconfont icongengduo1 align-start" @click="init"></view>
			</view>
			<view class="font text-light-black topic_cont_text">{{bookDesc}}</view>
		</view>
		
	</view>
	
</template>

<script>
	export default {
		props: {
			bookID:{
				type: String,
				default: ''
			},
			bookImgUrl: {
				type: String,
				default: ''
			},
			bookName: {
				type: String,
				default: ''
			},
			bookDesc: {
				type: String,
				default: ''
			}
		},
		methods: {
			init(){
				this.showCancelCol(true);
				this.throwID(this.bookID)
			},
			throwID(id){
				this.$emit('getID', id)
			},
			showCancelCol(bol){
				this.$emit('showCancelCol', bol)
			}
		},
	}
</script>

<style>
.topic_cont_text{
	max-width: 530rpx;
	max-height: 130rpx;
	overflow: hidden;
	word-break: break-all;  /* break-all(允许在单词内换行。) */
	text-overflow: ellipsis;  /* 超出部分省略号 */
	display: -webkit-box; /** 对象作为伸缩盒子模型显示 **/
	-webkit-box-orient: vertical; /** 设置或检索伸缩盒对象的子元素的排列方式 **/
	-webkit-line-clamp: 2; /** 显示的行数 **/
	}
</style>
