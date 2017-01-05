package com.wt.zhxm.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author wtt 自定义开关按钮
 */
public class ToggleView extends View {

    private boolean mSwitchState = false;
    private Bitmap switchBackgroupBitmap;
    private Bitmap slideButtonBitmap;
    private Paint paint;
    //触摸状态
    private boolean isTouchMode = false;
    private float currentX;
    private OnSwitchStateUpdateListener onSwitchStateUpdateListener;
    private int switchbaseWidth;
    private int switchbaseHeight;

    public ToggleView(Context context) {
        super(context);
        init();

    }

    public ToggleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

        //自定义属性
        String namespace = "http://schemas.android.com/apk/res/com.wt.zhxm";
        int switchBackgroundResource = attrs.getAttributeResourceValue(namespace, "switch_background", -1);
        int slideButtonResource = attrs.getAttributeResourceValue(namespace, "slide_button", -1);
        mSwitchState = attrs.getAttributeBooleanValue(namespace, "switch_state", false);

        setSwichBackGroundResource(switchBackgroundResource);
        setSlideButtonResource(slideButtonResource);
    }

    public ToggleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        paint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(switchBackgroupBitmap.getWidth(), switchBackgroupBitmap.getHeight());

    }

    /*
     * 在画布上绘制内容
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制背景
        canvas.drawBitmap(switchBackgroupBitmap, 0, 0, paint);
        // 绘制滑块
        if (isTouchMode) {
            // 让滑块向左移动自身一半的位置
            float newLeft = currentX - slideButtonBitmap.getWidth() / 2.0f;
            int maxLeft = switchBackgroupBitmap.getWidth() - slideButtonBitmap.getWidth();

            //限制滑动距离
            if (newLeft < 0) {
                newLeft = 0;
            } else if (newLeft > maxLeft) {
                newLeft = maxLeft;
            }
            canvas.drawBitmap(slideButtonBitmap, newLeft, 0, paint);
        } else {
            if (mSwitchState) {
                int newLeft = switchBackgroupBitmap.getWidth() - slideButtonBitmap.getWidth();
                canvas.drawBitmap(slideButtonBitmap, newLeft, 0, paint);
            } else {
                canvas.drawBitmap(slideButtonBitmap, 0, 0, paint);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouchMode = true;
                currentX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                isTouchMode = false;
                currentX = event.getX();

                //在中间的位置
                float center = switchBackgroupBitmap.getWidth() / 2.0f;

                boolean state = currentX > center;

                if (state != mSwitchState && onSwitchStateUpdateListener != null) {
                    // 把最新的boolean, 状态传出去了
                    onSwitchStateUpdateListener.OnStateUpdate(state);
                }
                mSwitchState = state;
                break;
            default:
                break;
        }
        // 重绘界面
        invalidate(); // 会引发onDraw()被调用, 里边的变量会重新生效.界面会更新
        return true;
    }

    /**
     * @param switchBackground 设置背景图片
     */
    public void setSwichBackGroundResource(int switchBackground) {
        switchBackgroupBitmap = BitmapFactory.decodeResource(getResources(), switchBackground);


    }

    /**
     * 设置滑动图片
     */
    public void setSlideButtonResource(int slideButton) {
        slideButtonBitmap = BitmapFactory.decodeResource(getResources(), slideButton);
    }

    public void setSwitchState(boolean mSwitchState) {
        this.mSwitchState = mSwitchState;
    }


    //定义一个回调接口
    public interface OnSwitchStateUpdateListener {
        void OnStateUpdate(boolean state);
    }

    public void setOnonSwitchStateUpdateListener(OnSwitchStateUpdateListener onSwitchStateUpdateListener) {
        this.onSwitchStateUpdateListener = onSwitchStateUpdateListener;
    }

    ;

}
