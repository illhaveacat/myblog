package com.stephen.myblog.mapper;

import com.stephen.myblog.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CommentMapper {

    @Select("select * from t_comment where content_id=#{id} order by comment_time desc")
    @Results({
            @Result(property = "commentTime",  column = "comment_time")
    })
    List<Comment> findByContentId(long id);

    @Insert("insert into t_comment(id,nickname,email,comment,comment_time,content_id) values(#{id},#{nickname},#{email},#{comment},#{commentTime},#{contentId})")
    void save(Comment comment);


    @Select({"<script>SELECT distinct a.*,b.title FROM t_comment a left join t_content b on" +
            " a.content_id=b.id where b.title like '%#{tilteKeys}%' or b.content like '%#{contentKeys}%' " +
            "order by a.content_id desc,a.comment_time desc </script>"})
    @ResultType()
    List<Comment> queryByPage(@Param("tilteKeys") String tilteKeys,@Param("contentKeys")String contentKeys);

}
