package xs.blog.research.feign2.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xs.blog.research.feign.service.Feign1ServiceApi;
import xs.blog.research.feign2.bean.TestBean;
import xs.blog.research.feign2.dao.TestBeanMapper;
import xs.blog.research.feign2.service.api.Feign2ServiceApi;
import xs.blog.utils.bean.Result;

import java.util.List;

/**
 * Created by xs on 2018/7/5
 */
@RestController
@RequestMapping("/feign2")
public class Feign2Service implements Feign2ServiceApi {
    @Autowired
    private Feign1ServiceApi feign1ServiceApi;

    @Autowired
    private TestBeanMapper testBeanMapper;
    @Override
    @RequestMapping("/getPage")
    public Result getPage(Integer pagenum, Integer pagesize) {
        if (pagenum == null) pagenum = 1;
        if (pagesize == null) pagesize = 10;
        PageHelper.startPage(pagenum, pagesize);
        System.out.println(JSON.toJSONString(feign1ServiceApi.add(pagenum, pagesize)));

        List<TestBean> beanList = testBeanMapper.getPage(null);
        return Result.buildSuccess(beanList);
    }

    @Override
    @RequestMapping("/save")
    public Result save(TestBean bean) {
        return Result.buildSuccess(testBeanMapper.insert(bean));
    }
}
