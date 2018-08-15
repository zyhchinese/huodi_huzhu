package com.lx.hd.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2017/9/29.
 */

public class ImageDownloadTask extends AsyncTask<Object, Object, Bitmap> {
    private ImageView imageView = null;
    /***
     * 这里获取到手机的分辨率大小
     * */
    public void setDisplayWidth(int width) {
        _displaywidth = width;
    }
    public int getDisplayWidth() {
        return _displaywidth;
    }
    public void setDisplayHeight(int height) {
        _displayheight = height;
    }
    public int getDisplayHeight() {
        return _displayheight;
    }
    public int getDisplayPixels() {
        return _displaypixels;
    }
    private int _displaywidth = 480;
    private int _displayheight = 800;
    private int _displaypixels = _displaywidth * _displayheight;
    @Override
    protected Bitmap doInBackground(Object... params) {
// TODO Auto-generated method stub
        Bitmap bmp = null;
        imageView = (ImageView) params[1];
        try {
            String url = (String) params[0];
            bmp = getBitmap(url, _displaypixels,true);
        } catch (Exception e) {
            return null;
        }
        return bmp;
    }
    protected void onPostExecute(Bitmap result) {
        if (imageView != null&&result!=null) {
            double w= TDevice.getScreenWidth();
            double a=result.getWidth();
            double b=result.getHeight();
            double h=b/a*w;
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) imageView.getLayoutParams(); // 取控件mGrid当前的布局参数
            linearParams.height =(int) h;// 当控件的高强制设成75象素
            imageView.setLayoutParams(linearParams);
            imageView.setImageBitmap(result);
            if (null != result && result.isRecycled() == false)
                System.gc();
        }
    }
    /**
     * 通过URL获得网上图片。如:http://www.xxxxxx.com/xx.jpg
     * */
    public Bitmap getBitmap(String url, int displaypixels, Boolean isBig) throws MalformedURLException, IOException {
        Bitmap bmp = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        InputStream stream = new URL(url).openStream();
        byte[] bytes = getBytes(stream);
//这3句是处理图片溢出的begin( 如果不需要处理溢出直接 opts.inSampleSize=1;)
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
        opts.inSampleSize = computeSampleSize(opts, -1, displaypixels);//缩放的比例，缩放是很难按准备的比例进行缩放的，其值表明缩放的倍数，SDK中建议其值是2的指数值,值越大会导致图片不清晰
//end
        opts.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
        return bmp;
    }
    /**
     * 数据流转成btyle[]数组
     * */
    private byte[] getBytes(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[2048];
        int len = 0;
        try {
            while ((len = is.read(b, 0, 2048)) != -1) {
                baos.write(b, 0, len);
                baos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = baos.toByteArray();
        return bytes;
    }
    /****
     * 处理图片bitmap size exceeds VM budget （Out Of Memory 内存溢出）
     */
    private int computeSampleSize(BitmapFactory.Options options,
                                  int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }
    private int computeInitialSampleSize(BitmapFactory.Options options,
                                         int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
}

