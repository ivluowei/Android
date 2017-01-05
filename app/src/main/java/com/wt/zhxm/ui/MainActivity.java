package com.wt.zhxm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wt.zhxm.R;
import com.wt.zhxm.activity.AppIntroduceActivity;
import com.wt.zhxm.activity.GiveOpinionActivity;
import com.wt.zhxm.adapter.LeftMenuAdapter;
import com.wt.zhxm.fragment.ITabFragment;
import com.wt.zhxm.fragment.impl.NewsFragment;
import com.wt.zhxm.fragment.impl.PhotosFragment;
import com.wt.zhxm.fragment.impl.SmartFragment;
import com.wt.zhxm.utils.DataCleanUtils;
import com.wt.zhxm.utils.PrefUtils;
import com.wt.zhxm.utils.ThemeChangeUtil;
import com.wt.zhxm.utils.ToastUtil;
import com.wt.zhxm.view.CircleImageView;
import com.wt.zhxm.view.MyDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

public class MainActivity extends AppCompatActivity implements TabLayout.onTabClickListener, Toolbar.OnMenuItemClickListener, View.OnClickListener {

    private DrawerLayout drawerLayout;
    private FrameLayout frameLayout;
    private LinearLayout lv_leftmenu;
    private TabLayout tabLayout;
    private Toolbar tollbar;
    private ArrayList<TabLayout.Tab> tabs;
    private ITabFragment fragment;
    public static final int MODE_BACK = 0;
    public static final int MODE_PHOTO = 1;
    private Toolbar mToolbar;
    private TextView mTitle;
    private ListView listview;
    private List<String> list = new ArrayList<>();
    private Intent intent;
    private LeftMenuAdapter adapter;
    private TextView mCacheSize;
    private TextView mLogin;
    private CircleImageView ivIcon;
    private TextView tvName;
    private Button sign_out;
    private Button btn_out;
    private boolean isLogin = false;
    private boolean isState;
    private long mExitTime = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10:
                    //取出登陆名登陆头像
                    tvName.setText(PrefUtils.getString(getApplicationContext(), "username", ""));
                    Glide.with(getApplicationContext()).load(PrefUtils.getString(getApplicationContext(),"usericon", ""))
                            .into(ivIcon);
                    break;
                case 20:
                    tvName.setText("先登陆！");
                    Glide.with(getApplicationContext()).load(R.drawable.login).into(ivIcon);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeChangeUtil.changeTheme(this);
        setContentView(R.layout.activity_main);
        ShareSDK.initSDK(getApplicationContext());
        isState = PrefUtils.getBoolean(MainActivity.this, "istate", false);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabs = new ArrayList<TabLayout.Tab>();
        tabs.add(new TabLayout.Tab(R.drawable.btn_tab_news_selector, R.string.tab_new, MODE_BACK, NewsFragment.class));
        tabs.add(new TabLayout.Tab(R.drawable.btn_tab_setting_selector, R.string.tab_zixun, MODE_PHOTO, PhotosFragment.class));
        tabs.add(new TabLayout.Tab(R.drawable.btn_tab_gov_selector, R.string.tab_zhibo, MODE_BACK, SmartFragment.class));
        tabLayout.setUpData(tabs, this);
        tabLayout.setCurrentTab(0);
        initView();

    }

    /**
     * 初始化控件
     */
    private void initView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawlayout);
        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        lv_leftmenu = (LinearLayout) findViewById(R.id.lv_leftmenus);
        tollbar = (Toolbar) findViewById(R.id.tollbar);
        listview = (ListView) findViewById(R.id.left_list);
        mLogin = (TextView) findViewById(R.id.ll_qq_login);
        ivIcon = (CircleImageView) findViewById(R.id.iv_setting_icon);
        tvName = (TextView) findViewById(R.id.tv_login_name);
        sign_out = (Button) findViewById(R.id.sign_out);
        btn_out = (Button) findViewById(R.id.btn_out);
        String username = PrefUtils.getString(getApplicationContext(), "username", "");
        String usericon = PrefUtils.getString(getApplicationContext(), "usericon", "");
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(usericon)) {
            tvName.setText(username);
            Glide.with(getApplicationContext()).load(usericon).into(ivIcon);
        }
        adapter = new LeftMenuAdapter(getApplicationContext(), this, isState);
        listview.setAdapter(adapter);
        initIntent();
        mLogin.setOnClickListener(this);
        sign_out.setOnClickListener(this);
        btn_out.setOnClickListener(this);
    }

    private void initIntent() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        DataCleanUtils.cleanSharedPreference(getApplicationContext());
                        ToastUtil.show(getApplicationContext(), getResources().getString(R.string.cache_clean));
                        adapter.getListenner(new LeftMenuAdapter.MyAdapterListenner() {
                            @Override
                            public void setListenner(TextView tvSize) {
                                tvSize.setText("");
                            }
                        });
                        adapter.notifyDataSetInvalidated();
                        break;
                    case 2:
                        ToastUtil.show(getApplicationContext(), getResources().getString(R.string.no_update));
                        break;
                    case 3:
                        intent = new Intent(getApplicationContext(), AppIntroduceActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case 4:
                        intent = new Intent(getApplicationContext(), GiveOpinionActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                }
            }
        });
    }

    @Override
    public void onTabClick(TabLayout.Tab tab) {
        setUpToolBar(tab.textResId, tab.modeResId);
        try {
            ITabFragment tmpFragment = (ITabFragment) getSupportFragmentManager().findFragmentByTag(tab.targetFragmentClz.getSimpleName());
            if (tmpFragment == null) {
                //tmpFragment当前的fragment，    fragment上一个fragment
                tmpFragment = tab.targetFragmentClz.newInstance();
                if (fragment == null) {
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.content_frame, tmpFragment.getFragment(), tab.targetFragmentClz.getSimpleName()).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().hide(fragment.getFragment())
                            .add(R.id.content_frame, tmpFragment.getFragment(), tab.targetFragmentClz.getSimpleName()).commit();
                }
            } else {
                getSupportFragmentManager().beginTransaction().hide(fragment.getFragment())
                        .show(tmpFragment.getFragment()).commit();
            }
            fragment = tmpFragment;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        fragment.onMenuItemClick(item, mToolbar);
        return false;
    }

    private void setUpToolBar(int textResId, int modeResId) {

        mToolbar = (Toolbar) findViewById(R.id.tollbar);
        mTitle = (TextView) findViewById(R.id.tv_tile);
        mToolbar.setNavigationIcon(R.drawable.img_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        if (mToolbar != null) {
            mToolbar.getMenu().clear();

            if (modeResId == MODE_PHOTO) {
                mToolbar.inflateMenu(R.menu.main);
            }
            mToolbar.setOnMenuItemClickListener(this);
        }

        setUpTitle(textResId);

    }

    protected void setUpTitle(int titleId) {
        if (titleId > 0 && mTitle != null) {
            mTitle.setText(titleId);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //   super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_qq_login:
                isLogin = true;
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                qq.setPlatformActionListener(new PlatformActionListener() {
                    private PlatformDb platDB;

                    @Override
                    public void onCancel(Platform arg0, int arg1) {

                    }

                    @Override
                    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                        if (arg1 == Platform.ACTION_USER_INFOR) {
                            platDB = arg0.getDb();
                            // 通过DB获取各种数据
                            platDB.getToken();
                            platDB.getUserGender();
                            platDB.getUserIcon();
                            platDB.getUserId();
                            platDB.getUserName();
                        }
                        Log.i("specter", "登陆名称" + platDB.getUserName());
                        Log.i("specter", "登陆头像" + platDB.getUserIcon());
                        PrefUtils.setString(getApplicationContext(), "username", platDB.getUserName());
                        //platDB.getUserIcon()这个获取到的图片 不清晰，arg2.get("figureurl_qq_2")这个清晰
                        PrefUtils.setString(getApplicationContext(), "usericon", arg2.get("figureurl_qq_2").toString());
                        Message msg = new Message();
                        msg.what = 10;
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void onError(Platform arg0, int arg1, Throwable arg2) {
                    }

                });
                qq.authorize();// 单独授权
                qq.showUser(null);// 授权并获取用户信息
                break;
            case R.id.sign_out:
                final MyDialog dialog = new MyDialog(MainActivity.this, 1);
                dialog.show();
                dialog.setMyDialogListenner(new MyDialog.MyDialogListenner() {
                    @Override
                    public void setDialog() {
                        PrefUtils.setString(MainActivity.this, "username", null);
                        PrefUtils.setString(MainActivity.this, "usericon", null);
                        Message msg = new Message();
                        msg.what = 20;
                        mHandler.sendMessage(msg);
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.btn_out:
                final MyDialog dialog2 = new MyDialog(MainActivity.this, 0);
                dialog2.show();
                dialog2.setMyDialogListenner(new MyDialog.MyDialogListenner() {
                    @Override
                    public void setDialog() {
                        finish();
                        dialog2.dismiss();
                    }
                });
                break;
        }
    }

    /*
        * 在anctivity销毁之前先终止Glide请求，否则会出错
        * */
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (Util.isOnMainThread()) {
//            Glide.with(getApplicationContext()).pauseRequests();
//        }
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime > 3000) {
                ToastUtil.show(this, "在按一次返回键，退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
        return true;
    }

}
