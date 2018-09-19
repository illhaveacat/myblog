package com.stephen.myblog.model;

import com.stephen.myblog.entity.Content;

import java.util.ArrayList;
import java.util.List;

public class Archive {
    private String datestr;
    private Integer count;
    private List<Content> contents = new ArrayList<Content>();

    public String getDatestr() {
        return datestr;
    }

    public void setDatestr(String datestr) {
        this.datestr = datestr;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }
}
