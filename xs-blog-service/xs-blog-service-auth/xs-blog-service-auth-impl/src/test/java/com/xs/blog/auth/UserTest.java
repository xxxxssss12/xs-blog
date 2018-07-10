package com.xs.blog.auth;

import com.alibaba.fastjson.JSON;
import com.xs.blog.redis.JedisConnector;
import com.xs.blog.test.BaseTest;
import com.xs.blog.test.ConfigTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xs on 2018/7/10
 */
public class UserTest extends BaseTest {

    @Autowired
    ConfigTest configTest;
    @Test
    public void testJedis() {
        System.out.println(JSON.toJSONString(configTest.hi()));
        JedisConnector.set("haha", "111");
        System.out.println(JedisConnector.get("haha"));
    }
}
