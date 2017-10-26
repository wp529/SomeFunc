package com.example.administrator.demo;

import android.Manifest;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.widget.Toast;

/**
 * 指纹识别
 * Created by WangPing on 2017/10/18.
 */

public class FingerUtil {
    private CancellationSignal signal;
    private FingerprintManagerCompat fingerprintManager;
    private Activity activity;
    private KeyguardManager mKeyManager;
    private FingerprintManager manager;

    public FingerUtil(Activity activity) {
        this.activity = activity;
        mKeyManager = (KeyguardManager) activity.getSystemService(Context.KEYGUARD_SERVICE);
        manager = (FingerprintManager) activity.getSystemService(Context.FINGERPRINT_SERVICE);
        signal = new CancellationSignal();
        fingerprintManager = FingerprintManagerCompat.from(activity);
    }

    public void startFingerListen(FingerprintManagerCompat.AuthenticationCallback callback) {
        fingerprintManager.authenticate(null, 0, signal, callback, null);
    }

    public void stopsFingerListen() {
        signal.cancel();
        signal = null;
    }

    /**
     * 检测手机是否支持指纹
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkFingerModule() {
        try {
            FingerprintManager fingerManager = (FingerprintManager) activity.getSystemService(Context.FINGERPRINT_SERVICE);
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
            return fingerManager.isHardwareDetected();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 检查是否可以使用指纹
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean isFinger() {
        if (!mKeyManager.isKeyguardSecure()) {
            Toast.makeText(activity, "没有开启锁屏密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        if (!manager.hasEnrolledFingerprints()) {
            Toast.makeText(activity, "没有录入指纹", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
