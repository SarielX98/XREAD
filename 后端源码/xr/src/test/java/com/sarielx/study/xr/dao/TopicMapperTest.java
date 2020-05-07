package com.sarielx.study.xr.dao;

import com.sarielx.study.xr.entity.Topic;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class TopicMapperTest {
    @Autowired
    private TopicMapper topicMapper;

    @Test
    void deleteByPrimaryKey() {
    }

    @Test
    void insert() {
    }

    @Test
    void insertSelective() {
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void updateByPrimaryKeySelective() {
    }

    @Test
    void updateByPrimaryKey() {
    }

    @Test
    void selectTopTenTopic() {
        List<Topic> topTenList = topicMapper.selectTopTenTopic();
        assertEquals(7,topTenList.size());
    }

    @Test
    void selectByClass(){
        List<Topic> topicList = topicMapper.selectByClass(1);
        assertEquals(7,topicList.size());
    }
}