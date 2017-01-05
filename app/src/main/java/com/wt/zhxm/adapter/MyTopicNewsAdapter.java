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
import com.wt.zhxm.bean.TopicNewsList;

import java.util.List;

/**
 * Created by Administrator on 2016/12/11 0011.
 */
public class MyTopicNewsAdapter extends BaseAdapter {
    private Context context;
    private List<TopicNewsList.Newslist> newsList;

    public MyTopicNewsAdapter(Context ctx, List<TopicNewsList.Newslist> mNewsList) {
        this.context = ctx;
        this.newsList = mNewsList;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int i) {
        return newsList.get(i);
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
            view = LayoutInflater.from(context).inflate(R.layout.list_topic_item, null);
            holder.iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
            holder.tvNewsTitle = (TextView) view.findViewById(R.id.tv_topic_title);
            holder.tvTime = (TextView) view.findViewById(R.id.tv_topic_date);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        holder.tvNewsTitle.setText(newsList.get(i).getTitle());
        holder.tvTime.setText(newsList.get(i).getCtime());
        Glide.with(context).load(newsList.get(i).getPicUrl()).into(holder.iv_pic);
        return view;
    }

    class ViewHolder {
        ImageView iv_pic;
        TextView tvNewsTitle;
        TextView tvTime;
    }
}
