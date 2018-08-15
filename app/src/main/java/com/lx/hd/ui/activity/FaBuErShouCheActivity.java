package com.lx.hd.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.ProvinceAdapter555;
import com.lx.hd.adapter.ProvinceAdapter666;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.ErShouChePinPaiEntity;
import com.lx.hd.bean.cxbean;
import com.lx.hd.bean.cxbean11;
import com.lx.hd.utils.GlideImageLoaderForPicker;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class FaBuErShouCheActivity extends BackCommonActivity implements View.OnClickListener {

    private EditText ed_title, ed_chexinghao, ed_price, ed_gonglishu, ed_lianxiren,
            ed_lianxidianhua, ed_miaoshu;
    private TextView tv_chexing, tv_pinpai, tv_kancheaddress, tv_diqu, tv_fabu, tv_zhucenianfen;
    private ImageView img1, img2, img3;
    private RelativeLayout relative_chexing, relative_pinpai, relative_kancheaddress, relative_zhucenianfen;
    private ArrayList<cxbean11> temp;
    private PopupWindow window;
    private RecyclerView recyclerView;
    private ErShouChePinPaiEntity erShouChePinPaiEntity;
    private ImageItem imageItem1, imageItem2, imageItem3;
    private Bitmap bitmap;
    private ArrayList<File> arrayList = new ArrayList<>();
    private String dwsheng = "", dwshi = "", dwxian = "";

    @Override
    protected int getContentView() {
        return R.layout.activity_fa_bu_er_shou_che;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("发布二手车");

        initView();


    }

    private void initView() {
        ed_title = (EditText) findViewById(R.id.ed_title);
        tv_chexing = (TextView) findViewById(R.id.tv_chexing);
        tv_pinpai = (TextView) findViewById(R.id.tv_pinpai);
        ed_chexinghao = (EditText) findViewById(R.id.ed_chexinghao);
        ed_price = (EditText) findViewById(R.id.ed_price);
        tv_kancheaddress = (TextView) findViewById(R.id.tv_kancheaddress);
        ed_gonglishu = (EditText) findViewById(R.id.ed_gonglishu);
        ed_lianxiren = (EditText) findViewById(R.id.ed_lianxiren);
        ed_lianxidianhua = (EditText) findViewById(R.id.ed_lianxidianhua);
        tv_diqu = (TextView) findViewById(R.id.tv_diqu);
        ed_miaoshu = (EditText) findViewById(R.id.ed_miaoshu);
        tv_fabu = (TextView) findViewById(R.id.tv_fabu);
        tv_zhucenianfen = (TextView) findViewById(R.id.tv_zhucenianfen);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        relative_chexing = (RelativeLayout) findViewById(R.id.relative_chexing);
        relative_pinpai = (RelativeLayout) findViewById(R.id.relative_pinpai);
        relative_kancheaddress = (RelativeLayout) findViewById(R.id.relative_kancheaddress);
        relative_zhucenianfen = (RelativeLayout) findViewById(R.id.relative_zhucenianfen);

        relative_chexing.setOnClickListener(this);
        relative_pinpai.setOnClickListener(this);
        relative_kancheaddress.setOnClickListener(this);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        tv_fabu.setOnClickListener(this);
        relative_zhucenianfen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoaderForPicker());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);//是否按矩形区域保存
        imagePicker.setFocusHeight(169);
        imagePicker.setFocusWidth(169);
//        imagePicker.setCrop(true);
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setOutPutX(800);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(800);//保存文件的高度。单位像素
        switch (v.getId()) {
            case R.id.relative_chexing:
                hideKeyboard();
                // 用于PopupWindow的View
                View contentView111 = LayoutInflater.from(this).inflate(R.layout.shaixuanqiangdanliebiao11, null, false);

                WindowManager windowManager111 = getWindowManager();
                Display display111 = windowManager111.getDefaultDisplay();

                // 创建PopupWindow对象，其中：
                // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
                // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
                window = new PopupWindow(contentView111, display111.getWidth(), (int) (display111.getHeight() * 0.4), true);
                // 设置PopupWindow的背景
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
                // 设置PopupWindow是否能响应外部点击事件
                window.setOutsideTouchable(true);
                // 设置PopupWindow是否能响应点击事件
                window.setTouchable(true);
                recyclerView = (RecyclerView) contentView111.findViewById(R.id.recyclerView);
                TextView tv_queren111 = (TextView) contentView111.findViewById(R.id.tv_queren);
                tv_queren111.setVisibility(View.GONE);

                initChexing();

                // 显示PopupWindow，其中：
                // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
//                window.showAsDropDown(tv_chexing, 0, 0);
                // 或者也可以调用此方法显示PopupWindow，其中：
                // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
                // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
                window.showAtLocation(findViewById(R.id.parent_layout), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.relative_pinpai:
                hideKeyboard();
                // 用于PopupWindow的View
                View contentView222 = LayoutInflater.from(this).inflate(R.layout.shaixuanqiangdanliebiao11, null, false);

                WindowManager windowManager222 = getWindowManager();
                Display display222 = windowManager222.getDefaultDisplay();

                // 创建PopupWindow对象，其中：
                // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
                // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
                window = new PopupWindow(contentView222, display222.getWidth(), (int) (display222.getHeight() * 0.4), true);
                // 设置PopupWindow的背景
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
                // 设置PopupWindow是否能响应外部点击事件
                window.setOutsideTouchable(true);
                // 设置PopupWindow是否能响应点击事件
                window.setTouchable(true);
                recyclerView = (RecyclerView) contentView222.findViewById(R.id.recyclerView);
                TextView tv_queren222 = (TextView) contentView222.findViewById(R.id.tv_queren);
                tv_queren222.setVisibility(View.GONE);

                initPinPai();

                // 显示PopupWindow，其中：
                // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
//                window.showAsDropDown(tv_pinpai, 0, 0);
                // 或者也可以调用此方法显示PopupWindow，其中：
                // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
                // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
                window.showAtLocation(findViewById(R.id.parent_layout), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.relative_kancheaddress:
                Bundle bundle = new Bundle();
                bundle.putString("question", "kanche");
                bundle.putString("x", "");
                bundle.putString("y", "");
                Intent intent = new Intent(FaBuErShouCheActivity.this, DeliverMapActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 001);
                break;
            case R.id.img1:
                Intent intent1 = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent1, 1000);
                break;
            case R.id.img2:
                Intent intent2 = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent2, 2);
                break;
            case R.id.img3:
                Intent intent3 = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent3, 3);
                break;
            case R.id.relative_zhucenianfen:
                TimePickerView pvTime = new TimePickerView.Builder(FaBuErShouCheActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = getTime(date2);
                        tv_zhucenianfen.setText(time);
                    }
                })
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
//                        .setTitleText("订单时间")
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
//                        .setTitleText("请选择时间")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
//                        .setTitleColor(Color.parseColor("#ffb400"))//标题文字颜色
                        .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                        .setCancelColor(Color.BLACK)//取消按钮文字颜色
                        .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
//                        .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                        .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                        .setRangDate(startDate,endDate)//起始终止年月日设定
//                        .setLabel("年","月","日","时","分","秒")
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                        .build();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
                break;
            case R.id.tv_fabu:
                if (ed_title.getText().toString().trim().equals("")) {
                    showToast("请输入标题");
                    return;
                }
                if (tv_chexing.getText().toString().trim().equals("请选择")) {
                    showToast("请选择车型");
                    return;
                }
                if (tv_pinpai.getText().toString().trim().equals("请选择")) {
                    showToast("请选择品牌");
                    return;
                }
                if (ed_chexinghao.getText().toString().trim().equals("")) {
                    showToast("请输入车型号");
                    return;
                }
                if (ed_price.getText().toString().trim().equals("")) {
                    showToast("请输入价格");
                    return;
                }
                if (tv_kancheaddress.getText().toString().trim().equals("请选择看车地址")) {
                    showToast("请选择看车地址");
                    return;
                }
                if (tv_zhucenianfen.getText().toString().trim().equals("请选择注册年份")) {
                    showToast("请选择注册年份");
                    return;
                }
                if (ed_gonglishu.getText().toString().trim().equals("")) {
                    showToast("请输入公里数");
                    return;
                }
                if (ed_lianxiren.getText().toString().trim().equals("")) {
                    showToast("请输入联系人");
                    return;
                }
                if (ed_lianxidianhua.getText().toString().trim().equals("")) {
                    showToast("请输入联系电话");
                    return;
                }
                if (arrayList.size() == 0) {
                    showToast("请上传爱车图片");
                }
                initJibenxinxi();
                break;
        }
    }

    public String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void initJibenxinxi() {
        HashMap<String, String> map = new HashMap<>();
        map.put("table", "huodi_second_hand_car");
        map.put("title", ed_title.getText().toString().trim());
        map.put("car_type_id", String.valueOf(tv_chexing.getTag()));
        map.put("car_type_name", tv_chexing.getText().toString());
        map.put("car_brand_id", String.valueOf(tv_pinpai.getTag()));
        map.put("car_brand_name", tv_pinpai.getText().toString());
        map.put("model_number", ed_chexinghao.getText().toString().trim());
        map.put("price", ed_price.getText().toString().trim());
        map.put("watch_car_add", tv_kancheaddress.getText().toString().trim());
        map.put("registered_year", tv_zhucenianfen.getText().toString().trim());
        map.put("kilometre", ed_gonglishu.getText().toString().trim());
        map.put("contacts", ed_lianxiren.getText().toString().trim());
        map.put("contact_number", ed_lianxidianhua.getText().toString().trim());
        map.put("province_id", "");
        map.put("province", dwsheng);
        map.put("city_id", "");
        map.put("city", dwshi);
        map.put("county_id", "");
        map.put("county", dwxian);
        map.put("region", tv_kancheaddress.getText().toString().trim());
        map.put("describe", ed_miaoshu.getText().toString().trim());
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.addSelfSecondhandcar(data)
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
                            System.out.println(body);
                            JSONObject jsonObject = new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")) {
                                JSONArray response = jsonObject.getJSONArray("response");
                                JSONObject jsonObject1 = response.getJSONObject(0);
                                String secondhandcarid = jsonObject1.getString("secondhandcarid");
                                initshangchuantupian(secondhandcarid);
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
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

    private void initshangchuantupian(String secondhandcarid) {
        String url = Constant.BASE_URL + "mbtwz/secondhandcar?action=insertSHcarUpImg";
        OkHttpClient okHttpClient = new OkHttpClient();
//                        RequestBody requestBody1 =new FormBody.Builder()
//                                .add("driverid",substring)
//                                .add("driver_img_type","1")
//                                .build();
        RequestBody requestBody;
        if (arrayList.size() == 1) {
            requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("line", arrayList.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), arrayList.get(0)))
                    .addFormDataPart("secondhandcarid", secondhandcarid)
                    .build();
        } else if (arrayList.size() == 2) {
            requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("line", arrayList.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), arrayList.get(0)))
                    .addFormDataPart("line1", arrayList.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), arrayList.get(1)))
                    .addFormDataPart("secondhandcarid", secondhandcarid)
                    .build();
        } else {
            requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("line", arrayList.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), arrayList.get(0)))
                    .addFormDataPart("line1", arrayList.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), arrayList.get(1)))
                    .addFormDataPart("line2", arrayList.get(2).getName(), RequestBody.create(MediaType.parse("image/*"), arrayList.get(2)))
                    .addFormDataPart("secondhandcarid", secondhandcarid)
                    .build();
        }
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                hideWaitDialog();
                System.out.println(e.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                System.out.println(string);
                hideWaitDialog();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast("发布成功");
                        finish();

                    }
                });


            }
        });
    }

    private void initPinPai() {
        PileApi.instance.selectPubSechancarBrand()
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
                            System.out.println(body);
                            JSONObject jsonObject = new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")) {
                                Gson gson = new Gson();
                                erShouChePinPaiEntity = gson.fromJson(body, ErShouChePinPaiEntity.class);
                                initjiazaipinpai();

                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(FaBuErShouCheActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initjiazaipinpai() {
        ProvinceAdapter666 adapter = new ProvinceAdapter666(this, erShouChePinPaiEntity.getResponse());
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickItemChild(new ProvinceAdapter666.OnClickItemChild() {
            @Override
            public void onClick(int id, String name) {
                tv_pinpai.setText(name);
                tv_pinpai.setTag(id);
                window.dismiss();
            }
        });
    }

    private void initChexing() {
        PileApi.instance.searchKuaiyunCartype()
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
                            temp = new Gson().fromJson(body, new TypeToken<ArrayList<cxbean11>>() {
                            }.getType());
                            if (temp.size() == 0) {
                                Toast.makeText(FaBuErShouCheActivity.this, "暂无数据", Toast.LENGTH_LONG).show();
                                return;
                            }
                            initjiazaichexing();


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(FaBuErShouCheActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initjiazaichexing() {
        ProvinceAdapter555 adapter = new ProvinceAdapter555(FaBuErShouCheActivity.this, temp);
        GridLayoutManager manager = new GridLayoutManager(FaBuErShouCheActivity.this, 4);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickItemChild(new ProvinceAdapter555.OnClickItemChild() {
            @Override
            public void onClick(int id, String name) {
                tv_chexing.setText(name);
                tv_chexing.setTag(id);
                window.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 001 && resultCode == 333) {
            tv_kancheaddress.setText(data.getStringExtra("myresuly"));
            dwsheng = data.getStringExtra("dwsheng");
            dwshi = data.getStringExtra("dwshi");
            dwxian = data.getStringExtra("dwxian");
        }

        if (data != null && requestCode == 1000) {
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images != null) {
                imageItem1 = images.get(0);
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 3;
                    bitmap = BitmapFactory.decodeFile(images.get(0).path, options);

                    img1.setImageBitmap(bitmap);
                    uploadFile(imageItem1, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                showToast("图片解析失败");
            }

        } else if (data != null && requestCode == 2) {
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images != null) {
                imageItem2 = images.get(0);
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 3;
                    bitmap = BitmapFactory.decodeFile(images.get(0).path, options);

                    img2.setImageBitmap(bitmap);
                    uploadFile(imageItem2, 2);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                showToast("图片解析失败");
            }
        } else if (data != null && requestCode == 3) {
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images != null) {
                imageItem3 = images.get(0);
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 3;
                    bitmap = BitmapFactory.decodeFile(images.get(0).path, options);

                    img3.setImageBitmap(bitmap);
                    uploadFile(imageItem3, 3);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                showToast("图片解析失败");
            }
        }
    }

    private void uploadFile(ImageItem imageItem, final int type) {


        File file = new File(imageItem.path);
        showWaitDialog("正在上传图片...");
        Luban.with(this)
                .load(file)
                .ignoreBy(100)
                //  .setTargetDir(AppConfig.DEFAULT_SAVE_IMAGE_PATH)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        hideWaitDialog();
//                        if (type==1){
//                            arrayList.add(0,file);
//                        }else if (type==2){
//                            arrayList.add(1,file);
//                        }else if (type==3){
//                            arrayList.add(2,file);
//                        }

                        if (arrayList.size() == 0) {
                            arrayList.add(0, file);
                        } else if (arrayList.size() == 1) {
                            arrayList.add(1, file);
                        } else if (arrayList.size() == 2) {
                            arrayList.add(2, file);
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        hideWaitDialog();
                    }
                }).launch();


    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            if (this.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
