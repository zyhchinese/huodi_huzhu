package com.lx.hd.ui.activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.ErShouChePinPaiEntity;
import com.lx.hd.bean.ErShouCheShouYeXiangQingEntity;
import com.lx.hd.bean.QiTaYueQianDaoXinXiEntity;
import com.lx.hd.bean.QianDaoXinXiEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class QianDaoActivity extends BackCommonActivity {

    private CalendarView calendarView;
    private ImageView img_jian,img_jia;
    private TextView tv_nianyue;
    private Button btn_qiandao;
    private TextView jf,tv_tianshu,tv_tianshu11;
    private QianDaoXinXiEntity qianDaoXinXiEntity;


    @Override
    protected int getContentView() {
        return R.layout.activity_qian_dao;
    }


    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("积分签到");

        calendarView= (CalendarView) findViewById(R.id.calendarView);
        img_jian= (ImageView) findViewById(R.id.img_jian);
        img_jia= (ImageView) findViewById(R.id.img_jia);
        tv_nianyue= (TextView) findViewById(R.id.tv_nianyue);
        btn_qiandao= (Button) findViewById(R.id.btn_qiandao);
        jf= (TextView) findViewById(R.id.jf);
        tv_tianshu= (TextView) findViewById(R.id.tv_tianshu);
        tv_tianshu11= (TextView) findViewById(R.id.tv_tianshu11);

        tv_nianyue.setText(calendarView.getCurYear()+"年"+calendarView.getCurMonth()+"月");
        calendarView.setWeeColor(0xFFF8F8F8,0xFFff3636);


        initSheZhi();


    }

    private void initSheZhi() {
        calendarView.setOnMonthChangeListener(new com.haibin.calendarview.CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                tv_nianyue.setText(year+"年"+month+"月");
                initBianHuaShuJu(year+""+month,year,month);
            }
        });

        img_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.scrollToPre();
            }
        });

        img_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.scrollToNext();
            }
        });

        btn_qiandao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initQianDao();
            }
        });

//        List<Calendar> schemes = new ArrayList<>();
//        int year = calendarView.getCurYear();
//        int month = calendarView.getCurMonth();

        initShuJu();



//        schemes.add(getSchemeCalendar(year, month, 3, 0xFFff3636, "假"));
//        schemes.add(getSchemeCalendar(year, month, 6, 0xFFff3636, "事"));
//        schemes.add(getSchemeCalendar(year, month, 9, 0xFFff3636, "议"));
//        schemes.add(getSchemeCalendar(year, month, 13, 0xFFff3636, "记"));
//        schemes.add(getSchemeCalendar(year, month, 14, 0xFFff3636, "记"));
//        schemes.add(getSchemeCalendar(year, month, 15, 0xFFff3636, "假"));
//        schemes.add(getSchemeCalendar(year, month, 18, 0xFFff3636, "记"));
//        schemes.add(getSchemeCalendar(year, month, 25, 0xFFff3636, "假"));


        /*
         * 此方法现在弃用，但不影响原来的效果，原因：数据量大时 size()>10000 ，遍历性能太差，超过Android限制的16ms响应，造成卡顿
         * 现在推荐使用 setSchemeDate(Map<String, Calendar> mSchemeDates)，Map查找性能非常好，经测试，50000以上数据，1ms解决
         */
//        calendarView.setSchemeDate(schemes);

    }

    private void initBianHuaShuJu(String s, final int year, final int month) {
        HashMap<String, String> map = new HashMap<>();
        map.put("yearmonth", s);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.selectSigninByMonth(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull ResponseBody responseBody) {


                        try {
                            String body = responseBody.string();
                            System.out.println(body);
                            JSONObject jsonObject = new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")) {
                                Gson gson = new Gson();
                                QiTaYueQianDaoXinXiEntity qiTaYueQianDaoXinXiEntity=gson.fromJson(body,QiTaYueQianDaoXinXiEntity.class);

                                List<Integer> integerList=new ArrayList<>();
                                for (int i = 0; i < qiTaYueQianDaoXinXiEntity.getResponse().size(); i++) {
                                    integerList.add(Integer.parseInt(qiTaYueQianDaoXinXiEntity.getResponse().get(i).getCreate_time().substring(8,10)));
                                }

                                List<Calendar> schemes = new ArrayList<>();
//                                int year = calendarView.getCurYear();
//                                int month = calendarView.getCurMonth();
                                boolean isok=true;
                                for (int i = 1; i < 32; i++) {
                                    isok=true;
                                    for (int j = 0; j < integerList.size(); j++) {
                                        if (i==integerList.get(j)){
                                            isok=false;
                                            schemes.add(getSchemeCalendar(year, month, i, 0xFFff3636, "假"));
                                        }
                                    }
                                    if (isok){
                                        schemes.add(getSchemeCalendar(year, month, i, 0xFFCACACA, "事"));
                                    }
                                }
                                calendarView.setSchemeDate(schemes);
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initQianDao() {
        PileApi.instance.addSignin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull ResponseBody responseBody) {


                        try {
                            String body = responseBody.string();
                            System.out.println(body);
                            JSONObject jsonObject = new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")) {
                                showToast("签到成功");
                                btn_qiandao.setText("已签到");
                                btn_qiandao.setBackgroundResource(R.drawable.jifenqiandao_anniu_bg_hui);
                                initShuJu();
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initShuJu() {
        PileApi.instance.selectSigninIndex()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull ResponseBody responseBody) {


                        try {
                            String body = responseBody.string();
                            System.out.println(body);
                            JSONObject jsonObject = new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")) {
                                Gson gson = new Gson();
                                qianDaoXinXiEntity=gson.fromJson(body,QianDaoXinXiEntity.class);

                                initFuZhu();

                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Toast.makeText(QianDaoActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initFuZhu() {
        if (qianDaoXinXiEntity.getResponse().get(0).getSigninFlag()==0){
            btn_qiandao.setText("立即签到");
            btn_qiandao.setBackgroundResource(R.drawable.jifenqiandao_anniu_bg);
        }else {
            btn_qiandao.setText("已签到");
            btn_qiandao.setBackgroundResource(R.drawable.jifenqiandao_anniu_bg_hui);
        }
        jf.setText(qianDaoXinXiEntity.getResponse().get(0).getUserScores());
        tv_tianshu.setText(qianDaoXinXiEntity.getResponse().get(0).getContinuitySignin());
        tv_tianshu11.setText(qianDaoXinXiEntity.getResponse().get(0).getSetSigninDay());
        List<Integer> integerList=new ArrayList<>();
        for (int i = 0; i < qianDaoXinXiEntity.getResponse().get(0).getSigninList().size(); i++) {
            integerList.add(qianDaoXinXiEntity.getResponse().get(0).getSigninList().get(i).getCreate_time().getDate());
        }

        List<Calendar> schemes = new ArrayList<>();
        int year = calendarView.getCurYear();
        int month = calendarView.getCurMonth();
        boolean isok=true;
        for (int i = 1; i < 32; i++) {
            isok=true;
            for (int j = 0; j < integerList.size(); j++) {
                if (i==integerList.get(j)){
                    isok=false;
                    schemes.add(getSchemeCalendar(year, month, i, 0xFFff3636, "假"));
                }
            }
            if (isok){
                schemes.add(getSchemeCalendar(year, month, i, 0xFFCACACA, "事"));
            }
        }
        calendarView.setSchemeDate(schemes);
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }
}
