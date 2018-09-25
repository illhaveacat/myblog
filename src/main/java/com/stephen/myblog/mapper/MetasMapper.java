package com.stephen.myblog.mapper;

import com.stephen.myblog.entity.Metas;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MetasMapper {


    @Select("select * from t_metas where id=#{id}")
    public Metas findOne(long c);

    @Select("select * from t_metas where type=#{type}")
    List<Metas> findByType(String type);

    @Select("select * from t_metas where name=#{name} type=#{type}")
    Metas findByNameAndType(String name ,String type);

    @Insert("insert into t_metas(id,name,type) values(#{id},#{name},#{type})")
    void save(Metas metas);

    @Delete("delete from t_metas t where t.id=#{id}")
    void delete(long id);

    @Select("SELECT DATE_FORMAT(c.createdate,'%Y-%m') as datestr , COUNT(*) as count from t_content c GROUP BY datestr ORDER BY  datestr desc")
    @ResultType(HashMap.class)
    List<Map<String,Object>> findByGroupDate();
}
