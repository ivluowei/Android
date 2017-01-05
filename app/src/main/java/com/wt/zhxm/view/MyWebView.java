package com.wt.zhxm.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class MyWebView extends WebView {

	public MyWebView(Context context) {
		super(context);

	}

	public MyWebView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public MyWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	@Override
    protected void onScrollChanged(final int l,final int t, final int oldl,final int oldt) {
    	super.onScrollChanged(l, t, oldl, oldt); 
    	  mOnScrollChangedCallback.onSChanged(l, t, oldl, oldt);
    	 
    }

	private OnScrollChangedCallback mOnScrollChangedCallback;

	public static interface OnScrollChangedCallback {
		public void onSChanged(int l, int t, int oldl, int oldt) ;
	}

	public OnScrollChangedCallback getmOnScrollChangedCallback() {
		return mOnScrollChangedCallback;
	}

	public void setmOnScrollChangedCallback(OnScrollChangedCallback mOnScrollChangedCallback) {
		this.mOnScrollChangedCallback = mOnScrollChangedCallback;
	}
	
}
