package com.stephen.myblog.mapper;

import com.stephen.myblog.entity.Attach;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface AttachMapper {

    @Select("select count(1) from t_attach")
    long count();

    @Select("select * from t_attach limit #{currIndex},#{pageSize}")
    List<Attach> queryByPage(Map<String,Object> data);

    @Insert("insert into t_attach(createdate) values(now())")
    void  save(Attach attach);

    @Delete("delete from t_attach t where t.id=#{id}")
    void delete(long id);
}
