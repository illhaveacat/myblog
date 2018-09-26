package com.stephen.myblog.mapper;

import com.stephen.myblog.entity.Relationships;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RelationshipsMapper {

    List<Relationships> findAllByCid(Long cid);

    List<Relationships> findAllByMid(Long mid);

    @Transactional
    @Delete("delete from t_relationships  where cid=#{cid} and type=#{type}")
    void deleteByCidAndType(@Param("cid") Long cid, @Param("type") String type);

    @Transactional
    @Delete("delete from t_relationships  where cid=#{cid}")
    void deleteByCid(@Param("cid")Long cid);

    @Insert("insert into t_relationships(id,cid,mid,type) values(#{id},#{cid},#{mid},#{type})")
    void save(Relationships r);

}
