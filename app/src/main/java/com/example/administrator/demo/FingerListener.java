package com.example.administrator.demo;

import android.content.Context;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by WangPing on 2017/10/18.
 */

public class FingerListener extends FingerprintManagerCompat.AuthenticationCallback {
    private Context ctx;

    public FingerListener(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
        Toast.makeText(ctx, "指纹识别成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(ctx, "指纹识别失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        switch (helpMsgId) {
            case 1001:      // 等待按下手指
                Log.e("WP", "请按下手指");
                break;
            case 1002:      // 手指按下
                Log.e("WP", "正在识别…");
                break;
            case 1003:      // 手指抬起
                Log.e("WP", "手指抬起，请重新按下");
                break;
        }
    }
}
