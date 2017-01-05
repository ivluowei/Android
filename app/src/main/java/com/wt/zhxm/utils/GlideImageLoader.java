package com.wt.zhxm.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.wt.zhxm.ui.MainActivity;
import com.youth.banner.loader.ImageLoader;


public class GlideImageLoader extends ImageLoader {
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        if (Util.isOnMainThread()) {
            if (!((MainActivity) context).isDestroyed()) {
                Glide.with(context)
                        .load(path)
                        .into(imageView);
            }
        }

    }


}
