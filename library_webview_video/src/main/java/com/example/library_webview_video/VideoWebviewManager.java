package com.example.library_webview_video;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2017/8/12.
 */

public class VideoWebviewManager {

    private Activity mContext;
    private WebView webView;
    private FrameLayout video_fullView;// 全屏时视频加载view
    private View xCustomView;
    private WebChromeClient.CustomViewCallback xCustomViewCallback;
    private VideoWebChromeClient xwebchromeclient;
    private static final VideoWebviewManager mySingleton = new VideoWebviewManager();

    private VideoWebviewManager() {
    }

    public static VideoWebviewManager getInstance() {
        return mySingleton;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initVideoWebview(Activity activity, WebView webView, FrameLayout fullFrameLayout, String url) {
        this.mContext = activity;
        this.webView = webView;
        this.video_fullView = fullFrameLayout;


        WebSettings ws = webView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        ws.setPluginState(WebSettings.PluginState.ON);
        ws.setBuiltInZoomControls(true);
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setAllowFileAccess(true);
        ws.setSupportMultipleWindows(true);// 新加
        ws.setDatabaseEnabled(true);
        ws.setDefaultTextEncodingName("UTF-8");
        xwebchromeclient = new VideoWebChromeClient();
        webView.setWebChromeClient(xwebchromeclient);
        webView.setWebViewClient(new VideoWebViewClient());
        webView.loadUrl("https://www.douyu.com/2102398");
    }


    public void resumeWebview() {
        if (webView != null) {
            webView.onResume();
            webView.resumeTimers();
        }
    }

    public void pauseWebview() {
        if (webView != null) {
            webView.onPause();
            webView.pauseTimers();
        }
    }


    public void destoryWebview() {
        video_fullView.removeAllViews();
        webView.loadUrl("about:blank");
        webView.stopLoading();
        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.destroy();
        webView = null;
    }

    /**
     * 判断是否是全屏
     *
     * @return
     */
    public boolean inCustomView() {
        return (xCustomView != null);
    }

    /**
     * 全屏时按返加键执行退出全屏方法
     */
    public void hideCustomView() {
        xwebchromeclient.onHideCustomView();
        mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public class VideoWebChromeClient extends WebChromeClient {
        // 播放网络视频时全屏会被调用的方法
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            Log.e("全屏播放", "已经进入了。。。。。。。。");
            mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            // 如果一个视图已经存在，那么立刻终止并新建一个
            if (xCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            webView.setVisibility(View.INVISIBLE);
            video_fullView.setVisibility(View.VISIBLE);
            video_fullView.addView(view);
            xCustomView = view;
            xCustomViewCallback = callback;
        }

        // 视频播放退出全屏会被调用的
        @Override
        public void onHideCustomView() {
            if (xCustomView == null) return;
            mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            xCustomView.setVisibility(View.GONE);
            video_fullView.removeView(xCustomView);
            xCustomView = null;
            video_fullView.setVisibility(View.GONE);
            xCustomViewCallback.onCustomViewHidden();
            webView.setVisibility(View.VISIBLE);
        }
    }

    public class VideoWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }
    }

}
