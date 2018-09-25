package com.stephen.myblog.servcie;

import com.stephen.myblog.entity.Content;
import com.stephen.myblog.entity.Links;
import com.stephen.myblog.entity.Metas;
import com.stephen.myblog.enums.MetaType;
import com.stephen.myblog.mapper.ContentMapper;
import com.stephen.myblog.mapper.LinksMapper;
import com.stephen.myblog.mapper.MetasMapper;
import com.stephen.myblog.model.Archive;
import com.stephen.myblog.model.SearchQo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SiteService {
    @Autowired
    private MetasMapper metasMapper;

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private LinksMapper linksMapper;

    public List<Archive> getArchivesList(){
        List<Archive> archives = new ArrayList<Archive>();
        List<Map<String,Object>>  lists = new ArrayList<Map<String,Object>>();
//        String sql = "SELECT DATE_FORMAT(c.createdate,'%Y-%m') as datestr , COUNT(*) as count from t_content c GROUP BY datestr ORDER BY  datestr desc";
        lists = metasMapper.findByGroupDate();
        if(lists.size()>0){
            for(int i=0;i<lists.size();i++){
                Map<String,Object> map = lists.get(i);
                Archive a = new Archive();
                a.setDatestr(map.get("datestr").toString());
                a.setCount(Integer.valueOf(map.get("count").toString()));
//                String csql ="SELECT * from t_content c where DATE_FORMAT(c.createdate,'%Y-%m') = ? ";
                List<Content> cs = contentMapper.findByCreatedate(a.getDatestr());
                a.setContents(cs);
                archives.add(a);
            }
        }
        return  archives;
    }


    public List<Links> getLinksList(){
        return linksMapper.findAll();
    }

    public  List<Content> findContentByKeywords(SearchQo sq){
        List<Content> contents= contentMapper.findContentByKeywordsPage(sq.getTitle(),sq.getPage(),sq.getPageSize());
        return  contents;
    }


    public  List<Content> findContentByTags(SearchQo sq){
        Metas metas = metasMapper.findByNameAndType(sq.getTag(), MetaType.TAGS.getName());
        if(metas == null) return null;
        Pageable pageable = new PageRequest(sq.getPage(),sq.getPageSize(), new Sort(Sort.Direction.ASC, "id"));
//      String sql ="SELECT c.* from t_content c join t_relationships s on s.cid = c.id WHERE s.mid = ?";
        List<Content> contents=contentMapper.findContentByTagsPage(metas.getId(),sq.getPage(),sq.getPageSize());
        return  contents;
    }

    public List<Content> findContentByCategory(SearchQo sq){
        Metas metas = metasMapper.findOne(Long.valueOf(sq.getCategory()));
        if(metas == null) return null;
        Pageable pageable = new PageRequest(sq.getPage(),sq.getPageSize(), new Sort(Sort.Direction.ASC, "id"));
//        String sql ="SELECT c.* from t_content c join t_relationships s on s.cid = c.id WHERE s.mid = ?";
        List<Content> contents=contentMapper.findContentByCategoryPage(metas.getId(),sq.getPage(),sq.getPageSize());
        return contents;
    }

    public Set<Content> getRecentArticles() {
        Set<Content> contents = new HashSet<Content>();
        List<Content> c = contentMapper.getRecentList();
        if (null != c) contents.addAll(c);
        return contents;
    }
}
