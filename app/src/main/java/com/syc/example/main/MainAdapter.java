package com.syc.example.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.syc.example.R;

import java.util.List;

/**
 * Created by Administrator on 2017/8/2.
 */

public class MainAdapter extends RecyclerView.Adapter {

    //先定义两个ItemViewType，0代表头，1代表表格中间的部分
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;

    //数据源
    private List<MainBean> dataList;

    //构造函数
    public MainAdapter(List<MainBean> dataList) {
        this.dataList = dataList;
    }

    /**
     * 判断header位置
     *
     * @param position
     * @return
     */
    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        //在onCreateViewHolder方法中，我们要根据不同的ViewType来返回不同的ViewHolder
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, null);
            //对于Header，我们应该返回填充有Header对应布局文件的ViewHolder（再次我们返回的都是一个布局文件，请根据不同的需求做相应的改动）
            return new HeaderViewHolder(view);
        }
        if (viewType == ITEM_VIEW_TYPE_ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, null);
            //对于Body中的item，我们也返回所对应的ViewHolder
            return new BodyViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeader(position)) {
            //大家在这里面处理头，这里只有一个TextView，大家可以根据自己的逻辑做修改
            ((HeaderViewHolder) holder).getTextView().setText("This is the Header!!");
        } else {
            //其他条目中的逻辑在此
            ((BodyViewHolder) holder).getTextView().setText(dataList.get(position - 1).text);
            holder.itemView.setTag(dataList.get(position - 1));
            holder.itemView.setOnClickListener(new MyClick(position));
        }


    }

    /**
     * 总条目数量是数据源数量+1，因为我们有个Header
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
    }


    class MyClick implements View.OnClickListener {

        int pos;

        MyClick(int pos) {
            this.pos = pos;
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                //注意这里使用getTag方法获取数据
                mOnItemClickListener.onItemClick(v, pos, (MainBean) v.getTag());
            }
        }
    }


    /**
     * 给头部专用的ViewHolder，大家根据需求自行修改
     */
    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_tv);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    /**
     * 给GridView中的条目用的ViewHolder，里面只有一个TextView
     */
    public class BodyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public BodyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_tv);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int pos, MainBean data);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}
