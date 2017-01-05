package com.wt.zhxm.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wt.zhxm.R;

/**
 * @author wtt 自定义dialog
 */
public class MyDialog extends Dialog {

    // 标题
    private TextView tvTitle;
    // 内容
    private TextView tvMsg;
    // 确定按钮
    private Button btnConfirm;
    private Context ctx;
    private ImageView ivIcon;
    private int TAG;
    private Button btnConfirms;
    private Button btnCancel;
    private MyDialogListenner myDialogListenner;

    public MyDialog(Context context, int tag) {
        super(context, R.style.MyDialog);
        this.ctx = context;
        this.TAG = tag;
    }

    public MyDialog(Context context, int theme, int tag) {
        super(context, R.style.MyDialog);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setCancelable(false);
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 0.8);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);
        if (TAG == 0) {
            setContentView(R.layout.smart_detail_dialog);
            // 实例化控件
            btnConfirm = (Button) findViewById(R.id.btn_confirm);
            btnCancel = (Button) findViewById(R.id.btn_cancel);
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (myDialogListenner != null) {
                        //调用接口
                        myDialogListenner.setDialog();

                    } else {
                        dismiss();
                    }
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });


        } else if (TAG == 1) {
            setContentView(R.layout.activity_setting_dialog);
            btnConfirms = (Button) findViewById(R.id.btn_setting_confirm);
            btnCancel = (Button) findViewById(R.id.btn_setting_cancel);
            btnConfirms.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (myDialogListenner != null) {
                        //调用接口
                        myDialogListenner.setDialog();

                    } else {
                        dismiss();
                    }
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }


    }

    public MyDialog setTitle(String title) {
        tvTitle.setText(title);

        return this;
    }

    public MyDialog setMsg(String msg) {
        tvMsg.setText(msg);
        return this;
    }

    public MyDialog setIcon(String uri) {
        Glide.with(ctx).load(uri).into(ivIcon);
        return this;
    }

    //定义一个接口，用于设置页面dialog退出登陆实现
    public interface MyDialogListenner {
        void setDialog();
    }

    public void setMyDialogListenner(MyDialogListenner myDialogListenner) {
        this.myDialogListenner = myDialogListenner;
    }
}
