package xs.blog.research.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by xs on 2018/7/4
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class Feign1Application {
    public static void main(String[] args) {
        SpringApplication.run(Feign1Application.class, args);
    }
}
