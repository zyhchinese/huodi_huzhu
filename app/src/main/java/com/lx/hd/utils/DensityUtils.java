package com.lx.hd.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by Tb on 2017/10/24.
 */

public class DensityUtils {
    /**获取屏幕的宽*/
    public final static int getWindowsWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
}
