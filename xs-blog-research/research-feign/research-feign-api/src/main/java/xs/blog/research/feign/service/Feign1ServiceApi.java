package xs.blog.research.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xs.blog.utils.bean.Result;

/**
 * Created by xs on 2018/7/4
 */
@FeignClient(name = "research-feign-1")
public interface Feign1ServiceApi {
    @RequestMapping("/feign1/add")
    Result add(@RequestParam("a")int a,@RequestParam("b") int b);
}
