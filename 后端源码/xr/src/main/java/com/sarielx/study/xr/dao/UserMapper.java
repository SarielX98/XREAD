package com.sarielx.study.xr.dao;

import com.sarielx.study.xr.entity.User;

import java.util.List;

public interface UserMapper {
    // 获取用户列表
    List<User> getUserList();

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> select();

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    // 判断是否存在该用户
    User findUserByName(String username);

    // 注册
    boolean register(User user);

    // 搜索用户
    List<User> searchUser(String keyword);

    Integer login(User user);
}