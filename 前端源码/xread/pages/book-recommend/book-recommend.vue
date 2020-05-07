<template>
	<view>
		<book-list :bookList="loadedBookList"></book-list>
	</view>
</template>

<script>
	import bookList from '@/components/common/book-list.vue';
	export default {
		components: {
			bookList
		},
		data() {
			return {
				loadedBookList: [],
			}
		},
		onLoad() {
			this.initLoadMore()
		},
		methods: {
			// 初始化加载
			async initLoadMore() {
				let obj = []
				await this.$B.get('/ranking/5a6844aafc84c2b8efaa6b6e').then(res=>{
					obj = res.ranking.books.map(v=>{
						return{
							id:v._id,
							name:v.title,
							imgurl:v.cover,
							synopsis:v.shortIntro,
						}
					});
					obj.forEach((item)=>{
						item.imgurl = decodeURIComponent(item.imgurl).replace('/agent/', '');
					});
					this.loadedBookList = obj
				})
			},
		}
	}
</script>

<style>

</style>
