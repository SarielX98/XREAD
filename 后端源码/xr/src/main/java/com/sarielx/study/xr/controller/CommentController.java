package com.sarielx.study.xr.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sarielx.study.xr.entity.Comment;
import com.sarielx.study.xr.entity.Post;
import com.sarielx.study.xr.jwtToken.CheckToken;
import com.sarielx.study.xr.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @RequestMapping(value="/getcommentlist/{page}", method = RequestMethod.GET)
    private Map<String,Object> getCommentList(@PathVariable("page") int pageNum, @RequestParam("limit") int pageSize){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        PageHelper.startPage(pageNum,pageSize);
        List<Comment> list = commentService.getCommentList();
        PageInfo<Comment> pageInfo = new PageInfo<>(list);
        modelMap.put("list",pageInfo);
        return modelMap;
    }

    @CheckToken
    @RequestMapping(value = "/{id}/delete",method = RequestMethod.POST)
    private Map<String, Object> updateStatus(@PathVariable Integer id){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success",commentService.delete(id));
        return modelMap;
    }

    @CheckToken
    @RequestMapping(value = "/delete_all",method = RequestMethod.POST)
    private Map<String, Object> updateStatus(@RequestParam Integer[] ids){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success",commentService.deleteAll(ids));
        return modelMap;
    }

    @CheckToken
    @RequestMapping(value = "docomment",method = RequestMethod.POST)
    private Map<String, Object> doComment(@RequestBody Comment comment){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success",commentService.doComment(comment));
        return modelMap;
    }

    @RequestMapping(value = "getcomment",method = RequestMethod.GET)
    private Map<String, Object> getComment(@RequestParam Integer postId){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success",commentService.getComment(postId));
        return modelMap;
    }
}
