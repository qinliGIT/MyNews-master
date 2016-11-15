package com.ql.mynews.web;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ql.mynews.R;
import com.ql.mynews.custom.ChromeFloatingCirclesDrawable;

/**
 * Created by Administrator on 2016/10/28.
 */
public class WebViewActivity extends Activity {
    private WebView webView;
    private String url = "www.baidu.com";
    private ProgressBar chromeFloatingCircles_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        String str = getIntent().getStringExtra("url");
        if (str != null) {
            url = str;
        }
        init();
    }

    private void init() {
        webView = (WebView) findViewById(R.id.web);
        chromeFloatingCircles_progress = (ProgressBar) findViewById(R.id.chromeFloatingCircles_progress);
        chromeFloatingCircles_progress.setIndeterminateDrawable(new ChromeFloatingCirclesDrawable.Builder(WebViewActivity.this).colors(getProgressDrawableColors()).build());

        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
//        settings.setCacheMode(Mode.LOAD_CACHE_ONLY);
        //WebView加载web资源
        webView.loadUrl(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    // 网页加载完成
                    chromeFloatingCircles_progress.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                } else {
                    // 加载中
                    chromeFloatingCircles_progress.setVisibility(View.VISIBLE);
                    webView.setVisibility(View.GONE);
                }

            }
        });
    }

    private int[] getProgressDrawableColors() {
        int[] colors = new int[4];
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(WebViewActivity.this);
        colors[0] = prefs.getInt(getString(R.string.firstcolor_pref_key), getResources().getColor(R.color.red));
        colors[1] = prefs.getInt(getString(R.string.secondcolor_pref_key), getResources().getColor(R.color.blue));
        colors[2] = prefs.getInt(getString(R.string.thirdcolor_pref_key), getResources().getColor(R.color.yellow));
        colors[3] = prefs.getInt(getString(R.string.fourthcolor_pref_key), getResources().getColor(R.color.green));
        return colors;
    }


    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();//返回上一页面
                return true;
            } else {
                System.exit(0);//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
