package com.xs.research.config.client.service;

import com.github.pagehelper.PageHelper;
import com.xs.research.config.client.bean.User;
import com.xs.research.config.client.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xs.blog.utils.bean.Result;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public Result addUser(String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return Result.buildSuccess(userMapper.insert(user));
    }

    @RequestMapping(value = "/getAll", produces = {"application/json;charset=UTF-8"})
    public Result getAll(){
//        PageHelper.startPage(pageNum, pageSize);
        return Result.buildSuccess(userMapper.selectAllUser(0,0));
    }
}
