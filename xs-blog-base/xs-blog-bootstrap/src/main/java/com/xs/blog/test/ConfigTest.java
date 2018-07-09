package com.xs.blog.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xs.blog.utils.bean.Result;

/**
 * Created by xs on 2018/7/9
 */
@RestController
@RequestMapping("/configTest")
public class ConfigTest {

    @Value("${spring.datasource.url}")
    private String config;

    @RequestMapping("/hi")
    public Result hi() {
        return Result.buildSuccess(config);
    }
}
