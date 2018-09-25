package com.stephen.myblog.mapper;

import com.stephen.myblog.entity.Links;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface LinksMapper {

    @Select("select * from t_links t where t.id=#{id}")
    Links findOne(long id);

    @Select("select * from t_links t")
    List<Links> findAll();

    @Select("select count(1) from t_links")
    long count();

    @Select("select * from t_links limit #{currIndex},#{pageSize}")
    List<Links> queryByPage(Map<String,Object> data);


    @Insert("insert into t_links(id,name,linklogo,linkurl,sort) values(#{id},#{name},#{linklogo},#{linkurl},#{sort})")
    void  save(Links links);

    @Delete("delete from t_links t where t.id=#{id}")
    void delete(long id);
}
