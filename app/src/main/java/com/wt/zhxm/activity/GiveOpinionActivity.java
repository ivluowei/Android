package com.wt.zhxm.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.wt.zhxm.R;
import com.wt.zhxm.bean.VoiceBean;
import com.wt.zhxm.utils.CheckInstall;
import com.wt.zhxm.utils.ToastUtil;

import java.util.ArrayList;

/**
 * @author wtt
 *         意见反馈页
 */
public class GiveOpinionActivity extends Activity implements OnClickListener {

    private ImageButton btnBack;
    private EditText mOpinion;
    private RelativeLayout rlVoice;
    private EditText mContactWay;
    private Button btnSubmit;
    private LinearLayout llQQ;
    private LinearLayout llTelephone;
    private TextView tvQQ;
    private Intent intent;
    private TextView tvNum;
    StringBuffer mTextBuffer = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_opinion);
        //初始化语音对象
        SpeechUtility.createUtility(GiveOpinionActivity.this, SpeechConstant.APPID +"=57db9967");
        initView();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        btnBack = (ImageButton) findViewById(R.id.btn_opinion_back);
        mOpinion = (EditText) findViewById(R.id.et_opinion);
        rlVoice = (RelativeLayout) findViewById(R.id.voice_rl);
        mContactWay = (EditText) findViewById(R.id.et_contact_way);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        llQQ = (LinearLayout) findViewById(R.id.ll_qq);
        tvQQ = (TextView) findViewById(R.id.tv_customer_server_qq);
        llTelephone = (LinearLayout) findViewById(R.id.ll_phone);
        tvNum = (TextView) findViewById(R.id.tv_num);
        btnBack.setOnClickListener(this);
        rlVoice.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        llQQ.setOnClickListener(this);
        llTelephone.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_opinion_back:
                finish();
                break;

            //语音留言
            case R.id.voice_rl:
                //1.创建RecognizerDialog对象
                RecognizerDialog mDialog = new RecognizerDialog(this, mInitListener);
                //2.设置accent、language等参数
			mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
			mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
                //若要将UI控件用于语义理解，必须添加以下参数设置，设置之后onResult回调返回将是语义理解
                //结果
                 mDialog.setParameter("asr_sch", "1");
                 mDialog.setParameter("nlp_version", "2.0");
                //3.设置回调接口
                mDialog.setListener(mRecognizerDialogListener);
                //4.显示dialog，接收语音输入
                mDialog.show();

                break;
            case R.id.btn_submit:
                if (!TextUtils.isEmpty(mOpinion.getText().toString())) {
                    mOpinion.setText("");
                    mContactWay.setText("");
                    ToastUtil.show(GiveOpinionActivity.this, getResources().getString(R.string.opinion_success_back));
                    finish();
                } else {
                    ToastUtil.show(GiveOpinionActivity.this, getResources().getString(R.string.opinion_fail_back));

                }
                break;
            case R.id.ll_qq:
                if (CheckInstall.isisQQClientAvailable(GiveOpinionActivity.this)) {
                    // 打开手机QQ与指定用户聊天界面
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + tvQQ.getText().toString() + "";
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                break;
            case R.id.ll_phone:
                intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + tvNum.getText().toString()));
                startActivity(intent);
                break;


            default:
                break;
        }
    }

	private InitListener mInitListener=new InitListener() {

		@Override
		public void onInit(int arg0) {

		}
	};

	private RecognizerDialogListener mRecognizerDialogListener=new RecognizerDialogListener() {

		@Override
		public void onResult(RecognizerResult result, boolean isLast) {
		 String text=paseData(result.getResultString());
		 mTextBuffer.append(text);
		 if (isLast) {
			String finalText=mTextBuffer.toString();
			mTextBuffer=new StringBuffer();
			mOpinion.setText(finalText);
		}
		}

		@Override
		public void onError(SpeechError arg0) {

		}
	};



    /**
     * @param resultString
     * 解析语音
     */
	protected String paseData(String resultString) {
		Gson gson=new Gson();
		VoiceBean bean=gson.fromJson(resultString, VoiceBean.class);
		ArrayList<VoiceBean.Ws> ws=bean.ws;
		StringBuffer buffer=new StringBuffer();
		for (VoiceBean.Ws Mws:ws) {
			String text=Mws.cw.get(0).w;
			buffer.append(text);
		}

		return buffer.toString();
	}

}
