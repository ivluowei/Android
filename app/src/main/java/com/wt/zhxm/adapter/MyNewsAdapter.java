package com.wt.zhxm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wt.zhxm.R;
import com.wt.zhxm.bean.SmartContent;

import java.util.List;

/**
 * Created by Administrator on 2016/12/11 0011.
 */
public class MyNewsAdapter extends BaseAdapter {
    private List<SmartContent.Tngou> mContentList;
    private Context context;
    private static final String TAG = "NewsListFragment";

    public MyNewsAdapter(Context ctx, List<SmartContent.Tngou> contentList) {
        this.context = ctx;
        this.mContentList = contentList;
    }

    @Override
    public int getCount() {
        // Log.d(TAG, "getCount: "+mContentList.size());
        return mContentList.size();
    }

    @Override
    public Object getItem(int i) {
        return mContentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.list_news_item, null);
            holder.mPic = (ImageView) view.findViewById(R.id.iv_news_pic);
            holder.mTile = (TextView) view.findViewById(R.id.tv_news_title);
            holder.mKeyword = (TextView) view.findViewById(R.id.tv_news_keyword);
            holder.mComment = (TextView) view.findViewById(R.id.tv_news_comment);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Glide.with(context).load("http://tnfs.tngou.net/image" + mContentList.get(i).getImg()).into(holder.mPic);
        holder.mTile.setText(mContentList.get(i).getTitle());
        holder.mKeyword.setText(mContentList.get(i).getKeywords());
        holder.mComment.setText(mContentList.get(i).getCount() + "跟帖");
        return view;
    }

    class ViewHolder {
        ImageView mPic;
        TextView mTile;
        TextView mKeyword;
        TextView mComment;
    }
}
