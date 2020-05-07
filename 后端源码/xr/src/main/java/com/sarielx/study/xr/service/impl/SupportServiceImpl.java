package com.sarielx.study.xr.service.impl;

import com.auth0.jwt.JWT;
import com.sarielx.study.xr.dao.PostMapper;
import com.sarielx.study.xr.dao.SupportMapper;
import com.sarielx.study.xr.entity.Support;
import com.sarielx.study.xr.handler.MyException;
import com.sarielx.study.xr.service.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
public class SupportServiceImpl implements SupportService{
    @Autowired
    SupportMapper supportMapper;
    @Autowired
    PostMapper postMapper;

    @Transactional
    @Override
    public boolean doSupport(Support support) {
        if (postMapper.selectByPrimaryKey(support.getPostId()) == null) throw new MyException("500","该帖子不存在");
        // 从token中获取用户id
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        String userId = JWT.decode(token).getAudience().get(0);
        support.setUserId(Integer.valueOf(userId));
        Support curSupport = supportMapper.isSupport(support.getUserId(),support.getPostId());
        // 判断是否已经顶踩过
        if (curSupport != null){
            // 已经顶踩过，判断当前操作是否相同
            if(curSupport.getType() == support.getType()) throw new MyException("501","请勿重复操作");
            // 修改本条信息
            curSupport.setType(support.getType());
            supportMapper.updateByPrimaryKeySelective(curSupport);
            return true;
        }else {
            // 直接创建
            support.setCreateTime((int) (System.currentTimeMillis() / 1000));
            supportMapper.insert(support);
            return true;
        }
    }
}
