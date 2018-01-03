package com.trsoft.app.lib.rx;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.ArrayMap;


import java.util.Set;

import rx.Subscription;

/**
 * Rx管理池
 * Created by huangzhe on 2017/4/17.
 */
public class RxApiManager implements RxActionManager<Object> {
    private static RxApiManager sInstance = null;

    private ArrayMap<Object, ArrayMap<Object,Subscription>> maps;

    public static RxApiManager instance() {
        if (sInstance == null) {
            synchronized (RxApiManager.class) {
                if (sInstance == null) {
                    sInstance = new RxApiManager();
                }
            }
        }
        return sInstance;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private RxApiManager() {
        maps = new ArrayMap<>();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void add(Object tag, Object id, Subscription subscription) {
        if(!maps.containsKey(tag)){
            maps.put(tag,new ArrayMap<Object, Subscription>());
        }
        maps.get(tag).put(id,subscription);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void remove(Object tag, Object id) {
        if (!maps.isEmpty()) {
            if(maps.containsKey(tag)&&maps.get(tag)!=null&&maps.get(tag).containsKey(id)) {
                maps.get(tag).remove(id);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void cancel(Object tag) {
        if (maps.isEmpty()) {
            return;
        }
        if (maps.get(tag) == null) {
            return;
        }
        if(maps.containsKey(tag)&&!maps.get(tag).isEmpty()){
            Set<Object> idSet=maps.get(tag).keySet();
            for(Object id:idSet){
                Subscription subscription=maps.get(tag).get(id);
                if(!subscription.isUnsubscribed()){
                    subscription.unsubscribe();
                }
            }
            maps.remove(tag);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void cancel(Object tag, Object id) {
        if (maps.isEmpty()) {
            return;
        }
        if (maps.get(tag) == null) {
            return;
        }
        if(maps.containsKey(tag)&&!maps.get(tag).isEmpty()){
            Subscription subscription=maps.get(tag).get(id);
            if(!subscription.isUnsubscribed()){
                subscription.unsubscribe();
                maps.get(tag).remove(id);
            }
        }
    }
}
