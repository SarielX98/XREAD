package com.sarielx.study.xr.service.impl;

import com.auth0.jwt.JWT;
import com.sarielx.study.xr.dao.CommentMapper;
import com.sarielx.study.xr.dao.PostMapper;
import com.sarielx.study.xr.dao.UserMapper;
import com.sarielx.study.xr.entity.Comment;
import com.sarielx.study.xr.handler.MyException;
import com.sarielx.study.xr.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    PostMapper postMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public List<Comment> getCommentList() {
        List<Comment> commentList = commentMapper.getCommentList();
        for (int i = 0; i < commentList.size(); i++){
            int userId = commentList.get(i).getUserId();
            commentList.get(i).setUser(userMapper.selectByPrimaryKey(userId));
            int postId = commentList.get(i).getPostId();
            commentList.get(i).setPost(postMapper.selectByPrimaryKey(postId));
        }
        return commentList;
    }

    @Override
    public boolean delete(Integer commentId) {
        int effectednum = commentMapper.deleteByPrimaryKey(commentId);
        if(effectednum > 0){
            return true;
        }else{
            throw new MyException("122223","删除失败");
        }
    }

    @Override
    public boolean deleteAll(Integer[] ids) {
        try{
            for (int i = 0; i < ids.length; i++){

                commentMapper.deleteByPrimaryKey(ids[i]);
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            throw new MyException("122224", "批量删除失败");
        }
    }

    @Transactional
    @Override
    public boolean doComment(Comment comment) {
        try{
            // 从token中获取用户id
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            // 从 http 请求头中取出 token
            String token = request.getHeader("token");
            String userId = JWT.decode(token).getAudience().get(0);
            // 判断回复的评论是否存在
            Integer fid = comment.getFid();
            Comment isCommentExsit = commentMapper.isCommentExist(fid);
            if (fid == 0 || isCommentExsit != null){
                comment.setUserId(Integer.valueOf(userId));
                comment.setCreateTime((int) (System.currentTimeMillis() / 1000));
                commentMapper.insertSelective(comment);
                if (fid > 0) commentMapper.addFnum(fid);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException("600","评论失败");
        }
    }

    @Transactional
    @Override
    public List<Comment> getComment(Integer postId) {
        return commentMapper.getComment(postId);
    }
}
