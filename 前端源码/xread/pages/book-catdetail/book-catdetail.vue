<template>
	<view>
		<book-list :bookList="books"></book-list>
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
				nomore: false,
				page: 1,
				books: [],
				gender:  '',//男生:male 女生:female 出版:press
				major: '', //主分类
			}
		},
		onLoad(e) {
			this.gender = e.gender||'male';
			this.major = e.major||'玄幻';
			uni.setNavigationBarTitle({
				title: this.major
			});
			this.getData();
		},
		methods: {
			initData(){
				this.nomore = false;
				this.page = 1;
				this.books = [];
			},
			getData(){
				this.$B.get('/book/by-categories', {
					gender: this.gender,
					type: 'hot',
					major: this.major,
					minor: '',
					start: this.page,
					limit: 10,
				}).then(res=>{
					if(res.ok){
						let books = res.books.map(v=>{
							return{
								id:v._id,
								name:v.title,
								imgurl:v.cover,
								synopsis:v.shortIntro,
							}
						});
						if(books && books.length>0){
							books.forEach((item)=>{
								item.imgurl = decodeURIComponent(item.imgurl).replace('/agent/', '');
							});
							this.books = [...this.books, ...books];
						}else{
							this.nomore = true;
						};
						this.page++;
					}else{
						uni.showToast({
							title: '请求出错',
							icon: 'none'
						});
					}
				})
			}
		},
		onPullDownRefresh() {
			uni.stopPullDownRefresh();
			uni.hideNavigationBarLoading();
			this.initData();
			this.getData();
		},
		onReachBottom() {
			this.getData();
		}
	}
</script>

<style>

</style>
