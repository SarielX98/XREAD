package com.sarielx.study.xr.service;

import com.sarielx.study.xr.entity.User;
import com.sarielx.study.xr.entity.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<User> getUserList();

    // 修改用户状态
    boolean updateStatus(Integer userId,Byte type);

    User getUserById(int userId);

    boolean addUser(User user);

    boolean modifyUser(User user);

    boolean deleteUser(Integer userId);
    // 用户注册
    boolean register(User user);
    // 用户登录
    User login(User user);
    // 管理员登录
    User superLogin(User user);
//    boolean sendCode(String phone);
    // 搜索用户
    List<User> searchUser(String keyword);
    // 修改头像
    String editUserpic(MultipartFile userpic);
    // 修改资料
    boolean editUserinfo(UserInfo userInfo);
    // 修改密码
    boolean repassword(String pwd,String newPwd,String reNewPwd);
    // 统计用户相关数据
    Map<String, Object> getCounts(Integer userId);
}
