package com.sarielx.study.xr.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sarielx.study.xr.entity.Follow;
import com.sarielx.study.xr.entity.Post;
import com.sarielx.study.xr.jwtToken.CheckToken;
import com.sarielx.study.xr.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    FollowService followService;

    @CheckToken
    @RequestMapping(value = "/dofollow",method = RequestMethod.POST)
    private Map<String,Object> foFollow(@RequestParam Integer followId){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success",followService.doFollow(followId));
        return modelMap;
    }

    @CheckToken
    @RequestMapping(value = "/unfollow",method = RequestMethod.POST)
    private Map<String,Object> unFollow(@RequestParam Integer followId){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success",followService.unFollow(followId));
        return modelMap;
    }

    @CheckToken
    @RequestMapping(value = "/getfollows",method = RequestMethod.GET)
    private Map<String, Object> getFollows(@RequestParam("page") Integer pageNum){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        PageHelper.startPage(pageNum,10);
        List<Follow> followsList = followService.getFollows();
        PageInfo<Follow> pageInfo = new PageInfo<>(followsList);
        modelMap.put("followList",pageInfo);
        return modelMap;
    }

    @CheckToken
    @RequestMapping(value = "/getfens",method = RequestMethod.GET)
    private Map<String, Object> getFens(@RequestParam("page") Integer pageNum){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        PageHelper.startPage(pageNum,10);
        List<Follow> fensList = followService.getFens();
        PageInfo<Follow> pageInfo = new PageInfo<>(fensList);
        modelMap.put("followList",pageInfo);
        return modelMap;
    }

    @CheckToken
    @RequestMapping(value = "/getfriends",method = RequestMethod.GET)
    private Map<String, Object> getFriends(@RequestParam("page") Integer pageNum){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        PageHelper.startPage(pageNum,10);
        List<Follow> friendsList = followService.getFriends();
        PageInfo<Follow> pageInfo = new PageInfo<>(friendsList);
        modelMap.put("followList",pageInfo);
        return modelMap;
    }
}
