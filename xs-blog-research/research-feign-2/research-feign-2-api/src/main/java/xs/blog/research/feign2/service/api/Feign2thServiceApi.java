package xs.blog.research.feign2.service.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import xs.blog.utils.bean.Result;

@FeignClient(name="research-feign-2")
public interface Feign2thServiceApi {

    @RequestMapping("/feign2th/testGet")
    Result testGet(int a, int b);

}
