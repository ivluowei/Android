package com.wt.zhxm.fragment.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.wt.zhxm.R;
import com.wt.zhxm.activity.TopicNewsListActivity;
import com.wt.zhxm.activity.TopicTopNewsActivity;
import com.wt.zhxm.adapter.MyTopicNewsAdapter;
import com.wt.zhxm.bean.TopicNews;
import com.wt.zhxm.bean.TopicNewsList;
import com.wt.zhxm.fragment.BaseFragment;
import com.wt.zhxm.fragment.ITabFragment;
import com.wt.zhxm.utils.GlideImageLoader;
import com.wt.zhxm.utils.GlobalContants;
import com.wt.zhxm.utils.NetUtils;
import com.wt.zhxm.utils.ToastUtil;
import com.wt.zhxm.view.XListView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class SmartFragment extends BaseFragment implements ITabFragment, XListView.IXListViewListener {
    private static final String TAG = "SmartFragment";
    private XListView xlistview;
    private View mHeaderView;
    private Banner banner;
    private boolean isRefresh = false;
    private List<TopicNews.Newslist> topNewsList = new ArrayList<TopicNews.Newslist>();
    private List<String> images = new ArrayList<String>();
    private int num = 10;
    private List<TopicNewsList.Newslist> mNewsList = new ArrayList<TopicNewsList.Newslist>();
    private MyTopicNewsAdapter adapter;

    @Override
    public int getLayoutId() {

        return R.layout.fragment_smart;
    }

    @Override
    public void setUpView(View view, Bundle savedInstanceState) {
        xlistview = (XListView) view.findViewById(R.id.lv_listview);
        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.header_banner, null);
        banner = (Banner) mHeaderView.findViewById(R.id.banner);
        //设置动画效果
        banner.setBannerAnimation(Transformer.CubeIn);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        xlistview.addHeaderView(mHeaderView);
        xlistview.setXListViewListener(this);

//        String cache = CacheUtils.getCache(getActivity(), GlobalContants.TOPIC_LIST_URL);
//        if (!TextUtils.isEmpty(cache)) {
//            paseListDate(cache);
//        }
        getDataFromServer();
        getDataListFromServer();
        adapter = new MyTopicNewsAdapter(getActivity(), mNewsList);
        xlistview.setAdapter(adapter);
        initData();

    }

    private void initData() {
        xlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), TopicNewsListActivity.class);
                intent.putExtra("url", mNewsList.get(i-2).getUrl());
                getActivity().startActivity(intent);
            }
        });
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getActivity(), TopicTopNewsActivity.class);
                intent.putExtra("topUrl", topNewsList.get(position).getUrl());
                getActivity().startActivity(intent);
            }
        });
    }

    /**
     * 请求服务器，获取专题列表数据
     */
    private void getDataListFromServer() {
        OkHttpUtils.get().url(GlobalContants.TOPIC_LIST_URL + num).addHeader("apikey", "e2543410a1c5ac820d431c9787819814").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                if (NetUtils.isNetworking(getActivity())) {
                    //  ToastUtil.show(getActivity(), getResources().getString(R.string.connection_failed));
                } else {
                    ToastUtil.show(getActivity(), getResources().getString(R.string.isNetWork));
                }
                xlistview.stopRefresh(true);
                xlistview.stopLoadMore();
            }

            @Override
            public void onResponse(Call call, String result) {
                Log.d(TAG, "onResponse: getDataListFromServer " + result);
                paseListDate(result);
                //CacheUtils.setCache(getActivity(), GlobalContants.Topic_TOP_URL, result);
                if (isRefresh) {
                    isRefresh = false;
                    xlistview.stopRefresh(true);
                    xlistview.stopLoadMore();
                }
            }
        });
    }

    private void paseListDate(String result) {
        Gson gson = new Gson();
        TopicNewsList topicNews = gson.fromJson(result, TopicNewsList.class);
        mNewsList.addAll(topicNews.getNewslist());
        adapter = new MyTopicNewsAdapter(getActivity(), mNewsList);
        xlistview.setAdapter(adapter);
        Log.d(TAG, "paseListDate:hffh " + mNewsList.get(0).getTitle());

    }

    /*
    * 请求服务器，获取专题数据
    */
    private void getDataFromServer() {
        OkHttpUtils.get().url(GlobalContants.Topic_TOP_URL).addHeader("apikey", "e2543410a1c5ac820d431c9787819814").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                if (NetUtils.isNetworking(getActivity())) {
                    ToastUtil.show(getActivity(), getResources().getString(R.string.connection_failed));
                } else {
                    ToastUtil.show(getActivity(), getResources().getString(R.string.isNetWork));
                }
            }

            @Override
            public void onResponse(Call call, String result) {
                Log.d(TAG, "onResponse:SmartFragment " + result);
                paseDate(result);
            }
        });
    }

    private void paseDate(String result) {
        topNewsList.clear();
        Gson gson = new Gson();
        TopicNews mTopNews = gson.fromJson(result, TopicNews.class);
        topNewsList = mTopNews.getNewslist();
        for (int i = 0; i < topNewsList.size(); i++) {
            images.add(topNewsList.get(i).getPicUrl());
        }
        banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
    }


    @Override
    public void onMenuItemClick(MenuItem item, Toolbar toolbar) {

    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        ++num;
        if (GlobalContants.TOPIC_LIST_URL + num != null) {
            getDataListFromServer();
        } else {
            xlistview.stopRefresh(true);
            xlistview.stopLoadMore();
        }

    }

    @Override
    public void onLoadMore() {
    }

}
