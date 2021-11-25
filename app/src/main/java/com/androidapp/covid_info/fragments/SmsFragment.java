package com.androidapp.covid_info.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.androidapp.covid_info.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SmsFragment extends Fragment {

    public static final String TAG = SmsFragment.class.getSimpleName();

    @BindView(R.id.webview)
    WebView webview;
    Activity context;
    private String mParam1;
    private String mParam2;
    public SmsFragment() {
        // Required empty public constructor
    }

    public static SmsFragment newInstance(String param1, String param2) {
        SmsFragment fragment = new SmsFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_sms, container, false);
        ButterKnife.bind(this, inflate);
        context = getActivity();
        webview.setWebChromeClient(new WebChromeClient());
        MyWebViewClient my = new MyWebViewClient();
        webview.setWebViewClient(my);
        webview.clearCache(true);
        webview.clearHistory();
        clearCookies(getActivity());

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        CookieManager.getInstance().setAcceptCookie(true);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webview, true);
        } else {
            CookieManager.getInstance().setAcceptCookie(true);
        }
        webview.loadUrl("https://covid19.who.int/");
        return inflate;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        public MyWebViewClient() {
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            webview.clearHistory();
            webview.clearCache(true);

        }
    }
    public static void clearCookies(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }

}
