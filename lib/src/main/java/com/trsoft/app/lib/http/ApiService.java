package com.trsoft.app.lib.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;
import com.trsoft.app.lib.BaseApplication;
import com.trsoft.app.lib.gson.IntegerDefault0Adapter;
import com.trsoft.app.lib.gson.LongDefaultAdapter;
import com.trsoft.app.lib.utils.Validator;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 访问后台API接口工具类
 * Created by huangzhe on 2017/4/13.
 */
public class ApiService {
    private static ApiService instance;
    private Retrofit retrofit;

    private ApiService(String baseUrl){
        //缓存
        File httpCacheDirectory=new File(BaseApplication.mContext.getCacheDir(),"responses");
        if(!httpCacheDirectory.exists()){
            httpCacheDirectory.mkdirs();
        }
        int cacheSize=10*1024*1024;//10 MB
        Cache cache=new Cache(httpCacheDirectory,cacheSize);

        //设置应用拦截器，可用于设置公共参数，头信息，日志拦截等
        HttpLoggingInterceptor logging=new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .retryOnConnectionFailure(true) //错误重联
                .addInterceptor(new RewriteCacheControlInterceptor())
                .cache(cache)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private Gson buildGson(){
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeAdapter(Integer.class,new IntegerDefault0Adapter())
                .registerTypeAdapter(int.class,new IntegerDefault0Adapter())
                .registerTypeAdapter(Long.class,new LongDefaultAdapter())
                .registerTypeAdapter(long.class,new LongDefaultAdapter())
                .create();
    }

    /**
     * 得到实例
     * @return
     */
    public static ApiService getInstance(String baseUrl){
        if(instance==null){
            synchronized (ApiService.class){
                if(instance==null){
                    instance=new ApiService(baseUrl);
                }
            }
        }
        return instance;
    }

    /**
     * 清除缓存
     */
    public void clearCache() throws IOException {
        OkHttpClient client= (OkHttpClient) instance.retrofit.callFactory();
        for(File file:client.cache().directory().listFiles()){
            file.delete();
        }
    }

    /**
     * 得到具体的Service
     * @param service
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service){
        return retrofit.create(service);
    }

    /**
     * 重定向缓存控制
     */
    class RewriteCacheControlInterceptor implements Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException {
            CacheControl.Builder cacheBuilder=new CacheControl.Builder();
            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
            cacheBuilder.maxStale(365, TimeUnit.DAYS);
            CacheControl cacheControl=cacheBuilder.build();
            //请求
            Request request=chain.request();
            if(!checkNetWorkIsAvailable(BaseApplication.mContext)){
                request=request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();
            }else{
                BaseApplication baseApplication=(BaseApplication)BaseApplication.mContext;
                if(baseApplication.getLoginConfig()!=null&& Validator.isNotEmpty(baseApplication.getLoginConfig().getToken())){
                    Logger.d("token:"+baseApplication.getLoginConfig().getToken());
                    request=request.newBuilder().header("token",baseApplication.getLoginConfig().getToken()).build();//加入token头
//                    request.url()=request.url()+"?token="+baseApplication.getLoginConfig().getToken();

                   request= request.newBuilder().url(request.url()+"?token="+baseApplication.getLoginConfig().getToken()+"&device_token=HDUTr65FRT").build();


                }
            }
            //返回数据
            Response originalResponse=chain.proceed(request);
            if(checkNetWorkIsAvailable(BaseApplication.mContext)){
                int maxAge=0;
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public ,max-age=" + maxAge)
                            .build();
            }else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
        /**
         * 检查是否有网络
         * @param context
         * @return
         */
        public boolean checkNetWorkIsAvailable(Context context) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                NetworkInfo network = manager.getActiveNetworkInfo();
                if (network != null && network.isConnectedOrConnecting()) {
                    return true;
                }
            }
            return false;
        }
    }
}
