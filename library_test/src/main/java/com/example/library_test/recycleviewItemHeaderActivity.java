package com.example.library_test;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class recycleviewItemHeaderActivity extends AppCompatActivity {


    private MyRecyclerCardviewAdapter adapter;
    private RecyclerView motivationRecyclerview;
//    String data = "{\"errcode\":0,\"message\":\"success\",\"contents\":[{\"id\":\"6\",\"name\":\"\\u7cbe\\u9009\\u8bfe\\u7a0b\",\"courses\":[{\"id\":\"1\",\"teacher_name\":\"\\u82cf\\u6167\",\"image_thumb1\":\"http:\\/\\/zbcourse.jyq365.com\\/uploads\\/video\\/2017\\/08-11\\/598d742297d25_thumb.jpg\",\"title\":\"\\u534e\\u6587\\u65e9\\u8bfb\\u5f00\\u8bfe\",\"play_times\":\"5\",\"money\":\"5.00\"}]},{\"id\":\"12\",\"name\":\"\\u6d4b\\u8bd5\\u680f\\u76ee\",\"courses\":[{\"id\":\"1\",\"teacher_name\":\"\\u82cf\\u6167\",\"image_thumb1\":\"http:\\/\\/zbcourse.jyq365.com\\/uploads\\/video\\/2017\\/08-11\\/598d742297d25_thumb.jpg\",\"title\":\"\\u534e\\u6587\\u65e9\\u8bfb\\u5f00\\u8bfe\",\"play_times\":\"5\",\"money\":\"5.00\"}]}]}\n";
String data ="{\"errcode\":0,\"message\":\"success\",\"contents\":[{\"id\":\"1\",\"name\":\"\\u7cbe\\u9009\\u8bfe\\u7a0b\",\"courses\":[{\"id\":\"3\",\"teacher_name\":\"\\u5029\\u5029\",\"image_thumb1\":\"http:\\/\\/61.54.243.41:5566\\/uploads\\/video\\/2017\\/08-09\\/598abb25ce3a7_thumb.jpg\",\"title\":\"\\u5029\\u5029\\u9996\\u79c0\\u5029\\u5029\\u9996\\u79c0\",\"play_times\":\"68\",\"money\":\"0.00\"},{\"id\":\"13\",\"teacher_name\":\"syc\",\"image_thumb1\":\"http:\\/\\/61.54.243.41:5566\\/uploads\\/video\\/2017\\/08-07\\/5987dfccd0822_thumb.jpg\",\"title\":\"\\u554a\\u554a\\u554a\\u554a\\u554a\",\"play_times\":\"29\",\"money\":\"0.01\"}]},{\"id\":\"9\",\"name\":\"\\u5206\\u7c7b8\",\"courses\":[{\"id\":\"10\",\"teacher_name\":\"\\u5029\\u5029\",\"image_thumb1\":\"http:\\/\\/61.54.243.41:5566\\/uploads\\/img\\/2017\\/07-25\\/59771088047ce_thumb.jpg\",\"title\":\"555555\",\"play_times\":\"0\",\"money\":\"0.01\"},{\"id\":\"11\",\"teacher_name\":\"\\u738b\\u8001\\u5e08\",\"image_thumb1\":\"http:\\/\\/61.54.243.41:5566\\/uploads\\/img\\/2017\\/07-25\\/597710a30002d_thumb.jpg\",\"title\":\"66666\",\"play_times\":\"0\",\"money\":\"0.01\"},{\"id\":\"3\",\"teacher_name\":\"\\u5029\\u5029\",\"image_thumb1\":\"http:\\/\\/61.54.243.41:5566\\/uploads\\/video\\/2017\\/08-09\\/598abb25ce3a7_thumb.jpg\",\"title\":\"\\u5029\\u5029\\u9996\\u79c0\\u5029\\u5029\\u9996\\u79c0\",\"play_times\":\"68\",\"money\":\"0.00\"}]},{\"id\":\"10\",\"name\":\"\\u6d4b\\u8bd5\\u680f\\u76ee\",\"courses\":[{\"id\":\"3\",\"teacher_name\":\"\\u5029\\u5029\",\"image_thumb1\":\"http:\\/\\/61.54.243.41:5566\\/uploads\\/video\\/2017\\/08-09\\/598abb25ce3a7_thumb.jpg\",\"title\":\"\\u5029\\u5029\\u9996\\u79c0\\u5029\\u5029\\u9996\\u79c0\",\"play_times\":\"68\",\"money\":\"0.00\"},{\"id\":\"13\",\"teacher_name\":\"syc\",\"image_thumb1\":\"http:\\/\\/61.54.243.41:5566\\/uploads\\/video\\/2017\\/08-07\\/5987dfccd0822_thumb.jpg\",\"title\":\"\\u554a\\u554a\\u554a\\u554a\\u554a\",\"play_times\":\"29\",\"money\":\"0.01\"}]}]}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        motivationRecyclerview = (RecyclerView) findViewById(R.id.MotivationRecyclerview);
        //显示RecyclerView
        try {
            initVerticalRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initVerticalRecyclerView() throws JSONException {

        //2.创建一个垂直的线性布局(一个布局管理器layoutManager只能绑定一个Recyclerview)
        GridLayoutManager layoutManager1 = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        //找到RecyclerView，并设置布局管理器
        motivationRecyclerview.setLayoutManager(layoutManager1);
        motivationRecyclerview.setHasFixedSize(true);

        //3.取得数据集(此处，应根据不同的主题查询得不同的数据传入到 MyRecyclerCardviewAdapter中构建adapter)
        initData(data);

        //4.创建adapter
        adapter = new MyRecyclerCardviewAdapter(this,mItems);
        //将RecyclerView组件绑定adapter
        motivationRecyclerview.setAdapter(adapter);

        //5.在Adapter中添加好事件后，变可以在这里注册事件实现监听了
        adapter.setOnItemClickListener(new MyRecyclerCardviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int positon) {
                Toast.makeText(getBaseContext(), "item" + positon, Toast.LENGTH_LONG).show();
            }
        });

    }

    ArrayList<Item> mItems;

    private void initData(String data) throws JSONException {
        mItems = new ArrayList<>();
        JSONObject result = new JSONObject(data);
        JSONArray array = result.getJSONArray("contents");

        for (int i = 0; i < array.length(); i++) {
            JSONObject content = array.getJSONObject(i);
            String headerName = content.getString("name");
            Item headerItem = new Item(headerName, null, null);
            mItems.add(headerItem);
            JSONArray courseArray = content.getJSONArray("courses");
            for (int j = 0; j < courseArray.length(); j++) {
                JSONObject courseJsonObject = courseArray.getJSONObject(j);
                Item item = new Item(null, courseJsonObject.getString("title"), courseJsonObject.getString("image_thumb1"));
                mItems.add(item);
            }
        }

    }

}
