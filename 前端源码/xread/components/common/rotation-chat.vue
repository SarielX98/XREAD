<template>
	<swiper class="px-2 py-2" :indicator-dots="true" :autoplay="true"
	:interval="3000" :duration="1000" indicator-active-color="#FFFFFF" :circular="true">
		<swiper-item v-for="(item,index) in imgArr" :key="index">
			<image :src="item.src"
			style="height: 300rpx;" class="w-100 rounded"></image>
		</swiper-item>
	</swiper>
</template>

<script>
	export default {
		props: {
			imgArr: {
				type: Array,
				default:()=>[]
			},
		},
	}
</script>

<style>
</style>
