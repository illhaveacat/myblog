package com.stephen.myblog.mapper;

import com.stephen.myblog.entity.Metas;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MetasMapper {


    @Select("select * from t_metas where id=#{id}")
    public Metas findOne(long c);

    @Select("select * from t_metas where type=#{type}")
    List<Metas> findByType(String type);

}
