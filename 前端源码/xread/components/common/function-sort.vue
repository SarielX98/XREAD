<template>
	<view class="flex align-center mx-3">
		<view class="flex flex-1 flex-column align-center justify-center"
		v-for="(item,index) in iconList" :key="index" @click="switchToPage(index)">
			<image :src="item.src" style="width: 80rpx;height: 80rpx;"></image>
			<text class="font text-muted mt-2">{{item.name}}</text>
		</view>
	</view>
</template>

<script>
	let pageList = {
		[0]:"/pages/book-category/book-category",
		[1]:"/pages/book-rank/book-rank",
		[2]:"/pages/book-recommend/book-recommend",
		[3]:"/pages/book-booklist/book-booklist",
	};
	export default {
		props: {
			iconList: {
				type: Array,
				default: () => []
			},
		},
		methods: {
			switchToPage(pageIndex) {
				uni.navigateTo({
					url: pageList[pageIndex],
				});
			}
		},
	}
</script>

<style>
</style>
