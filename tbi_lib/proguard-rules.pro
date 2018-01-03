## Add project specific ProGuard rules here.
## By default, the flags in this file are appended to flags specified
## in E:\VWork\Android\sdk/tools/proguard/proguard-android.txt
## You can edit the include path and order by changing the proguardFiles
## directive in build.gradle.
##
## For more details, see
##   http://developer.android.com/guide/developing/tools/proguard.html
#
## Add any project specific keep options here:
#
## If your project uses WebView with JS, uncomment the following
## and specify the fully qualified class name to the JavaScript interface
## class:
##-keepclassmembers class fqcn.of.javascript.interface.for.webview {
##   public *;
##}
#
## ------------------------------------- 混淆基础配置 ---------------------------------------------
-optimizationpasses 5                               # 指定代码的压缩级别
-dontusemixedcaseclassnames                         # 混淆时不会产生形形色色的类名
-dontskipnonpubliclibraryclasses                    # 指定不去忽略非公共的库类
-dontskipnonpubliclibraryclassmembers               # 指定不去忽略包可见的库类的成员
-dontpreverify                                      # 不预校验
-ignorewarnings                                     # 屏蔽警告
-verbose                                            # 混淆时记录日志
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*    #优化
#
#
## ------------------------------------- 不需要混淆的第三方类库 -------------------------------------
## Alipay过滤出去(来源于Alipay官网)
#-keep class com.alipay.android.app.IAlixPay{*;}
#-keep class com.alipay.android.app.IAlixPay$Stub{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
#-keep class com.alipay.sdk.app.PayTask{ public *;}
#-keep class com.alipay.sdk.app.AuthTask{ public *;}
#
## 微信过滤出去
#-keep class com.tencent.** {*;}
#
## 地图过滤出去
#-keep class com.amap.api.** {*;}
#
## 不需要混淆android-support-v4.jar
#-dontwarn android.support.v4.**
#-keep class android.support.v4.** { *; }
#-keep interface android.support.v4.app.** { *; }
#-keep public class * extends android.support.v4.**
#-keep public class * extends android.app.Fragment
#
## 过滤第三方包
-dontwarn com.lidroid.xutils.**
-keep class com.lidroid.xutils.**{*;}
-keep class * extends Java.lang.annotation.Annotation{*;}
#-keep class org.jdom.**{*;}
#-keep class java.io.**{*;}
#-keep class org.jaxen.**{*;}
#-keep interface org.jaxen.**{*;}
#
##保留一个完整的包
-keep class org.jdom2.** {
    *;
}
-keep class org.jaxen.** {
    *;
}
-keep class javax.xml.** {
    *;
}
#
#
#
# ------------------------------------- 不需要混淆的系统组件和本地实体类 ----------------------------
# 不混淆bean，** 换成具体的类名则表示不混淆某个具体的类
-keep class yytx.app.lib.entity.**
-keep class yytx.app.lib.util.**
-keep class yytx.app.lib.core.**
-keep class yytx.app.lib.view.component.wheel.adapters.**
-keep class yytx.app.lib.view.**
## Android底层组件和类不要混淆
##-keep public class * extends android.app.Activity
##-keep public class * extends android.app.Application
##-keep public class * extends android.app.Fragment
##-keep public class * extends android.app.Service
##-keep public class * extends android.content.BroadcastReceiver
##-keep public class * extends android.preference.Preference
-keep public class * extends yytx.app.lib.BaseApplication
-keep public class yytx.app.lib.BaseApplication
#-keep public abstract class Base*
##-keep interface yytxx.app.tbicustomer.IConst
#-keep public interface yytxx.app.tbicustomer.IConst extends IBaseConst
#-keep public class * extends IBaseConst
#-keep class **.R$* {
# *;
#}
#
#
## ------------------------------- 保护指定的类和类的成员，但条件是所有指定的类和类成员是要存在------------
#-keepclasseswithmembers class * {
#    protected void init(android.content.Context, android.util.AttributeSet);
#    protected abstract void init(android.content.Context, android.util.AttributeSet);
#}
#
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#    public String getValue();
#}
#
#-keep class * implements android.os.Parcelable {    # 保持 Parcelable 不被混淆
#  public static final android.os.Parcelable$Creator *;
#}
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
