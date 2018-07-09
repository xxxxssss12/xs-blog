package com.xs.blog.auth.dao;

import com.xs.blog.auth.bean.Permission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
}