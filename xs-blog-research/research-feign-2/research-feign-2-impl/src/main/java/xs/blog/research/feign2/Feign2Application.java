package xs.blog.research.feign2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by xs on 2018/7/5
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class Feign2Application {
    public static void main(String[] args) {
        SpringApplication.run(Feign2Application.class, args);
    }
}
