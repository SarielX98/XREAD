package com.sarielx.study.xr.service;

import com.sarielx.study.xr.entity.Post;

import java.util.List;

public interface PostService {
    // 获取全部帖子
    List<Post> getPostList();

    // 删除帖子
    boolean delete(Integer postId);

    // 批量删除
    boolean deleteAll(Integer[] ids);

    // 发布文章
    boolean createPost (Post post);

    // 获取帖子详情
    Post getPostDetail (Integer id);

    // 获取指定话题下的帖子列表
    List<Post> getPostByTopic(Integer topicId);

    // 指定用户下的帖子列表
    List<Post> getPostByUser(Integer userId);

    // 指定用户下的帖子列表(含隐私)
    List<Post> getAllPostByUser();
}
