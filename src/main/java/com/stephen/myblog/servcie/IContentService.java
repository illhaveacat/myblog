package com.stephen.myblog.servcie;

import com.stephen.myblog.entity.Content;

import java.util.List;

public interface IContentService {

    List<Content> queryByPage(int currPage, int pageSize);

}
