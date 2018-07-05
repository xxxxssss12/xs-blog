package xs.blog.research.feign2.dao;

import org.apache.ibatis.annotations.Mapper;
import xs.blog.research.feign2.bean.TestBean;

import java.util.List;
import java.util.Map;

@Mapper
public interface TestBeanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TestBean record);

    int insertSelective(TestBean record);

    TestBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TestBean record);

    int updateByPrimaryKey(TestBean record);

    List<TestBean> getPage(Map<String, Object> condition);
}