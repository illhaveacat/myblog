package com.stephen.myblog.controller;

import com.alibaba.fastjson.JSON;
import com.stephen.myblog.common.AjaxResult;
import com.stephen.myblog.entity.Comment;
import com.stephen.myblog.entity.Content;
import com.stephen.myblog.entity.Links;
import com.stephen.myblog.entity.Metas;
import com.stephen.myblog.enums.MetaType;
import com.stephen.myblog.mapper.CommentMapper;
import com.stephen.myblog.mapper.ContentMapper;
import com.stephen.myblog.mapper.MetasMapper;
import com.stephen.myblog.model.Archive;
import com.stephen.myblog.model.SearchQo;
import com.stephen.myblog.servcie.SiteService;
import com.stephen.myblog.util.SystemUtils;
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
import java.util.*;

@Controller
@RequestMapping(value = {"/home.do","/home"})
public class IndexController extends  BaseController {

    private static Logger log = LoggerFactory.getLogger(IndexController.class);

    private final static Integer SIZE = 12;

    private static final String[] ICONS = {"bg-ico-book", "bg-ico-game", "bg-ico-note", "bg-ico-chat", "bg-ico-code", "bg-ico-image", "bg-ico-web", "bg-ico-link", "bg-ico-design", "bg-ico-lock"};

    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private MetasMapper metasMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    SiteService siteService;

    @RequestMapping("/index")
    public String index(ModelMap map, HttpServletRequest req){

        Integer page = req.getParameter("page")==null?0:(Integer.parseInt(req.getParameter("page"))-1);
        Integer size = SIZE;

        List<Content> contents = contentMapper.getAll();

//        List<Metas> cs = getCategoryMetas();

//        String basepath = req.getContextPath();
        for (Content c :contents){
//            log.info("转换后的分类字符串------"+show_categories(req,c.getCategories()));
            c.setCategories(show_categories(req,c.getCategories()));
            c.setThumb_img(gen_thumb(c));
        }

        map.put("totals",contents.size());
        map.put("page",page+1);
        map.put("articles",contents);
        map.put("icons",ICONS);
//        map.put("footas",getRecentArticles());
        return this.render("index");
    }

    @RequestMapping(value = "/article/{id}")
    public String detail(@PathVariable Long id, ModelMap map){

        Content content = contentMapper.getOne(id);
        if(null == content){
            return "403";
        }

        if("markdown".equals(content.getFmt_type())){
            log.info("===============文章加载成功===============");
            content.setContent(SystemUtils.markdownToHtml(content.getContent()));
        }
        List<Metas> cs = getCategoryMetas();
        String categoryString = convertToString(content.getCategories(),cs);
        content.setCategories(categoryString);
        map.put("c",content);
        //标签需要单独取出来进行迭代
        List<String> tags = toTags(content.getTags());
        map.put("tags",tags);
        //加载评论
        List<Comment> comments=commentMapper.findByContentId(id);
        log.info("===============评论加载成功===============");
        log.info("文章评论："+ JSON.toJSONString(comments));
        map.put("comments",comments);
        return this.render("detail");
    }

    public  String show_categories(HttpServletRequest req,String categories) {
        String basepath = req.getContextPath();
        if (StringUtils.isNotBlank(categories)) {
            String[] arr = categories.split(",");
            StringBuffer sbuf = new StringBuffer();
            for (String c : arr) {
                Metas   m = metasMapper.findOne(Long.valueOf(c));
                sbuf.append("<a href=\""+basepath+ "/templates/pages/category/" +c + "\">" + m.getName() + "</a>");
            }
            return sbuf.toString();
        }
        return show_categories(req,"1");
    }

    /**
     * 归档
     * @param map
     * @return
     */
    @RequestMapping(value = "archives",method = RequestMethod.GET)
    public String archives(ModelMap map){
        List<Archive> archives = siteService.getArchivesList();
        map.put("archives",archives);
        return this.render("archives");
    }


    @RequestMapping(value = "links",method = RequestMethod.GET)
    public String links(ModelMap map){
        List<Links> linksList = siteService.getLinksList();
        map.put("links",linksList);
        return this.render("links");
    }


    @RequestMapping(value = "about",method = RequestMethod.GET)
    public String about(ModelMap map){
        return this.render("about");
    }


    @RequestMapping(value = "search/{keywords}",method = RequestMethod.GET)
    public String search(HttpServletRequest req, ModelMap map, @PathVariable String keywords) {
        Integer page = req.getParameter("page")==null?0:(Integer.parseInt(req.getParameter("page"))-1);
        SearchQo sq = new SearchQo();
        sq.setTitle(keywords);
        sq.setPage(page);
        List<Content> contents = siteService.findContentByKeywords(sq);
        long count=contentMapper.countByKeywords(keywords);
        map.put("keywords",keywords);
        map.put("totals",count);
        map.put("page",page+1);
        map.put("pageflag","search");
        map.put("type","搜索");
        map.put("icons",ICONS);
        map.put("clists",contents);
        return this.render("page-category");
    }

    @RequestMapping(value = "tag/{keywords}",method = RequestMethod.GET)
    public String tag(HttpServletRequest req, ModelMap map, @PathVariable String keywords) {
        Integer page = req.getParameter("page")==null?0:(Integer.parseInt(req.getParameter("page"))-1);
        if(StringUtils.isEmpty(keywords)) return "403";
        Metas metas = metasMapper.findByNameAndType(keywords, MetaType.TAGS.getName());
        if(metas == null) return "403";
        SearchQo sq = new SearchQo();
        sq.setTag(keywords);

        sq.setPage(page);

        List<Content> contents = siteService.findContentByTags(sq);

        long count=contentMapper.countByTagsPage(metas.getId());

        map.put("keywords",keywords);
        map.put("totals",count);
        map.put("page",page+1);
        map.put("type","标签");
        map.put("pageflag","tag");
        map.put("icons",ICONS);
        map.put("clists",contents);

        return this.render("page-category");
    }


    @RequestMapping(value = "category/{keywords}",method = RequestMethod.GET)
    public String category(HttpServletRequest req, ModelMap map, @PathVariable String keywords) {
        Integer page = req.getParameter("page")==null?0:(Integer.parseInt(req.getParameter("page"))-1);
        if(StringUtils.isEmpty(keywords)) return "403";
        Metas metas = metasMapper.findOne(Long.valueOf(keywords));
        if(metas == null) return "403";
        SearchQo sq = new SearchQo();
        sq.setCategory(keywords);
        sq.setPage(page);
        List<Content> contents = siteService.findContentByCategory(sq);
        long count=contentMapper.countByCategoryPage(metas.getId());

        map.put("keywords",metas.getName());
        map.put("categoryid",metas.getId());
        map.put("totals",count);
        map.put("page",page+1);
        map.put("type","分类");
        map.put("pageflag","category");
        map.put("icons",ICONS);
        map.put("clists",contents);
        return this.render("page-category");
    }

    @RequestMapping(value = "re_articles",method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getRecentArticles(ModelMap map){

         return new AjaxResult(getRecentArticles());

    }

    public List<Metas> getCategoryMetas(){

        return metasMapper.findByType(MetaType.CATEGORY.getName());

    }


    public String convertToString(String categories,List<Metas> cs ){
        if(StringUtils.isEmpty(categories) ) return "1";
        String[] arry =  categories.split(",");
        Map<String ,String > maps = new HashMap<String, String>();
        for(Metas m :cs){
            maps.put(String.valueOf(m.getId()),m.getName());
        }

        StringBuffer sb  = new StringBuffer();
        for (String s:arry){
            sb.append(",").append(maps.get(s));
        }

        return sb.toString().substring(1);
    }


    public List<String> toTags(String tags){
        if(StringUtils.isEmpty(tags)) return null;
        List<String> ts = new ArrayList<String>();
        String[] arry = tags.split(",");

        return Arrays.asList(arry);
    }

    //生成展示图片
    public String gen_thumb(Content content){
        String thumbImg=content.getThumb_img();
        if(StringUtils.isNotEmpty(thumbImg)){
            return thumbImg;
        }
        String getfirst = SystemUtils.get_first_thumb(content.getContent());
        if(StringUtils.isEmpty(getfirst)){
            int cid = Integer.parseInt(String.valueOf(content.getId()));
            int size = cid % 20;
            size = size == 0 ? 1 : size;
            return "/ui/pages/img/rand/" + size + ".jpg";
        }else {
            return getfirst;
        }
    }

    public AjaxResult getRecentArticles(){

        Set<Content> contents = new HashSet<Content>();

        contents = siteService.getRecentArticles();

        return new AjaxResult(contents);
    }

}
