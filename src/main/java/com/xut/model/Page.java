package com.xut.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Page<T> {
    int totalCount;
    List<T> list;

    public Page() {
    }

    public Page(int totalCount, List<T> list) {
        this.totalCount = totalCount;
        this.list = list == null ? new ArrayList<>() : list;
    }

    public boolean isEmpty() {
        return list == null
                || list instanceof List && ((List<?>) list).size() == 0
                || list instanceof Map && ((Map<?, ?>) list).size() == 0;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
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
