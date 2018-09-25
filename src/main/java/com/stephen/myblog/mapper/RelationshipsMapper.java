package com.stephen.myblog.mapper;

import com.stephen.myblog.entity.Relationships;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RelationshipsMapper {

    List<Relationships> findAllByCid(Long cid);

    List<Relationships> findAllByMid(Long mid);

    @Transactional
    @Delete("delete from t_relationships t where t.cid=#{cid} and t.type=#{type}")
    void deleteByCidAndType(Long cid,String type);

    @Transactional
    @Delete("delete from t_relationships t where t.cid=#{cid}")
    void deleteByCid(Long cid);

    @Insert("insert into t_relationships(id,cid,mid,type) values(#{id},#{cid},#{mid},#{type})")
    void save(Relationships r);

}
