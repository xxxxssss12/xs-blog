package com.xs.blog;

import com.xs.blog.env.CommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by xs on 2018/7/6
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.xs.blog"})
@EnableDiscoveryClient
@EnableHystrix
//@ComponentScan(basePackages={"com.xs.blog"})
public class FeignClientServiceApplication {

    public static void main(String[] args) {
        CommonConfig.init(args);
        SpringApplication.run(FeignClientServiceApplication.class, args);
    }
}
