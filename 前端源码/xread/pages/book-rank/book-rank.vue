<template>
	<view>
		<book-list :bookList="loadedBookList"></book-list>
		<!-- <uni-load-more :status="loadMoreStatus"></uni-load-more> -->
	</view>
</template>

<script>
	import bookList from '@/components/common/book-list.vue';
	import uniLoadMore from '@/components/uni-ui/uni-load-more/uni-load-more.vue';
	export default {
		data() {
			return {
				loadedBookList: [],
				loadMoreStatus: 'loading'
			}
		},
		components: {
			bookList,
			uniLoadMore
		},
		methods: {
			// 初始化加载
			async initLoadMore() {
				let obj = []
				await this.$B.get('/ranking/54d42d92321052167dfb75e3').then(res=>{
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
				// let obj = await this.$http.get('/readList');
				// setTimeout(() => {
				// 	if (obj.length) {
				// 		this.loadedBookList = [...obj];
				// 		this.loadMoreStatus = 'more'
				// 	}
				// }, 2000)
			},
			// 触底加载
			// async loadMore() {
			// 	let obj = []
			// 	await this.$B.get('/ranking/54d43437d47d13ff21cad58b').then(res=>{
			// 		obj = res.ranking.books.map(v=>{
			// 			return{
			// 				id:v._id,
			// 				name:v.title,
			// 				imgurl:v.cover,
			// 				synopsis:v.shortIntro,
			// 			}
			// 		});
			// 		obj.forEach((item)=>{
			// 			item.imgurl = decodeURIComponent(item.imgurl).replace('/agent/', '');
			// 		});
			// 	})
			// 	// let obj = await this.$http.get('/readList');
			// 	switch (obj.isLoadMore) {
			// 		case '正在加载中':
			// 			return
			// 			break;
			// 		case '没有更多了':
			// 			this.loadMoreStatus = 'noMore'
			// 			break;
			// 		case '上拉加载显示更多':
			// 			{ 	
			// 				this.loadMoreStatus = 'loading';
			// 				setTimeout(()=>{
			// 					this.loadedBookList = [...this.loadedBookList, ...obj.books];
			// 					this.loadMoreStatus = 'more'
			// 				},1500)
			// 			}
			// 			break;
			// 		default:
			// 			break;
			// 	}
			// }
		},
		onLoad() {
			this.initLoadMore()
		},
		// onReachBottom() {
		// 	this.loadMore()
		// }
	}
</script>

<style>

</style>
