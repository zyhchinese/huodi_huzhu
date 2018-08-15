package com.lx.hd.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.AuditOrderEntity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class OrderSearchActivity extends BackCommonActivity implements View.OnClickListener {
    private EditText ed_dingdanhao, ed_name, ed_phone;
    private Button btn_search;
    private TextView ed_time;
    private String orderstatus;


    @Override
    protected int getContentView() {
        return R.layout.activity_order_search;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("订单搜索");
        orderstatus = getIntent().getStringExtra("orderstatus");
        initView();
    }

    private void initView() {
        ed_dingdanhao = (EditText) findViewById(R.id.ed_dingdanhao);
        ed_time = (TextView) findViewById(R.id.ed_time);
        ed_name = (EditText) findViewById(R.id.ed_name);
        ed_phone = (EditText) findViewById(R.id.ed_phone);
        btn_search = (Button) findViewById(R.id.btn_search);



        btn_search.setOnClickListener(this);
        ed_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(ed_dingdanhao.getWindowToken(), 0) ;
                TimePickerView pvTime = new TimePickerView.Builder(OrderSearchActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = getTime(date2);
                        ed_time.setText(time);
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

            }
        });
    }

    public String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    public void onClick(View v) {
        if (ed_dingdanhao.getText().toString().trim().length() == 0 &&
                ed_time.getText().toString().trim().length() == 0 &&
                ed_name.getText().toString().trim().length() == 0 &&
                ed_phone.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "请填写一个搜索条件", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!phone(ed_phone.getText().toString().trim()) && ed_phone.getText().toString().trim().length() != 0) {
            Toast.makeText(this, "请填写正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }


        if (ed_time.getText().toString().trim().length() != 0){


            java.text.SimpleDateFormat simpleDateFormat=new java.text.SimpleDateFormat("yyyy-MM-dd");
            Date date= null;
            try {
                date = simpleDateFormat.parse(ed_time.getText().toString().trim());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String format = simpleDateFormat.format(date.getTime()-1*24*60*60*1000);
            String format1 = simpleDateFormat.format(date.getTime()+1*24*60*60*1000);
//            initDingDanSearch(format,format1);

            initPanduan(format,format1);





        }else {
//            initDingDanSearch(null,null);
            initPanduan(null,null);

//            Intent intent=new Intent(this,AuditStatusActivity.class);
//            intent.putExtra("orderno",ed_dingdanhao.getText().toString().trim());
//            intent.putExtra("createtimebefore","");
//            intent.putExtra("createtimeafter","");
//            intent.putExtra("link_name",ed_name.getText().toString().trim());
//            intent.putExtra("link_phone",ed_phone.getText().toString().trim());
//            setResult(2,intent);
//            finish();
        }

    }

    private void initPanduan(final String format, final String format1) {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderstatus", orderstatus);
        map.put("orderno", ed_dingdanhao.getText().toString().trim());
        map.put("createtimebefore", format);
        map.put("createtimeafter", format1);
        map.put("link_name", ed_name.getText().toString().trim());
        map.put("link_phone", ed_phone.getText().toString().trim());
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.searchLeaseOrder(params)
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


                            if (body.indexOf("false") != -1 || body.length() < 10) {
                                Toast.makeText(OrderSearchActivity.this, "没有符合条件的结果", Toast.LENGTH_SHORT).show();
                            }else {
                                Intent intent=new Intent(OrderSearchActivity.this,AuditStatusActivity.class);
                                intent.putExtra("orderno",ed_dingdanhao.getText().toString().trim());
                                intent.putExtra("createtimebefore",format);
                                intent.putExtra("createtimeafter",format1);
                                intent.putExtra("link_name",ed_name.getText().toString().trim());
                                intent.putExtra("link_phone",ed_phone.getText().toString().trim());
                                setResult(2,intent);
                                finish();
                            }
                        } catch (IOException e) {

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

//    private void initDingDanSearch(String s, String s1) {
//        HashMap<String, String> map = new HashMap<>();
//        map.put("orderstatus", orderstatus);
//        map.put("orderno", ed_dingdanhao.getText().toString().trim());
//        map.put("createtimebefore", s);
//        map.put("createtimeafter", s1);
//        map.put("link_name", ed_name.getText().toString().trim());
//        map.put("link_phone", ed_phone.getText().toString().trim());
//        Gson gson = new Gson();
//        String params = gson.toJson(map);
//        PileApi.instance.searchLeaseOrder(params)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<ResponseBody>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull ResponseBody responseBody) {
//
//                        try {
//                            String body = responseBody.string();
//                            System.out.println(body);
//
//                            if (body.indexOf("false") != -1 || body.length() < 10) {
//                                Toast.makeText(OrderSearchActivity.this, "暂无租赁订单", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Gson gson = new Gson();
////                                auditOrderEntityList = gson.fromJson(body, new TypeToken<List<AuditOrderEntity>>() {
////                                }.getType());
//
//                            }
//                        } catch (IOException e) {
//                        }
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }

    private boolean phone(String mobiles) {
        Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
