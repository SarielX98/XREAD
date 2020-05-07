export default {
	// 监听网络
	onNetWork(){
		let func = (res) => {
				if (res.networkType === 'none') {
					uni.showToast({
						title:'当前处于断网状态，请先连接',
						icon:'none'
					});
				}
		    }
		uni.getNetworkType({
		    success:func
		});
		uni.onNetworkStatusChange(func);
	},
	// 热更新
	update(){
		// #ifdef APP-PLUS  
		plus.runtime.getProperty(plus.runtime.appid, function(widgetInfo) {  
		    uni.request({  
		        url: 'http://www.example.com/update/',  
		        data: {  
		            version: widgetInfo.version,  
		            name: widgetInfo.name  
		        },  
		        success: (result) => {  
		            var data = result.data;  
		            if (data.update && data.wgtUrl) {  
		                uni.downloadFile({  
		                    url: data.wgtUrl,  
		                    success: (downloadResult) => {  
		                        if (downloadResult.statusCode === 200) {  
		                            plus.runtime.install(downloadResult.tempFilePath, {  
		                                force: false  
		                            }, function() {  
		                                console.log('install success...');  
		                                plus.runtime.restart();  
		                            }, function(e) {  
		                                console.error('install fail...');  
		                            });  
		                        }  
		                    }  
		                });  
		            }  
		        }  
		    });  
		});  
		// #endif
	},
	// 转换帖子列表数据
	formatCommonList(v){
		let isFollow = (v.user.fens !== null)
		let support = ''
		if(v.support !== null){
			support = v.support.type === 0 ? 'support' : 'unsupport'
		}
		return {
			id:v.id,
			user_id:v.userId,
			username: v.user.username,
			userpic: v.user.userpic,
			newstime: v.createTime,
			isFollow: isFollow,
			title: v.title,
			titlepic: v.titlepic,
			support: {
				type: support,
				support_count: v.dingnum,
				unsupport_count: v.cainum
			},
			comment_count:v.commentnum,
			share_count:v.sharenum
		}
	},
	// 数组置顶
	__toFirst(arr,index){
		if (index != 0){
			arr.unshift(arr.splice(index,1)[0]);
		}
		return arr;
	}
}