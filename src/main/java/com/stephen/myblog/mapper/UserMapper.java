package com.stephen.myblog.mapper;


import com.stephen.myblog.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select *  from t_user where username=#{username}")
    User getOne(String username);


}