package com.wt.zhxm.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.wt.zhxm.R;
import com.wt.zhxm.view.CustomVideoView;

/**
 * Created by Administrator on 2016/12/28 0028.
 */
public class WelcomeActivity extends Activity {
    private Button welcome_button;
    private CustomVideoView welcome_videoview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        welcome_button = (Button) this.findViewById(R.id.welcome_button);
        welcome_videoview = (CustomVideoView) this.findViewById(R.id.welcome_videoview);
        welcome_videoview.setVideoURI(Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.kr36));
        welcome_videoview.start();
        welcome_videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                welcome_videoview.start();

            }
        });
        welcome_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (welcome_videoview.isPlaying()) {
                    welcome_videoview.stopPlayback();
                    welcome_videoview = null;
                }
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        });
    }

    private String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
            versionName = packageInfo.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }
}

