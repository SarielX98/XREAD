<template>
	<view>
		<!-- 自定义导航 -->
		<uni-nav-bar left-icon="back" statusBar :border="false" @clickLeft="onClickLeft">
			<view class="flex align-center justify-center w-100"
			@click="changeIsopen">
				{{isopenText}}<text class="iconfont iconset"></text>
			</view>
		</uni-nav-bar>
		<!-- 文本域 -->
		<textarea v-model="content" value="" placeholder="说一句话吧" class="uni-textarea"/>
		<!-- 选中的话题 -->
		<view class="font-md px-2 py-1 flex">
			<view class="border px-3 py flex align-center justify-center" style="border-radius: 50rpx;">
				<text class="iconfont iconhuati mr-1"></text>
				{{topic.title ? "所属话题：" + topic.title : "请选择话题"}}
			</view>
		</view>
		<!-- 多图上传 -->
		<upload-image :show="show" ref="uploadImage" :list="imageList" @change="changeImage"></upload-image>
		<!-- 底部操作条 -->
		<view class="fixed-bottom bg-white flex align-center" style="85rpx">
			<!-- <view class="iconfont iconall footer-btn animated" 
			hover-class="jello"></view> -->
			<view class="iconfont iconhuati footer-btn animated" 
			hover-class="jello" @click="chooseTopic"></view>
			<view class="iconfont iconpic footer-btn animated" 
			hover-class="jello" @click="iconClickEvent('uploadImage')"></view>
			
			<view class="bg-pink text-white ml-auto flex align-center justify-center rounded mr-2 animated" 
			style="width: 140rpx;height: 60rpx;" hover-class="jello"
			@click="submit">发送</view>
		</view>
		
	</view>
</template>

<script>
	const isOpenArray = ['仅自己可见','所有人可见'];
	import uniNavBar from '@/components/uni-ui/uni-nav-bar/uni-nav-bar.vue';
	import uploadImage from '@/components/common/upload-image.vue';
	export default {
		components:{
			uniNavBar,
			uploadImage
		},
		data() {
			return {
				content:"",
				imageList:[],
				// 是否已经弹出提示框
				showBack:false,
				isopen:1,
				topic:{
					id:0,
					title:""
				}
			}
		},
		computed: {
			show() {
				return this.imageList.length > 0
			},
			isopenText(){
				return isOpenArray[this.isopen]
			},
			imgListIds(){
				return this.imageList.map(item=>{
					return{
						id:item.id
					}
				})
			}
		},
		// 页面加载时
		onLoad() {
			uni.getStorage({
				key:'add-input',
				success:(res)=>{
					if (res.data) {
						let result = JSON.parse(res.data);
						this.content = result.content;
						this.imageList = result.imageList;
					}
				}
			})
			// 监听选择话题事件
			uni.$on('chooseTopic',(e)=>{
				this.topic.id = e.id
				this.topic.title = e.title
			})
		},
		beforeDestroy() {
			uni.$off('chooseTopic',(e)=>{})
		},
		// 监听返回
		onBackPress() {
			if ((this.content !== '' || this.imageList.length > 0) && !this.showBack) {
				uni.showModal({
					content: '是否要保存为草稿？',
					showCancel: true,
					cancelText: '不保存',
					confirmText: '保存',
					success: res => {
						// 点击确认
						if (res.confirm) {
							this.store();
						} else { // 点击取消，清除缓存
							uni.removeStorage({
								key: "add-input"
							})
						}
						// 手动执行返回
						uni.navigateBack({ delta: 1 });
					},
				});
				this.showBack = true
				return true
			}
		},
		methods: {
			// 发布
			submit(){
				if(this.topic.id === 0){
					return uni.showToast({
						title: '请选择话题',
						icon:'none'
					});
				}
				uni.showLoading({
					title: '发布中...',
					mask: false
				});
				this.$H.post('/post/create',{
					imageList:this.imgListIds,
					content:this.content,
					isopen:this.isopen,
					postClassId:this.topic.id
				},{
					header:{
						"content-type":"application/json"
					},
					token:true
				}).then(res=>{
					uni.hideLoading()
					uni.showToast({
						title: '发布成功',
						icon: 'none'
					});
					this.showBack = true
					uni.navigateBack({
						delta: 1
					});
				}).catch(err=>{
					uni.hideLoading()
				})
			},
			// 选择话题
			chooseTopic(){
				uni.navigateTo({
					url: '../topic-nav/topic-nav?choose=true',
				});
			},
			// 切换可见性
			changeIsopen(){
				uni.showActionSheet({
				    itemList: isOpenArray,
				    success: (res)=> {
				        this.isopen = res.tapIndex
				    }
				});
			},
			// 底部图标点击事件
			iconClickEvent(e){
				switch (e){
					case 'uploadImage':
					this.$refs.uploadImage.chooseImage()
						break;
				}
			},
			// 返回上一页
			onClickLeft(){
				uni.navigateBack({ delta: 1 });
			},
			// 选中图片
			changeImage(e){
				this.imageList = e;
			},
			// 保存操作
			store(){
				// 保存为本地存储
				let obj = {
						content:this.content,
						imageList:this.imageList
					}
				uni.setStorage({
					key:'add-input',
					data:JSON.stringify(obj)
				})
			}
		}
	}
</script>

<style>
.footer-btn{
	width: 85rpx;
	height: 85rpx;
	display: flex;
	align-content: center;
	justify-content: center;
	font-size: 50rpx;
}
</style>
