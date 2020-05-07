package com.sarielx.study.xr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class User {
    private Integer id;

    @NotEmpty(message = "用户名不能为空")
    @Length(min = 6,max = 16,message = "用户名6~11个字符或数字")
    private String username;

    private String userpic;

    @NotEmpty(message = "密码不能为空")
    @Length(min = 6,max = 16,message = "密码应为6~16个字符或数字")
    private String password;

    private String phone;

    private String email;

    private Byte status;

    private Integer createTime;

    private String token;

    private UserInfo userInfo;

    private Follow fens;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic == null ? null : userpic.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Follow getFens() {
        return fens;
    }

    public void setFens(Follow fens) {
        this.fens = fens;
    }
}