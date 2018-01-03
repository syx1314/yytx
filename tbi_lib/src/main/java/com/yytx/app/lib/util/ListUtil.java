package com.yytx.app.lib.util;

import java.util.List;

/**
 * List工具类
 * Created by huangzhe on 2017/5/22.
 */

public class ListUtil {
    public static boolean isNotEmpty(List<?> list) {
        if (list != null && !list.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(List<?> list){
        return !isNotEmpty(list);
    }
}
