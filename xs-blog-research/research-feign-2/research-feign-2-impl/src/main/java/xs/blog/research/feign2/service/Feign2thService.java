package xs.blog.research.feign2.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xs.blog.research.feign.service.FeignServiceApi;
import xs.blog.research.feign2.bean.TestBean;
import xs.blog.research.feign2.service.api.Feign2ServiceApi;
import xs.blog.research.feign2.service.api.Feign2thServiceApi;
import xs.blog.utils.bean.Result;

@RestController
@RequestMapping("/feign2th")
public class Feign2thService implements Feign2thServiceApi {

    @Autowired
    private Feign2Service feign2Service;
    @Autowired
    private FeignServiceApi feignServiceApi;

    @RequestMapping("/testGet")
    @Override
    public Result testGet(int a, int b) {
        JSONObject obj = new JSONObject();

        Result rs = feignServiceApi.getAll();
        Result rs1 = feign2Service.getPage(1, 29);
        obj.put("rs", rs);
        obj.put("rs1", rs1);
        return Result.buildSuccess(obj);
    }
}
