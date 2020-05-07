<template>
	<view>
		
		<!-- 轮播图 -->
		<rotation-chat :imgArr="swiperImages"></rotation-chat>
		<!-- 功能分类 -->
		<function-sort :iconList="iconList"></function-sort>
		<!-- 推荐 -->
		<recommond :Rebooks="Rebooks"></recommond>
		
		<block v-for="(item, index) in bookResources" :key="index">
			<list-header> <template v-slot:title> {{item.headerTitle}} </template> </list-header>
			<book-list :bookList="item.books"></book-list>
		</block>
		
	</view>
</template>

<script>
	import rotationChat from '@/components/common/rotation-chat.vue';
	import functionSort from '@/components/common/function-sort.vue';
	import listHeader from '@/components/common/list-header.vue';
	import recommond from '@/components/compound/recommond.vue';
	import bookList from '@/components/common/book-list.vue';
	export default {
		components: {
			rotationChat,
			functionSort,
			listHeader,
			recommond,
			bookList
		},
		data() {
			return{
				// 轮播图列表
				swiperImages:[{
					src:"http://static.zongheng.com/upload/recommend/a9/d8/a9d8abf9117fbdecb0be7d2179e8e144.jpeg"
				},{
					src:"http://static.zongheng.com/upload/recommend/ee/0a/ee0a69bff82345ea7c67895d87f0e0bb.jpeg"
				},{
					src:"http://static.zongheng.com/upload/recommend/96/0e/960e8aa48135c1b7936f689bc2f16f23.jpeg"
				},{
					src:"http://static.zongheng.com/upload/recommend/fc/8d/fc8d4980e6e03727913d5323dde8843c.jpeg"
				},{
					src:"http://static.zongheng.com/upload/recommend/73/00/73004faddfc3d6c7b01fc4ffeccdda75.jpeg"
				}],
				// 功能分类图标
				iconList:[{
					src:"/static/icon/menu-icon.png",
					name:"分类"
				},{
					src:"/static/icon/rank-icon.png",
					name:"排行"
				},{
					src:"/static/icon/recommend-icon.png",
					name:"推荐"
				},{
					src:"/static/icon/booklist-icon.png",
					name:"书单"
				}],
				Rebooks:[],
				bookResources:[{
					headerTitle:"玄幻",
					books:[]
				},{
					headerTitle:"都市",
					books:[]
				},{
					headerTitle:"现代言情",
					books:[]
				}],
			}
		},
		// 监听点击导航栏搜索框
		onNavigationBarSearchInputClicked() {
			uni.navigateTo({
				url: '../search/search?type=book',
			});
		},
		// 监听点击导航栏按钮
		onNavigationBarButtonTap() {
			uni.navigateTo({
				url: '../book-category/book-category',
			});
		},
		onLoad() {
			
			// this.$http.get('/app_index').then(res => {
				// this.swiperImages = res.swiperImages;
				// this.Rebooks = res.Rebooks;
				// this.bookResources = res.bookResources;
			// })
			
			this.getData()
			
		},
		methods: {
			getData(){
				this.$B.get('/ranking/5a6844aafc84c2b8efaa6b6e').then(res=>{
					let obj = res.ranking.books.map(v=>{
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
					this.Rebooks = obj.slice(0,3)
				})
				this.$B.get('/book/by-categories', {
					gender: 'male',
					type: 'hot',
					major: this.bookResources[0].headerTitle,
					minor: '',
					start: 0,
					limit: 2,
				}).then(res=>{
					let books = res.books.map(v=>{
						return{
							id:v._id,
							name:v.title,
							imgurl:v.cover,
							synopsis:v.shortIntro,
						}
					});
					books.forEach((item)=>{
						item.imgurl = decodeURIComponent(item.imgurl).replace('/agent/', '');
					});
					this.bookResources[0].books = books
				})
				this.$B.get('/book/by-categories', {
					gender: 'male',
					type: 'hot',
					major: this.bookResources[1].headerTitle,
					minor: '',
					start: 0,
					limit: 2,
				}).then(res=>{
					let books = res.books.map(v=>{
						return{
							id:v._id,
							name:v.title,
							imgurl:v.cover,
							synopsis:v.shortIntro,
						}
					});
					books.forEach((item)=>{
						item.imgurl = decodeURIComponent(item.imgurl).replace('/agent/', '');
					});
					this.bookResources[1].books = books
				})
				this.$B.get('/book/by-categories', {
					gender: 'female',
					type: 'hot',
					major: this.bookResources[2].headerTitle,
					minor: '',
					start: 0,
					limit: 2,
				}).then(res=>{
					let books = res.books.map(v=>{
						return{
							id:v._id,
							name:v.title,
							imgurl:v.cover,
							synopsis:v.shortIntro,
						}
					});
					books.forEach((item)=>{
						item.imgurl = decodeURIComponent(item.imgurl).replace('/agent/', '');
					});
					this.bookResources[2].books = books
				})
			}
		}
	}
</script>

<style>

</style>
