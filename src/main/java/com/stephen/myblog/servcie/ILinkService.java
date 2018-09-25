package com.stephen.myblog.servcie;

import com.stephen.myblog.entity.Links;

import java.util.List;

public interface ILinkService {

    List<Links> queryByPage(int currPage,int pageSize);

}
