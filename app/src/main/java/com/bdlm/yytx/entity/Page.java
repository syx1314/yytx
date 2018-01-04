package com.bdlm.yytx.entity;

/**
 * Created by yyj on 2018/1/4.
 */

public class Page {
    private int curPage;
    private int nextPage;
    private int countPage;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getCountPage() {
        return countPage;
    }

    public void setCountPage(int countPage) {
        this.countPage = countPage;
    }
}
