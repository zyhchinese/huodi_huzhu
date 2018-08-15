package com.lx.hd.ui.activity;
/*
物流发货
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.MapPoiListViewDialogAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BaseActivity;
import com.lx.hd.bean.CarrenTal_Cx;
import com.lx.hd.bean.Carrental_dz_y;
import com.lx.hd.bean.Lease_order_detailList;
import com.lx.hd.bean.mappoibean;
import com.lx.hd.bean.selectAllAreaBean;
import com.lx.hd.widgets.myListView;
import com.zaaach.citypicker.CityPickerActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class DeliverMapActivity extends BaseActivity implements SearchView.OnQueryTextListener, AMapLocationListener, LocationSource, PoiSearch.OnPoiSearchListener {
    private MapView mapView;
    private AMap aMap;
    private ImageView img_back, td;
    private TextView map_text, submit;
    private myListView menu;
    private TextView cs, sheng_text, shi_text, xian_text;
    private EditText ed_content;
    private LinearLayout linear_kuaiyun_addr;
    private RelativeLayout relative_xuanze_addr;
    private ImageView img_xuanze, img_ok;
    private TextView tv_bianji;
    private static final int REQUEST_CODE_PICK_CITY = 0;
    private LinearLayout menu_main, select_sheng, select_shi, select_xian, huoyun_select;
    private SearchView searchView;
    private AlertDialog alertDialog;
    private MapPoiListViewDialogAdapter adapter;
    private ArrayList<mappoibean> data = new ArrayList<mappoibean>();
    //声明AMapLocationClient类对象，定位发起端
    private AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象，定位参数
    public AMapLocationClientOption mLocationOption = null;
    //声明mListener对象，定位监听器
    private OnLocationChangedListener mListener = null;
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    private int type;
    private int type1;
    private PoiSearch mSearch;
    private ListView listView;
    private Double x = 0.0, y = 0.0;
    private String sx, sy;
    private String shengid, shiid, xianid;
    private String dwsheng = "", dwshi = "", dwxian = "";
    private boolean xuanzhong11 = true;
    private String morendizhi = "";

    @Override
    protected int getContentView() {
        return R.layout.activity_deliver_map;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView = (MapView) findViewById(R.id.map);
        select_sheng = (LinearLayout) findViewById(R.id.select_sheng);
        select_shi = (LinearLayout) findViewById(R.id.select_shi);
        select_xian = (LinearLayout) findViewById(R.id.select_xian);
        huoyun_select = (LinearLayout) findViewById(R.id.huoyun_select);
        sheng_text = (TextView) findViewById(R.id.sheng_text);
        shi_text = (TextView) findViewById(R.id.shi_text);
        xian_text = (TextView) findViewById(R.id.xian_text);
        map_text = (TextView) findViewById(R.id.map_text);
        submit = (TextView) findViewById(R.id.submit);
        searchView = (SearchView) findViewById(R.id.searchView);
        cs = (TextView) findViewById(R.id.cs);
        ed_content = (EditText) findViewById(R.id.ed_content);
        linear_kuaiyun_addr = (LinearLayout) findViewById(R.id.linear_kuaiyun_addr);
        relative_xuanze_addr = (RelativeLayout) findViewById(R.id.relative_xuanze_addr);
        img_xuanze = (ImageView) findViewById(R.id.img_xuanze);
        img_ok = (ImageView) findViewById(R.id.img_ok);
        tv_bianji = (TextView) findViewById(R.id.tv_bianji);
        mapView.onCreate(savedInstanceState);
        menu = (myListView) findViewById(R.id.menu);
        menu_main = (LinearLayout) findViewById(R.id.menu_main);
        listView = new ListView(this);
        img_back = (ImageView) findViewById(R.id.img_back);
        td = (ImageView) findViewById(R.id.td);
        //根据id-search_mag_icon获取ImageView
        int search_mag_icon_id = searchView.getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView mSearchViewIcon = (ImageView) searchView.findViewById(search_mag_icon_id);
        mSearchViewIcon.setImageResource(R.mipmap.ss_map2);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                finish();
            }
        });
        alertDialog = new AlertDialog.Builder(
                this).create();
        init();
        //起点终点的判断
        Intent intent = getIntent();
        String question = intent.getStringExtra("question");
        sy = sx = "";
        sx = intent.getStringExtra("x");
        sy = intent.getStringExtra("y");
        if ("qidian".equals(question)) {
            type = 0;
            td.setImageResource(R.mipmap.qd);
            submit.setText("确认起点");
            tv_bianji.setText("存为常用发货起点");
        } else if ("zhongdian".equals(question)){
            type = 1;
            td.setImageResource(R.mipmap.zd);
            submit.setText("确认终点");
            tv_bianji.setText("存为常用发货终点");
        }else {
            type = 10;
            td.setImageResource(R.mipmap.zd);
            submit.setText("确认看车地址");
        }
        //如果是快运或者速运，做出相关处理（省市县）
        if (intent.getStringExtra("type") != null) {
            if ("kuaiyun".equals(intent.getStringExtra("type"))) {
                huoyun_select.setVisibility(View.GONE);
                linear_kuaiyun_addr.setVisibility(View.VISIBLE);
                type1 = 3;
            } else if ("suyun".equals(intent.getStringExtra("type"))) {

                huoyun_select.setVisibility(View.VISIBLE);
                linear_kuaiyun_addr.setVisibility(View.GONE);
                type1 = 4;

            }
        }
        if (type1 == 4) {
            if (type == 0) {
                SharedPreferences sp = getSharedPreferences("shengshixian1", Context.MODE_PRIVATE);
                String shengname = sp.getString("shengname", "");
                String shiname = sp.getString("shiname", "");
                String xianname = sp.getString("xianname", "");
                String shengid = sp.getString("shengid", "");
                String shiid = sp.getString("shiid", "");
                String xianid = sp.getString("xianid", "");
                if (shengname.equals("")) {
                    sheng_text.setText("请选择");
                    shi_text.setText("请选择");
                    xian_text.setText("请选择");
                    DeliverMapActivity.this.shengid = "";
                    DeliverMapActivity.this.shiid = "";
                    DeliverMapActivity.this.xianid = "";
                } else {
                    sheng_text.setText(shengname);
                    shi_text.setText(shiname);
                    xian_text.setText(xianname);
                    DeliverMapActivity.this.shengid = shengid;
                    DeliverMapActivity.this.shiid = shiid;
                    DeliverMapActivity.this.xianid = xianid;
                }

            } else if (type == 1) {
                SharedPreferences sp = getSharedPreferences("shengshixian2", Context.MODE_PRIVATE);
                String shengname = sp.getString("shengname", "");
                String shiname = sp.getString("shiname", "");
                String xianname = sp.getString("xianname", "");
                String shengid = sp.getString("shengid", "");
                String shiid = sp.getString("shiid", "");
                String xianid = sp.getString("xianid", "");
                if (shengname.equals("")) {
                    sheng_text.setText("请选择");
                    shi_text.setText("请选择");
                    xian_text.setText("请选择");
                    DeliverMapActivity.this.shengid = "";
                    DeliverMapActivity.this.shiid = "";
                    DeliverMapActivity.this.xianid = "";
                } else {
                    sheng_text.setText(shengname);
                    shi_text.setText(shiname);
                    xian_text.setText(xianname);
                    DeliverMapActivity.this.shengid = shengid;
                    DeliverMapActivity.this.shiid = shiid;
                    DeliverMapActivity.this.xianid = xianid;
                }
            }
        }


        select_sheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getssx(sheng_text, 1, shengid);
            }
        });
        select_shi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shengid == null || shengid.equals("") || shengid.equals("null")) {
                    showToast("请先选择省份");
                } else {
                    getssx(shi_text, 2, shengid);
                }
            }
        });
        select_xian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shiid == null || shiid.equals("") || shiid.equals("null")) {
                    showToast("请先选择城市");
                } else {
                    getssx(xian_text, 3, shiid);
                }

            }
        });
        searchView.setOnQueryTextListener(this);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(DeliverMapActivity.this, CityPickerActivity.class),
                        REQUEST_CODE_PICK_CITY);

//                Intent intent = new Intent(DeliverMapActivity.this, ProvinceActivity.class);
//                startActivity(intent);
            }
        });

        /*
        * activity 回传
        * */
        submit.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (type1 == 4) {
                    if (sheng_text.getText().toString().equals("请选择")) {
                        Toast.makeText(DeliverMapActivity.this, "请选择省、市、区", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (shi_text.getText().toString().equals("请选择")) {
                        Toast.makeText(DeliverMapActivity.this, "请选择省、市、区", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (xian_text.getText().toString().equals("请选择")) {
                        Toast.makeText(DeliverMapActivity.this, "请选择省、市、区", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (type == 0) {
                        SharedPreferences sp = getSharedPreferences("shengshixian1", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("shengname", sheng_text.getText().toString());
                        editor.putString("shiname", shi_text.getText().toString());
                        editor.putString("xianname", xian_text.getText().toString());
                        editor.putString("shengid", shengid);
                        editor.putString("shiid", shiid);
                        editor.putString("xianid", xianid);
                        editor.apply();
                    } else if (type == 1) {
                        SharedPreferences sp = getSharedPreferences("shengshixian2", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("shengname", sheng_text.getText().toString());
                        editor.putString("shiname", shi_text.getText().toString());
                        editor.putString("xianname", xian_text.getText().toString());
                        editor.putString("shengid", shengid);
                        editor.putString("shiid", shiid);
                        editor.putString("xianid", xianid);
                        editor.apply();
                    }
                    SharedPreferences sp = getSharedPreferences("chengshi", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("city", shi_text.getText().toString());
                    editor.apply();
                }

                String myresult = map_text.getText().toString().trim();
                if (type1 == 3 || type1 == 4) {
                    myresult = sheng_text.getText().toString() + " " + shi_text.getText().toString() + " " + xian_text.getText().toString() + "\n" + myresult;

                }


                Intent intent = new Intent();
                aMap.getCameraPosition();
                intent.putExtra("myresuly", map_text.getText().toString().trim());
                intent.putExtra("ssx1", sheng_text.getText().toString() + " " + shi_text.getText().toString() + " " + xian_text.getText().toString());
                intent.putExtra("ssx", sheng_text.getText().toString() + "," + shi_text.getText().toString() + "," + xian_text.getText().toString());
                intent.putExtra("shengid", shengid);
                intent.putExtra("shiid", shiid);
                intent.putExtra("xianid", xianid);
                intent.putExtra("dwsheng", dwsheng);
                intent.putExtra("dwshi", dwshi);
                intent.putExtra("dwxian", dwxian);
                intent.putExtra("x", x + "");
                intent.putExtra("y", y + "");
                intent.putExtra("address", ed_content.getText().toString());

                intent.putExtra("morendizhi", morendizhi);
                if (type == 0) {
                    setResult(002, intent);
                } else {
                    if (type==10){
                        setResult(333,intent);
                    }else {
                        setResult(003, intent);
                    }

                }

                finish();
            }
        });

        relative_xuanze_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (xuanzhong11) {
                    xuanzhong11 = false;
                    morendizhi = "1";
//                    img_xuanze.setImageResource(R.mipmap.img_huodimorendizhit);
                    img_xuanze.setVisibility(View.VISIBLE);

                    img_ok.setVisibility(View.INVISIBLE);
                } else {
                    xuanzhong11 = true;
                    morendizhi = "2";
//                    img_xuanze.setImageResource(R.mipmap.img_huodimorendizhif);
                    img_xuanze.setVisibility(View.INVISIBLE);

                    img_ok.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    //重写onActivityResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                getLatlon(city);
                adapter = new MapPoiListViewDialogAdapter(DeliverMapActivity.this, new ArrayList<mappoibean>());
                menu.setAdapter(adapter);
                menu_main.setVisibility(View.GONE);
                searchView.setQuery("", false);
            }
        }
    }

    /**
     * 拉取省市县信息
     */
    public void getssx(final View v, final int type, final String position) {
        if (type == 1) {
            PileApi.instance.selectAllArea1()
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
                                ArrayList<selectAllAreaBean> temp = new Gson().fromJson(body, new TypeToken<ArrayList<selectAllAreaBean>>() {
                                }.getType());
                                if (temp.size() == 0) {
                                    Toast.makeText(DeliverMapActivity.this, "暂无数据", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                showdialog((TextView) v, temp, type);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Toast.makeText(DeliverMapActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } else if (type == 2) {
            HashMap<String, String> map = new HashMap<>();
            map.put("areaid", position);
            Gson gson = new Gson();
            String data = gson.toJson(map);
            PileApi.instance.selectAllArea2(data)
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
                                ArrayList<selectAllAreaBean> temp = new Gson().fromJson(body, new TypeToken<ArrayList<selectAllAreaBean>>() {
                                }.getType());
                                if (temp.size() == 0) {
                                    Toast.makeText(DeliverMapActivity.this, "暂无数据", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                showdialog((TextView) v, temp, type);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Toast.makeText(DeliverMapActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } else if (type == 3) {
            HashMap<String, String> map = new HashMap<>();
            map.put("areaid", position);
            Gson gson = new Gson();
            String data = gson.toJson(map);
            PileApi.instance.selectAllArea3(data)
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
                                ArrayList<selectAllAreaBean> temp = new Gson().fromJson(body, new TypeToken<ArrayList<selectAllAreaBean>>() {
                                }.getType());
                                if (temp.size() == 0) {
                                    Toast.makeText(DeliverMapActivity.this, "暂无数据", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                showdialog((TextView) v, temp, type);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Toast.makeText(DeliverMapActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        }


    }

    //弹框
    public void showdialog(final TextView name, final ArrayList<selectAllAreaBean> temp, final int type) {

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(DeliverMapActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                if (type == 2) {
                    if (DeliverMapActivity.this.type == 0) {
                        SharedPreferences sp = getSharedPreferences("chengshi", Context.MODE_PRIVATE);
                        String city = sp.getString("city", "");
                        if (!city.equals(temp.get(options1).getAreaname()) && !city.equals("")) {
                            Toast.makeText(DeliverMapActivity.this, "与终点不同城", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else if (DeliverMapActivity.this.type == 1) {
                        SharedPreferences sp = getSharedPreferences("chengshi", Context.MODE_PRIVATE);
                        String city = sp.getString("city", "");
                        if (!city.equals(temp.get(options1).getAreaname()) && !city.equals("")) {
                            Toast.makeText(DeliverMapActivity.this, "与起点不同城", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }


                //返回的分别是三个级别的选中位置
                name.setText(temp.get(options1).getAreaname());
                if (type == 1) {
                    shengid = temp.get(options1).getAreaid();
                    shiid = "";
                    xianid = "";
                    shi_text.setText("请选择");
                    xian_text.setText("请选择");
                } else if (type == 2) {
                    shiid = temp.get(options1).getAreaid();
                    xianid = "";
                    xian_text.setText("请选择");

                } else if (type == 3) {
                    xianid = temp.get(options1).getAreaid();

                }
//                if (temp != null) {
//                    Lease_order_detailList bean = mMarkerData.get(position);
//                    bean.setDuring_time(temp.get(options1).getDuring_time() + "");
//                    mMarkerData.set(position, bean);
//                    datas.get(position).setTime(options1Items.get(options1));
//                } else if (temp2 != null) {
//                    Lease_order_detailList bean = mMarkerData.get(position);
//                    bean.setModel_id(temp2.get(options1).getId() + "");
//                    mMarkerData.set(position, bean);
//                    datas.get(position).setCarname(options1Items.get(options1));
//                }
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
        ArrayList<String> temp2 = new ArrayList<String>();
        for (selectAllAreaBean bean : temp) {
            temp2.add(bean.getAreaname());
        }
        pvOptions.setPicker(temp2);
        pvOptions.show();
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        final GeocodeSearch geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {

            @Override
            public void onGeocodeSearched(GeocodeResult result, int rCode) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onRegeocodeSearched(RegeocodeResult result, int rCode) {

                String formatAddress = result.getRegeocodeAddress().getFormatAddress();
                dwsheng = result.getRegeocodeAddress().getProvince();
                dwshi = result.getRegeocodeAddress().getCity();
                dwxian = result.getRegeocodeAddress().getDistrict();
                Log.e("formatAddress", "formatAddress:" + formatAddress);
                Log.e("formatAddress", "rCode:" + rCode);
                if (rCode == 1000) {
//                    map_text.setText(formatAddress);

                    if (type1 == 3) {
                        map_text.setText(dwsheng + dwshi + dwxian);
                    } else {
                        map_text.setText(formatAddress);
                    }
                } else {
                    map_text.setText("正在获取地址信息...");
                }
            }
        });
        if (aMap == null) {
            aMap = mapView.getMap();
            // aMap.moveCamera(CameraUpdateFactory.zoomTo(0)); //设置缩放为0，则一进来就显示整个中国大陆
            aMap.setLocationSource(this);// 设置定位监听
            aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
            aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
            aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        }
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                LatLng target = cameraPosition.target;
                x = target.latitude;
                y = target.longitude;
                System.out.println(target.latitude + "jinjin------" + target.longitude);
                LatLonPoint lp = new LatLonPoint(target.latitude, target.longitude);
                RegeocodeQuery query = new RegeocodeQuery(lp, 200, GeocodeSearch.AMAP);
                geocoderSearch.getFromLocationAsyn(query);

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {

            }
        });
        aMap.getUiSettings().setZoomControlsEnabled(false);
        setMapCustomStyleFile(this);
        location(); //开始定位

        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                menu_main.setVisibility(View.GONE);
                searchView.setQuery("", false);
            }
        });
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        SearchView sv = (SearchView) findViewById(R.id.searchView);
        //为该SearchView组件设置事件监听器
        //sv.setOnQueryTextListener(this);
        // 设置该SearchView内默认显示的提示文本
        //sv.setQueryHint("哈雷实验室");
        if (sv != null) {
            try {        //--拿到字节码
                Class<?> argClass = sv.getClass();
                //--指定某个私有属性,mSearchPlate是搜索框父布局的名字
                Field ownField = argClass.getDeclaredField("mSearchPlate");
                //--暴力反射,只有暴力反射才能拿到私有属性
                ownField.setAccessible(true);
                View mView = (View) ownField.get(sv);
                //--设置背景
                mView.setBackgroundColor(Color.TRANSPARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    // 设置搜索文本监听
    @Override
    public boolean onQueryTextSubmit(String s) {
        // 实际应用中应该在该方法内执行实际查询
        // 此处仅使用Toast显示用户输入的查询内容
        //   Toast.makeText(MainActivity.this, "您的选择是:" + s, Toast.LENGTH_SHORT).show();
        return false;
    }

    // 当搜索内容改变时触发该方法
    @Override
    public boolean onQueryTextChange(String s) {
//        if (TextUtils.isEmpty(s)) {
//            // 清除ListView的过滤
//            mListView.clearTextFilter();
//        } else {
//            // 使用用户输入的内容对ListView的列表项进行过滤
//            mListView.setFilterText(s);
//        }
        if (s.isEmpty()) {
            menu_main.setVisibility(View.GONE);
        } else {
            onSearch(s, cs.getText().toString());

        }
        return false;
    }

    private void setMapCustomStyleFile(Context context) {
        String styleName = "style_json.json";
        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        String filePath = null;
        try {
            inputStream = context.getAssets().open(styleName);
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);

            filePath = context.getFilesDir().getAbsolutePath();
            File file = new File(filePath + "/" + styleName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            outputStream = new FileOutputStream(file);
            outputStream.write(b);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();

                if (outputStream != null)
                    outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        aMap.setCustomMapStylePath(filePath + "/" + styleName);
        aMap.showMapText(true);//地图标注
        aMap.setTrafficEnabled(false);//显示实时路况图层，aMap是地图控制器对象。
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
//        if (!Constant.city.equals("")) {
//            cs.setText(Constant.city);
//            getLatlon(Constant.city);
//        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        mLocationClient.stopLocation();//停止定位
        mLocationClient.onDestroy();//销毁定位客户端。
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                dwsheng = aMapLocation.getProvince();//省信息
                dwshi = aMapLocation.getCity();//城市信息
                dwxian = aMapLocation.getDistrict();//城区信息
                aMapLocation.getStreet();//街道信息
                aMapLocation.getStreetNum();//街道门牌号信息
                aMapLocation.getCityCode();//城市编码
                aMapLocation.getAdCode();//地区编码

                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                    isFirstLoc = false;
                    //点击定位按钮 能够将地图的中心移动到定位点
                    // mListener.onLocationChanged(aMapLocation);
                    //添加图钉
                    //  aMap.addMarker(getMarkerOptions(amapLocation));
                    //获取定位信息
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(aMapLocation.getCountry() + ""
                            + aMapLocation.getProvince() + ""
                            + aMapLocation.getCity() + ""
                            + aMapLocation.getProvince() + ""
                            + aMapLocation.getDistrict() + ""
                            + aMapLocation.getStreet() + ""
                            + aMapLocation.getStreetNum());
                    cs.setText(aMapLocation.getCity() + "");
                    if (sx != null && sy != null) {
                        //设置缩放级别
                        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                        //将地图移动到定位点
                        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(Double.parseDouble(sx), Double.parseDouble(sy))));
                    }
// else {
//                        getLatlon(aMapLocation.getCity() + "");
//                    }
//                    map_text.setText(buffer.toString());

                    if (type1 == 3) {
                        map_text.setText(dwsheng + dwshi + dwxian);
                    } else {
                        map_text.setText(buffer.toString());
                    }
                    //Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
                }


            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    private void location() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }


    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * POI搜索
     *
     * @param key  关键字
     * @param city 城市
     */
    public void onSearch(String key, String city) {
        //POI搜索条件
        PoiSearch.Query query = new PoiSearch.Query(key, "", city);
        query.setPageSize(10);
        query.setPageNum(0);
        query.setCityLimit(true);
        mSearch = new PoiSearch(DeliverMapActivity.this, query);
        //设置异步监听
        mSearch.setOnPoiSearchListener(this);
        //查询POI异步接口
        mSearch.searchPOIAsyn();
    }

    /**
     * 异步搜索回调
     */
    @Override
    public void onPoiSearched(PoiResult poiResult, int rCode) {
        // showToast("rCode:"+rCode);
        if (rCode == 1000 && poiResult != null) {
            // ArrayList<AddressBean> data = new ArrayList<AddressBean>();
            ArrayList<PoiItem> items = poiResult.getPois();
            if (items.size() > 0) {
                data.clear();
                for (PoiItem item : items) {
                    //获取经纬度对象
                    LatLonPoint llp = item.getLatLonPoint();
                    double lon = llp.getLongitude();
                    double lat = llp.getLatitude();
                    //获取标题
                    String title = item.getTitle();
                    //   showToast(title);
                    //获取内容vh
                    String text = item.getSnippet();
                    data.add(new mappoibean(lon, lat, title, text));
                }
                if (adapter == null) {
                    adapter = new MapPoiListViewDialogAdapter(DeliverMapActivity.this, data);
                } else {
                    adapter.setMarkerData(data);
                }
                menu_main.setVisibility(View.VISIBLE);
                menu.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                menu.setSelection(0);
                menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(data.get(position).getLat(), data.get(position).getLon())));
                        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
                        menu_main.setVisibility(View.GONE);
                        hideKeyboard();
                        //alertDialog.cancel();
                    }
                });

//                alertDialog.setView(listView);
//                alertDialog.show();
            } else {
                adapter = new MapPoiListViewDialogAdapter(DeliverMapActivity.this, new ArrayList<mappoibean>());
                menu.setAdapter(adapter);
                menu_main.setVisibility(View.GONE);
                searchView.setQuery("", false);
                showToast("暂无数据");

            }
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && this.getCurrentFocus() != null) {
            if (this.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    private void getLatlon(final String cityName) {

        GeocodeSearch geocodeSearch = new GeocodeSearch(DeliverMapActivity.this);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                if (i == 1000) {
                    if (geocodeResult != null && geocodeResult.getGeocodeAddressList() != null &&
                            geocodeResult.getGeocodeAddressList().size() > 0) {
                        cs.setText(cityName);
                        GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
                        double latitude = geocodeAddress.getLatLonPoint().getLatitude();//纬度
                        double longititude = geocodeAddress.getLatLonPoint().getLongitude();//经度
//                        dwsheng=geocodeAddress.getProvince();
//                        dwshi=geocodeAddress.getCity();
//                        dwxian=geocodeAddress.getDistrict();
                        String adcode = geocodeAddress.getAdcode();//区域编码
                        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(latitude, longititude)));
                        aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
                        Log.e("地理编码", geocodeAddress.getAdcode() + "");
                        Log.e("纬度latitude", latitude + "");
                        Log.e("经度longititude", longititude + "");

                    } else {
                        showNormalDialog("提示", "此城市位置无法锁定", true);
                    }
                }
            }
        });

        GeocodeQuery geocodeQuery = new GeocodeQuery(cityName.trim(), "29");
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery);


    }

    private void showNormalDialog(String title, String Message, boolean isfp) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(DeliverMapActivity.this);
        normalDialog.setTitle(title);
        normalDialog.setMessage(Message);
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        dialog.dismiss();
                    }
                });
//        normalDialog.setNegativeButton("关闭",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //...To-do
//                    }
//                });
        // 显示
        if (isfp) {

            normalDialog.setCancelable(false);

        } else {
            normalDialog.setCancelable(true);
        }
        normalDialog.show();
    }

}
