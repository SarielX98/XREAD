package com.sarielx.study.xr.service.impl;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.sarielx.study.xr.dao.ImageMapper;
import com.sarielx.study.xr.entity.Image;
import com.sarielx.study.xr.handler.MyException;
import com.sarielx.study.xr.service.ImageService;
import com.sarielx.study.xr.util.FileUtil;
import com.sarielx.study.xr.util.QiniuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageMapper imageMapper;

    // 项目根路径下的目录  -- SpringBoot static 目录相当于是根路径下（SpringBoot 默认）
    public final static String UPLOAD_PATH_PREFIX = "static/uploadImg/";

    // 上传图片
    @Transactional
    @Override
    public List<Image> imageUpload(MultipartFile[] files) {
        // 从token中获取用户id
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        String userId = JWT.decode(token).getAudience().get(0);
        String result = null;
        List<Image> imageList = new ArrayList<>();
//        Calendar calendar = Calendar.getInstance();
        for(int i = 0 ; i < files.length ; i++){
            String fileName = files[i].getOriginalFilename();
            System.out.println("图片的文件名为：" + fileName);
            // 创建一个临时目录文件
            String tempFiles = "temp/"+fileName;
            File dest = new File(tempFiles);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }

            BufferedOutputStream out = null;
            QiniuUtil qn = new QiniuUtil();

            try{
                out = new BufferedOutputStream(new FileOutputStream(dest));
                out.write(files[i].getBytes());
                result = qn.uploadQiniu(dest,fileName);
                Image image = new Image();
                image.setUserId(Integer.valueOf(userId));
                image.setUrl(result);
                image.setCreateTime((int) (System.currentTimeMillis() / 1000));
                imageMapper.insert(image);
                Image newImage = image;
                newImage.setId(image.getId());
                imageList.add(i,newImage);
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch (IOException e1) {
                e1.getMessage();
            }finally {
                try {
                    if (null != out) {
                        out.flush();
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (dest.getParentFile().exists()) {
                    dest.delete();
                }
            }
        }
        return imageList;
    }
    // 上传用户头像
    @Transactional
    @Override
    public String uploadUserpic(MultipartFile userpic) {
        // 从token中获取用户id
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        String userId = JWT.decode(token).getAudience().get(0);
        String result = null;
        String fileName = userpic.getOriginalFilename();
        System.out.println("图片的文件名为：" + fileName);
        // 创建一个临时目录文件
        String tempFiles = "temp/"+fileName;
        File dest = new File(tempFiles);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        BufferedOutputStream out = null;
        QiniuUtil qn = new QiniuUtil();

        try{
            out = new BufferedOutputStream(new FileOutputStream(dest));
            out.write(userpic.getBytes());
            result = qn.uploadQiniu(dest,fileName);
            Image image = new Image();
            image.setUserId(Integer.valueOf(userId));
            image.setUrl(result);
            image.setCreateTime((int) (System.currentTimeMillis() / 1000));
            imageMapper.insert(image);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e1) {
            e1.getMessage();
        }finally {
            try {
                if (null != out) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (dest.getParentFile().exists()) {
                dest.delete();
            }
        }
        return result;
    }
//    // 上传图片
//    @Transactional
//    @Override
//    public boolean imageUpload(MultipartFile[] files) {
//        // 从token中获取用户id
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        // 从 http 请求头中取出 token
//        String token = request.getHeader("token");
//        String userId = JWT.decode(token).getAudience().get(0);
//        DateFormat df = new SimpleDateFormat("yyyy/MM/dd/");
//        String format = df.format(new Date());
//        String realPath = new String("src/main/resources/" + UPLOAD_PATH_PREFIX);
////        Calendar calendar = Calendar.getInstance();
//        for(int i = 0 ; i < files.length ; i++){
//            String fileName = files[i].getOriginalFilename();
//            System.out.println("图片的文件名为：" + fileName);
//            String filePath = realPath + format;
//            System.out.println("图片的绝对路径为：" + filePath);
//            Image image = new Image();
//            image.setUserId(Integer.valueOf(userId));
//            image.setUrl(filePath+fileName);
//            image.setCreateTime((int) (System.currentTimeMillis() / 1000));
//            try{
//                FileUtil.fileupload(files[i].getBytes(),filePath,fileName);
//                imageMapper.insert(image);
//            }catch (IOException e) {
//                e.printStackTrace();
//                throw new MyException("301","上传失败");
//            }
//        }
//        return true;
//    }

//    // 上传用户头像
//    @Transactional
//    @Override
//    public String uploadUserpic(MultipartFile userpic) {
//        // 从token中获取用户id
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        // 从 http 请求头中取出 token
//        String token = request.getHeader("token");
//        String userId = JWT.decode(token).getAudience().get(0);
//        DateFormat df = new SimpleDateFormat("yyyy/MM/dd/");
//        String format = df.format(new Date());
//        String realPath = new String("src/main/resources/" + UPLOAD_PATH_PREFIX);
//        String fileName = userpic.getOriginalFilename();
//        System.out.println("图片的文件名为：" + fileName);
//        String filePath = realPath + format;
//        System.out.println("图片的绝对路径为：" + filePath);
//        Image image = new Image();
//        image.setUserId(Integer.valueOf(userId));
//        image.setUrl(filePath+fileName);
//        image.setCreateTime((int) (System.currentTimeMillis() / 1000));
//        try{
//            FileUtil.fileupload(userpic.getBytes(),filePath,fileName);
//            imageMapper.insert(image);
//        }catch (IOException e) {
//            e.printStackTrace();
//            throw new MyException("301","上传失败");
//        }
//        String imgUrl = image.getUrl();
//        return imgUrl;
//    }



}
