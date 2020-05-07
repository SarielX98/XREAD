package com.sarielx.study.xr.controller;

import com.sarielx.study.xr.entity.TopicClass;
import com.sarielx.study.xr.service.TopicClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/topicclass")
public class TopicClassController {
    @Autowired
    private TopicClassService topicClassService;

    @RequestMapping(value = "/listtopicclass",method = RequestMethod.GET)
    private Map<String,Object> listTopicClass(){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        List<TopicClass> topicClassList = topicClassService.getTopicClassList();
        modelMap.put("topiclassList",topicClassList);
        return modelMap;
    }
}
