package com.xs.blog.auth.service;

import com.xs.blog.auth.bean.User;
import com.xs.blog.auth.dao.UserMapper;
import com.xs.blog.auth.feign.UserServiceApi;
import com.xs.blog.auth.util.PwdUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xs.blog.utils.bean.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xs on 2018/7/9
 */
@RestController
@RequestMapping("/auth/user")
public class UserService implements UserServiceApi {
    @Autowired
    private UserMapper userMapper;
    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    @Override
    public Result saveUser(String username, String password) {
        return null;
    }

    @Override
    @RequestMapping("/authPwd")
    public Result authPwd(String username, String password) {
        User user = getByUsername(username);
        if (user == null) return Result.buildFail("USER_0001","用户不存在");
        if (PwdUtil.isEquals(password, user.getPassword())) return Result.buildFail("USER_0002", "用户密码不匹配");
        return Result.buildSuccess();
    }

    @Override
    @RequestMapping("/getByUsername")
    public User getByUsername(String username) {
        if (StringUtils.isEmpty(username)) return null;
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        User user = userMapper.getOne(map);
        return user;
    }
}
