package com.lx.hd.mvp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.Util;
import com.lx.hd.R;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BaseActivity;
import com.lx.hd.bean.CarInfo;
import com.lx.hd.utils.GlideImageLoader;
import com.lx.hd.utils.ImageDownloadTask;
import com.lx.hd.utils.TDevice;

import com.lx.hd.widgets.MyWebViewClient;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class CarDetialActivity extends BaseActivity implements CarDetailContract.View {
    Banner banner;
    protected CarDetailContract.Presenter mPresenter;
    RequestOptions mOptions;
    private TextView tvCarName, tvLocalPrice, tvCsPrice;
    private WebView webView;
    private ImageView imgNote;
    String url = "www.baidu.com";
    private String note = "";
    private String id = "";
    LinearLayout llyRoot;
    ImageView imgBack;
    double w;
    private Handler mHandler;
    ImageDownloadTask imgtask;
    LinearLayout linearLayout;

    @Override
    protected int getContentView() {
        return R.layout.activity_car_detial;
    }

    @Override
    protected void beforSetContentView() {
        super.beforSetContentView();

    }

    @Override
    protected void initWidget() {
        super.initWidget();
        imgtask = new ImageDownloadTask();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        imgtask.setDisplayWidth(dm.widthPixels);
        imgtask.setDisplayHeight(dm.heightPixels);

        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 0x101) {

                    HashMap<String, Object> map = (HashMap<String, Object>) msg.obj;
                    ImageView imageView = (ImageView) map.get("imageView");
                    Drawable drawable = (Drawable) map.get("drawable");
                    String url = (String) map.get("url");
                    w = TDevice.getScreenWidth();
                    if (drawable != null && imageView != null) {
                        double a = drawable.getIntrinsicWidth();
                        double b = drawable.getIntrinsicHeight();
                        double h = b / a * w;
                        mOptions = new RequestOptions()
                                //     .placeholder(R.mipmap.nav_center_button)
                                //   .error(R.mipmap.nav_center_button)
                                .override((int) w, (int) h);

                        if (!isDestroy()) {
                            Glide.with(getApplicationContext())
                                    .load(url)
                                    .apply(mOptions)
                                    .into(imageView);
                        }

                    }

                }
                return false;
            }
        });
        id = getIntent().getStringExtra("id");
        linearLayout = (LinearLayout) findViewById(R.id.lly_empty_view);
        llyRoot = (LinearLayout) findViewById(R.id.lly_root);
        tvCarName = (TextView) findViewById(R.id.tv_car_name);
        tvLocalPrice = (TextView) findViewById(R.id.tv_price_locale);
        tvCsPrice = (TextView) findViewById(R.id.tv_price_cs);
        banner = (Banner) findViewById(R.id.banner1);
        imgNote = (ImageView) findViewById(R.id.img_note);
        imgBack = (ImageView) findViewById(R.id.img_back);
        webView = (WebView) findViewById(R.id.webView);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setWebView();
        w = TDevice.getScreenWidth();
        double a = 428;
        double b = 720;
        double h = a / b * w;

        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) banner.getLayoutParams(); // 取控件mGrid当前的布局参数
        linearParams.height = (int) h;// 当控件的高强制设成75象素
        banner.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件mGrid2
        new DetailPresenter(this);
    }

    @Override
    protected void initData() {
        super.initData();

        if (id != null) {
            if (!id.equals("")) {
                mPresenter.getBanner(id);
                mPresenter.getCarInfo(id);
            } else {
                showToast("当前车辆信息不存在");
            }
        }
        mPresenter.getWebView();

    }

    @Override
    public void setPresenter(CarDetailContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showNetworkError(int strId) {

    }

    @Override
    public void showBanner(List<String> banners) {

        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(banners);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        if (!isDestroy()) {
            banner.start();
        }

    }

    @Override
    public void showCarInfo(final List<CarInfo> carInfos) {
        if (carInfos != null) {
            if (carInfos.size() > 0) {
                note = carInfos.get(0).getNote();
                tvCarName.setText(carInfos.get(0).getGroupname());
                tvLocalPrice.setText(carInfos.get(0).getLocalprice()+"万起");
                tvCsPrice.setText(carInfos.get(0).getFactoryprice()+"万起");

//                List<String> list=new ArrayList<>();
//                for (int i=0;i<carInfos.size();i++){
//                    list.add(Constant.BASE_URL+carInfos.get(i).getFolder()+carInfos.get(i).getAutoname());
//
//                }

                for (int i = 0; i < carInfos.size(); i++) {
                    final ImageView imageView = new ImageView(CarDetialActivity.this);

                    llyRoot.addView(imageView);

                    final int finalI = i;
                    if (carInfos.get(finalI).getFolder().length() > 2 && carInfos.get(finalI).getAutoname().length() > 3) {
                        imgtask.execute(Constant.BASE_URL + carInfos.get(finalI).getFolder() + carInfos.get(finalI).getAutoname(), imageView);
                    }
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            HashMap<String,Object> map=new HashMap<String, Object>();
//                            Message msg = Message.obtain();
//                            Drawable drawable= getImageFromNetwork(Constant.BASE_URL+carInfos.get(finalI).getFolder()+carInfos.get(finalI).getAutoname());
//                            msg.what = 0x101;
//                           // msg.obj = drawable;
//                            map.put("imageView",imageView);
//                            map.put("drawable",drawable);
//                            map.put("url",Constant.BASE_URL+carInfos.get(finalI).getFolder()+carInfos.get(finalI).getAutoname());
//                            msg.obj = map;
//                            mHandler.sendMessage(msg);
//                        }
//                    }).start();

                }

                linearLayout.setVisibility(View.GONE);

            } else {
                linearLayout.setVisibility(View.VISIBLE);
            }

        }

    }

    @Override
    public void showWebView(String content) {
        webView.loadDataWithBaseURL(Constant.BASE_URL, getNewContent(note), "text/html", "utf-8", null);
    }


    private void setWebView() {
        //设置支持JavaScript脚本
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(false);

        //   webView.setDownloadListener(new MyWebViewDownLoadListener(AppMarketActivity.this));
        //webView.loadUrl("http://app.hiapk.com/essential/");
        //     webView.loadUrl("http://a.3533.com/ruanjian/");

        //设置WebViewClient
//        webView.setWebViewClient(new MyWebViewClient(CarDetialActivity.this));
//        //设置WebChromeClient
//        webView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            //处理javascript中的alert
//            public boolean onJsAlert(WebView view, String url, String message,
//                                     final JsResult result) {
//                //构建一个Builder来显示网页中的对话框
//                AlertDialog.Builder builder = new AlertDialog.Builder(CarDetialActivity.this);
//                builder.setTitle("提示对话框");
//                builder.setMessage(message);
//                builder.setPositiveButton(android.R.string.ok,
//                        new AlertDialog.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                //点击确定按钮之后,继续执行网页中的操作
//                                result.confirm();
//                            }
//                        });
//                builder.setCancelable(false);
//                builder.create();
//                builder.show();
//                return true;
//            }
//
//            //处理javascript中的confirm
//            public boolean onJsConfirm(WebView view, String url, String message,
//                                       final JsResult result) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(CarDetialActivity.this);
//                builder.setTitle("带选择的对话框");
//                builder.setMessage(message);
//                builder.setPositiveButton(android.R.string.ok,
//                        new AlertDialog.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                result.confirm();
//                            }
//                        });
//                builder.setNegativeButton(android.R.string.cancel,
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                result.cancel();
//                            }
//                        });
//                builder.setCancelable(false);
//                builder.create();
//                builder.show();
//                return true;
//            }
//
//            ;
//
//            //设置网页加载的进度条
//            public void onProgressChanged(WebView view, int newProgress) {
//                getWindow().setFeatureInt(Window.FEATURE_PROGRESS, newProgress * 100);
//                super.onProgressChanged(view, newProgress);
//            }
//
//            //设置应用程序的标题title
//            public void onReceivedTitle(WebView view, String title) {
//                setTitle(title);
//                super.onReceivedTitle(view, title);
//            }
//        });
    }

    public String getNewContent(String htmltext) {
        try {
            Document doc = Jsoup.parse(htmltext);
            Elements elements = doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width", "100%").attr("height", "auto");
            }

            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }

    Drawable getImageFromNetwork(String imageUrl) {
        URL myFileUrl = null;
        Drawable drawable = null;
        try {
            myFileUrl = new URL(imageUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);

            conn.connect();
            InputStream is = conn.getInputStream();
            drawable = Drawable.createFromStream(is, null);

            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drawable;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView=null;

//        if (this != null && !this.isFinishing()) {
//            Glide.with(this).clear(view);
//        }

    }

    public Bitmap createThumbnails(String url) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(url, options); // 此时返回bm为空

        options.inJustDecodeBounds = false;

        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可

        int be = (int) (options.outHeight / (float) 2);
        if (be <= 0)
            be = 1;

        options.inSampleSize = be;

        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了

        bitmap = BitmapFactory.decodeFile(url, options);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
//        ImageView iv = new ImageView(context);
//        iv.setImageBitmap(bitmap);

        return bitmap;
    }

}
