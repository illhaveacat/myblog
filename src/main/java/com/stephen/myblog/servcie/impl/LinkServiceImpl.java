package com.stephen.myblog.servcie.impl;

import com.stephen.myblog.entity.Links;
import com.stephen.myblog.mapper.LinksMapper;
import com.stephen.myblog.servcie.ILinkService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("linkService")
public class LinkServiceImpl implements ILinkService {

    @Autowired
    LinksMapper linksMapper;

    @Override
    public List<Links> queryByPage(int currPage, int pageSize) {
        Map<String, Object> data = new HashedMap();
        data.put("currIndex", (currPage-1)*pageSize);
        data.put("pageSize", pageSize);
        return linksMapper.queryByPage(data);
    }
}
