package com.wt.zhxm.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.wt.zhxm.R;
import com.wt.zhxm.utils.PrefUtils;

/**
 * @author wtt
 *         闪屏页
 */
public class SplashActivity extends Activity {

    private RelativeLayout rl_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        MyAnimation();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        rl_root = (RelativeLayout) findViewById(R.id.rl_root);
    }

    /**
     * 集合动画实现
     */
    private void MyAnimation() {
        // 旋转动画
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setFillAfter(true);
        // 缩放动画
        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(1000);
        scale.setFillAfter(true);
        // 渐变动画
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(2000);
        alpha.setFillAfter(true);

        // 动画集合
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(alpha);

        // 设置动画监听
        set.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                jumpNextPager();
            }

        });
        // 启动动画
        rl_root.startAnimation(set);

    }

    /**
     * 跳转到下一个界面
     */
    private void jumpNextPager() {
        //判断之前有没有显示过闪屏页
        boolean userGuide = PrefUtils.getBoolean(SplashActivity.this, "is_guide_showed", false);
        if (userGuide) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
