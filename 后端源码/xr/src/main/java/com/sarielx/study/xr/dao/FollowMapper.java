package com.sarielx.study.xr.dao;

import com.sarielx.study.xr.entity.Follow;

import java.util.List;

public interface FollowMapper {
    // 获取关注数
    int getFollowsnum(Integer userId);

    // 获取粉丝数
    int getFensnum(Integer userId);

    Follow checkFollow(Integer userId,Integer followId);

    boolean unFollow(Integer userId,Integer followId);

    // 获取关注列表
    List<Follow> getFollows (Integer userId);

    // 获取粉丝列表
    List<Follow> getFens(Integer userId);

    Follow checkFriend(Integer userId,Integer followId);

    int deleteByPrimaryKey(Integer id);

    int insert(Follow record);

    int insertSelective(Follow record);

    Follow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Follow record);

    int updateByPrimaryKey(Follow record);
}