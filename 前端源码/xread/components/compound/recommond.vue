<template>
	<view>
		<list-header>
			<template v-slot:title> 猜你喜欢 </template>
			<template v-slot:tips> 更多推荐 </template>
		</list-header>
		<view class="flex align-center">
			<block v-for="(item, index) in Rebooks" :key="index">
				<view class="flex flex-column align-center flex-1 w-100 mx-1" @click="toBookDetail(item.id)">
					<image :src="item.imgurl" style="width: 220rpx;height: 300rpx;"></image>
					<text class="font my-1 text-light-black text-ellipsis" style="width: 220rpx;">{{item.name}}</text>
				</view>
			</block>
		</view>
	</view>
</template>

<script>
	import listHeader from '../common/list-header.vue';
	export default {
		components: {
			listHeader
		},
		props: {
			Rebooks: {
				type: Array,
				default: () => []
			},
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
</style>
