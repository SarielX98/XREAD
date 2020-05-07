package com.sarielx.study.xr.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sarielx.study.xr.entity.Post;
import com.sarielx.study.xr.entity.User;
import com.sarielx.study.xr.jwtToken.CheckToken;
import com.sarielx.study.xr.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;

    // 获取帖子列表
    @RequestMapping(value="/getpostlist/{page}", method = RequestMethod.GET)
    private Map<String,Object> getPostList(@PathVariable("page") int pageNum, @RequestParam("limit") int pageSize){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        PageHelper.startPage(pageNum,pageSize);
        List<Post> list = postService.getPostList();
        PageInfo<Post> pageInfo = new PageInfo<>(list);
        modelMap.put("list",pageInfo);
        return modelMap;
    }

    @CheckToken
    @RequestMapping(value = "/{id}/delete",method = RequestMethod.POST)
    private Map<String, Object> updateStatus(@PathVariable Integer id){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success",postService.delete(id));
        return modelMap;
    }

    @CheckToken
    @RequestMapping(value = "/delete_all",method = RequestMethod.POST)
    private Map<String, Object> updateStatus(@RequestParam Integer[] ids){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success",postService.deleteAll(ids));
        return modelMap;
    }

    @CheckToken
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    private Map<String, Object> createPost(@RequestBody Post post){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("sucess",postService.createPost(post));
        return modelMap;
    }

    @RequestMapping(value = "/getpostdetail",method = RequestMethod.GET)
    private Map<String, Object> getPostDetail(@RequestParam Integer id){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Post post = postService.getPostDetail(id);
        modelMap.put("post",post);
        return modelMap;
    }

    @RequestMapping(value = "getpostbytopic",method = RequestMethod.GET)
    private Map<String, Object> getPostByTopic(@RequestParam("topic_id") Integer topicId,@RequestParam("page") Integer pageNum){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        PageHelper.startPage(pageNum,10);
        List<Post> postList = postService.getPostByTopic(topicId);
        PageInfo<Post> pageInfo = new PageInfo<>(postList);
        modelMap.put("postList",pageInfo);
        return modelMap;
    }

    @RequestMapping(value = "getpostbyuser",method = RequestMethod.GET)
    private Map<String, Object> getPostByUser(@RequestParam("user_id") Integer userId,@RequestParam("page") Integer pageNum){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        PageHelper.startPage(pageNum,10);
        List<Post> postList = postService.getPostByUser(userId);
        PageInfo<Post> pageInfo = new PageInfo<>(postList);
        modelMap.put("postList",pageInfo);
        return modelMap;
    }

    @CheckToken
    @RequestMapping(value = "getallpostbyuser",method = RequestMethod.GET)
    private Map<String, Object> getAllPostByUser(@RequestParam("page") Integer pageNum){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        PageHelper.startPage(pageNum,10);
        List<Post> postList = postService.getAllPostByUser();
        PageInfo<Post> pageInfo = new PageInfo<>(postList);
        modelMap.put("postList",pageInfo);
        return modelMap;
    }
}
