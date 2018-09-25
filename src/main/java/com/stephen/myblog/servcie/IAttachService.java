package com.stephen.myblog.servcie;

import com.stephen.myblog.entity.Attach;

import java.util.List;

public interface IAttachService {

    List<Attach> queryByPage(int currPage, int pageSize);

}
