package com.xut.model;

import java.util.List;

public class Page<T> {
    int totalCount;
    List<T> list;

    public Page() {
    }

    public Page(int totalCount, List<T> list) {
        this.totalCount = totalCount;
        this.list = list;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
