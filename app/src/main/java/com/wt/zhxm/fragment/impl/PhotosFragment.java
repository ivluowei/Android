package com.wt.zhxm.fragment.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.wt.zhxm.R;
import com.wt.zhxm.activity.PhotoDetailActivity;
import com.wt.zhxm.adapter.MyPhotoAdapter;
import com.wt.zhxm.bean.PhotoJson;
import com.wt.zhxm.fragment.BaseFragment;
import com.wt.zhxm.fragment.ITabFragment;
import com.wt.zhxm.utils.CacheUtils;
import com.wt.zhxm.utils.GlobalContants;
import com.wt.zhxm.utils.NetUtils;
import com.wt.zhxm.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class PhotosFragment extends BaseFragment implements ITabFragment {
    private ListView lvPhoto;
    private GridView gvPhoto;
    private List<PhotoJson.Books> mPhotoList = new ArrayList<PhotoJson.Books>();
    //设置当前列表显示，默认true;
    private boolean isListDisplay = true;
    private static String TAG = "PhotosFragment";
    private boolean isTrue = true;

    @Override
    public int getLayoutId() {

        return R.layout.fragment_photos;
    }

    @Override
    public void setUpView(View view, Bundle savedInstanceState) {
        lvPhoto = (ListView) view.findViewById(R.id.lv_photo);
        gvPhoto = (GridView) view.findViewById(R.id.gv_photo);
        String cache = CacheUtils.getCache(getActivity(), GlobalContants.PHOTO_URL);
        if (!TextUtils.isEmpty(cache)) {
            paseData(cache);
        }
        getDataService();
        lvPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), PhotoDetailActivity.class);
                intent.putExtra("url", mPhotoList.get(i).getBookUrl());
                getActivity().startActivity(intent);
            }
        });
        gvPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), PhotoDetailActivity.class);
                intent.putExtra("url", mPhotoList.get(i).getBookUrl());
                getActivity().startActivity(intent);
            }
        });

    }

    private void PhotoExhibitions() {
        if (isListDisplay) {
            isListDisplay = false;
            lvPhoto.setVisibility(View.GONE);
            gvPhoto.setVisibility(View.VISIBLE);

        } else {
            isListDisplay = true;
            lvPhoto.setVisibility(View.VISIBLE);
            gvPhoto.setVisibility(View.GONE);

        }
    }

    private void getDataService() {
        OkHttpUtils.get().url(GlobalContants.PHOTO_URL).addHeader("apikey", "e2543410a1c5ac820d431c9787819814").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                if (NetUtils.isNetworking(getActivity())) {
                  //  ToastUtil.show(getActivity(), getResources().getString(R.string.connection_failed));
                } else {
                    ToastUtil.show(getActivity(), getResources().getString(R.string.isNetWork));
                }
            }

            @Override
            public void onResponse(Call call, String result) {
                Log.d(TAG, "onResponse: " + result);
                paseData(result);
               CacheUtils.setCache(getActivity(), GlobalContants.PHOTO_URL, result);

            }
        });

    }

    private void paseData(String result) {
        Gson gson = new Gson();
        PhotoJson mPhotos = gson.fromJson(result, PhotoJson.class);
        Log.i("123", "组图:" + mPhotos);
        mPhotoList = mPhotos.getData().getBooks();
        Log.i("123", "组图集合:" + mPhotoList.toString());
        lvPhoto.setAdapter(new MyPhotoAdapter(getActivity(), mPhotoList));
        gvPhoto.setAdapter(new MyPhotoAdapter(getActivity(), mPhotoList));
    }


    @Override
    public void onMenuItemClick(MenuItem item, Toolbar toolbar) {

        toolbar.getMenu().clear();

        if (isTrue) {
            toolbar.inflateMenu(R.menu.main1);
        } else {
            toolbar.inflateMenu(R.menu.main);
        }
        isTrue = !isTrue;

        PhotoExhibitions();
    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }

}
