package com.sarielx.study.xr.service;

import com.sarielx.study.xr.entity.Follow;

import java.util.List;

public interface FollowService {
    // 关注用户
    boolean doFollow(Integer followId);

    // 取消关注
    boolean unFollow(Integer followId);

    // 关注列表
    List<Follow> getFollows();

    // 粉丝列表
    List<Follow> getFens();

    // 互关列表
    List<Follow> getFriends();

    // 朋友数
    int getFriendsnum (Integer userId);
}
