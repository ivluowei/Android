package com.wt.zhxm.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.wt.zhxm.R;
import com.wt.zhxm.utils.PrefUtils;
import com.wt.zhxm.utils.StatusBarColorUtils;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends Activity implements OnClickListener {
    private ViewPager pager;
    //引导页背景图数组
    private int[] imageResourIds = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
    private List<ImageView> mImageView = new ArrayList<ImageView>();
    private LinearLayout llPoint;

    //原点间的距离
    private int mPointWidth;
    private View mReadPoint;

    //导航页按钮
    private Button btnStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        StatusBarColorUtils.setChangeColor(this, getResources().getColor(R.color.black));
        initView();
        initPagerAdapter();


    }

    /**
     * 初始化布局
     */
    private void initView() {
        mReadPoint = findViewById(R.id.view_red_point);
        llPoint = (LinearLayout) findViewById(R.id.ll_point_group);
        btnStart = (Button) findViewById(R.id.btn_start);
        pager = (ViewPager) findViewById(R.id.vp_guide);
        mImageView = new ArrayList<ImageView>();
        for (int i = 0; i < imageResourIds.length; i++) {
            ImageView image = new ImageView(GuideActivity.this);
            image.setBackgroundResource(imageResourIds[i]);
            mImageView.add(image);

            //添加小圆点
            View point = new View(GuideActivity.this);
            point.setBackgroundResource(R.drawable.shape_point_gray);
            LayoutParams params = new LayoutParams(10, 10);
            if (i > 0) {
                //设置原点间隔
                params.leftMargin = 10;
            }
            //给原点设置大小
            point.setLayoutParams(params);
            //把原点添加到容器中去
            llPoint.addView(point);

        }

        btnStart.setOnClickListener(this);


        //获取视图树，对layout结束事件进行监听
        llPoint.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {


            @Override
            public void onGlobalLayout() {
                //当layout结束后执行此方法
                llPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mPointWidth = llPoint.getChildAt(1).getLeft() - llPoint.getChildAt(0).getLeft();


            }
        });
    }

    /**
     * 实现监听
     */
    private void initPagerAdapter() {
        pager.setAdapter(new MyPagerAdapter());
        pager.setOnPageChangeListener(new GuidePageListener());

    }

    /**
     * @author wtt 继承PagerAdapter
     */
    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {

            return imageResourIds.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {

            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageView.get(position));
            return mImageView.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * @author wtt
     *         实现OnPageChangeListener接口
     */
    class GuidePageListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // 滑动状态发生改变

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // 滑动事件
            int len = (int) (mPointWidth * arg1) + mPointWidth * arg0;
            //获取当前红点布局参数
            RelativeLayout.LayoutParams mparams = new RelativeLayout.LayoutParams(10, 10);
            mparams.leftMargin = len;
            mReadPoint.setLayoutParams(mparams);


        }

        // 某个页面被选中
        @Override
        public void onPageSelected(int arg0) {
            //最后一个页面被选中
            if (arg0 == imageResourIds.length - 1) {
                btnStart.setVisibility(View.VISIBLE);
            } else {
                btnStart.setVisibility(View.INVISIBLE);
            }

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                //更新sp，表示已经展示过新手引导了
                PrefUtils.setBoolean(GuideActivity.this, "is_guide_showed", true);
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }

    }


}
