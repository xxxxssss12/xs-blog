package xs.blog.research.feign.service;

import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xs.blog.utils.bean.Result;

/**
 * Created by xs on 2018/7/4
 */
@FeignClient(name = "research-config-client")
public interface FeignServiceApi {

//    @Headers({"Content-Type: application/json","Accept: application/json"})
//    @RequestLine("POST /user/add")
    @RequestMapping("/user/add")
    Result add(@RequestParam("username") String username, @RequestParam("password") String password);

    @RequestMapping("/user/getAll")
    Result getAll();
}
