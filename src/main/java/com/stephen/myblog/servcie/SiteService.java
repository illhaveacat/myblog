package com.stephen.myblog.servcie;

import com.stephen.myblog.entity.Content;
import com.stephen.myblog.mapper.ContentMapper;
import com.stephen.myblog.mapper.LinksMapper;
import com.stephen.myblog.mapper.MetasMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SiteService {
    @Autowired
    private MetasMapper metasMapper;

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private LinksMapper linksMapper;

   /* public List<Archive> getArchivesList(){


        List<Archive> archives = new ArrayList<Archive>();
        List<Map<String,Object>>  lists = new ArrayList<Map<String,Object>>();

        String sql = "SELECT DATE_FORMAT(c.createdate,'%Y-%m') as datestr , COUNT(*) as count from t_content c GROUP BY datestr ORDER BY  datestr desc";

        lists = metasMapper.nativeQuery4Map(sql);

        if(lists.size()>0){
            for(int i=0;i<lists.size();i++){
                Map<String,Object> map = lists.get(i);
                Archive a = new Archive();
                a.setDatestr(map.get("datestr").toString());
                a.setCount(Integer.valueOf(map.get("count").toString()));
                String csql ="SELECT * from t_content c where DATE_FORMAT(c.createdate,'%Y-%m') = ? ";
                List<Content> cs = contentRepository.findAllByNativeSql(csql,new Content(),new Object[]{a.getDatestr()});

                a.setContents(cs);
                archives.add(a);
            }
        }
        return  archives;
    }


    public List<Links> getLinksList(){

        return linksRepository.findAll();
    }

    public Page<Content> findContentByKeywords(SearchQo sq){

        Pageable pageable = new PageRequest(sq.getPage(),sq.getPageSize(), new Sort(Sort.Direction.ASC, "id"));

        PredicateBuilder pb  = new PredicateBuilder();

        if (!StringUtils.isEmpty(sq.getTitle())) {
            pb.add("title","%" + sq.getTitle() + "%", LinkEnum.LIKE);
        }


        return contentRepository.findAll(pb.build(), Operator.AND, pageable);

    }


    public Page<Content> findContentByTags(SearchQo sq){


        Metas metas = metasRepository.findByNameAndType(sq.getTag(), MetaType.TAGS.getName());
        if(metas == null) return null;

        Pageable pageable = new PageRequest(sq.getPage(),sq.getPageSize(), new Sort(Sort.Direction.ASC, "id"));

        String sql ="SELECT c.* from t_content c join t_relationships s on s.cid = c.id WHERE s.mid = ?";



        return contentRepository.findAllByNativeSql(sql,pageable,new Content(),new Object[]{metas.getId()});

    }

    public Page<Content> findContentByCategory(SearchQo sq){


        Metas metas = metasRepository.findOne(Long.valueOf(sq.getCategory()));
        if(metas == null) return null;

        Pageable pageable = new PageRequest(sq.getPage(),sq.getPageSize(), new Sort(Sort.Direction.ASC, "id"));

        String sql ="SELECT c.* from t_content c join t_relationships s on s.cid = c.id WHERE s.mid = ?";



        return contentRepository.findAllByNativeSql(sql,pageable,new Content(),new Object[]{metas.getId()});

    }*/

    public Set<Content> getRecentArticles() {
        Set<Content> contents = new HashSet<Content>();
        List<Content> c = contentMapper.getRecentList();
        if (null != c) contents.addAll(c);
        return contents;
    }
}
