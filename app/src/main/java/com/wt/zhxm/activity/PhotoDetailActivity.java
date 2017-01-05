package com.wt.zhxm.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.wt.zhxm.utils.StatusBarColorUtils;
import com.wt.zhxm.view.MyWebView;
import com.wt.zhxm.view.MyWebView.OnScrollChangedCallback;

/**
 * @author wtt 组图详情页
 */
public class PhotoDetailActivity extends Activity implements OnClickListener {
    private ImageButton btnBack;
    private ImageButton btnSize;
    private ImageButton btnShare;
    private MyWebView mWebView;
    private ProgressBar mPgrogressBar;
    private String url;
    private RelativeLayout mRa;
    // 设置默认当前选择的字体的规格
    private int checkedItem = 2;
    // 设置选择字体后的规格
    private int mchoseItem;

    private WebSettings settings;
    // 当前网页的标题
    private String currtItemTitle;
    private AlertDialog.Builder builder;
    private int webnow;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_detail);
        StatusBarColorUtils.setChangeColor(this,getResources().getColor(R.color.green));
        url = getIntent().getStringExtra("url");
        initView();
        initData();
    }

    /**
     * 初始化布局
     */
    private void initView() {

        btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnSize = (ImageButton) findViewById(R.id.btn_size);
        btnShare = (ImageButton) findViewById(R.id.btn_share);
        mWebView = (MyWebView) findViewById(R.id.wv_photos_web);
        mPgrogressBar = (ProgressBar) findViewById(R.id.pb_photos_progress);
        mRa = (RelativeLayout) findViewById(R.id.mRa);
        mRa.setVisibility(View.INVISIBLE);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        btnBack.setOnClickListener(this);
        btnSize.setOnClickListener(this);
        btnShare.setOnClickListener(this);

        //设置WebView的一些属性
        settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        mWebView.loadUrl(url);
        //设置WebView监听
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mPgrogressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mPgrogressBar.setVisibility(View.INVISIBLE);
            }
        });
        //设置WebView监听,拿到标题
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                currtItemTitle = title;
            }
        });

        //拿到webview的滑动监听
        mWebView.setmOnScrollChangedCallback(new OnScrollChangedCallback() {
            @SuppressLint({"NewApi", "ResourceAsColor"})
            @Override
            public void onSChanged(int l, int t, int oldl, int oldt) {
                mRa.setVisibility(View.VISIBLE);
                webnow = mWebView.getHeight() + mWebView.getScrollY();
                handleTitleBarColorEvaluate();
            }
        });
    }

    /**
     * 标题栏渐变
     */
    protected void handleTitleBarColorEvaluate() {
        float viewHeight = 3;
        float height = (float) webnow / (float) 2000;
        if (height <= 0.6) {
            Log.i("123", "标题透明" + height);
            mRa.setVisibility(View.INVISIBLE);

            return;
        }
        if (height <= 0.93) {
            mRa.setVisibility(View.INVISIBLE);
            return;
        }
        if (height <= viewHeight && height > 0.93) {
            Log.i("123", "标题渐变" + height);
            float f = 255.0F * ((float) height / (float) viewHeight);
            mRa.setBackgroundColor(Color.argb((int) f, 37, 164, 187));
            return;
        }
        Log.i("123", "标题全显" + height);
        mRa.setBackgroundColor(Color.argb((int) 255, 37, 164, 187));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_size:
                String[] items = {"超大号字体", "大号字体", "正常字体", "小号字体", "超小号字体"};
                builder = new AlertDialog.Builder(PhotoDetailActivity.this);
                builder.setTitle("字体设置");
                builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mchoseItem = which;
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (mchoseItem) {
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
                        checkedItem = mchoseItem;
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
                break;
            case R.id.btn_share:
                ShowShareSDK shareSDK = new ShowShareSDK(PhotoDetailActivity.this);
                shareSDK.ShowShare(currtItemTitle, mWebView.getUrl(), currtItemTitle);
                break;
            default:
                break;
        }
    }

    // 销毁对话框
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
