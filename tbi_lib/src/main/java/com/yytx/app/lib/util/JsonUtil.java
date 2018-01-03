package com.yytx.app.lib.util;

import com.yytx.app.lib.entity.BaseBeanResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * JSON工具类
 * Created by huangzhe on 2017/2/21.
 */

public class JsonUtil {
    /**
     * Json 转 List<Map>
     * @param data
     * @return
     * @throws JSONException
     */
    public static List<Map<String, Object>> jsonToMapList(String listName,BaseBeanResult data) throws JSONException {
        List<Map<String, Object>> list=null;
//        if (data.isSuccess() && Validator.isNotEmpty(data.getMsg())) {
//            list = new ArrayList<>();
//            JSONObject objJson = new JSONObject(data.getMsg());
//            if (objJson != null && objJson.has(listName)) {
//                JSONArray jsonArray = objJson.getJSONArray(listName);
//                for (int index = 0; index < jsonArray.length(); index++) {
//                    JSONObject obj = jsonArray.getJSONObject(index);
//                    Map<String, Object> objMap = new HashMap<String, Object>();
//                    for (Iterator<String> iterator = obj.keys(); iterator.hasNext(); ) {
//                        String key = iterator.next();
//                        objMap.put(key, obj.getString(key));
//                    }
//                    list.add(objMap);
//                }
//            }
//        }
        return list;
    }
}
