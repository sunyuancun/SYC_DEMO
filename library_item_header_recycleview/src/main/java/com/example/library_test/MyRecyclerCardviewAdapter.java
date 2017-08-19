package com.example.library_test;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.library_common.image.GlideImageLoader;

import java.util.ArrayList;
/**
 * Created by Administrator on 2017/8/15.
 */

public class MyRecyclerCardviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_Theme.ordinal()) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.videothemelist, parent, false);

            return new ThemeVideoHolder(view);

        } else if (viewType == ITEM_TYPE.ITEM_TYPE_Video.ordinal()) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.videocardview, parent, false);
            return new VideoViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ThemeVideoHolder) {
            themeTitle.setText(mdatas.get(position).name);

        } else if (holder instanceof VideoViewHolder) {
            ((VideoViewHolder) holder).videovname.setText(mdatas.get(position).title);
            GlideImageLoader imageLoader = new GlideImageLoader();
            imageLoader.displayImage(mContext, mdatas.get(position).image_thumb1, ((VideoViewHolder) holder).videologo);
            if (mOnItemClickListener != null) {
                ((VideoViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(((VideoViewHolder) holder).videologo, position);
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return mdatas.size();
    }

    public int getItemViewType(int position) {

        return mdatas.get(position).name != null ? ITEM_TYPE.ITEM_TYPE_Theme.ordinal() : ITEM_TYPE.ITEM_TYPE_Video.ordinal();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {

            ((GridLayoutManager) manager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    return getItemViewType(position) == ITEM_TYPE.ITEM_TYPE_Theme.ordinal() ? ((GridLayoutManager) manager).getSpanCount() : 1;
                }
            });
        }
    }

    public static enum ITEM_TYPE {
        ITEM_TYPE_Theme,
        ITEM_TYPE_Video
}

    //数据集
    public ArrayList<Item> mdatas;
    private TextView themeTitle;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;

    public MyRecyclerCardviewAdapter(Context context, ArrayList<Item> datas) {
        super();
        this.mdatas = datas;
        this.mContext = context;
    }

    public class ThemeVideoHolder extends RecyclerView.ViewHolder {

        public ThemeVideoHolder(View itemView) {
            super(itemView);
            themeTitle = (TextView) itemView.findViewById(R.id.hometab1_theme_title);
        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        public ImageView videologo;
        public TextView videovname;

        public VideoViewHolder(View itemView) {
            super(itemView);
            videologo = (ImageView) itemView.findViewById(R.id.videologo);
            videovname = (TextView) itemView.findViewById(R.id.videoname);
        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int positon);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
