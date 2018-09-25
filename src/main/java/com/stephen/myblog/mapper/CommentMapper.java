package com.stephen.myblog.mapper;

import com.stephen.myblog.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper {

    @Select("select * from t_comment where content_id=#{id} order by comment_time desc")
    @Results({
            @Result(property = "commentTime",  column = "comment_time")
    })
    List<Comment> findByContentId(long id);

    @Insert("insert into t_comment(id,nickname,email,comment,comment_time,content_id) values(#{id},#{nickname},#{email},#{comment},#{commentTime},#{contentId})")
    void save(Comment comment);

}
