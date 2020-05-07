package com.sarielx.study.xr.service.impl;

import com.auth0.jwt.JWT;
import com.sarielx.study.xr.dao.*;
import com.sarielx.study.xr.entity.Image;
import com.sarielx.study.xr.entity.Topic;
import com.sarielx.study.xr.entity.User;
import com.sarielx.study.xr.entity.UserInfo;
import com.sarielx.study.xr.handler.MyException;
import com.sarielx.study.xr.service.FollowService;
import com.sarielx.study.xr.service.ImageService;
import com.sarielx.study.xr.service.TokenService;
import com.sarielx.study.xr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private FollowMapper followMapper;
    @Autowired
    private FollowService followService;

    @Value("60")
    private String EXPIRE_TIME;

    @Override
    public List<User> getUserList() {
        List<User> userList = userMapper.select();
        for (int i = 0; i < userList.size(); i++){
            int userId = userList.get(i).getId();

            userList.get(i).setUserInfo(userInfoMapper.selectByUid(userId));
        }
        return userList;
    }

    @Override
    public boolean updateStatus(Integer userId, Byte type) {
        User user = userMapper.selectByPrimaryKey(userId);
        user.setStatus(type);
        int effectednum = userMapper.updateByPrimaryKeySelective(user);
        if(effectednum > 0){
            return true;
        }else{
            throw new MyException("111110","话题状态修改失败");
        }
    }

    @Override
    public User getUserById(int userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Transactional
    @Override
    public boolean addUser(User user) {
        if(user.getUsername() != null && !"".equals(user.getUsername())){
            try{
                int effectedNum = userMapper.insert(user);
                if(effectedNum > 0){
                    return true;
                }else{
                    throw new RuntimeException("注册失败！");
                }
            }catch (Exception e){
                throw new RuntimeException("注册失败:" + e.getMessage());
            }
        }else{
            throw new RuntimeException("不能为空！");
        }
    }

    @Transactional
    @Override
    public boolean modifyUser(User user) {
        // 更新区域信息
        int effectedNum = userMapper.updateByPrimaryKeySelective(user);
        if (effectedNum > 0) {
            return true;
        } else {
            throw new RuntimeException("更新区域信息失败!");
        }
    }

    @Override
    public boolean deleteUser(Integer userId) {
        int effectednum = userMapper.deleteByPrimaryKey(userId);
        if(effectednum > 0){
            return true;
        }else{
            throw new MyException("100001","删除失败");
        }
    }

    // 用户注册
    @Transactional
    @Override
    public boolean register(User user) {
        User existUser = userMapper.findUserByName(user.getUsername());
        if (existUser == null){
            try {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                String encodePwd = bCryptPasswordEncoder.encode(String.valueOf(user.getPassword()));
                User newUser = new User();
                newUser.setUsername(String.valueOf(user.getUsername()));
                newUser.setPassword(encodePwd);
                newUser.setStatus((byte) 1);
                newUser.setCreateTime((int) (System.currentTimeMillis() / 1000));
                userMapper.register(newUser);
                User curUser = userMapper.findUserByName(user.getUsername());
                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(curUser.getId());
                userInfoMapper.insertSelective(userInfo);
                return true;
            }catch (Exception e) {
                throw new MyException("102","注册失败："+e.getMessage());
            }

        }else {
            throw new MyException("101","该用户名已存在！");
        }
    }

    // 用户登录
    @Transactional
    @Override
    public User login(User user) {
        User existUser = userMapper.findUserByName(user.getUsername());
        if (existUser == null){
            throw new MyException("103","该用户名不存在！");
        }else{
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String dbPwd = existUser.getPassword();
            boolean matchsResult = bCryptPasswordEncoder.matches(String.valueOf(user.getPassword()),dbPwd);
            if(!matchsResult){
                throw new MyException("104","用户名或密码错误");
            }else{
                Date expiresDate = new Date(System.currentTimeMillis()+Integer.valueOf(EXPIRE_TIME)*60*1000);
                String token = tokenService.getToken(existUser,expiresDate);
                User curUser = existUser;
                curUser.setToken(token);
                curUser.setUserInfo(userInfoMapper.selectByUid(curUser.getId()));
                return curUser;
            }
        }
    }

    // 管理员登录
    @Transactional
    @Override
    public User superLogin(User user) {
        if (!user.getUsername().equals("admin111") ){
            throw new MyException("99999","该用户权限不足");
        }else{
            User existUser = userMapper.findUserByName(user.getUsername());
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String dbPwd = existUser.getPassword();
            boolean matchsResult = bCryptPasswordEncoder.matches(String.valueOf(user.getPassword()),dbPwd);
            if(!matchsResult){
                throw new MyException("104","用户名或密码错误");
            }else{
                Date expiresDate = new Date(System.currentTimeMillis()+Integer.valueOf(EXPIRE_TIME)*60*1000);
                String token = tokenService.getToken(existUser,expiresDate);
                User curUser = existUser;
                curUser.setToken(token);
                curUser.setUserInfo(userInfoMapper.selectByUid(curUser.getId()));
                return curUser;
            }
        }
    }

    // 搜索用户
    @Override
    public List<User> searchUser(String keyword) {
        return userMapper.searchUser(keyword);
    }

    // 修改用户头像
    @Transactional
    @Override
    public String editUserpic(MultipartFile userpic) {
        try{
            String imgUrl = imageService.uploadUserpic(userpic);
            // 从token中获取用户id
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            // 从 http 请求头中取出 token
            String token = request.getHeader("token");
            String userId = JWT.decode(token).getAudience().get(0);
            User user = new User();
            user.setId(Integer.valueOf(userId));
            user.setUserpic(imgUrl);
            userMapper.updateByPrimaryKeySelective(user);
            return imgUrl;
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException("600","修改失败");
        }
    }

    // 修改用户信息
    @Transactional
    @Override
    public boolean editUserinfo(UserInfo userInfo) {
        try{
            // 从token中获取用户id
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            // 从 http 请求头中取出 token
            String token = request.getHeader("token");
            String userId = JWT.decode(token).getAudience().get(0);
            userInfo.setUserId(Integer.valueOf(userId));
            userInfoMapper.editUserinfo(userInfo);
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException("800","修改失败");
        }
        return true;
    }

    // 修改用户密码
    @Transactional
    @Override
    public boolean repassword(String pwd, String newPwd, String reNewPwd) {
        if (newPwd.equals(reNewPwd)) {
            try{
                // 从token中获取用户id
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                // 从 http 请求头中取出 token
                String token = request.getHeader("token");
                String userId = JWT.decode(token).getAudience().get(0);
                String dbPwd = userMapper.selectByPrimaryKey(Integer.valueOf(userId)).getPassword();
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                boolean matchsResult = bCryptPasswordEncoder.matches(pwd,dbPwd);
                if (!matchsResult) throw new MyException("701","密码错误");
                String encodePwd = bCryptPasswordEncoder.encode(newPwd);
                User user = new User();
                user.setId(Integer.valueOf(userId));
                user.setPassword(encodePwd);
                userMapper.updateByPrimaryKeySelective(user);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            throw new MyException("700","两次密码不一致");
        }
        return true;
    }

    @Override
    public Map<String, Object> getCounts(Integer userId) {
        int post_count = postMapper.getPostnum(userId);
        int comments_count = commentMapper.getCommentnumByUser(userId);
        int withfollow_count = followMapper.getFollowsnum(userId);
        int withfen_count = followMapper.getFensnum(userId);
        int friend_count = followService.getFriendsnum(userId);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("post_count",post_count);
        modelMap.put("comments_count",comments_count);
        modelMap.put("withfollow_count",withfollow_count);
        modelMap.put("withfen_count",withfen_count);
        modelMap.put("friend_count",friend_count);
        return modelMap;
    }

    //    @Override
//    public boolean sendCode(String phone) {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        HttpSession session = request.getSession();
//        // 判断是否已经发送过
//        if(session.getAttribute("phone")!= null) throw new MyException("200","你操作得太快了");
//        // 生成4位验证码
//        int ran = (int)(Math.random()*(9999-1000+1))+1000;//产生1000-9999的随机数
//        String code = String.valueOf(ran);
//        // 发送验证码
//        String result = null;
//        // 模拟发送验证码
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("phone",phone);
//        jsonObject.put("code",code);
//        jsonObject.put("createTime",60);
//        session.setAttribute("code",jsonObject);
//        JSONObject userCode = (JSONObject)session.getAttribute("code");
//        System.out.println(userCode.get("code"));
//        throw new MyException("200","验证码"+userCode.get("code"));
//        // return true;
//    }
}
