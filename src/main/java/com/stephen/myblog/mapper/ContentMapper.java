package com.stephen.myblog.mapper;

import com.stephen.myblog.entity.Content;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface ContentMapper {

    @Select("SELECT * FROM t_content")
    List<Content> getAll();

    @Select("SELECT * FROM t_content WHERE id = #{id}")
    Content getOne(Long id);

    @Select("select count(1) from t_content")
    long count();

    @Select("SELECT * FROM t_content t  ORDER BY id desc LIMIT 8")
    List<Content> getRecentList();

    @Insert("insert into t_content(id,author_id，categories,content,createdate,fmt_type,hits,status,tags,thumb_img,title)" +
            " values(#{id},#{author_id},#{categories},#{content},now(),#{fmt_type},#{hits},#{status},#{tags},#{thumb_img},#{title})")
    void save(Content content);

    @Update({"<script>update t_content t set modifydate=now()," +
            "<if test='#{categories} != null'>categories = #{categories},</if>"+
            "<if test='#{content} != null'>content = #{content},</if>"+
            "<if test='#{fmt_type} != null'>fmt_type = #{fmt_type},</if>"+
            "<if test='#{hits} != null'>hits = #{hits},</if>"+
            "<if test='#{status} != null'>status = #{status},</if>"+
            "<if test='#{tags} != null'>tags = #{tags},</if>"+
            "<if test='#{thumb_img} != null'>thumb_img = #{thumb_img},</if>"+
            "<if test='#{title} != null'>title = #{title}</if>"+
            "where t.id=#{id}</script>"}
    )
    void update(Content content);

    @Delete("delete from t_content t where t.id=#{id}")
    void delete(long id);

    @Select("select * from t_content limit #{currIndex},#{pageSize}")
    List<Content> queryByPage(Map<String,Object> data);


    @Select("SELECT * from t_content c where DATE_FORMAT(c.createdate,'%Y-%m') = #{dateStr}")
    List<Content> findByCreatedate(@Param("dateStr") String dateStr);


    @Select("SELECT c.* from t_content c join t_relationships s on s.cid = c.id WHERE s.mid = #{mid} limit #{currIndex},#{pageSize}")
    List<Content> findContentByTagsPage(@Param("mid")long mid,int currIndex,int pageSize);


    @Select("SELECT count(c.*) from t_content c join t_relationships s on s.cid = c.id WHERE s.mid = #{mid}")
    long countByTagsPage(@Param("mid")long mid);


    @Select("SELECT c.* from t_content c join t_relationships s on s.cid = c.id WHERE s.mid = #{mid} limit #{currIndex},#{pageSize}")
    List<Content> findContentByCategoryPage(@Param("mid")long mid,int currIndex,int pageSize);


    @Select("SELECT count(c.*) from t_content c join t_relationships s on s.cid = c.id WHERE s.mid = #{mid}")
    long countByCategoryPage(@Param("mid")long mid);

    @Select("SELECT c.* from t_content c where c.title like '%#{title}%' limit #{currIndex},#{pageSize} ")
    List<Content> findContentByKeywordsPage(String title,int currIndex,int pageSize);


    @Select("SELECT count(c.*) from t_content c where c.title like '%#{title}%'")
    long  countByKeywords(String title);

//    @Insert("INSERT INTO users(title,slug,createdate,modifydate,content,author_id,type,fmt_type,thumb_img,tags,categories) VALUES(#{userName}, #{passWord}, #{userSex})")
//    void insert(UserEntity user);
//
//    @Update("UPDATE users SET userName=#{userName},nick_name=#{nickName} WHERE id =#{id}")
//    void update(UserEntity user);
//
//    @Delete("DELETE FROM users WHERE id =#{id}")
//    void delete(Long id);
}
