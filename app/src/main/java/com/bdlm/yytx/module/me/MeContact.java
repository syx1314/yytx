package com.bdlm.yytx.module.me;

import com.bdlm.yytx.entity.UserInfoBean;
import com.trsoft.app.lib.mvp.BaseModel;
import com.trsoft.app.lib.mvp.IBaseListener;
import com.trsoft.app.lib.mvp.IBaseModel;
import com.trsoft.app.lib.mvp.IBaseView;

/**
 * Created by yyj on 2018/1/4.
 */

public interface MeContact {

    interface IMeListener extends IBaseListener {
        void userInfo(UserInfoBean userInfoBean);
        void logoutResult(String msg);
        void feedBack(String msg);
    }

    interface IMeView extends IBaseView {
        void getUserInfo(UserInfoBean userInfoBean);
        void responseLogout(String msg);
    }
   interface  IFeedBackView extends IBaseView
   {
       void resultFeedBack(String msg);
   }
     abstract class IMeModel extends BaseModel {
        abstract void getUserInfo(IMeListener listener);
        abstract void logout(IMeListener listener);
        abstract void submitFeedBack(String mobile,String content,IMeListener listener);
    }


}
