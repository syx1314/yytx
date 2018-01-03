package com.trsoft.app.lib.mvp;

/**
 * Created by yyj on 2017/12/26.
 */

public interface IBaseView{
    // 规定View必须要实现setPresenter方法，则View中保持对Presenter的引用。
//    void setPresenter(P presenter);
    void error(String msg);
}
