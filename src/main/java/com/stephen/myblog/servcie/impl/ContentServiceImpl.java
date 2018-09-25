package com.stephen.myblog.servcie.impl;

import com.stephen.myblog.entity.Content;
import com.stephen.myblog.mapper.ContentMapper;
import com.stephen.myblog.servcie.IContentService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("contentService")
public class ContentServiceImpl implements IContentService {

    @Autowired
    ContentMapper contentMapper;

    @Override
    public List<Content> queryByPage(int currPage, int pageSize) {
        Map<String, Object> data = new HashedMap();
        data.put("currIndex", (currPage-1)*pageSize);
        data.put("pageSize", pageSize);
        return contentMapper.queryByPage(data);
    }
}
