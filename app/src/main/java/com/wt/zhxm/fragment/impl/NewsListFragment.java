package com.wt.zhxm.fragment.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.wt.zhxm.R;
import com.wt.zhxm.activity.NewsDetailActivity;
import com.wt.zhxm.adapter.MyNewsAdapter;
import com.wt.zhxm.bean.NewsInfo;
import com.wt.zhxm.bean.SmartCategory;
import com.wt.zhxm.bean.SmartContent;
import com.wt.zhxm.fragment.BaseFragment;
import com.wt.zhxm.utils.CacheUtils;
import com.wt.zhxm.utils.GlideImageLoader;
import com.wt.zhxm.utils.GlobalContants;
import com.wt.zhxm.utils.ToastUtil;
import com.wt.zhxm.view.XListView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * Created by Administrator on 2016/12/11 0011.
 */
public class NewsListFragment extends BaseFragment implements XListView.IXListViewListener {
    private static final String TAG = "NewsListFragment";
    private XListView xListView;
    private int id;
    private SmartCategory.Tngou mlist;
    private View mHeaderView;
    private Banner banner;
    private List<SmartContent.Tngou> mContentList = new ArrayList<SmartContent.Tngou>();
    private List<String> images = new ArrayList<String>();
    private List<String> titles = new ArrayList<String>();
    private MyNewsAdapter adapter;
    private int page = 1;
    private boolean isRefresh = false;
    private NewsInfo info;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
            intent.putExtra("url", info.getUrl());
            startActivity(intent);
        }
    };

    public static NewsListFragment newInstance(SmartCategory.Tngou mlist) {
        NewsListFragment listFragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", mlist.getId());
        listFragment.setArguments(bundle);
        return listFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        id = getArguments().getInt("id");
        Log.d("id", id + "");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_newslist;
    }

    @Override
    protected void setUpView(View view, Bundle savedInstanceState) {
        xListView = (XListView) view.findViewById(R.id.listview);
        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.header_banner, null);
        banner = (Banner) mHeaderView.findViewById(R.id.banner);
        //设置动画效果
        banner.setBannerAnimation(Transformer.FlipHorizontal);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        xListView.addHeaderView(mHeaderView);
        xListView.setXListViewListener(this);
        adapter = new MyNewsAdapter(getActivity(), mContentList);
        xListView.setAdapter(adapter);
        String cache = CacheUtils.getCache(getActivity(), GlobalContants.SMART_LIST_URL+id + page);
        if (!TextUtils.isEmpty(cache)) {
            paseData(cache, 1);
        }
        getDataFromServer();
        initData();
    }

        private void initData() {
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemClick: 点击下标"+i);
                getDetails(i-2);
            }
        });
    }

    /*
    * 拿新闻详情数据
    * */
    private void getDetails(final int i) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                OkHttpUtils.get().url(GlobalContants.SMART_DETAILS_URL + mContentList.get(i).getId()).addHeader("apikey", "e2543410a1c5ac820d431c9787819814").build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(Call call, String result) {
                        Log.d(TAG, "onResponse:详情 " + result);
                        paseData(result, 2);
                        mHandler.sendEmptyMessage(0);
                    }
                });
            }
        }.start();
    }

    private void getDataFromServer() {
        OkHttpUtils.get().url(GlobalContants.SMART_LIST_URL + id + page).addHeader("apikey", "e2543410a1c5ac820d431c9787819814").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Call call, String result) {
                Log.d(TAG, "onResponse: " + result);
                paseData(result, 1);
                CacheUtils.setCache(getActivity(), GlobalContants.SMART_LIST_URL+ id + page, result);
                if (isRefresh) {
                    isRefresh = false;
                    xListView.stopRefresh(true);
                }
            }
        });
    }

    private void paseData(String result, int code) {
        Gson gson = new Gson();
        switch (code) {
            case 1:
                SmartContent mContent = gson.fromJson(result, SmartContent.class);
                mContentList = mContent.getTngou();
                Log.d("mContentList", mContentList.size() + "");
                for (int i = 0; i < mContentList.size(); i++) {
                    images.add("http://tnfs.tngou.net/image" + mContentList.get(i).getImg());
                    titles.add(mContentList.get(i).getTitle());
                }
                banner.setBannerTitles(titles);
                banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
                adapter = new MyNewsAdapter(getActivity(), mContentList);
                xListView.setAdapter(adapter);
                break;
            case 2:
                info = gson.fromJson(result, NewsInfo.class);
                break;
            default:
                break;
        }


    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        ++page;
        if (GlobalContants.SMART_LIST_URL + id + page != null) {
            mContentList.clear();
            getDataFromServer();
        } else {
            ToastUtil.show(getActivity(), getResources().getString(R.string.no_pager));
        }
        getDataFromServer();
    }

    @Override
    public void onLoadMore() {

    }

}
