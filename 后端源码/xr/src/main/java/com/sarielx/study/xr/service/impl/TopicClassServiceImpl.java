package com.sarielx.study.xr.service.impl;

import com.sarielx.study.xr.dao.TopicClassMapper;
import com.sarielx.study.xr.entity.TopicClass;
import com.sarielx.study.xr.service.TopicClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicClassServiceImpl implements TopicClassService {
    @Autowired
    private TopicClassMapper topicClassMapper;

    // 获取话题分类
    @Override
    public List<TopicClass> getTopicClassList() {
        return topicClassMapper.select();
    }
}
