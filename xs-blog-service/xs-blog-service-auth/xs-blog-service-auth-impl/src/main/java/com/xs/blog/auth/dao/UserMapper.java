package com.xs.blog.auth.dao;

import org.apache.ibatis.annotations.Mapper;
import com.xs.blog.auth.bean.User;

import java.util.Map;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User getOne(Map<String, Object> map);
}