package com.sarielx.study.xr.dao;

import com.sarielx.study.xr.entity.Image;

public interface ImageMapper {
    Image findByIdUid(Integer id,Integer userId);

    int deleteByPrimaryKey(Integer id);

    int insert(Image record);

    int insertSelective(Image record);

    Image selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Image record);

    int updateByPrimaryKey(Image record);
}