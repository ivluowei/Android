package com.wt.zhxm.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.wt.zhxm.R;
import com.wt.zhxm.utils.ShowShareSDK;
import com.wt.zhxm.view.MyWebView;
import com.wt.zhxm.view.MyWebView.OnScrollChangedCallback;

/**
 * @author wtt
 *         专题页
 */
public class TopicTopNewsActivity extends Activity {

    private ImageButton btnBack;
    private ImageButton btn_share;
    private ImageButton btn_size;
    private MyWebView mWebView;
    private ProgressBar pbProgress;
    // 当前选择的字体状态,默认选中正常字体
    private int checkedItem = 2;
    private int currtChoseItem;
    private WebSettings settings;
    private AlertDialog.Builder builder;
    private String currtItemTitle;
    private RelativeLayout mRa;
    private int webnow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topictopnews_detail);
        initView();
        initData();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        btnBack = (ImageButton) findViewById(R.id.btn_back);
        btn_share = (ImageButton) findViewById(R.id.btn_share);
        btn_size = (ImageButton) findViewById(R.id.btn_size);
        mWebView = (MyWebView) findViewById(R.id.wv_topic_top_web);
        pbProgress = (ProgressBar) findViewById(R.id.pb_topic_top_progress);
        btnBack.setOnClickListener(new MyOnClickListenner());
        btn_size.setOnClickListener(new MyOnClickListenner());
        btn_share.setOnClickListener(new MyOnClickListenner());
        mRa = (RelativeLayout) findViewById(R.id.mRa);

    }

    /**
     * 初始化数据
     */
    private void initData() {
        String url = getIntent().getStringExtra("topUrl");
        mWebView.loadUrl(url);
        settings = mWebView.getSettings();
        //显示放大缩小按钮
        settings.setBuiltInZoomControls(true);
        //支持js
        settings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            /*
             * 网页开始加载
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pbProgress.setVisibility(View.VISIBLE);
            }

            /*
             * 网页加载结束
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbProgress.setVisibility(View.INVISIBLE);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                currtItemTitle = title;
            }
        });

        //监听webview滑动事件
        mWebView.setmOnScrollChangedCallback(new OnScrollChangedCallback() {
            @Override
            public void onSChanged(int l, int t, int oldl, int oldt) {
                webnow = mWebView.getHeight() + mWebView.getScrollY();
                handleTitleBarColorEvaluate();
            }
        });
    }

    /**
     * 处理颜色渐变
     */
    protected void handleTitleBarColorEvaluate() {
        float viewHeight = 3;
        float height = (float) webnow / (float) 2000;
        if (height <= 0.6) {
            mRa.setBackgroundColor(Color.argb(0, 255, 131, 10));
            return;
        }
        if (height <= viewHeight) {
            float f = 255.0F * ((float) height / (float) viewHeight);
            mRa.setBackgroundColor(Color.argb((int) f, 255, 0, 0));
            return;
        }
        mRa.setBackgroundColor(Color.argb(255, 255, 0, 0));
    }

    class MyOnClickListenner implements OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_back:
                    finish();
                    break;
                case R.id.btn_size:
                    String[] items = {"超大号字体", "大号字体", "正常字体", "小号字体", "超小号字体"};
                    builder = new AlertDialog.Builder(TopicTopNewsActivity.this);
                    builder.setTitle("字体设置");
                    builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            currtChoseItem = which;
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        // 字体大小改变
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (currtChoseItem) {
                                case 0:
                                    settings.setTextSize(TextSize.LARGEST);
                                    break;
                                case 1:
                                    settings.setTextSize(TextSize.LARGER);
                                    break;
                                case 2:
                                    settings.setTextSize(TextSize.NORMAL);
                                    break;
                                case 3:
                                    settings.setTextSize(TextSize.SMALLER);
                                    break;
                                case 4:
                                    settings.setTextSize(TextSize.SMALLEST);
                                    break;
                                default:
                                    break;
                            }
                            // 重新给选择状态赋值
                            currtChoseItem = which;
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    builder.show();
                    checkedItem = currtChoseItem;
                    break;
                case R.id.btn_share:
                    ShowShareSDK shareSDK = new ShowShareSDK(TopicTopNewsActivity.this);
                    shareSDK.ShowShare(currtItemTitle, mWebView.getUrl(), currtItemTitle);
                    break;
                default:
                    break;
            }
        }

    }

    /*
     * 消除dialog,如果不销毁的话在某些手机上就会窗体溢出
     */
    @SuppressLint("NewApi")
    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (builder != null) {
            builder.setOnDismissListener(new OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    dialog.dismiss();
                }
            });
        }
    }
}
