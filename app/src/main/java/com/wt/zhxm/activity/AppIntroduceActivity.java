package com.wt.zhxm.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wt.zhxm.R;
import com.wt.zhxm.utils.PrefUtils;

/**
 * @author wtt
 *         关于应用页
 */
public class AppIntroduceActivity extends Activity {

    private ImageButton btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_introduce);
        initView();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        ImageView icon = (ImageView) findViewById(R.id.tttttt);
        String micon = PrefUtils.getString(this, "username", "");
        Log.i("123", "" + micon);
        Glide.with(this).load("http://q.qlogo.cn/qqapp/1105671802/979B592FD1706F8C71801FB2327C22D0/40").into(icon);
        btnBack = (ImageButton) findViewById(R.id.btn_about_back);
        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
