package com.xs.blog.auth.hystrix;

import com.xs.blog.auth.bean.User;
import com.xs.blog.auth.feign.UserServiceApi;
import xs.blog.utils.bean.Result;

/**
 * Created by xs on 2018/7/9
 */
public class UserServiceFallback implements UserServiceApi {
    @Override
    public Result saveUser(String username, String password) {
        return Result.hystrixFail();
    }

    @Override
    public Result authPwd(String username, String password) {
        return Result.hystrixFail();
    }

    @Override
    public User getByUsername(String username) {
        return null;
    }
}
