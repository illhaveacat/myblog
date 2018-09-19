package com.stephen.myblog.model;

import com.stephen.myblog.entity.Metas;

public class MetasDto extends Metas {
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
