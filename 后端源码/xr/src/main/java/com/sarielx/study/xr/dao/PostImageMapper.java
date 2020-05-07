package com.sarielx.study.xr.dao;

import com.sarielx.study.xr.entity.PostImage;

import java.util.List;

public interface PostImageMapper {
    // 根据话题id获取图片信息
    List<PostImage> selectByPostId(Integer postId);

    int deleteByPrimaryKey(Integer id);

    int insert(PostImage record);

    int insertSelective(PostImage record);

    PostImage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PostImage record);

    int updateByPrimaryKey(PostImage record);
}