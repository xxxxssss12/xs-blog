package xs.blog.research.feign2.service.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import xs.blog.research.feign2.bean.TestBean;
import xs.blog.utils.bean.Result;

/**
 * Created by xs on 2018/7/5
 */
@FeignClient(name="research-feign-2")
public interface Feign2ServiceApi {

    @RequestMapping("/feign2/getPage")
    Result getPage(Integer pagenum, Integer pagesize);

    @RequestMapping("/feign2/save")
    Result save(TestBean bean);
}
