package com.sarielx.study.xr.service.impl;

import com.auth0.jwt.JWT;
import com.sarielx.study.xr.dao.FollowMapper;
import com.sarielx.study.xr.dao.UserInfoMapper;
import com.sarielx.study.xr.dao.UserMapper;
import com.sarielx.study.xr.entity.Follow;
import com.sarielx.study.xr.entity.User;
import com.sarielx.study.xr.handler.MyException;
import com.sarielx.study.xr.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    FollowMapper followMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserInfoMapper userInfoMapper;

    @Transactional
    @Override
    public boolean doFollow(Integer followId) {

        // 从token中获取用户id
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        String userId = JWT.decode(token).getAudience().get(0);
        User user = userMapper.selectByPrimaryKey(followId);
        if (user == null) throw new MyException("903","不存在该用户");
        if (followId == Integer.valueOf(userId)) throw new MyException("900","非法操作");
        Follow checkFollow = followMapper.checkFollow(Integer.valueOf(userId),followId);
        if (checkFollow != null) throw new MyException("901","已经关注过了");
        Follow newFollow = new Follow();
        newFollow.setUserId(Integer.valueOf(userId));
        newFollow.setFollowId(followId);
        newFollow.setCreateTime((int) (System.currentTimeMillis() / 1000));
        followMapper.insert(newFollow);

        return true;
    }

    @Transactional
    @Override
    public boolean unFollow(Integer followId) {
        // 从token中获取用户id
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        String userId = JWT.decode(token).getAudience().get(0);
        User user = userMapper.selectByPrimaryKey(followId);
        if (user == null) throw new MyException("903","不存在该用户");
        if (followId == Integer.valueOf(userId)) throw new MyException("900","非法操作");
        Follow checkFollow = followMapper.checkFollow(Integer.valueOf(userId),followId);
        if (checkFollow == null) throw new MyException("904","暂未关注");
        return followMapper.unFollow(Integer.valueOf(userId),followId);
    }

    @Override
    public List<Follow> getFollows() {
        // 从token中获取用户id
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        String userId = JWT.decode(token).getAudience().get(0);
        List<Follow> followsList = followMapper.getFollows(Integer.valueOf(userId));
        for(int i = 0; i < followsList.size(); i++){
            int followId = followsList.get(i).getFollowId();
            User followUser = followsList.get(i).getUser();
            followUser.setUserInfo(userInfoMapper.selectByUid(followId));
            followsList.get(i).setUser(followUser);
        }
        return followsList;
    }

    @Override
    public List<Follow> getFens() {
        // 从token中获取用户id
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        String userId = JWT.decode(token).getAudience().get(0);
        List<Follow> fensList = followMapper.getFens(Integer.valueOf(userId));
        for(int i = 0; i < fensList.size(); i++){
            int fenId = fensList.get(i).getUserId();
            User fenUser = fensList.get(i).getUser();
            fenUser.setUserInfo(userInfoMapper.selectByUid(fenId));
            fensList.get(i).setUser(fenUser);
        }

        return fensList;
    }

    @Transactional
    @Override
    public List<Follow> getFriends() {
        // 从token中获取用户id
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        String userId = JWT.decode(token).getAudience().get(0);
        List<Follow> followsList = followMapper.getFollows(Integer.valueOf(userId));
        List<Follow> friendsList = new ArrayList<>();
        for (int i = 0 ; i < followsList.size() ; i++){
            Integer followId = followsList.get(i).getFollowId();
            Follow checkFriend = followMapper.checkFriend(followId,Integer.valueOf(userId));
            if (checkFriend != null) friendsList.add(i,checkFriend);
        }
        for(int j = 0; j < friendsList.size(); j++){
            int fenId = friendsList.get(j).getUserId();
            User fenUser = friendsList.get(j).getUser();
            fenUser.setUserInfo(userInfoMapper.selectByUid(fenId));
            friendsList.get(j).setUser(fenUser);
        }
        return friendsList;
    }

    @Transactional
    @Override
    public int getFriendsnum(Integer userId) {
        List<Follow> followsList = followMapper.getFollows(userId);
        List<Follow> friendsList = new ArrayList<>();
        for (int i = 0 ; i < followsList.size() ; i++){
            Integer followId = followsList.get(i).getFollowId();
            Follow checkFriend = followMapper.checkFriend(followId,Integer.valueOf(userId));
            if (checkFriend != null) friendsList.add(i,checkFriend);
        }
        return friendsList.size();
    }
}
