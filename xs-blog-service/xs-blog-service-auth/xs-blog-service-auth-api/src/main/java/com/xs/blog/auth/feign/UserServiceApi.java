package com.xs.blog.auth.feign;

import com.xs.blog.auth.bean.User;
import com.xs.blog.auth.hystrix.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import xs.blog.utils.bean.Result;

/**
 * Created by xs on 2018/7/9
 */
@FeignClient(name = "blog-service-auth", fallback = UserServiceFallback.class)
public interface UserServiceApi {

    @RequestMapping(value="/auth/user/saveUser", method = RequestMethod.POST)
    Result saveUser(String username, String password);

    @RequestMapping(value = "/auth/user/authPwd")
    Result authPwd(String username, String password);

    @RequestMapping("/getByUsername")
    User getByUsername(String username);
}