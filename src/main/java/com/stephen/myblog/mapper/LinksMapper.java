package com.stephen.myblog.mapper;

import org.apache.ibatis.annotations.Select;

public interface LinksMapper {

    @Select("select count(1) from t_links")
    long count();
}
