package com.sarielx.study.xr.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sarielx.study.xr.entity.Topic;
import com.sarielx.study.xr.jwtToken.CheckToken;
import com.sarielx.study.xr.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    TopicService topicService;

    @RequestMapping(value = "/gettopiclist/{page}",method = RequestMethod.GET)
    private Map<String,Object> getTopicList(@PathVariable("page") int pageNum, @RequestParam("limit") int pageSize){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        PageHelper.startPage(pageNum,pageSize);
        List<Topic> topicList = topicService.getTopicList();
        PageInfo<Topic> pageInfo = new PageInfo<>(topicList);
        modelMap.put("list",pageInfo);
        return modelMap;
    }

    @RequestMapping(value = "/tentoptopic",method = RequestMethod.GET)
    private Map<String,Object> getTenTopTopic(){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        List<Topic> topicList = topicService.getTopTenTopic();
        modelMap.put("listTenTopTopic",topicList);
        return modelMap;
    }

    @RequestMapping(value = "/topicclass/{id}/page/{page}",method = RequestMethod.GET)
    private Map<String,Object> getTopicByClass(@PathVariable("id") Integer topicClassId, @PathVariable("page") int pageNum){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        PageHelper.startPage(pageNum,10);
        List<Topic> topicList = topicService.getTopicByClass(topicClassId);
        PageInfo<Topic> pageInfo = new PageInfo<>(topicList);
        modelMap.put("listTopic",pageInfo);
        return modelMap;
    }

//    @RequestMapping(value = "/topicbyclass",method = RequestMethod.GET)
//    private Map<String,Object> getTopicByClass(@RequestParam("topic_class_id") Integer topicClassId,@RequestParam("page") int pageNum){
//        Map<String,Object> modelMap = new HashMap<String, Object>();
//        PageHelper.startPage(pageNum,10);
//        List<Topic> topicList = topicService.getTopicByClass(topicClassId);
//        PageInfo<Topic> pageInfo = new PageInfo<>(topicList);
//        modelMap.put("listTopic",pageInfo);
//        return modelMap;
//    }

    @RequestMapping(value = "/searchtopic",method = RequestMethod.POST)
    private Map<String, Object> searchTopic(@RequestParam String keyword,@RequestParam("page") int pageNum){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        PageHelper.startPage(pageNum,10);
        List<Topic> topicList = topicService.searchTopic(keyword);
        if(topicList.size() > 0){
            PageInfo<Topic> pageInfo = new PageInfo<>(topicList);
            modelMap.put("listTopic",pageInfo);
        }
        else {
            modelMap.put("false","查询不到相关话题");
        }
        return modelMap;
    }

    @CheckToken
    @RequestMapping(value = "/{id}/update_status",method = RequestMethod.POST)
    private Map<String, Object> updateStatus(@PathVariable Integer id,@RequestParam Byte type){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success",topicService.updateStatus(id,type));
        return modelMap;
    }

    @CheckToken
    @RequestMapping(value = "/addtopic",method = RequestMethod.POST)
    private Map<String, Object> addTopic(@RequestBody Topic topic){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("data",topicService.addTopic(topic));
        return modelMap;
    }

    @CheckToken
    @RequestMapping(value = "/update_topic/",method = RequestMethod.POST)
    private Map<String, Object> updateStatus(@RequestBody Topic topic){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success",topicService.updateTopic(topic));
        return modelMap;
    }

    @CheckToken
    @RequestMapping(value = "/{id}/delete",method = RequestMethod.POST)
    private Map<String, Object> updateStatus(@PathVariable Integer id){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success",topicService.delete(id));
        return modelMap;
    }

    @CheckToken
    @RequestMapping(value = "/delete_all",method = RequestMethod.POST)
    private Map<String, Object> updateStatus(@RequestParam Integer[] ids){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success",topicService.deleteAll(ids));
        return modelMap;
    }

}
