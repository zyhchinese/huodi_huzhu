package com.lx.hd.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.MyAdapter111;
import com.lx.hd.adapter.ProvinceAdapter111;
import com.lx.hd.adapter.RecyclerViewKuaiYunAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.CheckSiJiRenZhengEntity;
import com.lx.hd.bean.CheckSiJiRenZhengImgEntity;
import com.lx.hd.bean.HuoDKuaiYun_liebiao;
import com.lx.hd.bean.KuYunCheChangEntity;
import com.lx.hd.bean.ProvinceEntity;
import com.lx.hd.bean.cxbean;
import com.lx.hd.ui.activity.DriverCertificationActivity;
import com.lx.hd.ui.activity.DriverCertificationStateActivity;
import com.lx.hd.ui.activity.HuoDKuaiyunActivity;
import com.lx.hd.ui.activity.KuaiYun_XiangQingActivity;
import com.lx.hd.widgets.duoxuandialog.MyAdapter;
import com.lx.hd.widgets.duoxuandialog.ShuJuEntity;
import com.lx.hd.widgets.duoxuandialog.ShuJuEntity1;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static com.lx.hd.base.Constant.REQUEST_CODE;

/**
 * Created by 赵英辉 on 2018/7/10.
 */

public class HuoYuanKuaiYunFragment extends Fragment implements View.OnClickListener, OnRefreshListener, OnLoadmoreListener {


    private ImageView img_back;
    private RecyclerView act_recyclerView;//数据列表
    private SmartRefreshLayout smart;
    private TextView tv_chufadi,tv_mudidi,tv_chexing;
    private HuoDKuaiYun_liebiao huoDKuaiYun_liebiao;//实体类
    private HuoDKuaiYun_liebiao huoDKuaiYun_liebiao1;//实体类
    private RecyclerViewKuaiYunAdapter adapter;
    private String city = "";
    private String province11 = "";
    private int page=1;

    private int position;

    private List<ProvinceEntity> provinceEntityList;
    private List<ProvinceEntity> cityList;
    private List<ProvinceEntity> countyList;
    private RecyclerView recyclerView;
    private PopupWindow window;
    private int line=0;
    private String chufadisheng="",chufadishi="",chufadixian="",mudidisheng="",mudidishi="",mudidixian="";
    private ArrayList<cxbean> temp;
    private String che="";
    private List<CheckSiJiRenZhengEntity> checkSiJiRenZhengEntityList;
    private List<CheckSiJiRenZhengImgEntity> checkSiJiRenZhengImgEntityList;

    private ArrayList<ShuJuEntity> cexiangdatas;
    private ArrayList<ShuJuEntity> chechangdatas;
    private String yongcheleixing="2";
    private List<ShuJuEntity1> duoxuanlist1 = new ArrayList<>();
    private String chechang="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.huodikuaiyun1, container, false);

        city= Constant.HUOYUAN_SHI;
        province11=Constant.HUOYUAN_SHENG;
//        chufadishi=city;
        chufadisheng=province11;

        img_back= (ImageView) view.findViewById(R.id.go_back);
        img_back.setVisibility(View.INVISIBLE);
        act_recyclerView= (RecyclerView) view.findViewById(R.id.act_recyclerView);//列表数据
        smart= (SmartRefreshLayout) view.findViewById(R.id.smart);

        tv_chufadi= (TextView) view.findViewById(R.id.tv_chufadi);
        tv_mudidi= (TextView) view.findViewById(R.id.tv_mudidi);
        tv_chexing= (TextView) view.findViewById(R.id.tv_chexing);

        tv_chufadi.setOnClickListener(this);
        tv_mudidi.setOnClickListener(this);
        tv_chexing.setOnClickListener(this);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
            }
        });
        tv_chufadi.setText(province11);

        page=1;
        //加载快运数据
        initCarInfo();

        smart.setOnRefreshListener(this);
        smart.setOnLoadmoreListener(this);

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new ClassicsHeader(context);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context);//指定为经典Footer，默认是 BallPulseFooter
            }
        });
        smart.setDisableContentWhenRefresh(true);
        smart.setDisableContentWhenLoading(true);

        initChexing();
        loadcc();

        return view;

    }

    //加载快运数据
    private void initCarInfo() {
        HashMap map = new HashMap();
        map.put("city",province11);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.selectKuaiyunOrderList(page,10,params)
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

                            if (body.indexOf("false") != -1 || body.length() < 41) {
                                Toast.makeText(getContext(), "暂无订单信息", Toast.LENGTH_SHORT).show();
                                if (huoDKuaiYun_liebiao!=null&&adapter!=null){
                                    if (huoDKuaiYun_liebiao.getRows()!=null){
                                        huoDKuaiYun_liebiao.getRows().clear();
                                        adapter.notifyDataSetChanged();
                                    }
                                }

                            } else {
                                Gson gson = new Gson();
                                huoDKuaiYun_liebiao=gson.fromJson(body,HuoDKuaiYun_liebiao.class);

                                //加载列表
                                initRecyclerView();
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


    //快运详情
    private void initRecyclerView() {

        try {
            adapter=new RecyclerViewKuaiYunAdapter(getContext(),huoDKuaiYun_liebiao.getRows());
        } catch (Exception e) {
            e.printStackTrace();
        }
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext());
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
//        adapter.setOnClickItem(new RecyclerViewKuaiYunAdapter.OnClickItem() {
//            @Override
//            public void onClick(int position) {
//                Intent intent=new Intent(HuoDKuaiyun1Activity.this,KuaiYun_XiangQingActivity.class);
//                intent.putExtra("id",huoDKuaiYun_liebiao.getRows().get(position).getId()+"");
//                intent.putExtra("cs",city);
//                startActivity(intent);
//            }
//        });
        adapter.setOnClickItemHang(new RecyclerViewKuaiYunAdapter.OnClickItemHang() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(getContext(),KuaiYun_XiangQingActivity.class);
                intent.putExtra("id",huoDKuaiYun_liebiao.getRows().get(position).getId()+"");
                intent.putExtra("cs",city);
                startActivity(intent);

            }
        });
        adapter.setOnCallPhone(new RecyclerViewKuaiYunAdapter.OnCallPhone() {
            @Override
            public void onClick(int position) {
                HuoYuanKuaiYunFragment.this.position=position;
                String consigneephone=huoDKuaiYun_liebiao.getRows().get(position).getContactphone();
                AlertDialog.Builder builder11=new AlertDialog.Builder(getContext())
                        .setTitle("提示")
                        .setMessage("确定拨打电话："+consigneephone)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                testCallPhone();
                            }
                        });
                builder11.show();
            }
        });
    }


    private void testCallPhone() {

        if (Build.VERSION.SDK_INT >= 23) {

            //判断有没有拨打电话权限
            if (PermissionChecker.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                //请求拨打电话权限
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);

            } else {
                callPhone("0531-80969707");
            }

        } else {
            callPhone("0531-80969707");
        }
    }
    private void callPhone(String phoneNum) {
        String consigneephone=huoDKuaiYun_liebiao.getRows().get(position).getContactphone();
        //直接拨号
        Uri uri = Uri.parse("tel:" + consigneephone );
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        //此处不判断就会报错
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

            startActivity(intent);
        }
    }

    /**
     * 请求权限的回调方法
     *
     * @param requestCode  请求码
     * @param permissions  请求的权限
     * @param grantResults 权限的结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE && PermissionChecker.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            //ToastUtils.show(context, "授权成功");
            callPhone("0531-80969707");
        } else {
            Toast.makeText(getContext(), "您拒绝了电话申请权限", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
//                finish();
                break;
            case  R.id.tv_chufadi:
//                chufadisheng="";
//                chufadishi="";
//                chufadixian="";
                line=1;
                // 用于PopupWindow的View
                View contentView= LayoutInflater.from(getContext()).inflate(R.layout.shaixuanqiangdanliebiao, null, false);

                WindowManager windowManager = getActivity().getWindowManager();
                Display display = windowManager.getDefaultDisplay();

                // 创建PopupWindow对象，其中：
                // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
                // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
                window=new PopupWindow(contentView, display.getWidth(), (int) (display.getHeight()*0.8), true);
                // 设置PopupWindow的背景
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
                // 设置PopupWindow是否能响应外部点击事件
                window.setOutsideTouchable(true);
                // 设置PopupWindow是否能响应点击事件
                window.setTouchable(true);
                recyclerView= (RecyclerView) contentView.findViewById(R.id.recyclerView);
                TextView tv_queren= (TextView) contentView.findViewById(R.id.tv_queren);
                tv_queren.setVisibility(View.GONE);

                initsheng();

                // 显示PopupWindow，其中：
                // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
                window.showAsDropDown(tv_chufadi, 0, 0);
                // 或者也可以调用此方法显示PopupWindow，其中：
                // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
                // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
//                 window.showAtLocation(parent, gravity, x, y);
                break;
            case R.id.tv_mudidi:
//                mudidisheng="";
//                mudidishi="";
//                mudidixian="";
                line=2;
                // 用于PopupWindow的View
                View contentView11= LayoutInflater.from(getContext()).inflate(R.layout.shaixuanqiangdanliebiao, null, false);

                WindowManager windowManager11 = getActivity().getWindowManager();
                Display display11 = windowManager11.getDefaultDisplay();

                // 创建PopupWindow对象，其中：
                // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
                // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
                window=new PopupWindow(contentView11, display11.getWidth(), (int) (display11.getHeight()*0.8), true);
                // 设置PopupWindow的背景
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
                // 设置PopupWindow是否能响应外部点击事件
                window.setOutsideTouchable(true);
                // 设置PopupWindow是否能响应点击事件
                window.setTouchable(true);
                recyclerView= (RecyclerView) contentView11.findViewById(R.id.recyclerView);
                TextView tv_queren1= (TextView) contentView11.findViewById(R.id.tv_queren);
                tv_queren1.setVisibility(View.GONE);

                initsheng();

                // 显示PopupWindow，其中：
                // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
                window.showAsDropDown(tv_chufadi, 0, 0);
                // 或者也可以调用此方法显示PopupWindow，其中：
                // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
                // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
//                 window.showAtLocation(parent, gravity, x, y);
                break;
            case R.id.tv_chexing:
                line=3;
                // 用于PopupWindow的View
                View contentView22= LayoutInflater.from(getContext()).inflate(R.layout.dialog_item111, null, false);

                WindowManager windowManager22 = getActivity().getWindowManager();
                Display display22 = windowManager22.getDefaultDisplay();

                // 创建PopupWindow对象，其中：
                // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
                // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
                window=new PopupWindow(contentView22, display22.getWidth(), (int) (display22.getHeight()*0.8), true);
                // 设置PopupWindow的背景
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
                // 设置PopupWindow是否能响应外部点击事件
                window.setOutsideTouchable(true);
                // 设置PopupWindow是否能响应点击事件
                window.setTouchable(true);


//                recyclerView= (RecyclerView) contentView22.findViewById(R.id.recyclerView);
//                TextView tv_queren2= (TextView) contentView22.findViewById(R.id.tv_queren);
//                tv_queren2.setVisibility(View.VISIBLE);
//
//                initChexing();
//                tv_queren2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        che="";
//                        for (cxbean cx : temp) {
//                            if (cx.isType()){
//                                che=che+cx.getCartypename()+",";
//                            }
//                        }
//                        if (!che.equals("")){
//                            che=che.substring(0,che.length()-1);
//                            tv_chexing.setText(che);
//                        }else {
//                            tv_chexing.setText("车型");
//                        }
//                        page=1;
//                        initshaixuan();
//                        window.dismiss();
//                    }
//                });



                //初始化控件
                TextView tv_dismiss=(TextView) contentView22.findViewById(R.id.tv_dismiss);
                TextView tv_queren66=(TextView) contentView22.findViewById(R.id.tv_queren);
                final EditText ed_chechang= (EditText) contentView22.findViewById(R.id.ed_chechang);
                RecyclerView act_recyclerView1 = (RecyclerView) contentView22.findViewById(R.id.recyclerView1);
                RecyclerView act_recyclerView2 = (RecyclerView) contentView22.findViewById(R.id.recyclerView2);
                final TextView tv_yongcheleixing11= (TextView) contentView22.findViewById(R.id.tv_yongcheleixing11);
                final TextView tv_yongcheleixing22= (TextView) contentView22.findViewById(R.id.tv_yongcheleixing22);
                final TextView tv_yongcheleixing33= (TextView) contentView22.findViewById(R.id.tv_yongcheleixing33);

                if (yongcheleixing.equals("2")){
                    tv_yongcheleixing11.setBackgroundResource(R.drawable.bg_orovince_bg);
                    tv_yongcheleixing22.setBackgroundResource(R.drawable.bg_orovince_bg_f);
                    tv_yongcheleixing33.setBackgroundResource(R.drawable.bg_orovince_bg_f);
                    tv_yongcheleixing11.setTextColor(Color.parseColor("#ffffff"));
                    tv_yongcheleixing22.setTextColor(Color.parseColor("#666666"));
                    tv_yongcheleixing33.setTextColor(Color.parseColor("#666666"));
                }else if (yongcheleixing.equals("0")){
                    tv_yongcheleixing11.setBackgroundResource(R.drawable.bg_orovince_bg_f);
                    tv_yongcheleixing22.setBackgroundResource(R.drawable.bg_orovince_bg);
                    tv_yongcheleixing33.setBackgroundResource(R.drawable.bg_orovince_bg_f);
                    tv_yongcheleixing11.setTextColor(Color.parseColor("#666666"));
                    tv_yongcheleixing22.setTextColor(Color.parseColor("#ffffff"));
                    tv_yongcheleixing33.setTextColor(Color.parseColor("#666666"));
                }else if (yongcheleixing.equals("1")){
                    tv_yongcheleixing11.setBackgroundResource(R.drawable.bg_orovince_bg_f);
                    tv_yongcheleixing22.setBackgroundResource(R.drawable.bg_orovince_bg_f);
                    tv_yongcheleixing33.setBackgroundResource(R.drawable.bg_orovince_bg);
                    tv_yongcheleixing11.setTextColor(Color.parseColor("#666666"));
                    tv_yongcheleixing22.setTextColor(Color.parseColor("#666666"));
                    tv_yongcheleixing33.setTextColor(Color.parseColor("#ffffff"));
                }

                tv_yongcheleixing11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv_yongcheleixing11.setBackgroundResource(R.drawable.bg_orovince_bg);
                        tv_yongcheleixing22.setBackgroundResource(R.drawable.bg_orovince_bg_f);
                        tv_yongcheleixing33.setBackgroundResource(R.drawable.bg_orovince_bg_f);
                        tv_yongcheleixing11.setTextColor(Color.parseColor("#ffffff"));
                        tv_yongcheleixing22.setTextColor(Color.parseColor("#666666"));
                        tv_yongcheleixing33.setTextColor(Color.parseColor("#666666"));
                        yongcheleixing="2";
                    }
                });
                tv_yongcheleixing22.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv_yongcheleixing11.setBackgroundResource(R.drawable.bg_orovince_bg_f);
                        tv_yongcheleixing22.setBackgroundResource(R.drawable.bg_orovince_bg);
                        tv_yongcheleixing33.setBackgroundResource(R.drawable.bg_orovince_bg_f);
                        tv_yongcheleixing11.setTextColor(Color.parseColor("#666666"));
                        tv_yongcheleixing22.setTextColor(Color.parseColor("#ffffff"));
                        tv_yongcheleixing33.setTextColor(Color.parseColor("#666666"));
                        yongcheleixing="0";
                    }
                });
                tv_yongcheleixing33.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv_yongcheleixing11.setBackgroundResource(R.drawable.bg_orovince_bg_f);
                        tv_yongcheleixing22.setBackgroundResource(R.drawable.bg_orovince_bg_f);
                        tv_yongcheleixing33.setBackgroundResource(R.drawable.bg_orovince_bg);
                        tv_yongcheleixing11.setTextColor(Color.parseColor("#666666"));
                        tv_yongcheleixing22.setTextColor(Color.parseColor("#666666"));
                        tv_yongcheleixing33.setTextColor(Color.parseColor("#ffffff"));
                        yongcheleixing="1";
                    }
                });
                //初始化列表
                MyAdapter adapter = new MyAdapter(getContext(), chechangdatas, false);
                GridLayoutManager manager = new GridLayoutManager(getContext(), 4){
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
                act_recyclerView1.setLayoutManager(manager);
                act_recyclerView1.setAdapter(adapter);

                MyAdapter adapter1 = new MyAdapter(getContext(), cexiangdatas, false);
                GridLayoutManager manager1 = new GridLayoutManager(getContext(), 4){
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
                act_recyclerView2.setLayoutManager(manager1);
                act_recyclerView2.setAdapter(adapter1);
                //取消按钮的点击事件
                tv_dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        window.dismiss();
                        window = null;
                    }


                });
                //确认按钮的点击事件
                tv_queren66.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv_chexing.setText("车型");

                        if (yongcheleixing.equals("0")){
                            tv_chexing.setText("整车");
                        }else if (yongcheleixing.equals("1")){
                            tv_chexing.setText("零担");
                        }
                        //车长
                        duoxuanlist1.clear();
                        for (ShuJuEntity entity:chechangdatas){
                            duoxuanlist1.add(new ShuJuEntity1(entity.getId(),entity.getName(),entity.isType()));
                        }

                        for (ShuJuEntity1 temp : duoxuanlist1) {

                            if (temp.isType()) {
                                if (tv_chexing.getText().toString().equals("车型")) {
                                    tv_chexing.setText(temp.getName());

                                } else {
                                    tv_chexing.setText(tv_chexing.getText().toString() + "," + temp.getName());

                                }

                            }
                        }
                        if (!ed_chechang.getText().toString().trim().equals("")){
                            if (tv_chexing.getText().toString().equals("车型")){
                                tv_chexing.setText(ed_chechang.getText().toString().trim());
                            }else {
                                tv_chexing.setText(tv_chexing.getText().toString() + "," + ed_chechang.getText().toString().trim());
                            }

                        }

                        //赋值变量
                        if (yongcheleixing.equals("0")||yongcheleixing.equals("1")){
                            if (tv_chexing.getText().toString().length()==2){
                                chechang="";
                            }else {
                                chechang=tv_chexing.getText().toString().substring(3,tv_chexing.getText().toString().length());
                            }

                        }else {
                            chechang=tv_chexing.getText().toString();
                        }

                        //车型
                        duoxuanlist1.clear();
                        for (ShuJuEntity entity:cexiangdatas){
                            duoxuanlist1.add(new ShuJuEntity1(entity.getId(),entity.getName(),entity.isType()));
                        }
                        for (ShuJuEntity1 temp : duoxuanlist1) {

                            if (temp.isType()) {
                                if (tv_chexing.getText().toString().equals("车型")) {
                                    tv_chexing.setText(temp.getName());

                                } else {
                                    tv_chexing.setText(tv_chexing.getText().toString() + "," + temp.getName());

                                }

                            }
                        }

                        //赋值变量
                        che="";
                        for (ShuJuEntity1 temp : duoxuanlist1){
                            if (temp.isType()){
                                che=che+temp.getName()+",";
                            }
                        }
                        if (!che.equals("")){
                            che=che.substring(0,che.length()-1);
                        }

                        page=1;
                        initshaixuan();
                        window.dismiss();

                        window = null;
                    }
                });

                // 显示PopupWindow，其中：
                // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
                window.showAsDropDown(tv_chufadi, 0, 0);
                // 或者也可以调用此方法显示PopupWindow，其中：
                // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
                // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
//                 window.showAtLocation(parent, gravity, x, y);
                break;
        }
    }

    private void initshaixuan(){
        HashMap<String, String> map = new HashMap<>();
        if (chufadisheng.equals("全国")){
            map.put("sprovince", "");
        }else {
            map.put("sprovince", chufadisheng);
        }

        if (chufadishi.equals("全省")){
            map.put("scity", "");
        }else {
            map.put("scity", chufadishi);
        }
        if (chufadixian.equals("全市")){
            map.put("scounty", "");
        }else {
            map.put("scounty", chufadixian);
        }

        if (mudidisheng.equals("全国")){
            map.put("eprovince", "");
        }else {
            map.put("eprovince", mudidisheng);
        }

        if (mudidishi.equals("全省")){
            map.put("ecity", "");
        }else {
            map.put("ecity", mudidishi);
        }
        if (mudidixian.equals("全市")){
            map.put("ecounty", "");
        }else {
            map.put("ecounty", mudidixian);
        }

        map.put("cartypenames", che);
        if (yongcheleixing.equals("2")){
            map.put("use_car_type", "");
        }else {
            map.put("use_car_type", yongcheleixing);
        }

        if (chechang.equals("车型")){
            map.put("lengthnames", "");
        }else {
            map.put("lengthnames", chechang);
        }

        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.selectKuaiyunOrderListByParam(page,10,params)
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

                            if (body.indexOf("false") != -1 || body.length() < 41) {
                                Toast.makeText(getContext(), "暂无订单信息", Toast.LENGTH_SHORT).show();
                                if (huoDKuaiYun_liebiao!=null&&adapter!=null){
                                    if (huoDKuaiYun_liebiao.getRows()!=null){
                                        huoDKuaiYun_liebiao.getRows().clear();
                                        adapter.notifyDataSetChanged();
                                    }
                                }

                            }else {

                                Gson gson = new Gson();
                                huoDKuaiYun_liebiao=gson.fromJson(body,HuoDKuaiYun_liebiao.class);

                                //加载列表
                                initRecyclerView();
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

    private void initChexing(){
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
                            temp = new Gson().fromJson(body, new TypeToken<ArrayList<cxbean>>() {
                            }.getType());
                            if (temp.size() == 0) {
                                Toast.makeText(getContext(), "暂无数据", Toast.LENGTH_LONG).show();
                                return;
                            }

                            cexiangdatas = new ArrayList<ShuJuEntity>();
                            for (cxbean data : temp) {
                                cexiangdatas.add(new ShuJuEntity(Integer.parseInt(data.getId()), data.getCartypename(), false));
                            }
//                            initChexingtanchuang();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void loadcc() {
        PileApi.instance.searchVehicleLength()
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
                            System.out.println("body = " + body);
                            JSONObject jsonObject = new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")) {
                                KuYunCheChangEntity kuYunCheChangEntity = new Gson().fromJson(body, KuYunCheChangEntity.class);


                                if (kuYunCheChangEntity.getResponse().size() == 0) {
                                    Toast.makeText(getContext(), "暂无数据", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                chechangdatas = new ArrayList<ShuJuEntity>();
                                for (KuYunCheChangEntity.ResponseBean data : kuYunCheChangEntity.getResponse()) {
                                    chechangdatas.add(new ShuJuEntity(data.getId(), data.getLengthname(), false));
                                }
                            }

//                            showDuoXuanDiaLog(datas, false, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initChexingtanchuang(){
        MyAdapter111 adapter111=new MyAdapter111(getContext(),temp);
        GridLayoutManager manager=new GridLayoutManager(getContext(),4);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter111);
    }

    private void initsheng() {
        //请求省份
        PileApi.instance.loadProvince()
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

                            if (body.indexOf("false") != -1 || body.length() < 3) {
//                                showToast("获取信息失败，请重试");
                            } else {
                                Gson gson = new Gson();
                                provinceEntityList = gson.fromJson(body, new TypeToken<List<ProvinceEntity>>() {
                                }.getType());
                                provinceEntityList.add(0,new ProvinceEntity("全国"));

                                initRecyclerView111();
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

    private void initRecyclerView111() {
        ProvinceAdapter111 adapter111 = new ProvinceAdapter111(getContext(), provinceEntityList);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter111);
        adapter111.setOnClickItemChild(new ProvinceAdapter111.OnClickItemChild() {
            @Override
            public void onClick(int position,String name) {
                if (line==1){
                    chufadisheng=name;
                }else if (line==2){
                    mudidisheng=name;
                }

                if (!name.equals("全国")){
                    initShi(position);
                }else {
                    if (line==1){
                        chufadishi="";
                        chufadixian="";
                        tv_chufadi.setText(chufadisheng);
                    }else if (line==2){
                        mudidishi="";
                        mudidixian="";
                        tv_mudidi.setText(mudidisheng);
                    }

                    page=1;
                    initshaixuan();
                    window.dismiss();
                }


            }
        });

    }

    private void initShi(int id){
        HashMap<String, String> map = new HashMap<>();
        map.put("provinceid", id+"");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.loadCity(params)
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
                            if (body.indexOf("false") != -1 || body.length() < 2) {
//                                showToast("获取信息失败，请重试");
                            } else {


                                Gson gson = new Gson();
                                cityList = gson.fromJson(body, new TypeToken<List<ProvinceEntity>>() {
                                }.getType());
                                cityList.add(0,new ProvinceEntity("全省"));

                                ProvinceAdapter111 adapter111 = new ProvinceAdapter111(getContext(), cityList);
                                GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
                                recyclerView.setLayoutManager(manager);
                                recyclerView.setAdapter(adapter111);
                                adapter111.setOnClickItemChild(new ProvinceAdapter111.OnClickItemChild() {
                                    @Override
                                    public void onClick(int position,String name) {
                                        if (line==1){
                                            chufadishi=name;
                                        }else if (line==2){
                                            mudidishi=name;
                                        }

                                        if (!name.equals("全省")){
                                            initxian(position);
                                        }else {

                                            if (line==1){
                                                chufadixian="";
                                                tv_chufadi.setText(chufadisheng);
                                            }else if (line==2){
                                                mudidixian="";
                                                tv_mudidi.setText(mudidisheng);
                                            }

                                            page=1;
                                            initshaixuan();
                                            window.dismiss();
                                        }

                                    }
                                });



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

    private void initxian(int id){
        HashMap<String, String> map = new HashMap<>();
        map.put("cityid", id + "");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.loadCountry(params)
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
                            if (body.indexOf("false") != -1 || body.length() < 2) {
//                                showToast("获取信息失败，请重试");
                            } else {


                                Gson gson = new Gson();
                                countyList = gson.fromJson(body, new TypeToken<List<ProvinceEntity>>() {
                                }.getType());
                                countyList.add(0,new ProvinceEntity("全市"));

                                ProvinceAdapter111 adapter111 = new ProvinceAdapter111(getContext(), countyList);
                                GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
                                recyclerView.setLayoutManager(manager);
                                recyclerView.setAdapter(adapter111);
                                adapter111.setOnClickItemChild(new ProvinceAdapter111.OnClickItemChild() {
                                    @Override
                                    public void onClick(int position,String name) {
                                        if (line==1){
                                            chufadixian=name;
                                        }else if (line==2){
                                            mudidixian=name;
                                        }

                                        if (!name.equals("全市")){
                                            if (line==1){
                                                tv_chufadi.setText(chufadixian);
                                            }else if (line==2){
                                                tv_mudidi.setText(mudidixian);
                                            }
                                        }else {
                                            if (line==1){
                                                tv_chufadi.setText(chufadishi);
                                            }else if (line==2){
                                                tv_mudidi.setText(mudidishi);
                                            }
                                        }
                                        page=1;
                                        initshaixuan();

                                        window.dismiss();
                                    }
                                });

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

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page=1;
        if (line==0){
            initCarInfo();
        }else {
            initshaixuan();
        }

        if (smart.isRefreshing()){
            smart.finishRefresh();
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        if (line==0){
            HashMap map = new HashMap();
            map.put("city",province11);
            Gson gson = new Gson();
            String params = gson.toJson(map);
            PileApi.instance.selectKuaiyunOrderList(page,5,params)
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

                                if (body.indexOf("false") != -1 || body.length() < 41) {
                                    Toast.makeText(getContext(), "没有更多信息", Toast.LENGTH_SHORT).show();
//                                if (huoDKuaiYun_liebiao.getRows()!=null){
//                                    huoDKuaiYun_liebiao.getRows().clear();
//                                    adapter.notifyDataSetChanged();
//                                }
                                    if (smart.isLoading()){
                                        smart.finishLoadmore();
                                    }
                                } else {
                                    Gson gson = new Gson();
                                    huoDKuaiYun_liebiao1=gson.fromJson(body,HuoDKuaiYun_liebiao.class);
                                    huoDKuaiYun_liebiao.getRows().addAll(huoDKuaiYun_liebiao1.getRows());
                                    adapter.notifyDataSetChanged();
                                    if (smart.isLoading()){
                                        smart.finishLoadmore();
                                    }
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
        }else {
            HashMap<String, String> map = new HashMap<>();
            if (chufadisheng.equals("全国")){
                map.put("sprovince", "");
            }else {
                map.put("sprovince", chufadisheng);
            }

            if (chufadishi.equals("全省")){
                map.put("scity", "");
            }else {
                map.put("scity", chufadishi);
            }
            if (chufadixian.equals("全市")){
                map.put("scounty", "");
            }else {
                map.put("scounty", chufadixian);
            }

            if (mudidisheng.equals("全国")){
                map.put("eprovince", "");
            }else {
                map.put("eprovince", mudidisheng);
            }

            if (mudidishi.equals("全省")){
                map.put("ecity", "");
            }else {
                map.put("ecity", mudidishi);
            }
            if (mudidixian.equals("全市")){
                map.put("ecounty", "");
            }else {
                map.put("ecounty", mudidixian);
            }

            map.put("cartypenames", che);
            if (yongcheleixing.equals("2")){
                map.put("use_car_type", "");
            }else {
                map.put("use_car_type", yongcheleixing);
            }

            if (chechang.equals("车型")){
                map.put("lengthnames", "");
            }else {
                map.put("lengthnames", chechang);
            }
            Gson gson = new Gson();
            String params = gson.toJson(map);
            PileApi.instance.selectKuaiyunOrderListByParam(page,5,params)
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

                                if (body.indexOf("false") != -1 || body.length() < 41) {
                                    Toast.makeText(getContext(), "没有更多信息", Toast.LENGTH_SHORT).show();
//                                if (huoDKuaiYun_liebiao.getRows()!=null){
//                                    huoDKuaiYun_liebiao.getRows().clear();
//                                    adapter.notifyDataSetChanged();
//                                }
                                    if (smart.isLoading()){
                                        smart.finishLoadmore();
                                    }
                                } else {
                                    Gson gson = new Gson();
                                    huoDKuaiYun_liebiao1=gson.fromJson(body,HuoDKuaiYun_liebiao.class);
                                    huoDKuaiYun_liebiao.getRows().addAll(huoDKuaiYun_liebiao1.getRows());
                                    adapter.notifyDataSetChanged();
                                    if (smart.isLoading()){
                                        smart.finishLoadmore();
                                    }
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
    }
}
