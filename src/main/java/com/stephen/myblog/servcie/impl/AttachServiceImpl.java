package com.stephen.myblog.servcie.impl;

import com.stephen.myblog.entity.Attach;
import com.stephen.myblog.mapper.AttachMapper;
import com.stephen.myblog.servcie.IAttachService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("attachService")
public class AttachServiceImpl implements IAttachService {

    @Autowired
    AttachMapper attachMapper;

    @Override
    public List<Attach> queryByPage(int currPage, int pageSize) {
        Map<String, Object> data = new HashedMap();
        data.put("currIndex", (currPage-1)*pageSize);
        data.put("pageSize", pageSize);
        return attachMapper.queryByPage(data);
    }
}
