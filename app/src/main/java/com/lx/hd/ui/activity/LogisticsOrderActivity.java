package com.lx.hd.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.LogisticsFahuoAdapter;
import com.lx.hd.adapter.LogisticsJehuoAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.bean.QueryListEntity;
import com.lx.hd.bean.SiJiJieHuoEntity;
import com.lx.hd.bean.WuLiuFaHuoEntity;

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

public class LogisticsOrderActivity extends BackActivity implements View.OnClickListener {

    private ImageView img_search;
    private TextView tv_jiehuo, tv_fahuo;
    private RecyclerView act_recyclerView, act_recyclerView1;
    private CardView cardView1, cardView2;
    private List<WuLiuFaHuoEntity> wuLiuFaHuoEntityList;
    private List<SiJiJieHuoEntity> siJiJieHuoEntityList;
    private int position;


    @Override
    protected int getContentView() {
        return R.layout.activity_logistics_order;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("物流订单");
        showSearchIcon(true);
        setTitleIcon(R.mipmap.img_wuliudingdan);

        img_search = (ImageView) findViewById(R.id.img_search);
        tv_jiehuo = (TextView) findViewById(R.id.tv_jiehuo);
        tv_fahuo = (TextView) findViewById(R.id.tv_fahuo);
        act_recyclerView = (RecyclerView) findViewById(R.id.act_recyclerView);
        act_recyclerView1 = (RecyclerView) findViewById(R.id.act_recyclerView1);
        cardView1 = (CardView) findViewById(R.id.cardView1);
        cardView2 = (CardView) findViewById(R.id.cardView2);
        img_search.setImageResource(R.mipmap.img_search1);
        img_search.setOnClickListener(this);
        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        //请求物流发货单列表
        initwuliufahuo();

        //请求司机接货单列表
        initsijijiehuo();
    }

    private void initsijijiehuo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderno", "");
        map.put("createtime", "");
        map.put("owner_link_name", "");
        map.put("owner_link_phone", "");
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.searchfindorder(data)
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
                                tv_jiehuo.setVisibility(View.GONE);
                                cardView2.setVisibility(View.GONE);
                                showToast("暂无司机接货单");
                            } else {
                                Gson gson = new Gson();

                                siJiJieHuoEntityList = gson.fromJson(body, new TypeToken<List<SiJiJieHuoEntity>>() {
                                }.getType());


                                tv_jiehuo.setVisibility(View.VISIBLE);

                                if (siJiJieHuoEntityList.size() > 3) {
                                    cardView2.setVisibility(View.VISIBLE);
                                } else {
                                    cardView2.setVisibility(View.GONE);
                                }


                                //物流司机接货单列表
                                initRecyclerView1();

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

    private void initwuliufahuo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderno", "");
        map.put("createtime", "");
        map.put("owner_link_name", "");
        map.put("owner_link_phone", "");
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.searchsendorder(data)
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
                                tv_fahuo.setVisibility(View.GONE);
                                cardView1.setVisibility(View.GONE);
                                showToast("暂无发货单");
                            } else {
                                Gson gson = new Gson();
                                wuLiuFaHuoEntityList = gson.fromJson(body, new TypeToken<List<WuLiuFaHuoEntity>>() {
                                }.getType());

                                tv_fahuo.setVisibility(View.VISIBLE);

                                if (wuLiuFaHuoEntityList.size() > 3) {
                                    cardView1.setVisibility(View.VISIBLE);
                                } else {
                                    cardView1.setVisibility(View.GONE);

                                }

                                //物流发货单列表
                                initRecyclerView();

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

    //物流司机接货单列表
    private void initRecyclerView1() {
        LogisticsJehuoAdapter adapter = new LogisticsJehuoAdapter(this, siJiJieHuoEntityList, 1);
        LinearLayoutManager manager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        act_recyclerView1.setLayoutManager(manager);
        act_recyclerView1.setAdapter(adapter);
        adapter.setOnClickItem(new LogisticsJehuoAdapter.OnClickItem() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(LogisticsOrderActivity.this, LogisticsOrderJieHuoDetailsActivity.class);
                intent.putExtra("id", siJiJieHuoEntityList.get(position).getId()+"");
                startActivity(intent);
            }
        });
        adapter.setOnClickItem1(new LogisticsJehuoAdapter.OnClickItem1() {
            @Override
            public void onClick(int position) {
                LogisticsOrderActivity.this.position=position;
                testCallPhone();
            }
        });
    }

    //物流发货单列表
    private void initRecyclerView() {
        LogisticsFahuoAdapter adapter = new LogisticsFahuoAdapter(this, wuLiuFaHuoEntityList, 1);
        LinearLayoutManager manager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        adapter.setOnClickItem(new LogisticsFahuoAdapter.OnClickItem() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(LogisticsOrderActivity.this, LogisticsOrderDetailsActivity.class);
                intent.putExtra("orderno", wuLiuFaHuoEntityList.get(position).getOwner_orderno());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_search:
                Intent intent = new Intent(this, OrderSearchsActivity.class);
                intent.putExtra("type", "1");
                startActivity(intent);

                break;
            case R.id.cardView1:
                Intent intent1 = new Intent(this, FahuoOrderActivity.class);
                startActivity(intent1);
                break;
            case R.id.cardView2:
                Intent intent2 = new Intent(this, JiHuoOrderActivity.class);
                startActivity(intent2);
                break;
        }
    }

    private void testCallPhone() {

        if (Build.VERSION.SDK_INT >= 23) {

            //判断有没有拨打电话权限
            if (PermissionChecker.checkSelfPermission(LogisticsOrderActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                //请求拨打电话权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);

            } else {
                callPhone("0531-80969707");
            }

        } else {
            callPhone("0531-80969707");
        }
    }
    private void callPhone(String phoneNum) {
        //直接拨号
        Uri uri = Uri.parse("tel:" + siJiJieHuoEntityList.get(position).getOwner_link_phone());
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        //此处不判断就会报错
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

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

        if (requestCode == REQUEST_CODE && PermissionChecker.checkSelfPermission(LogisticsOrderActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            //ToastUtils.show(context, "授权成功");
            callPhone("0531-80969707");
        } else {
            Toast.makeText(this, "您拒绝了电话申请权限", Toast.LENGTH_SHORT).show();
        }
    }
}
