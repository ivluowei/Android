package com.wt.zhxm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wt.zhxm.R;
import com.wt.zhxm.bean.PhotoJson;

import java.util.List;

/**
 * Created by Administrator on 2016/12/10 0010.
 */
public class MyPhotoAdapter extends BaseAdapter{
    private Context context;
    private List<PhotoJson.Books> mPhotoList;
    ImageLoader imageLoader;
    public MyPhotoAdapter(Context ctx,List<PhotoJson.Books> photoList){
        this.context=ctx;
        this.mPhotoList=photoList;
    }
    @Override
    public int getCount() {
        return mPhotoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPhotoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.list_photo_item,null);
            holder.ivPhoto = (ImageView) view.findViewById(R.id.iv_photo);
            holder.ivTitle = (TextView) view.findViewById(R.id.tv_photo_title);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
//        imageLoader=ImageLoader.getInstance();
//        imageLoader.displayImage(mPhotoList.get(position).getHeadImage(),holder.ivPhoto);
        Glide.with(context).load(mPhotoList.get(position).getHeadImage()).into(holder.ivPhoto);
        holder.ivTitle.setText(mPhotoList.get(position).getTitle());
        return view;
    }

    class ViewHolder {
        ImageView ivPhoto;
        TextView ivTitle;
    }
}
