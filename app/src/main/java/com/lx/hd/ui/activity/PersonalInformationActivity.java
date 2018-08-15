package com.lx.hd.ui.activity;
/**
 * 个人信息activity
 * tb
 * 20170830
 */

import android.app.DatePickerDialog;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lx.hd.AppConfig;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BaseActivity;
import com.lx.hd.bean.CItem;
import com.lx.hd.bean.UserInfoBean;
import com.lx.hd.utils.GlideImageLoaderForPicker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class PersonalInformationActivity extends BaseActivity {
    private Spinner spinner;      //性别
    private Spinner spinner2;    //收入水平
    private TextView csny;        //出生年月
    private EditText zy;           //职业
    private EditText cs;          //城市
    private EditText nc;  //昵称
    private TextView clxx;       //电动车辆信息
    private Button upload,upload1;       //上报按钮
    private ImageView goback, grxx_bj; //返回按钮
    private CircleImageView imgAvatar; //返回按钮
    private TextView sxview, xzview;
    private EditText tv_email, tv_qq, tv_wx;
    private boolean flag = false;
    RequestOptions mOptions;
    private final static int[] dayArr = new int[]{20, 19, 21, 20, 21, 22, 23,
            23, 23, 24, 23, 22};
    private int year = 1995, day = 7, mon = 13;
    private final static String[] constellationArr = new String[]{"摩羯座",
            "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座",
            "天蝎座", "射手座", "摩羯座"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOptions = new RequestOptions()
                .placeholder(R.mipmap.user_header_defult)
                .error(R.mipmap.user_header_defult)
                .fitCenter();
        spinner = (Spinner) findViewById(R.id.Spinner);
        goback = (ImageView) findViewById(R.id.goback);
        grxx_bj = (ImageView) findViewById(R.id.grxx_bj);
        imgAvatar = (CircleImageView) findViewById(R.id.img_header);
        spinner2 = (Spinner) findViewById(R.id.Spinner2);
        sxview = (TextView) findViewById(R.id.sxview);
        xzview = (TextView) findViewById(R.id.xzview);
        csny = (TextView) findViewById(R.id.csny);
        zy = (EditText) findViewById(R.id.zy);
        cs = (EditText) findViewById(R.id.cs);
        nc = (EditText) findViewById(R.id.nc);
        clxx = (TextView) findViewById(R.id.clxx);
        tv_email = (EditText) findViewById(R.id.tv_email);
        tv_qq = (EditText) findViewById(R.id.tv_qq);
        tv_wx = (EditText) findViewById(R.id.tv_wx);
        upload = (Button) findViewById(R.id.upload);
        upload1 = (Button) findViewById(R.id.upload1);

        spinner.setEnabled(false);
        spinner2.setEnabled(false);
        imgAvatar.setEnabled(false);
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker imagePicker = ImagePicker.getInstance();
                imagePicker.setImageLoader(new GlideImageLoaderForPicker());   //设置图片加载器
                imagePicker.setShowCamera(true);  //显示拍照按钮
                imagePicker.setCrop(false);        //允许裁剪（单选才有效）
                imagePicker.setSaveRectangle(true); //是否按矩形区域保存
                imagePicker.setSelectLimit(1);    //选中数量限制
                imagePicker.setOutPutX(800);//保存文件的宽度。单位像素
                imagePicker.setOutPutY(800);//保存文件的高度。单位像素

                Intent intent = new Intent(PersonalInformationActivity.this, ImageGridActivity.class);
                startActivityForResult(intent, Constant.REQUEST_CODE);
            }
        });
        List<String> liststates = new ArrayList<String>();
        liststates.add("男");
        liststates.add("女");
        List<CItem> liststates2 = new ArrayList<CItem>();
        liststates2.add(new CItem("0", "3-5万/年"));
        liststates2.add(new CItem("1", "5-8万/年"));
        liststates2.add(new CItem("2", "8-11万/年"));
        liststates2.add(new CItem("3", "11-15万/年"));
        liststates2.add(new CItem("4", "15万以上"));

        ArrayAdapter adap = new ArrayAdapter<String>(PersonalInformationActivity.this, R.layout.item_spinselect, liststates);
        ArrayAdapter<CItem> adap2 = new ArrayAdapter<CItem>(PersonalInformationActivity.this,
                R.layout.item_spinselect, liststates2);
        adap.setDropDownViewResource(R.layout.item_dialogspinselect);
        adap2.setDropDownViewResource(R.layout.item_dialogspinselect);
        spinner.setAdapter(adap);
        spinner2.setAdapter(adap2);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        grxx_bj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                setFlag();


            }
        });
        csny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PersonalInformationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        csny.setText(String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth));
                        sxview.setVisibility(View.VISIBLE);
                        sxview.setText(getYear(year));
                        xzview.setVisibility(View.VISIBLE);
                        xzview.setText(getConstellation(monthOfYear + 1, dayOfMonth));
                        updateDate();
                    }
                }, year, day, mon).show();
            }
        });
        upload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setEnabled(true);
                spinner2.setEnabled(true);
                nc.setEnabled(true);
                csny.setEnabled(true);
                zy.setEnabled(true);
                cs.setEnabled(true);
                imgAvatar.setEnabled(true);
//                tv_email.setEnabled(true);
//                tv_qq.setEnabled(true);
//                tv_wx.setEnabled(true);
                upload1.setVisibility(View.GONE);
                upload.setVisibility(View.VISIBLE);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("birthday", csny.getText().toString());
                map.put("occupation", zy.getText().toString());
                map.put("custname", nc.getText().toString());
                map.put("addressdetail", cs.getText().toString());
                CItem citem = (CItem) spinner2.getSelectedItem();
                map.put("incomelevel", citem.GetID());
                map.put("vehicle_information", "");
                int sex = 0;
                if (spinner.getSelectedItem().toString().equals("男")) {
                    sex = 0;
                } else {
                    sex = 1;
                }
                map.put("sex", sex + "");
                Gson gson = new Gson();
                String params = gson.toJson(map);
                PileApi.instance.UpdateCustomer(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                showWaitDialog("保存中..请稍等");
                            }

                            @Override
                            public void onNext(@NonNull ResponseBody responseBody) {
                                try {
                                    String body = responseBody.string();
                                    if (body.indexOf("true") != -1) {
                                        hideWaitDialog();
                                        showToast("保存成功！");
                                        finish();
                                    } else {
                                        showToast("保存失败,请重试");
                                        hideWaitDialog();
                                    }
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
        });

        getUserInfo();
    }


    private void updateUserInfo(UserInfoBean user) {
        if (user != null) {
            String userLogo = Constant.BASE_URL + user.getFolder() + "/" + user.getAutoname();
            Glide.with(PersonalInformationActivity.this)
                    .load(userLogo)
                    .apply(mOptions)
                    .into(imgAvatar);
            nc.setText(user.getCustname());
            spinner.setSelection(user.getSex(), true);
            csny.setText(user.getBirthday());
            tv_email.setText(user.getEmail());
            tv_qq.setText(user.getQq());
            tv_wx.setText(user.getWechat());

            if (!"".equals(user.getBirthday())) {
                String cs = user.getBirthday();
                String[] cssz = cs.split("-");
                if (cssz.length > 0) {
                    sxview.setVisibility(View.VISIBLE);
                    sxview.setText(getYear(Integer.valueOf(cssz[0])));
                    xzview.setVisibility(View.VISIBLE);
                    xzview.setText(getConstellation(Integer.valueOf(cssz[1]), Integer.valueOf(cssz[2])));
                    year = Integer.valueOf(cssz[0]);
                    mon = Integer.valueOf(cssz[1]);
                    day = Integer.valueOf(cssz[2]);
                }
            }
            zy.setText(user.getOccupation());
            cs.setText(user.getAddressdetail());
            int Incomelevel;
            if (!user.getIncomelevel().equals("")) {
                Incomelevel = Integer.parseInt(user.getIncomelevel());
            } else {
                Incomelevel = 0;
            }
            spinner2.setSelection(Incomelevel, true);
            clxx.setText(user.getVehicle_information());
        }
    }

    public void updateDate() {

        if (!"".equals(csny.getText().toString())) {
            String cs = csny.getText().toString();
            String[] cssz = cs.split("-");
            if (cssz.length > 1) {
                year = Integer.valueOf(cssz[0]);
                mon = Integer.valueOf(cssz[1]);
                day = Integer.valueOf(cssz[2]);
            }
        }
    }

    private void getUserInfo() {
        showWaitDialog("正在获取个人信息");
        PileApi.instance.getUserInfo()
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
                                showToast("获取个人信息失败，请登录后重试");
                            } else {
                                Gson gson = new Gson();
                                List<UserInfoBean> userList = gson.fromJson(body, new TypeToken<List<UserInfoBean>>() {
                                }.getType());
                                updateUserInfo(userList.get(0));

                            }
                            hideWaitDialog();
                        } catch (IOException e) {

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

    @Override
    protected int getContentView() {
        return R.layout.activity_personal_information;
    }

    /**
     * Java通过生日计算星座
     *
     * @param month
     * @param day
     * @return
     */
    public static String getConstellation(int month, int day) {
        return day < dayArr[month - 1] ? constellationArr[month - 1]
                : constellationArr[month];
    }

    /**
     * 通过生日计算属相
     *
     * @param year
     * @return
     */
    public static String getYear(int year) {
        if (year < 1900) {
            return "未知";
        }
        int start = 1900;
        String[] years = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊",
                "猴", "鸡", "狗", "猪"};
        return years[(year - start) % years.length];
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == Constant.REQUEST_CODE) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    uploadFile(images.get(0));
                } else {
                    showToast("图片解析失败");
                }

            } else {
                showToast("没有数据");
            }
        }
    }

    private void uploadFile(final ImageItem image) {
        File file = new File(image.path);
        showWaitDialog("正在上传头像...");
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
                        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
                        MultipartBody.Part photo = MultipartBody.Part.createFormData("avatar", file.getName(), photoRequestBody);
                        PileApi.instance.updateAvatar(photo)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<ResponseBody>() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onNext(@NonNull ResponseBody responseBody) {
                                        hideWaitDialog();
                                        try {
                                            String body = responseBody.string();
                                            if (body.equals("\"true\"")) {
                                                showToast("头像上传成功");
                                                getUserInfo();
                                            } else {
                                                showToast("头像上传失败，请重新上传");
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            onError(e);
                                        }
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        hideWaitDialog();
                                        showToast("头像上传失败，请检查登录状态");
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideWaitDialog();
                    }
                }).launch();
    }

    private void setFlag() {
        if (flag) {
            spinner.setEnabled(false);
            spinner2.setEnabled(false);
            nc.setEnabled(false);
            csny.setEnabled(false);
            zy.setEnabled(false);
            cs.setEnabled(false);
            tv_email.setEnabled(false);
            tv_qq.setEnabled(false);
            tv_wx.setEnabled(false);
            flag = false;
            upload.setVisibility(View.GONE);

        } else {
            spinner.setEnabled(true);
            spinner2.setEnabled(true);
            nc.setEnabled(true);
            csny.setEnabled(true);
            zy.setEnabled(true);
            cs.setEnabled(true);
            tv_email.setEnabled(true);
            tv_qq.setEnabled(true);
            tv_wx.setEnabled(true);
            upload.setVisibility(View.VISIBLE);
            flag = true;
        }
    }
}
