package com.sarielx.study.xr.service.impl;

import com.sarielx.study.xr.dao.TopicMapper;
import com.sarielx.study.xr.entity.Topic;
import com.sarielx.study.xr.handler.MyException;
import com.sarielx.study.xr.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicMapper topicMapper;

    // 获取全部话题
    @Override
    public List<Topic> getTopicList() {
        return topicMapper.getTopicList();
    }

    // 获取最近话题
    @Override
    public List<Topic> getTopTenTopic() {
        return topicMapper.selectTopTenTopic();
    }

    // 根据话题分类获取话题
    @Override
    public List<Topic> getTopicByClass(int topicClassId) {
        return topicMapper.selectByClass(topicClassId);
    }

    @Override
    public List<Topic> searchTopic(String keyword) {
        return topicMapper.searchTopic(keyword);
    }

    @Override
    public boolean updateStatus(Integer topicId,Byte type) {
        Topic topic = topicMapper.selectByPrimaryKey(topicId);
        topic.setType(type);
        int effectednum = topicMapper.updateByPrimaryKeySelective(topic);
        if(effectednum > 0){
            return true;
        }else{
            throw new MyException("99990","话题状态修改失败");
        }
    }

    @Override
    public Topic addTopic(Topic topic) {
            topic.setCreateTime((int) (System.currentTimeMillis() / 1000));
            int effectednum = topicMapper.insertSelective(topic);
            if(effectednum > 0) {
                Topic newTopic = topicMapper.selectByPrimaryKey(topic.getId());
                return newTopic;
            }else{
                throw new MyException("99991","添加失败");
            }
    }

    @Override
    public boolean updateTopic(Topic topic) {
        int effectednum = topicMapper.updateByPrimaryKeySelective(topic);
        if(effectednum > 0){
            return true;
        }else{
            throw new MyException("99992","修改失败");
        }
    }

    @Override
    public boolean delete(Integer topicId) {
        int effectednum = topicMapper.deleteByPrimaryKey(topicId);
        if(effectednum > 0){
            return true;
        }else{
            throw new MyException("99993","删除失败");
        }
    }

    @Override
    public boolean deleteAll(Integer[] ids) {
        try{
            for (int i = 0; i < ids.length; i++){

                topicMapper.deleteByPrimaryKey(ids[i]);
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            throw new MyException("99994", "批量删除失败");
        }
    }
}
