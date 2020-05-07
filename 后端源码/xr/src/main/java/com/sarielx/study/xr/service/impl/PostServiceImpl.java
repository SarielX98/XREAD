package com.sarielx.study.xr.service.impl;

import com.auth0.jwt.JWT;
import com.sarielx.study.xr.dao.*;
import com.sarielx.study.xr.entity.*;
import com.sarielx.study.xr.handler.MyException;
import com.sarielx.study.xr.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    TopicMapper topicMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    ImageMapper imageMapper;
    @Autowired
    PostMapper postMapper;
    @Autowired
    PostImageMapper postImageMapper;
    @Autowired
    TopicPostMapper topicPostMapper;
    @Autowired
    SupportMapper supportMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    FollowMapper followMapper;

    @Override
    public List<Post> getPostList() {
        List<Post> postList = postMapper.getPostList();
        for(int i = 0; i < postList.size(); i++){
            int topicId = postList.get(i).getPostClassId();
            postList.get(i).setTopic(topicMapper.selectByPrimaryKey(topicId));
        }
        return postList;
    }

    @Override
    public boolean delete(Integer postId) {
        int effectednum = postMapper.deleteByPrimaryKey(postId);
        if(effectednum > 0){
            return true;
        }else{
            throw new MyException("111113","删除失败");
        }
    }

    @Override
    public boolean deleteAll(Integer[] ids) {
        try{
            for (int i = 0; i < ids.length; i++){

                postMapper.deleteByPrimaryKey(ids[i]);
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            throw new MyException("111114", "批量删除失败");
        }
    }

    // 发布文章
    @Transactional
    @Override
    public boolean createPost(Post post) {
        Topic topic = topicMapper.selectByPrimaryKey(post.getPostClassId());
        if (topic != null){
            try{
                // 从token中获取用户id
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                // 从 http 请求头中取出 token
                String token = request.getHeader("token");
                String userId = JWT.decode(token).getAudience().get(0);
                //String path = userInfoMapper.selectByUid(Integer.valueOf(userId)).getPath();
                //if (path == null) path = "未知";
                // 发布文章
                String title = post.getContent();
                if (post.getContent().length()>30) title = post.getContent().substring(0,30);
                Post newPost = new Post();
                newPost.setUserId(Integer.valueOf(userId));
                newPost.setTitle(title);
                newPost.setTitlepic("");
                newPost.setContent(post.getContent());
                newPost.setPath("未知");
                newPost.setType((byte) 0);
                newPost.setPostClassId(post.getPostClassId());
                newPost.setShareId(0);
                newPost.setIsopen(post.getIsopen());
                newPost.setCreateTime((int) (System.currentTimeMillis() / 1000));
                postMapper.insertSelective(newPost);
                // 判断图片
                int listSize = post.getImageList().size();
                if(listSize > 0){
                    List<Image> imgidarr = new ArrayList<>();
                    for (int i = 0 ; i < listSize ; i++){
                        Image image = imageMapper.findByIdUid(post.getImageList().get(i).getId(), Integer.valueOf(userId));
                        imgidarr.add(i,image);
                        if (imgidarr.size()>0){
                            //imgidarr.get(i).setImageId(post.getImageList().get(i).getId());
                            PostImage postImage = new PostImage();
                            postImage.setPostId(newPost.getId());
                            postImage.setImageId(imgidarr.get(i).getId());
                            postImage.setCreateTime((int) (System.currentTimeMillis() / 1000));
                            postImageMapper.insert(postImage);
                        }
                    }
                    newPost.setTitlepic(imgidarr.get(0).getUrl());
                    postMapper.updateByPrimaryKeySelective(newPost);
                }
                // 关联话题
                TopicPost topicPost = new TopicPost();
                topicPost.setCreateTime((int) (System.currentTimeMillis() / 1000));
                topicPost.setPostId(newPost.getId());
                topicPost.setTopicId(post.getPostClassId());
                topicPostMapper.insert(topicPost);
                return true;
            }catch (Exception e){
                e.printStackTrace();
                throw new MyException("505","发布失败");
            }
        }else {
            throw new MyException("501","该话题不存在");
        }
    }

    // 获取帖子详情
    @Transactional
    @Override
    public Post getPostDetail(Integer id) {
        Post post = new Post();
        post = postMapper.getPostDetail(id);
        // 获取评论数
        int commentnum = commentMapper.getCommentnum(id);
        post.setCommentnum(commentnum);
        // 关联分享
        Integer shareId = post.getShareId();
        if (shareId > 0){
            Post share = new Post();
            share = postMapper.getShare(shareId);
            post.setShare(share);
        }
        // 关联图片
        Integer postId = post.getId();
        List<PostImage> postImageList = postImageMapper.selectByPostId(postId);
        if (postImageList != null) {
            List<Image> imageList = new ArrayList<>();
            for (int i = 0; i < postImageList.size(); i++){
                Integer imgId = postImageList.get(i).getImageId();
                Image image = imageMapper.selectByPrimaryKey(imgId);
                imageList.add(i,image);
            }
            post.setImageList(imageList);
        }
        // 关联顶踩
        // 获取顶踩数
        int dingnum = supportMapper.getDing(postId);
        int cainum = supportMapper.getCai(postId);
        post.setDingnum(dingnum);
        post.setCainum(cainum);
        // 从token中获取用户id
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        if (token != null) {
            String userId = JWT.decode(token).getAudience().get(0);
            Support support = supportMapper.isSupport(Integer.valueOf(userId),postId);
            if (support != null) post.setSupport(support);
            // 判断用户是否关注
            Follow isFollow = followMapper.checkFollow(Integer.valueOf(userId),post.getUserId());
            User newUser = post.getUser();
            newUser.setFens(isFollow);
            post.setUser(newUser);
        }

        return post;
    }

    @Override
    public List<Post> getPostByTopic(Integer topicId) {
        List<Post> postList = postMapper.getPostByTopic(topicId);
        for(int i = 0; i < postList.size(); i++){
            // 关联分享
            Integer shareId = postList.get(i).getShareId();
            if (shareId > 0){
                Post share = new Post();
                share = postMapper.getShare(shareId);
                postList.get(i).setShare(share);
            }
            // 关联图片
            Integer postId = postList.get(i).getId();
            List<PostImage> postImageList = postImageMapper.selectByPostId(postId);
            if (postImageList != null) {
                List<Image> imageList = new ArrayList<>();
                for (int j = 0; j < postImageList.size(); j++){
                    Integer imgId = postImageList.get(j).getImageId();
                    Image image = imageMapper.selectByPrimaryKey(imgId);
                    imageList.add(j,image);
                }
                postList.get(i).setImageList(imageList);
            }
            // 获取评论数
            int commentnum = commentMapper.getCommentnum(postId);
            postList.get(i).setCommentnum(commentnum);
            // 关联顶踩
            // 获取顶踩数
            int dingnum = supportMapper.getDing(postId);
            int cainum = supportMapper.getCai(postId);
            postList.get(i).setDingnum(dingnum);
            postList.get(i).setCainum(cainum);
            // 从token中获取用户id
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            // 从 http 请求头中取出 token
            String token = request.getHeader("token");
            if (token != null) {
                String userId = JWT.decode(token).getAudience().get(0);
                Support support = supportMapper.isSupport(Integer.valueOf(userId),postId);
                if (support != null) postList.get(i).setSupport(support);
                // 判断用户是否关注
                Follow isFollow = followMapper.checkFollow(Integer.valueOf(userId),postList.get(i).getUserId());
                User newUser = postList.get(i).getUser();
                newUser.setFens(isFollow);
                postList.get(i).setUser(newUser);
            }
        }
        return postList;
    }

    @Override
    public List<Post> getPostByUser(Integer userId) {
        List<Post> postList = postMapper.getPostByUser(userId);
        for(int i = 0; i < postList.size(); i++){
            // 关联分享
            Integer shareId = postList.get(i).getShareId();
            if (shareId > 0){
                Post share = new Post();
                share = postMapper.getShare(shareId);
                postList.get(i).setShare(share);
            }
            // 关联图片
            Integer postId = postList.get(i).getId();
            List<PostImage> postImageList = postImageMapper.selectByPostId(postId);
            if (postImageList != null) {
                List<Image> imageList = new ArrayList<>();
                for (int j = 0; j < postImageList.size(); j++){
                    Integer imgId = postImageList.get(j).getImageId();
                    Image image = imageMapper.selectByPrimaryKey(imgId);
                    imageList.add(j,image);
                }
                postList.get(i).setImageList(imageList);
            }
            // 获取评论数
            int commentnum = commentMapper.getCommentnum(postId);
            postList.get(i).setCommentnum(commentnum);
            // 关联顶踩
            // 获取顶踩数
            int dingnum = supportMapper.getDing(postId);
            int cainum = supportMapper.getCai(postId);
            postList.get(i).setDingnum(dingnum);
            postList.get(i).setCainum(cainum);
            // 从token中获取用户id
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            // 从 http 请求头中取出 token
            String token = request.getHeader("token");
            if (token != null) {
                String curUserId = JWT.decode(token).getAudience().get(0);
                Support support = supportMapper.isSupport(Integer.valueOf(curUserId),postId);
                if (support != null) postList.get(i).setSupport(support);
                // 判断用户是否关注
                Follow isFollow = followMapper.checkFollow(Integer.valueOf(userId),postList.get(i).getUserId());
                User newUser = postList.get(i).getUser();
                newUser.setFens(isFollow);
                postList.get(i).setUser(newUser);
            }

        }
        return postList;
    }

    @Override
    public List<Post> getAllPostByUser() {
        // 从token中获取用户id
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        String userId = JWT.decode(token).getAudience().get(0);
        List<Post> postList = postMapper.getAllPostByUser(Integer.valueOf(userId));
        for(int i = 0; i < postList.size(); i++){
            // 关联分享
            Integer shareId = postList.get(i).getShareId();
            if (shareId > 0){
                Post share = new Post();
                share = postMapper.getShare(shareId);
                postList.get(i).setShare(share);
            }
            // 关联图片
            Integer postId = postList.get(i).getId();
            List<PostImage> postImageList = postImageMapper.selectByPostId(postId);
            if (postImageList != null) {
                List<Image> imageList = new ArrayList<>();
                for (int j = 0; j < postImageList.size(); j++){
                    Integer imgId = postImageList.get(j).getImageId();
                    Image image = imageMapper.selectByPrimaryKey(imgId);
                    imageList.add(j,image);
                }
                postList.get(i).setImageList(imageList);
            }
            // 获取评论数
            int commentnum = commentMapper.getCommentnum(postId);
            postList.get(i).setCommentnum(commentnum);
            // 关联顶踩
            // 获取顶踩数
            int dingnum = supportMapper.getDing(postId);
            int cainum = supportMapper.getCai(postId);
            postList.get(i).setDingnum(dingnum);
            postList.get(i).setCainum(cainum);
            Support support = supportMapper.isSupport(Integer.valueOf(userId),postId);
            if (support != null) postList.get(i).setSupport(support);
            // 判断用户是否关注
            Follow isFollow = followMapper.checkFollow(Integer.valueOf(userId),postList.get(i).getUserId());
            User newUser = postList.get(i).getUser();
            newUser.setFens(isFollow);
            postList.get(i).setUser(newUser);
        }
        return postList;
    }
}
