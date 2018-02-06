package com.bdlm.yytx.module.webview;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseLoginActivity;
import com.bdlm.yytx.common.view.CommonTitle;
import com.bdlm.yytx.constant.Constant;
import com.trsoft.app.lib.utils.Validator;

import butterknife.BindView;

public class LoadHtmlLoginActivity extends BaseLoginActivity {

    @BindView(R.id.ct_title)
    CommonTitle ctTitle;
    @BindView(R.id.webView)
    WebView webView;
    String title;
    String url;

    @Override
    protected int getLayout() {
        return R.layout.activity_load_html;
    }

    @Override
    protected void createPersenter() {
        title = getIntent().getStringExtra(Constant.BUNDLE_STRING);
        url = getIntent().getStringExtra(Constant.BUNDLE_URL);
        mImmersionBar.statusBarColor(R.color.red).init();
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        if (Validator.isNotEmpty(title)) {
            ctTitle.setTvTitle(title);
        }
        if (Validator.isNotEmpty(url)) {
            webView.loadUrl(url);
        }
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                activity.setProgress(newProgress * 1000);
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }
}
