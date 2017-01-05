package com.wt.zhxm.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wt.zhxm.R;
import com.wt.zhxm.utils.DataCleanUtils;
import com.wt.zhxm.utils.PrefUtils;
import com.wt.zhxm.utils.ThemeChangeUtil;
import com.wt.zhxm.view.ToggleView;

/**
 * Created by Administrator on 2016/12/12 0012.
 */
public class LeftMenuAdapter extends BaseAdapter {
    private static final String TAG = "LeftMenuAdapter";
    // private List<String> list;
    private Context context;
    private String[] mLefts = {"清理缓存", "夜间模式", "版本更新", "关于应用", "意见反馈"};
    private Activity activity;
    private Toolbar mTollbar;
    private ListView mListview;
    private boolean isState;

    public LeftMenuAdapter(Context ctx, Activity ac,boolean mState) {
        this.context = ctx;
        this.activity = ac;
        this.isState=mState;
    }

    @Override
    public int getCount() {
        return mLefts.length;
    }

    @Override
    public Object getItem(int i) {
        // Log.d(TAG, "getItem: "+list.get(i));
        return mLefts[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.left_menu_item, null);
            holder.mTitle = (TextView) view.findViewById(R.id.left_title);
            holder.mCacheSize = (TextView) view.findViewById(R.id.tv_cache_size);
            holder.toggleView = (ToggleView) view.findViewById(R.id.toggleView);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (i == 0) {
            holder.mCacheSize.setVisibility(View.VISIBLE);
        } else {
            holder.mCacheSize.setVisibility(View.GONE);
        }
        if (i == 1) {
            holder.toggleView.setVisibility(View.VISIBLE);
        } else {
            holder.toggleView.setVisibility(View.GONE);
        }
        if (isState){
            holder.toggleView.setSwitchState(true);
        }else {
            holder.toggleView.setSwitchState(false);
        }
        holder.mTitle.setText(mLefts[i]);
        String size = null;
        try {
            size = DataCleanUtils.getTotalCacheSize(context);
            holder.mCacheSize.setText(size);
            listenner.setListenner(holder.mCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.toggleView.setOnonSwitchStateUpdateListener(new ToggleView.OnSwitchStateUpdateListener() {
            @Override
            public void OnStateUpdate(boolean state) {
                PrefUtils.setBoolean(context, "istate", state);
                if (state) {
                    if (!ThemeChangeUtil.isChange) {
                        Log.d(TAG, "OnStateUpdate: state为true时"+ThemeChangeUtil.isChange);
                        ThemeChangeUtil.isChange = true;
                        activity.recreate();
                    }
                } else {
                    Log.d(TAG, "OnStateUpdate: state为fase时"+ThemeChangeUtil.isChange);
                    ThemeChangeUtil.isChange = false;
                    activity.recreate();
                }

            }
        });
        return view;
    }

    class ViewHolder {
        TextView mTitle;
        TextView mCacheSize;
        ToggleView toggleView;
    }

    private MyAdapterListenner listenner;

    public interface MyAdapterListenner {
        void setListenner(TextView tvSize);

    }

    public void getListenner(MyAdapterListenner mListenner) {
        this.listenner = mListenner;
    }
}
