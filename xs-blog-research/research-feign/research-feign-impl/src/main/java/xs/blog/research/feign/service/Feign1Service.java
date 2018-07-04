package xs.blog.research.feign.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xs.blog.utils.bean.Result;

/**
 * Created by xs on 2018/7/4
 */
@RestController
@RequestMapping("/feign1")
public class Feign1Service implements Feign1ServiceApi {
    @Autowired
    FeignServiceApi feignServiceApi;
    @Override
    @RequestMapping("/add")
    public Result add(int a, int b) {
        return Result.buildSuccess(a+b);
    }

    @RequestMapping("/getProxy")
    public Result getProxy() {
        return feignServiceApi.getAll();
    }

}
