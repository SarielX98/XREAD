package com.sarielx.study.xr.dao;

import com.sarielx.study.xr.entity.Comment;

import java.util.List;

public interface CommentMapper {
    // 获取评论列表
    List<Comment> getCommentList();

    // 获取用户评论数
    int getCommentnumByUser(Integer userId);

    // 获取评论数
    int getCommentnum(Integer postId);

    // 判断评论是否存在
    Comment isCommentExist(Integer fid);

    // 回复数+1
    boolean addFnum(Integer fid);

    // 获取当前文章的评论列表
    List<Comment> getComment(Integer postId);

    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);
}