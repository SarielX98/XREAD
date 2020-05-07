package com.sarielx.study.xr.dao;

import com.sarielx.study.xr.entity.Support;

public interface SupportMapper {
    // 获取顶数量
    int getDing(Integer postId);

    // 获取踩数量
    int getCai(Integer postId);

    // 判断用户是否顶踩过
    Support isSupport(Integer userId,Integer postId);

    int deleteByPrimaryKey(Integer id);

    int insert(Support record);

    int insertSelective(Support record);

    Support selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Support record);

    int updateByPrimaryKey(Support record);
}