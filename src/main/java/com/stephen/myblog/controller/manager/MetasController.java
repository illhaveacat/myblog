package com.stephen.myblog.controller.manager;

import com.stephen.myblog.common.AjaxResult;
import com.stephen.myblog.entity.Content;
import com.stephen.myblog.entity.Metas;
import com.stephen.myblog.entity.Relationships;
import com.stephen.myblog.enums.MetaType;
import com.stephen.myblog.mapper.ContentMapper;
import com.stephen.myblog.mapper.MetasMapper;
import com.stephen.myblog.mapper.RelationshipsMapper;
import com.stephen.myblog.servcie.MetasService;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/metas")
public class MetasController {

    private static Logger log = LoggerFactory.getLogger(MetasController.class);

    private final static Integer SIZE = 10;
    @Autowired
    private MetasMapper metasMapper;

    @Autowired
    private RelationshipsMapper relationshipsMapper;

    @Autowired
    private MetasService metasService;

    @Autowired
    private ContentMapper contentMapper;

    //按钮随机颜色
    private static final String[] COLORS = {"btn-success", "btn-warning", "btn-danger", "btn-success", "btn-secondary", "btn-primary"};


    @RequestMapping("/index")
    public String index(ModelMap map, HttpServletRequest req) {
        List<Map<String, Object>> byCategoryOrTags = metasService.getByCategoryOrTags(MetaType.CATEGORY.getName());
        map.put("categories", byCategoryOrTags);
        List<Map<String, Object>> tags = metasService.getByCategoryOrTags(MetaType.TAGS.getName());
        map.put("tags", tags);
        map.put("colors", COLORS);
        map.put("menu_code", "mainmetas");
        return "metas/index";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(HttpServletRequest req) {
        Metas meta = new Metas();
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String type = req.getParameter("type");
        if (StringUtils.isNotBlank(id)) meta.setId(Long.valueOf(id));
        if (StringUtils.isEmpty(type)) type = MetaType.CATEGORY.getName();
        meta.setName(name);
        meta.setType(type);
        metasMapper.save(meta);
        return "1";
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable Long id) {
        if (id == null) {
            return "0";
        }
        metasMapper.delete(id);
        log.info("删除->id : " + id);
        return "1";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult edit(@PathVariable Long id) {
        Metas one = metasMapper.findOne(id);
        return new AjaxResult(one);
    }

    @RequestMapping(value = "/delete_t/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String delete_t(@PathVariable Long id) {
        if (id == null) {
            return "0";
        }
        Metas metas = metasMapper.findOne(id);
        //得到和它关联的所有的文章。
        List<Relationships> rs = relationshipsMapper.findAllByMid(id);
        for (Relationships r : rs) {
            Content content = contentMapper.getOne(r.getCid());
            content.setTags(reMeta(metas.getName(), content.getTags()));
            contentMapper.save(content);
        }
        metasMapper.delete(id);
        log.info("删除->id : " + id);
        return "1";
    }

    @RequestMapping(value = "/delete_c/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String delete_c(@PathVariable Long id) {
        if (id == null) {
            return "0";
        }
        Metas metas = metasMapper.findOne(id);
        //得到和它关联的所有的文章。
        List<Relationships> rs = relationshipsMapper.findAllByMid(id);
        for (Relationships r : rs) {
            Content content = contentMapper.getOne(r.getCid());
            content.setCategories(reCategory(String.valueOf(id), content.getCategories()));
            contentMapper.save(content);
        }
        metasMapper.delete(id);
        log.info("删除->id : " + id);
        return "1";
    }

    private String reMeta(String name, String metas) {
        String[] ms = metas.split(",");
        StringBuffer sbuf = new StringBuffer();
        for (String m : ms) {
            if (!name.equals(m)) {
                sbuf.append(",").append(m);
            }
        }
        if (sbuf.length() > 0) {
            return sbuf.substring(1);
        }
        return "";
    }

    private String reCategory(String id, String categories) {
        String[] ms = categories.split(",");
        StringBuffer sbuf = new StringBuffer();
        for (String m : ms) {
            if (!id.equals(m)) {
                sbuf.append(",").append(m);
            }
        }
        if (sbuf.length() > 0) {
            return sbuf.substring(1);
        }
        return "";
    }
}
