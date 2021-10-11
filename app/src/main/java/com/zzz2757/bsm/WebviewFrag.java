package com.zzz2757.bsm;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class WebviewFrag extends Fragment {
    private View view;
    private String page;
    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.frag_webview,container,false);

        page = getArguments().getString("page");
        webView = (WebView)view.findViewById(R.id.webview);
        webView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.background));
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

        webView.setWebChromeClient(new WebviewFrag.MyWebChromeClient());

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
        webView.loadUrl(Common.getBaseUrl() +"app/"+page);

        return view;
    }

    public class MyWebChromeClient extends WebChromeClient {
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
