package com.stephen.myblog.mapper;

import com.stephen.myblog.entity.Content;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ContentMapper {

    @Select("SELECT * FROM t_content")
    List<Content> getAll();

    @Select("SELECT * FROM t_content WHERE id = #{id}")
    Content getOne(Long id);

    @Select("select count(1) from t_content")
    long count();

    @Select("SELECT * FROM t_content t  ORDER BY id desc LIMIT 8")
    List<Content> getRecentList();

//    @Insert("INSERT INTO users(title,slug,createdate,modifydate,content,author_id,type,fmt_type,thumb_img,tags,categories) VALUES(#{userName}, #{passWord}, #{userSex})")
//    void insert(UserEntity user);
//
//    @Update("UPDATE users SET userName=#{userName},nick_name=#{nickName} WHERE id =#{id}")
//    void update(UserEntity user);
//
//    @Delete("DELETE FROM users WHERE id =#{id}")
//    void delete(Long id);
}
