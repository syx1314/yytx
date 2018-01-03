package com.yytx.app.lib.entity;

import java.util.List;

/**
 * 分页实体
 * Created by huangzhe on 2016/11/26.
 */

public class BasePageList<T> {
    private List<T> list;
    private int start;
    private int rows;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
