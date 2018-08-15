package com.lx.hd.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Administrator on 2017/8/21.
 */

public class GlideImageLoaderForGoods extends ImageLoader {
    RequestOptions mOptions;
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //fuck，设置了竟然没用，还得去首页动态设置控件的高度
        double w=TDevice.getScreenWidth();
        double a=428;
        double b=720;
        double h=a/b*w;
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE); //设置图片缩放
        mOptions = new RequestOptions()
            //    .placeholder(R.mipmap.default_banners)
           //     .error(R.mipmap.default_banners)
                .fitCenter()
                .override((int)w,(int)h);
        Glide.with(context.getApplicationContext())
                .load(path)
                .apply(mOptions)
                .into(imageView);
    }
}