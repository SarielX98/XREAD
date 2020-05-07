package com.sarielx.study.xr.entity;

import java.util.Date;

public class Topic {
    private Integer id;

    private String title;

    private String titlepic;

    private String desc;

    private Byte type;

    private Integer createTime;

    private Integer topicClassId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
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

    public Integer getTopicClassId() {
        return topicClassId;
    }

    public void setTopicClassId(Integer topicClassId) {
        this.topicClassId = topicClassId;
    }
}