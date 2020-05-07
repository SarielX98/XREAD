package com.sarielx.study.xr.dao;

import com.sarielx.study.xr.entity.Post;

import java.util.List;

public interface PostMapper {
    // 获取全部帖子
    List<Post> getPostList();

    // 获取用户帖子数
    int getPostnum(Integer userId);

    // 获取帖子详情
    Post getPostDetail(Integer id);

    // 获取分享
    Post getShare(Integer shareId);

    // 指定话题下的帖子列表
    List<Post> getPostByTopic(Integer postClassId);

    // 指定用户下的帖子列表
    List<Post> getPostByUser(Integer userId);

    // 指定用户下的帖子列表(含隐私)
    List<Post> getAllPostByUser(Integer userId);

    int deleteByPrimaryKey(Integer id);

    int insert(Post record);

    int insertSelective(Post record);

    // 判断帖子是否存在
    Post selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Post record);

    int updateByPrimaryKeyWithBLOBs(Post record);

    int updateByPrimaryKey(Post record);
}