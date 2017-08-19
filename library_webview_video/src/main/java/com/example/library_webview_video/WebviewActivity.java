package com.example.library_webview_video;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.FrameLayout;

public class WebviewActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        WebView webView = (WebView) findViewById(R.id.video_web);
        FrameLayout video_fullView = (FrameLayout) findViewById(R.id.video_fullView);
        VideoWebviewManager.getInstance().initVideoWebview(this, webView, video_fullView, "https://www.baidu.com/");
    }

    @Override
    protected void onResume() {
        super.onResume();
        VideoWebviewManager.getInstance().resumeWebview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        VideoWebviewManager.getInstance().pauseWebview();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VideoWebviewManager.getInstance().destoryWebview();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (VideoWebviewManager.getInstance().inCustomView()) {
                VideoWebviewManager.getInstance().hideCustomView();
                return true;
            } else {
                finish();
            }
        }
        return false;
    }

}
