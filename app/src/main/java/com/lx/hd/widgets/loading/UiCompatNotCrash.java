package com.lx.hd.widgets.loading;

/**
 * Created by Administrator on 2017/8/18.
 */

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.TextView;

/**
 * Wrapper compatibility class to call some API-Specific methods
 * And offer alternate procedures when possible
 *
 * @hide
 */
@TargetApi(21)
class UiCompatNotCrash {
    static void setOutlineProvider(View marker, final BalloonMarkerDrawable balloonMarkerDrawable) {
        marker.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setConvexPath(balloonMarkerDrawable.getPath());
            }
        });
    }

    static void setBackground(View view, Drawable background) {
        view.setBackground(background);
    }

    static void setTextDirection(TextView number, int textDirection) {
        number.setTextDirection(textDirection);
    }
}