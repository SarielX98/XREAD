<template>
	<view>
		<block v-for="(item, index) in bookList" :key="index">
			<view class="flex align-center p-1 border-bottom" hover-class="bg-light" 
			style="height: 320rpx;" @click="toBookDetail(item.id)">
				<image :src="item.imgurl" mode="aspectFill" class="rounded mx-2" 
				style="width: 250rpx;height: 250rpx;"></image>
				<view class="flex-1" style="height: 100%;">
					<view class="py-2 font-md font-weight-bold"> {{item.name}} </view>
					<view class="font text-light-black topic_cont_text"> {{item.synopsis}} </view>
				</view>
			</view>
		</block>
	</view>
</template>

<script>
	export default {
		props: {
			bookList: {
				type: Array,
				default: () => []
			}
		},
		methods: {
			toBookDetail(id) {
				uni.navigateTo({
					url: `/pages/book-detail/book-detail?bookID=${id}`,
				});
			}
		},
	}
</script>

<style>
	.topic_cont_text {
		colof: #999;
		max-height: 200rpx;
		overflow: hidden;
		word-break: break-all;
		/* break-all(允许在单词内换行。) */
		text-overflow: ellipsis;
		/* 超出部分省略号 */
		display: -webkit-box;
		/** 对象作为伸缩盒子模型显示 **/
		-webkit-box-orient: vertical;
		/** 设置或检索伸缩盒对象的子元素的排列方式 **/
		-webkit-line-clamp: 4;
		/** 显示的行数 **/

	}
</style>
