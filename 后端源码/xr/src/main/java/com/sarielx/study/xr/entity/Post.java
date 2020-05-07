package com.sarielx.study.xr.entity;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class Post {
    private Integer id;

    private Integer userId;

    private String title;

    private String titlepic;

    private Integer sharenum;

    private Integer commentnum;

    private Integer dingnum;

    private Integer cainum;

    private String path;

    private Byte type;

    private Integer createTime;

    @NotEmpty(message = "话题不能为空！")
    private Integer postClassId;

    private Topic topic;

    private Integer shareId;

    @NotEmpty(message = "请设置是否可见")
    private Byte isopen;

    @NotEmpty(message = "内容不能为空！")
    private String content;

    private List<Image> imageList;

    private User user;

    private Post share;

    private Support support;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getTitlepic() {
        return titlepic;
    }

    public void setTitlepic(String titlepic) {
        this.titlepic = titlepic == null ? null : titlepic.trim();
    }

    public Integer getSharenum() {
        return sharenum;
    }

    public void setSharenum(Integer sharenum) {
        this.sharenum = sharenum;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getPostClassId() {
        return postClassId;
    }

    public void setPostClassId(Integer postClassId) {
        this.postClassId = postClassId;
    }

    public Integer getShareId() {
        return shareId;
    }

    public void setShareId(Integer shareId) {
        this.shareId = shareId;
    }

    public Byte getIsopen() {
        return isopen;
    }

    public void setIsopen(Byte isopen) {
        this.isopen = isopen;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getShare() {
        return share;
    }

    public void setShare(Post share) {
        this.share = share;
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
    }

    public Integer getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(Integer commentnum) {
        this.commentnum = commentnum;
    }

    public Integer getDingnum() {
        return dingnum;
    }

    public void setDingnum(Integer dingnum) {
        this.dingnum = dingnum;
    }

    public Integer getCainum() {
        return cainum;
    }

    public void setCainum(Integer cainum) {
        this.cainum = cainum;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}