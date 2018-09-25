package com.stephen.myblog.controller.manager;

import com.stephen.myblog.entity.Attach;
import com.stephen.myblog.mapper.AttachMapper;
import com.stephen.myblog.servcie.IAttachService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class AttachController {

    private static Logger log = LoggerFactory.getLogger(AttachController.class);

    private final static Integer SIZE = 12;


    @Autowired
    private AttachMapper attachMapper;

    @Autowired
    private IAttachService attachService;

    @RequestMapping("/index")
    public String index(ModelMap map, HttpServletRequest req){
        Integer page = req.getParameter("page")==null?1:(Integer.parseInt(req.getParameter("page")));
        Integer size = SIZE;
        List<Attach> attachs = attachService.queryByPage(page,size);
        long count =attachMapper.count();
        map.put("totals",count);
        map.put("page",page+1);
        map.put("attachs",attachs);
        map.put("menu_code","mainfile");
        return "attach/index";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String save(Attach attach){
//      attach.setUserid();
        attach.setCreatedate(new Date());
        attachMapper.save(attach);
        log.info("保存附件");
        return "1";
    }


    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable Long id){
        attachMapper.delete(id);
        log.info("删除->id : "+id);
        return "1";
    }
}
