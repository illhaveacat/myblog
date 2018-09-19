package com.stephen.myblog.mapper;

import org.apache.ibatis.annotations.Select;

public interface AttachMapper {

    @Select("select count(1) from t_attach")
    public long count();

}
