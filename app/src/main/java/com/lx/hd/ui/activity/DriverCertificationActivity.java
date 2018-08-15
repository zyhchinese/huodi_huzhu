package com.lx.hd.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.CheckSiJiRenZhengEntity;
import com.lx.hd.bean.CheckSiJiRenZhengImgEntity;
import com.lx.hd.utils.GlideImageLoaderForPicker;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class DriverCertificationActivity extends BackCommonActivity implements View.OnClickListener {

    private TextView tv_shili1, tv_shili2, tv_shili3, tv_shili4, tv_shili5, tv_shili6;
    private ImageView img1, img2, img3, img4, img5, img6;
    private Button btn_tijiao, btn_send;
    private EditText ed_name, ed_chepaihao, ed_phone_number, ed_yanzhengma;
    private String mAuthCode = "";
    private CountDownTimer mTimer;
    private String substring;
    private ImageItem imageItem1;
    private ImageItem imageItem2;
    private ImageItem imageItem3;
    private ImageItem imageItem4;
    private ImageItem imageItem5;
    private ImageItem imageItem6;
    private int num;
    private List<CheckSiJiRenZhengEntity> checkSiJiRenZhengEntityList;
    private List<CheckSiJiRenZhengImgEntity> checkSiJiRenZhengImgEntityList;
    private Bitmap bitmap;


    @Override
    protected int getContentView() {
        return R.layout.activity_driver_certification;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("货主认证");
        initView();
    }

    private void initView() {
        tv_shili1 = (TextView) findViewById(R.id.tv_shili1);
        tv_shili2 = (TextView) findViewById(R.id.tv_shili2);
        tv_shili3 = (TextView) findViewById(R.id.tv_shili3);
        tv_shili4 = (TextView) findViewById(R.id.tv_shili4);
        tv_shili5 = (TextView) findViewById(R.id.tv_shili5);
        tv_shili6 = (TextView) findViewById(R.id.tv_shili6);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        img5 = (ImageView) findViewById(R.id.img5);
        img6 = (ImageView) findViewById(R.id.img6);
        btn_tijiao = (Button) findViewById(R.id.btn_tijiao);
        btn_send = (Button) findViewById(R.id.btn_send);
        ed_name = (EditText) findViewById(R.id.ed_name);
        ed_chepaihao = (EditText) findViewById(R.id.ed_chepaihao);
        ed_phone_number = (EditText) findViewById(R.id.ed_phone_number);
        ed_yanzhengma = (EditText) findViewById(R.id.ed_yanzhengma);
        tv_shili1.setOnClickListener(this);
        tv_shili2.setOnClickListener(this);
        tv_shili3.setOnClickListener(this);
        tv_shili4.setOnClickListener(this);
        tv_shili5.setOnClickListener(this);
        tv_shili6.setOnClickListener(this);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        img4.setOnClickListener(this);
        img5.setOnClickListener(this);
        img6.setOnClickListener(this);
        btn_tijiao.setOnClickListener(this);
        btn_send.setOnClickListener(this);
    }

    private void initDialog(int id) {

        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        dialog.getWindow().setContentView(R.layout.dialog_shilitupian);
        ImageView imageView = (ImageView) dialog.getWindow().findViewById(R.id.img_shili);
        imageView.setImageResource(id);

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
            case R.id.tv_shili1:
                initDialog(R.mipmap.img_shili2);
                break;
            case R.id.tv_shili2:
                initDialog(R.mipmap.img_shili1);
                break;
            case R.id.tv_shili3:
                initDialog(R.mipmap.img_shili3);
                break;
            case R.id.tv_shili4:
                initDialog(R.mipmap.img_shili4);
                break;
            case R.id.tv_shili5:
                initDialog(R.mipmap.img_shili5);
                break;
            case R.id.tv_shili6:
                initDialog(R.mipmap.img_shili6);
                break;
            case R.id.img1:


                Intent intent1 = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent1, 1);
                break;
            case R.id.img2:
                Intent intent2 = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent2, 2);
                break;
            case R.id.img3:
                Intent intent3 = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent3, 3);
                break;
            case R.id.img4:
                Intent intent4 = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent4, 4);
                break;
            case R.id.img5:
                Intent intent5 = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent5, 5);
                break;
            case R.id.img6:
                Intent intent6 = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent6, 6);
                break;
            case R.id.btn_tijiao:
                if (ed_name.getText().toString().trim().equals("")) {
                    showToast("请填写您的姓名");
                    return;
                }
                if (ed_chepaihao.getText().toString().trim().equals("")) {
                    showToast("请填写您的身份证号");
                    return;
                }
//                if (!isCarNo(ed_chepaihao.getText().toString().trim())){
//                    showToast("请填写正确的车牌号");
//                    return;
//                }
//                if (ed_chepaihao.getText().toString().trim().length()==7){
//                    showToast("请填写正确的车牌号");
//                    return;
//                }
                if (ed_phone_number.getText().toString().trim().equals("") || ed_phone_number.getText().toString().trim().length()!=11) {
                    showToast("请填写正确的手机号");
                    return;
                }
                if (ed_yanzhengma.getText().toString().trim().equals("")) {
                    showToast("请填写验证码");
                    return;
                }
                if (mAuthCode.equals("")) {
                    showToast("请发送验证码");
                }
                if (!ed_yanzhengma.getText().toString().trim().equals(mAuthCode)) {
                    showToast("验证码填写错误");
                    return;
                }
                if (imageItem1==null||imageItem2==null||imageItem3==null){
                    showToast("上传的图片信息不完整");
                    return;
                }
                initJibenxinxi();

                break;
            case R.id.btn_send:

                if (ed_phone_number.getText().toString().trim().equals("") || ed_phone_number.getText().toString().trim().length()!=11) {
                    showToast("请填写正确的手机号");
                    return;
                }
                requestSmsCode();
//                HashMap<String,String> map=new HashMap<String, String>();
//                map.put("phone",ed_phone_number.getText().toString().trim());
//                Gson gson=new Gson();
//                String params=gson.toJson(map);
//                showWaitDialog("");
//                PileApi.instance.mCheckPhone(params)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Observer<ResponseBody>() {
//                            @Override
//                            public void onSubscribe(@NonNull Disposable d) {
//
//                            }
//
//                            @Override
//                            public void onNext(@NonNull ResponseBody responseBody) {
//                                hideWaitDialog();
//                                try {
//                                    String body=responseBody.string();
//                                    if(body.equals("\"false\"")){
//                                        requestSmsCode();
//                                    }else {
//                                        showToast("该手机已注册");
//                                    }
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                            @Override
//                            public void onError(@NonNull Throwable e) {
//                                showToast("检测手机号异常");
//                                hideWaitDialog();
//                            }
//
//                            @Override
//                            public void onComplete() {
//
//                            }
//                        });
                break;
        }
    }

    public boolean isCarNo(String CarNum) {
        //匹配第一位汉字
        String str = "京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼甲乙丙己庚辛壬寅辰戍午未申";
        if (!(CarNum == null || CarNum.equals(""))) {
            String s1 = CarNum.substring(0, 1);//获取字符串的第一个字符
            if (str.contains(s1)) {
                String s2 = CarNum.substring(1, CarNum.length());
                //不包含I O i o的判断
                if (s2.contains("I") || s2.contains("i") || s2.contains("O") || s2.contains("o")) {
                    return false;
                } else {
                    if (!CarNum.matches("^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$")) {
                        return true;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return false;
    }

    //上传基本信息网络请求
    private void initJibenxinxi() {
        HashMap<String, String> map = new HashMap<>();
        map.put("custname", ed_name.getText().toString().trim());
        map.put("custcall", ed_phone_number.getText().toString().trim());
        map.put("custidcard", ed_chepaihao.getText().toString().trim());
//        map.put("driver_checkno", ed_yanzhengma.getText().toString().trim());
//        map.put("driver_checkno", "1234");
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.insertOwnerCertification(data)
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
                            JSONObject jsonObject=new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            JSONArray response = jsonObject.getJSONArray("response");
                            JSONObject jsonObject1 = response.getJSONObject(0);
                            substring=jsonObject1.getString("id");

                            if (flag.equals("200")){
                                if (imageItem1!=null){
                                    uploadFile(imageItem1, "1");
                                    btn_tijiao.setClickable(false);
                                }
                                if (imageItem2!=null){
                                    uploadFile(imageItem2, "2");
                                }
                                if (imageItem3!=null){
                                    uploadFile(imageItem3, "3");
                                }
                            }else {
                                showToast("信息提交失败");
                            }


//                            if (body.indexOf("false") != -1) {
//                                showToast("信息提交失败");
//                            } else {
//                                substring = body.substring(1, body.length() - 1);
//
//                                if (imageItem1!=null){
//                                    uploadFile(imageItem1, "1");
//                                    btn_tijiao.setClickable(false);
//                                }
//                                if (imageItem2!=null){
//                                    uploadFile(imageItem2, "2");
//                                }
//                                if (imageItem3!=null){
//                                    uploadFile(imageItem3, "0");
//                                }
//                                if (imageItem4!=null){
//                                    uploadFile(imageItem4, "3");
//                                }
//                                if (imageItem5!=null){
//                                    uploadFile(imageItem5, "4");
//                                }
//                                if (imageItem6!=null){
//                                    uploadFile(imageItem6, "5");
//                                }
//
//
//
//
//
//
//
//
//                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("2222");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println();
                    }
                });

    }


    private void uploadFile(ImageItem imageItem, final String type) {


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
                        String url = Constant.BASE_URL + "mbtwz/logisticssendwz?action=insertOCUploadImgA";
                        OkHttpClient okHttpClient = new OkHttpClient();
//                        RequestBody requestBody1 =new FormBody.Builder()
//                                .add("driverid",substring)
//                                .add("driver_img_type","1")
//                                .build();
                        RequestBody requestBody = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("line", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                                .addFormDataPart("ownerid", substring)
                                .addFormDataPart("driver_img_type", type)
                                .build();
                        okhttp3.Request request = new okhttp3.Request.Builder()
                                .url(url)
                                .post(requestBody)
                                .build();
                        okHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                hideWaitDialog();
//                                showToast("网络异常");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        btn_tijiao.setClickable(true);
                                        num = -1;
                                        if (num==-1){
                                            showToast("提交失败");
                                            return;
                                        }

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

                                        btn_tijiao.setVisibility(View.GONE);

                                        num++;
                                        if (num==3){
                                            initSuccess();
                                        }

                                    }
                                });



                            }
                        });

                    }

                    @Override
                    public void onError(Throwable e) {
                        hideWaitDialog();
                    }
                }).launch();


    }

    private void initSuccess() {
        AlertDialog builder = new AlertDialog.Builder(this).create();
        builder.setCancelable(false);
        builder.show();
        builder.getWindow().setContentView(R.layout.dialog_success);
        builder.getWindow().setBackgroundDrawableResource(R.drawable.bg_yuanjiao_touming1);
        builder.getWindow().findViewById(R.id.btn_queding)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendPayLocalReceiver(DriverCertificationStateActivity.class.getName());

                        initPanduan();
                    }
                });

    }
    protected boolean sendPayLocalReceiver(String className) {
        if (mManager != null) {
            Intent intent = new Intent();
            intent.putExtra("className", className);
            intent.setAction(ACTION_PAY_FINISH_ALL);
            return mManager.sendBroadcast(intent);
        }

        return false;
    }

    //向跳转页面传值
    private void initPanduan() {
        PileApi.instance.checkOwnerStatus()
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

                            JSONObject jsonObject=new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")){
                                //审核通过
                                Intent intent = new Intent(DriverCertificationActivity.this, DriverCertificationStateActivity.class);
                                startActivity(intent);
                                finish();
                            }else if (flag.equals("210")){
                                //未认证
                                Intent intent = new Intent(DriverCertificationActivity.this, DriverCertificationActivity.class);
                                startActivity(intent);
                                finish();
                            }else if (flag.equals("220")){
                                //正在审核中
                                Intent intent = new Intent(DriverCertificationActivity.this, DriverCertificationStateActivity.class);
                                startActivity(intent);
                                finish();
                            }else if (flag.equals("230")){
                                //审核未通过
                                Intent intent = new Intent(DriverCertificationActivity.this, DriverCertificationStateActivity.class);
                                startActivity(intent);
                                finish();
                            }

//                            String substring = body.substring(1, body.length() - 1);
//
//                            if (substring.equals("-1")) {
//                                startActivity(new Intent(DriverCertificationActivity.this, DriverCertificationActivity.class));
//                            } else {
//
//                                Gson gson = new Gson();
//                                checkSiJiRenZhengEntityList = gson.fromJson(body, new TypeToken<List<CheckSiJiRenZhengEntity>>() {
//                                }.getType());
//                                String imglist = checkSiJiRenZhengEntityList.get(0).getImglist();
//                                Gson gson1 = new Gson();
//                                checkSiJiRenZhengImgEntityList = gson1.fromJson(imglist, new TypeToken<List<CheckSiJiRenZhengImgEntity>>() {
//                                }.getType());
//                                if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status() == 0) {
//                                    //审核中
//                                    Intent intent = new Intent(DriverCertificationActivity.this, DriverCertificationStateActivity.class);
////                                    intent.putExtra("shuju", (Serializable) checkSiJiRenZhengEntityList);
////                                    intent.putExtra("tupian", (Serializable) checkSiJiRenZhengImgEntityList);
//                                    startActivity(intent);
//                                    finish();
//                                } else if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status() == 1) {
//                                    //审核通过
//                                    startActivity(new Intent(DriverCertificationActivity.this, CarLeaseActivity.class));
//                                } else if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status() == 2) {
//                                    //审核拒绝
//                                    Intent intent = new Intent(DriverCertificationActivity.this, DriverCertificationStateActivity.class);
////                                    intent.putExtra("shuju", (Serializable) checkSiJiRenZhengEntityList);
////                                    intent.putExtra("tupian", (Serializable) checkSiJiRenZhengImgEntityList);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("2222");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println();
                    }
                });
    }

    private boolean phone(String mobiles) {
        Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
        if (data != null && requestCode == 1) {
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images != null) {
                imageItem1 = images.get(0);
                try {
//                    FileInputStream fileInputStream=new FileInputStream(images.get(0).path);
                    BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inSampleSize=3;
                    bitmap= BitmapFactory.decodeFile(images.get(0).path,options);

                    img1.setImageBitmap(bitmap);
//                    bitmap.recycle();
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                img1.setImageURI(Uri.parse(images.get(0).path));

            } else {
                showToast("图片解析失败");
            }
        } else if (data != null && requestCode == 2) {
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images != null) {
                imageItem2 = images.get(0);

                try {
//                    FileInputStream fileInputStream=new FileInputStream(images.get(0).path);
                    BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inSampleSize=3;
                    bitmap= BitmapFactory.decodeFile(images.get(0).path,options);

                    img2.setImageBitmap(bitmap);
//                    bitmap.recycle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                img2.setImageURI(Uri.parse(images.get(0).path));
            } else {
                showToast("图片解析失败");
            }
        } else if (data != null && requestCode == 3) {
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images != null) {
                imageItem3 = images.get(0);

                try {
//                    FileInputStream fileInputStream=new FileInputStream(images.get(0).path);
                    BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inSampleSize=3;
                    bitmap= BitmapFactory.decodeFile(images.get(0).path,options);

                    img3.setImageBitmap(bitmap);
//                    bitmap.recycle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                img3.setImageURI(Uri.parse(images.get(0).path));
            } else {
                showToast("图片解析失败");
            }
        } else if (data != null && requestCode == 4) {
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images != null) {
                imageItem4 = images.get(0);

                try {
//                    FileInputStream fileInputStream=new FileInputStream(images.get(0).path);
                    BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inSampleSize=3;
                    bitmap= BitmapFactory.decodeFile(images.get(0).path,options);

                    img4.setImageBitmap(bitmap);
//                    bitmap.recycle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                img4.setImageURI(Uri.parse(images.get(0).path));
            } else {
                showToast("图片解析失败");
            }
        } else if (data != null && requestCode == 5) {
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images != null) {
                imageItem5 = images.get(0);

                try {
//                    FileInputStream fileInputStream=new FileInputStream(images.get(0).path);
                    BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inSampleSize=4;
                    bitmap= BitmapFactory.decodeFile(images.get(0).path,options);

                    img5.setImageBitmap(bitmap);
//                    bitmap.recycle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                img5.setImageURI(Uri.parse(images.get(0).path));
            } else {
                showToast("图片解析失败");
            }
        } else if (data != null && requestCode == 6) {
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images != null) {
                imageItem6 = images.get(0);

                try {
//                    FileInputStream fileInputStream=new FileInputStream(images.get(0).path);
                    BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inSampleSize=3;
                    bitmap= BitmapFactory.decodeFile(images.get(0).path,options);

                    img6.setImageBitmap(bitmap);
//                    bitmap.recycle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                img6.setImageURI(Uri.parse(images.get(0).path));
            } else {
                showToast("图片解析失败");
            }

        } else {
            showToast("没有数据");
        }
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bitmap!=null){
            bitmap.recycle();
        }

    }

    private void requestSmsCode() {

        if (btn_send.getTag() == null) {
            //    mRequestType = 1;
            btn_send.setAlpha(0.6f);
            btn_send.setTag(true);
            mTimer = new CountDownTimer(60 * 1000, 1000) {

                @SuppressLint("DefaultLocale")
                @Override
                public void onTick(long millisUntilFinished) {
                    btn_send.setText(String.format("%s%s%d%s", "等待", "(", millisUntilFinished / 1000, ")"));
                }

                @Override
                public void onFinish() {
                    btn_send.setTag(null);
                    btn_send.setText("获取验证码");
                    btn_send.setAlpha(1.0f);
                }
            }.start();
            String phoneNumber = ed_phone_number.getText().toString().trim();
            HashMap<String, String> map = new HashMap<>();
            map.put("phone", phoneNumber);
            Gson gson = new Gson();
            String params = gson.toJson(map);
            showWaitDialog("正在获取验证码...");
            PileApi.instance.mGetAuthCode(params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull ResponseBody responseBody) {

                            hideWaitDialog();
                            //"status": true, "msg": "短信发送成功，请注意接收短信！","spstatus":"null"}
                            try {
                                String body = responseBody.string();
                                if (body.length() == 6) {
                                    body = body.substring(1, 5);
                                    mAuthCode = body;
                                    showToast("短信发送成功，请注意查收");
                                } else {
                                    showToast("短信发送失败，请稍后重试");

                                }
//                                    JSONObject jsonObject=new JSONObject(body);
//                                    if(jsonObject.getString("status").equals("true")){
//                                        mAuthCode=jsonObject.getString("smscode");
//                                        showToast(jsonObject.getString("msg"));
//                                    }else {
//                                        showToast(jsonObject.getString("msg"));
//                                    }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            hideWaitDialog();
                            showToast("短信发送失败，请稍后重试");
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } else {
            showToast("别激动,休息一下呗");
        }
    }
}
