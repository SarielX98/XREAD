package com.sarielx.study.xr.service;

import com.sarielx.study.xr.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ImageService {

    // 上传多图
    List<Image> imageUpload(MultipartFile[] files);

    // 上传用户头像
    String uploadUserpic(MultipartFile userpic);
}
