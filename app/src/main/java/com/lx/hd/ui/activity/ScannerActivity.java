package com.lx.hd.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BaseActivity;
import com.lx.hd.utils.GlideImageLoaderForPicker;
import com.lx.hd.widgets.CustomViewPager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ScannerActivity extends BaseActivity implements QRCodeView.Delegate {

    private CustomViewPager mViewPager;
    private QRCodeView mQRCodeView;
    private LinearLayout llyScanner, llyInputNumber;
    private ImageView imgBack, imgSelectCode;
    private TextView tvTitle, tvOrderNo;
    public static boolean isOpen = false;
    private ImageView imgScanner, imgInput, imageView;
    private TextView tvScanner, tvInput, tvTips;

    private TextView tvInputCofirm;
    private EditText etInputEdit;

    @Override
    protected int getContentView() {
        return R.layout.activity_scanner2;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mViewPager = (CustomViewPager) findViewById(R.id.viewpage);
        imageView = (ImageView) findViewById(R.id.img_flash);
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgSelectCode = (ImageView) findViewById(R.id.img_select_code);
        tvTitle = (TextView) findViewById(R.id.tv_title_scaner);
        mViewPager = (CustomViewPager) findViewById(R.id.viewpage);
        llyScanner = (LinearLayout) findViewById(R.id.linear0);
        llyInputNumber = (LinearLayout) findViewById(R.id.linear1);
        imgScanner = (ImageView) findViewById(R.id.img_scanner);
        imgInput = (ImageView) findViewById(R.id.img_input);
        tvScanner = (TextView) findViewById(R.id.tv_scanner);
        tvInput = (TextView) findViewById(R.id.tv_input);
        tvTips = (TextView) findViewById(R.id.tv_tips);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpen) {
                    isOpen = true;
                    imageView.setSelected(true);
                    mQRCodeView.openFlashlight();
                } else {
                    isOpen = false;
                    imageView.setSelected(false);
                    mQRCodeView.closeFlashlight();
                }

            }
        });

        //将要分页显示的View装入数组中
        LayoutInflater mLi = LayoutInflater.from(this);
        View view1 = mLi.inflate(R.layout.fragment_scanner_camera_new, null);
        //    initView1(view1);
        View view2 = mLi.inflate(R.layout.fragment_scanner_input, null);
        //    initView2(view2);
        //每个页面的view数据
        final ArrayList<View> views = new ArrayList();
        views.add(view1);
        views.add(view2);

        mQRCodeView = (ZXingView) view1.findViewById(R.id.zxingview);
        mQRCodeView.setDelegate(this);

        tvInputCofirm = (TextView) view2.findViewById(R.id.tv_next);
        tvOrderNo = (TextView) view2.findViewById(R.id.tv_order_no);
        etInputEdit = (EditText) view2.findViewById(R.id.et_input);

        tvTitle.setText("二维码扫描");
        imgScanner.setSelected(true);
        imgInput.setSelected(false);
        tvScanner.setTextColor(getResources().getColor(R.color.swiperefresh_color2));
        tvInput.setTextColor(getResources().getColor(R.color.white));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgSelectCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker imagePicker = ImagePicker.getInstance();
                imagePicker.setImageLoader(new GlideImageLoaderForPicker());   //设置图片加载器
                imagePicker.setShowCamera(false);  //显示拍照按钮
                imagePicker.setCrop(false);        //允许裁剪（单选才有效）
                imagePicker.setSaveRectangle(true); //是否按矩形区域保存
                imagePicker.setSelectLimit(1);    //选中数量限制
                imagePicker.setOutPutX(800);//保存文件的宽度。单位像素
                imagePicker.setOutPutY(800);//保存文件的高度。单位像素

                Intent intent = new Intent(ScannerActivity.this, ImageGridActivity.class);
                startActivityForResult(intent, Constant.REQUEST_CODE);
            }
        });
        llyScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
                tvTitle.setText("二维码扫描");
                imgScanner.setSelected(true);
                imgInput.setSelected(false);
                tvScanner.setTextColor(getResources().getColor(R.color.swiperefresh_color2));
                tvInput.setTextColor(getResources().getColor(R.color.white));
                tvTips.setVisibility(View.VISIBLE);
            }
        });
        llyInputNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
                tvTitle.setText("二维码扫描");
                imgScanner.setSelected(false);
                imgInput.setSelected(true);
                tvScanner.setTextColor(getResources().getColor(R.color.white));
                tvInput.setTextColor(getResources().getColor(R.color.swiperefresh_color2));
                tvTips.setVisibility(View.GONE);
                PileApi.instance.getOrderNo()
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
                                    tvOrderNo.setText(body);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });

        tvInputCofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tempInput = etInputEdit.getText().toString().trim();
                if (tempInput.equals("")) {
                    showToast("终端码不能为空");
                    return;
                }
                analysisCode(tempInput);

            }
        });
        //填充ViewPager的数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager) container).addView(views.get(position));
                return views.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager) container).removeView(views.get(position));
            }

        };

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setNoScroll(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);

        mQRCodeView.showScanRect();
        mQRCodeView.startSpot();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        if (TextUtils.isEmpty(result)) {
            showToast("未发现二维码");
        } else {
            if (Patterns.WEB_URL.matcher(result).matches() || URLUtil.isValidUrl(result)) {
                Uri uri = Uri.parse(result);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            } else {
                Intent intent1 = new Intent(ScannerActivity.this, SysTextActivity.class);
                intent1.putExtra("text", result);
                startActivity(intent1);
            }
        }
        vibrate();
        mQRCodeView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        showToast("打开相机出错");
        //   Log.e(TAG, "打开相机出错");
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == Constant.REQUEST_CODE) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                try {
                    mQRCodeView.showScanRect();

                    final String picturePath = images.get(0).path;

            /*
            这里为了偷懒，就没有处理匿名 AsyncTask 内部类导致 Activity 泄漏的问题
            请开发在使用时自行处理匿名内部类导致Activity内存泄漏的问题，处理方式可参考 https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E6%80%BB%E7%BB%93.md
             */
                    showWaitDialog("正在解析二维码...");
                    new AsyncTask<Void, Void, String>() {
                        @Override
                        protected String doInBackground(Void... params) {

                            return QRCodeDecoder.syncDecodeQRCode(picturePath);
                        }

                        @Override
                        protected void onPostExecute(final String result) {
                            hideWaitDialog();
                            if (TextUtils.isEmpty(result)) {
                                showToast("未发现二维码");
                            } else {
                                if (Patterns.WEB_URL.matcher(result).matches() || URLUtil.isValidUrl(result)) {
                                    Uri uri = Uri.parse(result);
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(intent);
                                } else {
                                    Intent intent1 = new Intent(ScannerActivity.this, SysTextActivity.class);
                                    intent1.putExtra("text", result);
                                    startActivity(intent1);
                                }

                            }
                        }
                    }.execute();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                showToast("没有数据");
            }
        }
    }

    private void analysisCode(final String result) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("electricsbm", result);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.mCheckOrderIsHave(params)
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
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("electricsbm", result);
                                Gson gson = new Gson();
                                String params = gson.toJson(map);
                                PileApi.instance.mCheckOrderIsCharging(params)
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
                                                        showToast("此充电枪号正在充电");
                                                        finish();
                                                    } else {
                                                        Intent intent = new Intent(ScannerActivity.this, PayMethodActivity.class);
                                                        intent.putExtra("orderNo", result);
                                                        startActivity(intent);
                                                        finish();

                                                    }
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onError(@NonNull Throwable e) {

                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });

                            } else {
                                showToast("此扫码枪号不存在");
                                finish();

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
