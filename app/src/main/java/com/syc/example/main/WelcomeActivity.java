package com.syc.example.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.library_qq_comment.QQCommentActivity;
import com.example.library_test.recycleviewItemHeaderActivity;
import com.example.library_webview_video.WebviewActivity;
import com.syc.example.R;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends Activity {
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noStatusLine();
        setContentView(R.layout.activity_welcome);
        activity = WelcomeActivity.this;
        initRecyclerview();
    }

    private void noStatusLine() {
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initRecyclerview() {
        //找到RecyclerView控件
        RecyclerView home_rv = (RecyclerView) findViewById(R.id.home_rv);
        //做一些假数据
        List<String> imageList = new ArrayList<>();
        imageList.add("http://img.sccnn.com/bimg/339/25976.jpg");
        imageList.add("http://img.sccnn.com/bimg/337/35579.jpg");
        imageList.add("http://img.sccnn.com/bimg/339/26130.jpg");
        List<String> titleList = new ArrayList<>();
        titleList.add("我的中国梦");
        titleList.add("京东年货节");
        titleList.add("西湖龙井");

        List<MainBean> dataList = new ArrayList<>();
        int size = 3;
        for (int i = 0; i < size; i++) {
            MainBean mainBean = new MainBean();
            if (i == 0) {
                mainBean.text = "QQ评论";
                mainBean.image = getResources().getDrawable(R.mipmap.ic_launcher);
            }

            if (i == 1) {
                mainBean.text = "带分类头Recyclewview";
                mainBean.image = getResources().getDrawable(R.mipmap.ic_launcher);
            }

            if (i == 2) {
                mainBean.text = "webview video";
                mainBean.image = getResources().getDrawable(R.mipmap.ic_launcher);
            }

            dataList.add(mainBean);
        }
        MainAdapter adapter = new MainAdapter(titleList, imageList, dataList);
        home_rv.setAdapter(adapter);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? layoutManager.getSpanCount() : 1;
            }
        });
        home_rv.setLayoutManager(layoutManager);
        home_rv.addItemDecoration(new MainItemDecoration());
        adapter.setOnItemClickListener(new MainAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int pos, MainBean data) {
                if (pos == 1) {
                    startActivity(new Intent(activity, QQCommentActivity.class));
                }

                if (pos == 2) {
                    startActivity(new Intent(activity, recycleviewItemHeaderActivity.class));
                }

                if (pos == 3) {
                    startActivity(new Intent(activity, WebviewActivity.class));
                }

            }
        });
    }
}
