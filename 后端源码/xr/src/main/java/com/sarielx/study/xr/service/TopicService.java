package com.sarielx.study.xr.service;

import com.sarielx.study.xr.entity.Topic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface TopicService {
    // 获取全部话题列表
    List<Topic> getTopicList();
    // 获取前十话题
    List<Topic> getTopTenTopic();
    // 根据话题分类获取话题
    List<Topic> getTopicByClass(int topicClassId);
    // 模糊查询话题
    List<Topic> searchTopic(String keyword);
    // 修改话题状态
    boolean updateStatus(Integer topicId,Byte type);
    // 新增话题
    Topic addTopic(Topic topic);
    // 修改话题
    boolean updateTopic(Topic topic);
    // 删除话题
    boolean delete(Integer topicId);
    // 批量删除
    boolean deleteAll(Integer[] ids);
}
