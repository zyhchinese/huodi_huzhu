package com.lx.hd.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.account.LoginActivity;
import com.lx.hd.adapter.GoodsColorsAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BaseActivity;
import com.lx.hd.bean.GoodsBean;
import com.lx.hd.bean.GoodsColosBean;
import com.lx.hd.bean.PrimaryBanner;
import com.lx.hd.bean.ShopListBean;
import com.lx.hd.utils.DialogHelper;
import com.lx.hd.utils.GlideImageLoader;
import com.lx.hd.utils.GlideImageLoaderForGoods;
import com.lx.hd.utils.ZETTool;
import com.lx.hd.widgets.MyGridView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class GoodsDetialActivity extends BaseActivity {
    List<String> list;
    List<PrimaryBanner> primaryBanner;
    private Banner banner;
    private MyGridView mGridView;
    private MyGridView mGridView2;
    private TextView zftype;
    private TextView tvBuy, tvToCart, proname, productdes, price, sl_tv;
    private WebView webView;
    private String proid;
    ImageView goback, zjsl, jssl;
    private TextView tv_price_jf;
    private GoodsColorsAdapter goosadt;
    private GoodsColorsAdapter typeadt;
    List<GoodsColosBean> typedata;
    List<GoodsColosBean> colordata;
    int sl = 1;//数量临时存储于此
    int type = 1;//1=普通 2=积分
    int colnum = 0;
    private boolean isLogin = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_goods_detial;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        Intent it = getIntent();
        proid = it.getStringExtra("proid");
        banner = (Banner) findViewById(R.id.banner);
        tvBuy = (TextView) findViewById(R.id.tv_to_buy);
        sl_tv = (TextView) findViewById(R.id.sl_tv);
        tvToCart = (TextView) findViewById(R.id.tv_to_cart);
        proname = (TextView) findViewById(R.id.proname);
        productdes = (TextView) findViewById(R.id.productdes);
        price = (TextView) findViewById(R.id.price);
        mGridView = (MyGridView) findViewById(R.id.colors);
        mGridView2 = (MyGridView) findViewById(R.id.types);
        goback = (ImageView) findViewById(R.id.goback);
        zjsl = (ImageView) findViewById(R.id.zjsl);
        zftype = (TextView) findViewById(R.id.zftype);
        jssl = (ImageView) findViewById(R.id.jssl);
        webView = (WebView) findViewById(R.id.webView);
        tv_price_jf= (TextView) findViewById(R.id.tv_price_jf);

        type = getIntent().getIntExtra("type", 1);
        //设置支持JavaScript脚本
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(false);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            //处理javascript中的alert
            public boolean onJsAlert(WebView view, String url, String message,
                                     final JsResult result) {
                //构建一个Builder来显示网页中的对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(GoodsDetialActivity.this);
                builder.setTitle("提示对话框");
                builder.setMessage(message);
                builder.setPositiveButton(android.R.string.ok,
                        new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //点击确定按钮之后,继续执行网页中的操作
                                result.confirm();
                            }
                        });
                builder.setCancelable(false);
                builder.create();
                builder.show();
                return true;
            }

            //处理javascript中的confirm
            public boolean onJsConfirm(WebView view, String url, String message,
                                       final JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GoodsDetialActivity.this);
                builder.setTitle("带选择的对话框");
                builder.setMessage(message);
                builder.setPositiveButton(android.R.string.ok,
                        new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        });
                builder.setCancelable(false);
                builder.create();
                builder.show();
                return true;
            }

            ;

            //设置网页加载的进度条
            public void onProgressChanged(WebView view, int newProgress) {
                getWindow().setFeatureInt(Window.FEATURE_PROGRESS, newProgress * 100);
                super.onProgressChanged(view, newProgress);
            }

            //设置应用程序的标题title
            public void onReceivedTitle(WebView view, String title) {
                setTitle(title);
                super.onReceivedTitle(view, title);
            }
        });

        zjsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sl = sl + 1;
                sl_tv.setText(sl + "");
            }
        });
        jssl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sl > 1) {
                    sl = sl - 1;
                    sl_tv.setText(sl + "");
                }
            }
        });
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mGridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (GoodsColosBean bean : typedata) {
                    bean.setIsok(false);
                }
                typedata.get(position).setIsok(true);
                setColorsData(typedata, 2);
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (GoodsColosBean bean : colordata) {
                    bean.setIsok(false);
                }
                colordata.get(position).setIsok(true);

                setColorsData(colordata, 1);
            }
        });
        tvBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                islogin();
                GoodsColosBean bean = null;
                for (GoodsColosBean temp : colordata) {
                    if (temp.isok()) {
                        bean = temp;
                    }
                }
                if (bean == null) {
                    showToast("请选择商品颜色！");
                    return;
                }
                GoodsColosBean bean2 = null;
                for (GoodsColosBean temp : typedata) {
                    if (temp.isok()) {
                        bean2 = temp;
                    }
                }
                if (bean2 == null) {
                    showToast("请选择商品类型！");
                    return;
                }

                insertProOrder_0(proid, proname.getText().toString(), bean.getColosname(), bean2.getColosname(), sl + "", type + "");

            }
        });
        tvToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                islogin();
                GoodsColosBean bean = null;
                for (GoodsColosBean temp : colordata) {
                    if (temp.isok()) {
                        bean = temp;
                    }
                }
                if (bean == null) {
                    showToast("请选择商品颜色！");
                    return;
                }
                GoodsColosBean bean2 = null;
                for (GoodsColosBean temp : typedata) {
                    if (temp.isok()) {
                        bean2 = temp;
                    }
                }
                if (bean2 == null) {
                    showToast("请选择商品类型！");
                    return;
                }
                addShoppingCart(proid, proname.getText().toString(), bean.getColosname(), bean2.getColosname(), sl + "", type + "");
                // startActivity(new Intent(GoodsDetialActivity.this, ShoppingCartActivity.class));
            }
        });
        loadBanner();
        getProductDeatilParam();
    }

    private void loadBanner() {
        HashMap<String, String> map = new HashMap<>();
        map.put("proid", proid);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.getProductDeatilBanner(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        String body = null;
                        if (responseBody != null) {
                            try {
                                body = responseBody.string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (body != null) {

                            List<PrimaryBanner> primaryBanners = new Gson().fromJson(body, new TypeToken<ArrayList<PrimaryBanner>>() {
                            }.getType());
                            list = new ArrayList<>();
                            primaryBanner = primaryBanners;
                            for (int i = 0; i < primaryBanners.size(); i++) {
                                String url = Constant.BASE_URL + primaryBanners.get(i).getFolder() + primaryBanners.get(i).getAutoname();
                                list.add(url);
                            }

                            //设置banner样式
                            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                            //设置图片加载器
                            banner.setImageLoader(new GlideImageLoaderForGoods());
                            //设置图片集合
                            banner.setImages(list);
                            //设置banner动画效果
                            banner.setBannerAnimation(Transformer.Default);
                            //设置自动轮播，默认为true
                            banner.isAutoPlay(true);
                            //设置轮播时间
                            banner.setDelayTime(3000);
                            //设置指示器位置（当banner模式中有指示器时）
                            banner.setIndicatorGravity(BannerConfig.CENTER);
                            //banner设置方法全部调用完毕时最后调用

                            banner.start();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
//        colnum = ZETTool.getScreenWidth(GoodsDetialActivity.this) / 100;
//        mGridView.setNumColumns(colnum);
//        mGridView2.setNumColumns(colnum);

    }

    private void setColorsData(List<GoodsColosBean> data, int type) {
        if (type == 1) {
            if (data != null) {
                goosadt = new GoodsColorsAdapter(GoodsDetialActivity.this, data, colnum);
                mGridView.setAdapter(goosadt);
                setMyGridViewHeightBasedOnChildren(mGridView);
                goosadt.notifyDataSetChanged();
            }
        } else {
            if (data != null) {
                typeadt = new GoodsColorsAdapter(GoodsDetialActivity.this, data, colnum);
                mGridView2.setAdapter(typeadt);
                setMyGridViewHeightBasedOnChildren(mGridView2);
                typeadt.notifyDataSetChanged();
            }

        }
    }

    private void updateUI(GoodsBean goods) {
        if (goods != null) {
            if (type == 1) {
                zftype.setText("¥：");
                price.setText(goods.getPrice() + "  /  积："+goods.getPrice_jf());
            } else {
                zftype.setText(goods.getPrice() + "");
                price.setText("积分");
            }
            tv_price_jf.setText(goods.getPrice_jf()+"");
            proname.setText(goods.getProname());
            productdes.setText(goods.getProductdes());
            String[] colors = goods.getColor().split(",");
            String[] types = goods.getFitcar().split(",");
            colordata = new ArrayList<GoodsColosBean>();
            typedata = new ArrayList<GoodsColosBean>();
            for (int i = 0; i < colors.length; i++) {
                colordata.add(new GoodsColosBean(colors[i], false, 1));
            }
            setColorsData(colordata, 1);
            for (int j = 0; j < types.length; j++) {
                typedata.add(new GoodsColosBean(types[j], false, 2));
            }
            setColorsData(typedata, 2);
            webView.loadDataWithBaseURL(Constant.BASE_URL, getNewContent(goods.getNote()), "text/html", "utf-8", null);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.clearCache(true);
        webView.clearHistory();
    }

    public static String getNewContent(String htmltext) {
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

    private void getProductDeatilParam() {
        showWaitDialog("正在获取信息...");
        HashMap<String, String> map = new HashMap<>();
        map.put("id", proid);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.getProductDeatilParam(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {

                        try {
                            String body = responseBody.string();
                            if (body.length() < 10) {
                                //   if(body.equals("\"false\""))
                                showToast("获取信息失败，请重试");
                            } else {
                                ArrayList<GoodsBean> datas = new Gson().fromJson(body, new TypeToken<ArrayList<GoodsBean>>() {
                                }.getType());
                                if (datas.size() > 0) {
                                    updateUI(datas.get(0));
                                }
                            }
                            hideWaitDialog();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //添加商品
    private void insertProOrder_0(String id, String proname, String color, String specification, String totalcount, final String type) {
        showWaitDialog("提交信息中...");
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("proname", proname);
        map.put("color", color);
        map.put("specification", specification);
        map.put("totalcount", totalcount);
        map.put("type", type);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.insertProOrder_0(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {

//                        try {
                        try {
                            String body = responseBody.string();
                            body = body.replace("\"", "");
                            if ("false".equals(body)) {
                                showToast("提交失败，请重试");
                            } else {
                                if (isLogin) {
                                    Intent it = new Intent(GoodsDetialActivity.this, ConfirmOrderActivity.class);
                                    it.putExtra("uuid", body);
                                    it.putExtra("type", type);
                                    startActivity(it);
                                }

                            }
                            hideWaitDialog();
                            //
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //添加购物车商品
    private void addShoppingCart(String id, String proname, String color, String specification, String totalcount, String type) {
        showWaitDialog("提交信息中...");
        HashMap<String, String> map = new HashMap<>();
        map.put("proid", id);
        map.put("proname", proname);
        map.put("color", color);
        map.put("fitcar", specification);
        map.put("count", totalcount);
        map.put("type", type);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.addShoppingCart(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {

                        try {
                            String body = responseBody.string();
                            body = body.replace("\"", "");
                            if ("false".equals(body)) {
                                showToast("提交失败，请重试");
                            } else {
                                if (isLogin) {
                                    showNormalDialog();
                                }

                            }
                            hideWaitDialog();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 动态设置ListView的高度
     *
     * @param listView
     */
    public void setMyGridViewHeightBasedOnChildren(MyGridView listView) {
        if (listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + 20;
        listView.setLayoutParams(params);
    }

    private void islogin() {
        PileApi.instance.mCheckLoginState()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            String body = responseBody.string();
                            if (body.equals("\"true\"")) {
                                isLogin = true;
                            } else {
                                isLogin = false;
                                DialogHelper.getConfirmDialog(GoodsDetialActivity.this, "温馨提示", "当前用户未登录，是否去登录", "去登录", "取消", true, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(GoodsDetialActivity.this, LoginActivity.class));
                                    }
                                }, null).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isLogin = false;
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void showNormalDialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(GoodsDetialActivity.this);
        normalDialog.setTitle("成功");
        normalDialog.setMessage("恭喜您添加成功");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }

}
