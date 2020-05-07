package com.sarielx.study.xr.dao;

import com.sarielx.study.xr.entity.User;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    @Ignore
    void deleteByPrimaryKey() {
        int effectedNum = userMapper.deleteByPrimaryKey(14);
        assertEquals(1,effectedNum);
    }

    @Test
    @Ignore
    void insert() {
        User user = new User();
        user.setUsername("SarielX98");
        user.setPassword("183461");
        user.setStatus(Byte.valueOf("1"));
        int effectedNum = userMapper.insert(user);
        assertEquals(1,effectedNum);
    }

    @Test
    @Ignore
    void insertSelective() {
    }

    @Test
    @Ignore
    void select() {
        List<User> userList = userMapper.select();
        assertEquals(3,userList.size());
    }

    @Test
    @Ignore
    void selectByPrimaryKey() {
        User user = userMapper.selectByPrimaryKey(7);
        assertEquals("zcmcss",user.getUsername());
    }

    @Test
    @Ignore
    void updateByPrimaryKeySelective() {
        User user = new User();
        user.setPassword("333333");
        user.setId(13);
        user.setEmail("123@qq.com");
        int effectedNum = userMapper.updateByPrimaryKeySelective(user);
        assertEquals(1,effectedNum);
    }

    @Test
    @Ignore
    void updateByPrimaryKey() {
        User user = new User();
        user.setPassword("33333");
        user.setId(13);
        user.setPhone("17367073790");
        int effectedNum = userMapper.updateByPrimaryKeySelective(user);
        assertEquals(1,effectedNum);
    }

    @Test
    void findUserByName() {
        User user = userMapper.findUserByName("Sariel");
        assertEquals(14,user.getId());
    }

    @Test
    void register() {
        User user = new User();
        user.setUsername("Sariel");
        user.setPassword("183461");
        user.setStatus((byte) 1);
        user.setCreateTime((int) (System.currentTimeMillis() / 1000));
        boolean effectedNum = userMapper.register(user);
        assertEquals(true,effectedNum);
    }

    @Test
    void login() {
        User user = new User();
        user.setUsername("Sariel");
        user.setPassword("183461");
        int id = userMapper.login(user);
        assertEquals(14,id);
    }
}