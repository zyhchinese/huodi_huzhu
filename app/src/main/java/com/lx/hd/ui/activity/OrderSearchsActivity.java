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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.TypeSpinnerAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.HuoDiSearch;
import com.lx.hd.bean.HuoDiSearch1;
import com.lx.hd.bean.HuoDiSearch2;
import com.lx.hd.bean.Lease_order_detailList;
import com.lx.hd.bean.SiJiJieHuoEntity;
import com.lx.hd.bean.WuLiuFaHuoEntity;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class OrderSearchsActivity extends BackCommonActivity implements View.OnClickListener {

    private EditText ed_dingdanhao, ed_name, ed_phone;
    private Button btn_search;
    private TextView ed_time, ed_type;
    //    private Spinner spinner_type;
//    String[] type=new String[]{"物流发货单","司机接货单"};
    private List<String> list = new ArrayList<>();
    private String type;
    private List<HuoDiSearch> huoDiSearchList;
    private List<HuoDiSearch1> huoDiSearchList1;
    private List<HuoDiSearch2> huoDiSearchList2;


    @Override
    protected int getContentView() {
        return R.layout.activity_order_searchs;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("订单搜索");
        type = getIntent().getStringExtra("type");
        initView();
//        if (type.equals("2")){
//            ed_type.setText("物流发货单");
//        }else if (type.equals("3")){
//            ed_type.setText("司机接货单");
//        }
    }

    private void initView() {
        ed_dingdanhao = (EditText) findViewById(R.id.ed_dingdanhao);
        ed_time = (TextView) findViewById(R.id.ed_time);
        ed_type = (TextView) findViewById(R.id.ed_type);
        ed_name = (EditText) findViewById(R.id.ed_name);
        ed_phone = (EditText) findViewById(R.id.ed_phone);
        btn_search = (Button) findViewById(R.id.btn_search);
//        spinner_type= (Spinner) findViewById(R.id.spinner_type);
//        TypeSpinnerAdapter adapter=new TypeSpinnerAdapter(this,type);
//        spinner_type.setAdapter(adapter);
        list.add("同城小件单");
        list.add("同城搬家单");
        list.add("货滴快运单");


        btn_search.setOnClickListener(this);
        ed_type.setOnClickListener(this);
        ed_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(ed_dingdanhao.getWindowToken(), 0);
                TimePickerView pvTime = new TimePickerView.Builder(OrderSearchsActivity.this, new TimePickerView.OnTimeSelectListener() {
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
//        if (ed_dingdanhao.getText().toString().trim().length() == 0 &&
//                ed_time.getText().toString().trim().length() == 0 &&
//                ed_name.getText().toString().trim().length() == 0 &&
//                ed_phone.getText().toString().trim().length() == 0) {
//            Toast.makeText(this, "请填写一个搜索条件", Toast.LENGTH_SHORT).show();
//            return;
//        }
        switch (v.getId()) {
            case R.id.btn_search:
                if (!phone(ed_phone.getText().toString().trim()) && ed_phone.getText().toString().trim().length() != 0) {
                    Toast.makeText(this, "请填写正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                initwuliufahuo();

                break;
            case R.id.ed_type:

                OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        ed_type.setText(list.get(options1));
                    }
                })
//                        .setSubmitText("确定")//确定按钮文字
//                        .setCancelText("取消")//取消按钮文字
//                        .setTitleText("城市选择")//标题
                        .setSubCalSize(18)//确定和取消文字大小
//                        .setTitleSize(20)//标题文字大小
//                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(Color.parseColor("#333333"))//确定按钮文字颜色
                        .setCancelColor(Color.parseColor("#333333"))//取消按钮文字颜色
//                        .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
//                        .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
                        .setContentTextSize(18)//滚轮文字大小
//                        .setTextColorCenter(Color.BLUE)//设置选中项的颜色
                        .setTextColorCenter(Color.parseColor("#000000"))//设置选中项的颜色
                        .setDividerColor(Color.parseColor("#ffffff"))
                        .setTitleBgColor(Color.parseColor("#ffffff"))
//                        .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
//                        .setLinkage(false)//设置是否联动，默认true
//                        .setLabels("省", "市", "区")//设置选择的三级单位
//                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .setCyclic(false, false, false)//循环与否
//                        .setSelectOptions(1, 1, 1)  //设置默认选中项
//                        .setOutSideCancelable(false)//点击外部dismiss default true
//                        .isDialog(true)//是否显示为对话框样式
                        .build();
                pvOptions.setPicker(list);
                pvOptions.show();
                break;
        }
    }


    private boolean phone(String mobiles) {
        Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
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

    private void initwuliufahuo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderno", ed_dingdanhao.getText().toString().trim());
        map.put("createtime", ed_time.getText().toString().trim());
        map.put("link_name", ed_name.getText().toString().trim());
        map.put("link_phone", ed_phone.getText().toString().trim());
        if (ed_type.getText().toString().trim().equals("同城小件单")) {
            map.put("ordertype", "0");
        } else if (ed_type.getText().toString().trim().equals("同城搬家单")) {
            map.put("ordertype", "1");
        } else {
            map.put("ordertype", "2");
        }

        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.searchOrderByParam(data)
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

                            if (body.length() > 10) {
                                Gson gson1 = new Gson();
                                if (ed_type.getText().toString().trim().equals("同城小件单")){
                                    huoDiSearchList = gson1.fromJson(body, new TypeToken<List<HuoDiSearch>>() {
                                    }.getType());
                                }else if (ed_type.getText().toString().trim().equals("同城搬家单")){
                                    huoDiSearchList1 = gson1.fromJson(body, new TypeToken<List<HuoDiSearch1>>() {
                                    }.getType());
                                }else {
                                    huoDiSearchList2 = gson1.fromJson(body, new TypeToken<List<HuoDiSearch2>>() {
                                    }.getType());
                                }
                                Intent intent=new Intent(OrderSearchsActivity.this,SearchResultActivity.class);
//                                intent.putExtra("shuju", (Serializable) huoDiSearchList);
                                if (ed_type.getText().toString().trim().equals("同城小件单")) {
                                    intent.putExtra("type", "0");
                                    intent.putExtra("shuju", (Serializable) huoDiSearchList);
                                } else if (ed_type.getText().toString().trim().equals("同城搬家单")) {
                                    intent.putExtra("type", "1");
                                    intent.putExtra("shuju1", (Serializable) huoDiSearchList1);
                                } else {
                                    intent.putExtra("type", "2");
                                    intent.putExtra("shuju2", (Serializable) huoDiSearchList2);
                                }
                                startActivity(intent);
                            } else {
                                Toast.makeText(OrderSearchsActivity.this, "暂无搜索结果", Toast.LENGTH_SHORT).show();
                            }

                        } catch (IOException e) {
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


}
