package com.xs.blog.auth;

import com.xs.blog.redis.JedisConnector;
import com.xs.blog.test.BaseTest;
import org.junit.Test;

/**
 * Created by xs on 2018/7/10
 */
public class UserTest extends BaseTest {

    @Test
    public void testJedis() {
        JedisConnector.set("haha", "111");
        System.out.println(JedisConnector.get("haha"));
    }
}
