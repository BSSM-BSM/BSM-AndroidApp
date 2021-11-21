package com.zzz2757.bsm.Board;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.zzz2757.bsm.Common;
import com.zzz2757.bsm.R;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class PostWriteActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_write);

        webView = (WebView)findViewById(R.id.post_write_webview);
        webView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.background));
        final WebSettings webSet = webView.getSettings();
        webSet.setJavaScriptEnabled(true);
        webSet.setJavaScriptCanOpenWindowsAutomatically(true);
        webSet.setSupportMultipleWindows(true);
        webSet.setAllowFileAccessFromFileURLs(true);
        webSet.setDomStorageEnabled(true);
        webSet.setSupportZoom(true);
        webSet.setBuiltInZoomControls(true);
        webSet.setDisplayZoomControls(false);
        webSet.setDefaultTextEncodingName("utf-8");

        webView.setWebChromeClient(new MyWebChromeClient());

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setAcceptThirdPartyCookies(webView, true);
        cookieManager.removeAllCookies(null);
        cookieManager.flush();
        List<Cookie> cookies = Common.cookieJar.loadForRequest(HttpUrl.parse(Common.getBaseUrl()));
        for (Cookie cookie : cookies) {
            String cookieString = cookie.name() + "=" + cookie.value() + ";";
            cookieManager.setCookie(cookie.domain(), cookieString);
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String boardType = bundle.getString("boardType");
        int postNo = bundle.getInt("postNo");
        if(postNo==0){
            webView.loadUrl(Common.getBaseUrl() +"app/board/write/"+boardType);
        }else{
            webView.loadUrl(Common.getBaseUrl() +"app/board/write/"+boardType+"/"+postNo);
        }
    }

    public class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onCloseWindow(WebView window) {
            window.setVisibility(View.GONE);
            window.destroy();
            finish();
            super.onCloseWindow(window);
        }
        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            new AlertDialog.Builder(view.getContext())
                    .setTitle("")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    result.confirm();
                                } })
                    .setCancelable(false)
                    .create()
                    .show();
            return true;
        }
    }
}