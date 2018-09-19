package com.stephen.myblog.servcie;

import com.stephen.myblog.mapper.MetasMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *   17/4/13.
 */
@Service
public class MetasService {
    @Autowired
    private MetasMapper metasMapper;

    public List<Map<String,Object>> getByCategoryOrTags(String type){

        List<Map<String,Object>>  lists = new ArrayList<Map<String,Object>>();

        String sql = "select a.*, count(b.cid) as count from t_metas a left join `t_relationships` b on a.id = b.mid  " +
                "  where a.type = '"+type+"' group by a.id order by count desc, a.id desc  ";

//        lists = metasMapper.nativeQuery4Map(sql);

        return  lists;
    }
}
