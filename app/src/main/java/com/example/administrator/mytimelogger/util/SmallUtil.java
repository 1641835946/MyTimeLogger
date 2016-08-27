package com.example.administrator.mytimelogger.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/8/25.
 */
public class SmallUtil {

    public static String generateDurationStr (long duration) {
        String durationStr = generatePart((int)(duration / 3600)) + ":" +
                generatePart((int)(duration / 3600 %60)) + ":" +
                generatePart((int)(duration % 60));
        return durationStr;
    }

    private static String generatePart(int time) {
        String back;
        if (time < 10) {
            back = "0" + time;
        } else {
            back = "" + time;
        }
        return back;
    }

    public static DisplayMetrics getScreenSize(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics;
    }

}
