package com.stephen.myblog.controller.manager;

import com.stephen.myblog.common.AjaxResult;
import com.stephen.myblog.entity.Links;
import com.stephen.myblog.mapper.LinksMapper;
import com.stephen.myblog.servcie.ILinkService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/links")
public class LinksController {

    private static Logger log = LoggerFactory.getLogger(LinksController.class);

    private final static Integer SIZE = 10;

    @Autowired
    private LinksMapper linksMapper;

    @Autowired
    private ILinkService linkService;


    @RequestMapping("/index")
    public String index(ModelMap map, HttpServletRequest req){
        Integer page = req.getParameter("page")==null?1:(Integer.parseInt(req.getParameter("page")));
        Integer size = SIZE;
        List<Links> linksPage = linkService.queryByPage(page,size);
        long count=linksMapper.count();
        map.put("totals",count);
        map.put("page",page+1);
        map.put("linkslist",linksPage);
        map.put("menu_code","mainlinks");
        return "links/index";
    }
    @RequestMapping("/save")
    @ResponseBody
    public String save(Links links){

        if(StringUtils.isEmpty(links.getName())){
            return "0";
        }

        linksMapper.save(links);
        log.info("保存友情链接");
        return "1";
    }

    @RequestMapping(value = "/edit/{id}")
    @ResponseBody
    public AjaxResult edit(@PathVariable Long id, ModelMap map){

        Links links= linksMapper.findOne(id);

        log.info("编辑->links : "+links.toString());

        return new AjaxResult(links);
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable Long id){

        linksMapper.delete(id);
        log.info("删除->id : "+id);

        return "1";
    }
}
