package com.syc.example.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.syc.example.R;
import com.syc.example.comment.QQCommentActivity;

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
        List<MainBean> dataList = new ArrayList<>();

        int size = 1;
        for (int i = 0; i < size; i++) {
            MainBean mainBean = new MainBean();
            if (i == 0) {
                mainBean.text = "QQ评论";
                mainBean.image = getResources().getDrawable(R.drawable.ic_comment_black_24dp);
            }
            dataList.add(mainBean);
        }
        MainAdapter adapter = new MainAdapter(dataList);
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


            }
        });
    }
}
