package com.lx.hd.ui.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.CarHomeListTopViewAdapter;
import com.lx.hd.adapter.CarPPAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.CarPP;
import com.lx.hd.bean.PinyinComparator;
import com.lx.hd.utils.CharToPY;
import com.lx.hd.widgets.MyGridView;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class CarHomeActivity extends BackCommonActivity {
    private ListView listview;
    // 汉字转换成拼音的类
    private CharToPY characterParser;
    private MyGridView mGridView;
    private SwipeRefreshLayout swiperereshlayout;
    private View mDialogView;
    private ImageView ss;
    AlertDialog dialog = null;
    private EditText etInputPwd;
    CarPPAdapter listx;
    private LinearLayout xdj;
    CarHomeListTopViewAdapter listtop;

    private LinearLayout showss;
    // 根据拼音来排列ListView里面的数据类
    private PinyinComparator pinyinComparator;

    @Override
    protected int getContentView() {
        return R.layout.activity_car_home;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("电车之家");
        // 实例化汉字转拼音类
        characterParser = CharToPY.getInstance();
        pinyinComparator = new PinyinComparator();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        listview = (ListView) findViewById(R.id.car_pp);
        swiperereshlayout = (SwipeRefreshLayout) findViewById(R.id.swiperereshlayout);
        mGridView = (MyGridView) findViewById(R.id.carhome_topview);
        mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_carhome_ss, null, false);
        ss = (ImageView) mDialogView.findViewById(R.id.ss_upload);
        etInputPwd = (EditText) mDialogView.findViewById(R.id.et_input_pwd);
        xdj = (LinearLayout) findViewById(R.id.xdj);
        xdj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CarHomeActivity.this, AskLowPriceActivity.class));
            }
        });
        showss = (LinearLayout) findViewById(R.id.wpss);
        builder.setView(mDialogView);
        dialog = builder.create();
//        List<CarPP> temp = new ArrayList<CarPP>();
//        temp.add(new CarPP("1", "宝马"));
//        temp.add(new CarPP("1", "大众"));
//        temp.add(new CarPP("1", "标志"));
//        temp.add(new CarPP("1", "奥迪"));
//        temp.add(new CarPP("1", "奔驰"));
//        temp.add(new CarPP("1", "本田"));
//        temp.add(new CarPP("1", "福特"));
//        temp.add(new CarPP("1", "五菱"));
//        temp.add(new CarPP("1", "宝骏"));
//        temp.add(new CarPP("1", "君越"));
//        temp.add(new CarPP("1", "雪弗兰"));
//        temp.add(new CarPP("1", "兰博基尼"));
//        temp.add(new CarPP("1", "布加迪威龙"));
//        temp.add(new CarPP("1", "桑塔纳"));
//        List<CarPP> temp1 = toPY(temp);
//        List<carhometopBean> temptop = new ArrayList<carhometopBean>();
//        temptop.add(new carhometopBean("1", "宝马"));
//        temptop.add(new carhometopBean("1", "大众"));
//        temptop.add(new carhometopBean("1", "标志"));
//        temptop.add(new carhometopBean("1", "宝马"));
//        temptop.add(new carhometopBean("1", "奥迪"));
//        temptop.add(new carhometopBean("1", "兰博基尼"));
//        temptop.add(new carhometopBean("1", "布加迪威龙"));
//        temptop.add(new carhometopBean("1", "宝骏"));
//        temptop.add(new carhometopBean("1", "五菱"));
//        temptop.add(new carhometopBean("1", "福特"));
        // 根据a-z进行排序源数据
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it1 = new Intent(CarHomeActivity.this, CarHome_XQActivity.class);
                it1.putExtra("id", listx.getItem(position).getId() + "");
                it1.putExtra("name", listx.getItem(position).getBrandname());
                startActivity(it1);
            }
        });

        showss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCarHomeListData(etInputPwd.getText().toString());
                etInputPwd.setText("");
                dialog.dismiss();

            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it1 = new Intent(CarHomeActivity.this, CarHome_XQActivity.class);
                it1.putExtra("id", listtop.getItem(position).getId() + "");
                it1.putExtra("name", listtop.getItem(position).getBrandname());
                startActivity(it1);
            }
        });

        swiperereshlayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light);
        //给swipeRefreshLayout绑定刷新监听
        swiperereshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //设置2秒的时间来执行以下事件
                getCarHomeListData();
                getTopCarHomeListData();
            }
        });
        getCarHomeListData();
        getTopCarHomeListData();

    }

    public List<CarPP> toPY(List<CarPP> data) {
        List<CarPP> temp = data;
        // 汉字转换成拼音
        if (temp != null && temp.size() > 0) {
            for (int i = 0; i < temp.size(); i++) {
                String pinyin = characterParser.getSelling(temp.get(i).getBrandname());
                String sortString = "";
                if (pinyin.length() > 0) {
                    sortString = pinyin.substring(0, 1).toUpperCase();
                }
                // 正则表达式，判断首字母是否是英文字母
                if (sortString.matches("[A-Z]")) {
                    temp.get(i).setIndex(sortString.toUpperCase());
                } else {
                    temp.get(i).setIndex("#");
                }

            }
        }
        return temp;
    }

    /**
     * 动态设置ListView的高度
     *
     * @param listView
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
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
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
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

    private void UpdateTopViewdata(List<CarPP> CarPP) {
        if (CarPP != null) {
//            CarPP = toPY(CarPP);
//            Collections.sort(CarPP, pinyinComparator);
            listtop = new CarHomeListTopViewAdapter(CarHomeActivity.this, CarPP);
            mGridView.setAdapter(listtop);
            setMyGridViewHeightBasedOnChildren(mGridView);
            //     listx.notifyDataSetChanged();

        }
    }

    private void updateInfo(List<CarPP> CarPP) {
        if (CarPP != null) {
            CarPP = toPY(CarPP);
            Collections.sort(CarPP, pinyinComparator);
            listx = new CarPPAdapter(CarHomeActivity.this, CarPP);
            listview.setAdapter(listx);
            setListViewHeightBasedOnChildren(listview);
            //     listx.notifyDataSetChanged();

        }
    }

    private void getCarHomeListData() {
        showWaitDialog("正在刷新信息...");
        HashMap<String, String> map = new HashMap<>();
        map.put("ishot", "");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.getCarHomeListData(params)
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
                                Gson gson = new Gson();
                                List<CarPP> CarPPList = gson.fromJson(body, new TypeToken<List<CarPP>>() {
                                }.getType());
                                updateInfo(CarPPList);
                            }
                            hideWaitDialog();
                            swiperereshlayout.setRefreshing(false);
                        } catch (IOException e) {
                            swiperereshlayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                        swiperereshlayout.setRefreshing(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void selectCarHomeListData(String brandname) {
        showWaitDialog("正在刷新信息...");
        HashMap<String, String> map = new HashMap<>();
        map.put("brandname", brandname);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.searchCarBrand(params)
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
                                showToast("暂无您要查找的品牌信息，请检查关键字是否有误");
                            } else {
                                Gson gson = new Gson();
                                List<CarPP> CarPPList = gson.fromJson(body, new TypeToken<List<CarPP>>() {
                                }.getType());
                                updateInfo(CarPPList);
                            }
                            hideWaitDialog();
                            swiperereshlayout.setRefreshing(false);
                        } catch (IOException e) {
                            swiperereshlayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                        swiperereshlayout.setRefreshing(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getTopCarHomeListData() {
        showWaitDialog("正在刷新信息...");
        HashMap<String, String> map = new HashMap<>();
        map.put("ishot", "1");
//        map.put("page", "1");
//        map.put("row", "10");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.getCarHomeListData(params)
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
                                showToast("暂无数据");
                            } else {
                                Gson gson = new Gson();
                                List<CarPP> CarPPList = gson.fromJson(body, new TypeToken<List<CarPP>>() {
                                }.getType());
                                UpdateTopViewdata(CarPPList);
                            }
                            hideWaitDialog();
                            swiperereshlayout.setRefreshing(false);
                        } catch (IOException e) {
                            swiperereshlayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                        swiperereshlayout.setRefreshing(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
