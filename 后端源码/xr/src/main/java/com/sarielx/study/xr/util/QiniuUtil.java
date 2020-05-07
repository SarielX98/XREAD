package com.sarielx.study.xr.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.log4j.Logger;

import java.io.File;

public class QiniuUtil {

    //设置好账号的ACCESS_KEY和SECRET_KEY
    final String ACCESS_KEY = "t-bNWxNuQT-LKxOylROFQ0x2lzKgRq5Efm8yRKgb";
    final String SECRET_KEY = "0dNIv1-dTz-e7S7otUbAxO2Hkg06ZBva5dOP9yC2";
    //要上传的空间
    final String BUCKET_NAME = "xread-img";

    /**
     * 七牛云上传图片
     * @param localFilePath
     * @return
     */
    public String uploadQiniu (File localFilePath, String fileName){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = ACCESS_KEY;
        String secretKey = SECRET_KEY;
        String bucket = BUCKET_NAME;
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "/images/"+fileName+"tId"+System.currentTimeMillis();
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        String result = null;
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

            System.out.println("{七牛图片上传key: "+ putRet.key+",七牛图片上传hash: "+ putRet.hash+"}");

            result = "http://q9jczboph.bkt.clouddn.com/"+putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
            result = null;
        }
        return result;
    }
}
