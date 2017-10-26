package com.example.administrator.demo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 截屏
 * Created by WangPing on 2017/10/19.
 */

public class ScreenShotUtil {
    /**
     * 截屏功能 不包含状态栏
     *
     * @param activity 需要截屏的界面
     */
    public static void screenShot(Activity activity) {
        View dView = activity.getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(dView.getDrawingCache());
        Log.e("WP", "bitmap");
        if (bitmap != null) {
            Log.e("WP", "not null");
            try {
                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                String filePath = sdCardPath + File.separator + "screenshot.png";
                File file = new File(filePath);
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream os = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
                Log.e("WP", "存储完成");
            } catch (Exception e) {
                Log.e("WP", e.toString());
            }
        }
    }
}
