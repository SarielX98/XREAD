<template>
	<view class="flex align-center border" style="height: 80rpx;justify-content: space-around;">
		<block v-for="(item,index) in tabArr" :key="index">
			<view class="flex flex-column align-center justify-between" style="height: 80rpx;" @click="changeTab(index)">
				<view class="pt-1 animated faster font-md" :class="tabIndex === index ? 'font-weight-bold pulse' : 'text-light-black'">{{item}}</view>
				<view class="rounded bg-pink animated zoomIn faster" style="width: 80rpx;height: 8rpx;" v-if="tabIndex === index"></view>
			</view>
		</block>
	</view>
</template>

<script>
	export default {
		
		props: {
			tabIndex: {
				type: Number,
				default: 0
			},
			tabArr: {
				type: Array,
				default: () => []
			},
		},
		methods: {
			changeTab(index) {
				this.$emit('changeTab', index)
			}
		},
	}
</script>

<style>
</style>
