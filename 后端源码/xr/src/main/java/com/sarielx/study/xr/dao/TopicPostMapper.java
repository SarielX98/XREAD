package com.sarielx.study.xr.dao;

import com.sarielx.study.xr.entity.TopicPost;

public interface TopicPostMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TopicPost record);

    int insertSelective(TopicPost record);

    TopicPost selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TopicPost record);

    int updateByPrimaryKey(TopicPost record);
}