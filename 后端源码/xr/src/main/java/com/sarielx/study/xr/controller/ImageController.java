package com.sarielx.study.xr.controller;

import com.sarielx.study.xr.jwtToken.CheckToken;
import com.sarielx.study.xr.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @CheckToken
    @RequestMapping(value = "/uploadimg",method = RequestMethod.POST)
    private Map<String,Object> uploadImage (@RequestParam("imglist")MultipartFile[] files){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("data",imageService.imageUpload(files));
        return modelMap;
    }
}
