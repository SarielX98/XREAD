package com.sarielx.study.xr.dao;

import com.sarielx.study.xr.entity.TopicClass;

import java.util.List;

public interface TopicClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TopicClass record);

    int insertSelective(TopicClass record);

    List<TopicClass> select();

    TopicClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TopicClass record);

    int updateByPrimaryKey(TopicClass record);
}