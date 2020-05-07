package com.sarielx.study.xr.dao;

import com.sarielx.study.xr.entity.Topic;

import java.util.List;

public interface TopicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Topic record);

    int insertSelective(Topic record);

    Topic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Topic record);

    int updateByPrimaryKey(Topic record);

    // 获取全部话题
    List<Topic> getTopicList();

    // 获取最近话题
    List<Topic> selectTopTenTopic();

    // 根据分类获取话题列表
    List<Topic> selectByClass(Integer topicClassId);

    // 模糊查询话题
    List<Topic> searchTopic(String keyword);
}