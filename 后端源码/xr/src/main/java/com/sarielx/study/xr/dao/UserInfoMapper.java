package com.sarielx.study.xr.dao;

import com.sarielx.study.xr.entity.UserInfo;

public interface UserInfoMapper {
    boolean editUserinfo(UserInfo userInfo);

    UserInfo selectByUid(Integer userId);

    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
}