package com.stephen.myblog.controller.manager;

import com.stephen.myblog.common.AjaxResult;
import com.stephen.myblog.controller.BaseController;
import com.stephen.myblog.entity.Comment;
import com.stephen.myblog.mapper.CommentMapper;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
@RequestMapping("/comment")
public class CommentController  extends BaseController {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommentMapper commentMapper;

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult save(HttpServletRequest req){
        logger.info("======保存游客评论=======");
        String nickname=req.getParameter("nickname");
        String email=req.getParameter("email");
        String comment=req.getParameter("comment");
        long contentId=Long.valueOf(req.getParameter("contentId"));
        Comment bean=new Comment();
        bean.setComment(comment);
        bean.setCommentTime(new Date());
        bean.setContentId(contentId);
        bean.setNickname(nickname);
        bean.setEmail(email);
        commentMapper.save(bean);
        return new AjaxResult(true,"保存评论成功",null);
    }

}
