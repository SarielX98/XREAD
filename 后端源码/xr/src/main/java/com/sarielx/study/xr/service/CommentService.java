package com.sarielx.study.xr.service;

import com.sarielx.study.xr.entity.Comment;

import java.util.List;

public interface CommentService {
    // 获取评论列表
    List<Comment> getCommentList();

    // 删除评论
    boolean delete(Integer commentId);

    // 批量删除
    boolean deleteAll(Integer[] ids);

    // 用户评论
    boolean doComment (Comment comment);

    // 获取当前文章的评论列表
    List<Comment> getComment(Integer postId);
}
