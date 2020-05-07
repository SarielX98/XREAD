package com.sarielx.study.xr.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sarielx.study.xr.entity.Topic;
import com.sarielx.study.xr.entity.User;
import com.sarielx.study.xr.entity.UserInfo;
import com.sarielx.study.xr.handler.ResultInfo;
import com.sarielx.study.xr.jwtToken.CheckToken;
import com.sarielx.study.xr.jwtToken.PassToken;
import com.sarielx.study.xr.service.TokenService;
import com.sarielx.study.xr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

//    @RequestMapping(value = "sendcode", method = RequestMethod.POST)
//    private Map<String,Object> sendCode(@Pattern(regexp = "^1[34578]\\d{9}$",message = "手机号码不合法") @RequestParam String phone){
//        Map<String,Object> modelMap = new HashMap<String, Object>();
//        modelMap.put("success",userService.sendCode(phone));
//        return modelMap;
//    }
    // 注册
    @PassToken
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    private Map<String, Object> register(@RequestBody @Valid User user){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success",userService.register(user));
        return modelMap;
    }

    // 登录
    @RequestMapping(value="/login",method = RequestMethod.POST)
    private Map<String,Object> login(@RequestBody @Valid User user) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        User curUser = userService.login(user);
        modelMap.put("user", curUser);
        return modelMap;
    }

    // 管理员登录
    @RequestMapping(value="/superlogin",method = RequestMethod.POST)
    private Map<String,Object> superLogin(@RequestBody @Valid User user) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        User curUser = userService.superLogin(user);
        modelMap.put("user", curUser);
        return modelMap;
    }

    // 退出登录
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    private Map<String, Object> logout(@RequestHeader String token){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (StringUtils.isEmpty(token)) {
            modelMap.put("success","退出成功");
        }
        return modelMap;
    }

    // 搜索用户
    @RequestMapping(value = "/searchuser",method = RequestMethod.POST)
    private Map<String, Object> searchUser(@RequestParam String keyword,@RequestParam("page") int pageNum){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        PageHelper.startPage(pageNum,10);
        List<User> userList = userService.searchUser(keyword);
        if(userList.size() > 0){
            PageInfo<User> pageInfo = new PageInfo<>(userList);
            modelMap.put("listUser",pageInfo);
        }
        else {
            modelMap.put("false","查询不到相关用户");
        }
        return modelMap;
    }

    // 获取用户列表
    @RequestMapping(value="/getuserlist/{page}", method = RequestMethod.GET)
    private Map<String,Object> getUserList(@PathVariable("page") int pageNum, @RequestParam("limit") int pageSize){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        PageHelper.startPage(pageNum,pageSize);
        List<User> list = userService.getUserList();
        for (int i = 0; i < list.size(); i++){
            if(list.get(i).getUsername().equals("admin111")){
                list.remove(i);
            }
        }
        PageInfo<User> pageInfo = new PageInfo<>(list);
        modelMap.put("list",pageInfo);
        return modelMap;
    }

    // 修改用户状态
    @CheckToken
    @RequestMapping(value = "/{id}/update_status",method = RequestMethod.POST)
    private Map<String, Object> updateStatus(@PathVariable Integer id,@RequestParam Byte type){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success",userService.updateStatus(id,type));
        return modelMap;
    }

    // 删除用户
    @CheckToken
    @RequestMapping(value = "/{id}/delete",method = RequestMethod.POST)
    private Map<String, Object> updateStatus(@PathVariable Integer id){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success",userService.deleteUser(id));
        return modelMap;
    }

    @RequestMapping(value="/getuserbyid", method = RequestMethod.GET)
    private Map<String,Object> getUserById(Integer userId){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        User user = userService.getUserById(userId);
        modelMap.put("user",user);
        return modelMap;
    }

    @RequestMapping(value="/adduser", method = RequestMethod.POST)
    private Map<String,Object> addUser(@RequestBody User user){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success", userService.addUser(user));
        return modelMap;
    }

    @RequestMapping(value="/modifyuser", method = RequestMethod.POST)
    private Map<String,Object> modifyUser(@Valid @RequestBody User user){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success", userService.modifyUser(user));
        return modelMap;
    }

    @CheckToken
    @RequestMapping(value="/edituserpic", method = RequestMethod.POST)
    private Map<String,Object> editUserpic(@RequestParam("userpic") MultipartFile userpic){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success", userService.editUserpic(userpic));
        return modelMap;
    }

    @CheckToken
    @RequestMapping(value = "/edituserinfo",method = RequestMethod.POST)
    private Map<String, Object> editUserinfo(@RequestBody UserInfo userInfo){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success",userService.editUserinfo(userInfo));
        return modelMap;
    }

    @CheckToken
    @RequestMapping(value = "repassword",method = RequestMethod.POST)
    private Map<String, Object> rePassword(@RequestParam String password,@RequestParam String newPassword,@RequestParam String reNewPassword){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success",userService.repassword(password, newPassword, reNewPassword));
        return modelMap;
    }

    @RequestMapping(value = "getcounts",method = RequestMethod.GET)
    private Map<String, Object> getCounts(@RequestParam Integer userId){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("data",userService.getCounts(userId));
        return modelMap;
    }

}
