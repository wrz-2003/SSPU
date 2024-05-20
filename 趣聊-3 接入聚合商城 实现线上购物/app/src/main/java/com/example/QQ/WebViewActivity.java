package com.example.QQ;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = (WebView) findViewById(R.id.webview);

        // 设置 WebView 的相关配置
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);  // 如果网页需要 JavaScript
        settings.setDomStorageEnabled(true);  // 启用 DOM storage API
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);  // 根据 cache-control 决定是否从网络上取数据
        settings.setAppCacheEnabled(true);  // 启用应用缓存
        String cachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        settings.setAppCachePath(cachePath);
        settings.setLoadsImagesAutomatically(true);  // 自动加载图片

        // 添加硬件加速
        webView.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);

        // WebViewClient 主要帮助 WebView 处理各种通知、请求事件
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // 页面开始加载时可以放置加载动画
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 页面加载结束时可以停止加载动画
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                // 可以处理页面加载错误
            }
        });

        // 显示对话框让用户选择打开方式
        showOpenDialog();
    }

    private void showOpenDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("趣聊畅购")
                .setMessage("您希望如何打开？")
                .setPositiveButton("在应用内打开", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        webView.loadUrl("https://fangxingouoizt.kuaizhan.com/?cid=POsNI0e");
                    }
                })
                .setNegativeButton("在浏览器中打开", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://fangxingouoizt.kuaizhan.com/?cid=POsNI0e"));
                        startActivity(browserIntent);
                        finish();  // 关闭当前 Activity
                    }
                })
                .setCancelable(false)  // 防止用户点击对话框外部时对话框消失
                .show();
    }
}
