package com.lx.hd.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.account.LoginActivity;
import com.lx.hd.adapter.ShoppingCart_Adapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.base.fragment.BaseRecyclerAdapter;
import com.lx.hd.bean.GoodsBean;
import com.lx.hd.bean.ProlistBean;
import com.lx.hd.bean.QueryListEntity;
import com.lx.hd.bean.ShopListBean;
import com.lx.hd.bean.ShopListType;
import com.lx.hd.bean.UploadShoplistBean;
import com.lx.hd.bean.carhometopBean;
import com.lx.hd.interf.onShopPingListener;
import com.lx.hd.utils.DialogHelper;
import com.lx.hd.widgets.myListView;
import com.lx.hd.widgets.toprecyclerview.ItemOrderIn;
import com.lx.hd.widgets.toprecyclerview.ItemOrderTop;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 购物车
 * Created by TB on 2017/9/30.
 */

public class ShoppingCartActivity extends BackCommonActivity implements onShopPingListener {
    private myListView ls;
    ArrayList<GoodsBean> temp = new ArrayList<GoodsBean>();
    private RelativeLayout qsbg;
    private TextView yx, zje;
    private TextView zje1;
    private ImageView qx, zwsj;
    private Button upload;
    private TextView dw;
    private TextView tv_shouye,tv_fenlei,tv_wode;
    private boolean qxzt = false;//当前是否全选
    int xzsl = 0; //被选择的数量
    float sumjg = 0.00f; //总价格
    float sumjg1 = 0.00f; //总积分价格
    int type;
    private DecimalFormat decimalFormat;
    private String proids="";

    @Override
    protected int getContentView() {
        return R.layout.activity_shopping_cart;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("购物车");
        decimalFormat=new DecimalFormat("###0.00");
        ls = (myListView) findViewById(R.id.listview);
        yx = (TextView) findViewById(R.id.yx);
        zje = (TextView) findViewById(R.id.zje);
        zwsj = (ImageView) findViewById(R.id.zwsj);
        qx = (ImageView) findViewById(R.id.qx);
        dw = (TextView) findViewById(R.id.dw);
        qsbg = (RelativeLayout) findViewById(R.id.qxbg);
        zje1= (TextView) findViewById(R.id.zje1);
        tv_shouye= (TextView) findViewById(R.id.tv_shouye);
        tv_shouye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShoppingCartActivity.this,ShangChengActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tv_fenlei= (TextView) findViewById(R.id.tv_fenlei);
        tv_fenlei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp=getSharedPreferences("fenlei", Context.MODE_PRIVATE);
                Intent intent=new Intent(ShoppingCartActivity.this,ShangPinFenLeiActivity.class);
                intent.putExtra("protypeid1", sp.getString("protypeid1",""));
                intent.putExtra("protypeid2", sp.getString("protypeid2",""));
                intent.putExtra("protypeid3", sp.getString("protypeid3",""));
                intent.putExtra("proname1", sp.getString("proname1",""));
                intent.putExtra("proname2", sp.getString("proname2",""));
                intent.putExtra("proname3", sp.getString("proname3",""));

                intent.putExtra("proname", sp.getString("proname",""));
                startActivity(intent);
                finish();
            }
        });
        tv_wode= (TextView) findViewById(R.id.tv_wode);
        tv_wode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShoppingCartActivity.this,OrderCenterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        upload = (Button) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                islogin();
                proids="";
                if (xzsl > 0) {
                    UploadShoplistBean data = new UploadShoplistBean();
                    data.setTotalcount(xzsl);
                    data.setTotalmoney(sumjg);
                    data.setTotaljifen(sumjg1);
                    ArrayList<ProlistBean> tempdata = new ArrayList<ProlistBean>();
                    if (temp.size() > 0 && temp != null) {
                        boolean isok = false;
                        for (GoodsBean goods : temp) {
                            if (goods.isok()) {
                                if (!isok) {
                                    type = goods.getType();//取得第一个type
                                    isok = true;
                                    data.setType(type);
                                }

                                if (goods.getType() != type) {
                                    showToast("积分商品与普通商品不能同时结算!");
                                    return;
                                }
                                tempdata.add(new ProlistBean((float) goods.getPrice() * goods.getCount(), goods.getProname(), goods.getProid(), goods.getFitcar(), goods.getColor(), goods.getCount(), (float) goods.getPrice(),(float) goods.getPrice_jf()));
                                proids=proids+goods.getProid()+",";
                            }
                        }
                    }
                    if (tempdata.size() > 0) {
                        data.setProList(tempdata);
                        addMyOrder(data);


                    } else {
                        showToast("请先选择您要结算的商品!");
                    }
                } else {
                    showToast("请先选择您要结算的商品!");
                }
            }
        });
        qx.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {

                                      islogin();
                                      sumjg = 0.00f;
                                      sumjg1=0.00f;
                                      xzsl = 0;
                                      if (temp != null && temp.size() != 0) {
                                          if (qxzt) {
                                              qxzt = false;
                                              qx.setImageResource(R.mipmap.gwc_wxz);

                                          } else {
                                              qxzt = true;
                                              qx.setImageResource(R.mipmap.bg_radio_select);

                                          }
                                          for (GoodsBean data : temp) {
                                              data.setIsok(qxzt);
                                              if (data.isok()) {
                                                  xzsl = xzsl + 1;
                                                  sumjg = (float) (sumjg + (data.getPrice() * data.getCount()));
                                                  sumjg1 = (float) (sumjg1 + (data.getPrice_jf() * data.getCount()));
                                              }
                                          }
                                          yx.setText("已选" + xzsl + "件商品");
                                          String format = decimalFormat.format(sumjg);
                                          String format1 = decimalFormat.format(sumjg1);
                                          zje.setText(format + "");
                                          zje1.setText(format1 + "");
                                          setdatainfo(temp);
                                      }
                                  }
                              }

        );
//        ShoppingCart_Adapter A = new ShoppingCart_Adapter(this, temp);
//        ls.setAdapter(A);
        loadShopList();

    }

    private void setdatainfo(ArrayList<GoodsBean> datas) {
        temp = datas;
        ShoppingCart_Adapter A = new ShoppingCart_Adapter(ShoppingCartActivity.this, datas);
        ls.setAdapter(A);
        A.notifyDataSetChanged();
    }

    private void loadShopList() {
        PileApi.instance.searchShoppingCart()
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
                                //     showToast("暂无更多");
                                qsbg.setVisibility(View.GONE);
                                yx.setText("已选0件商品");
                                zje.setText("0.00");
                                zje1.setText("0.00");
                                sumjg = 0.00f;
                                sumjg1 = 0.00f;
                                zwsj.setVisibility(View.VISIBLE);
                                setdatainfo(new ArrayList<GoodsBean>());
                            } else {
                                qsbg.setVisibility(View.VISIBLE);
                                zwsj.setVisibility(View.GONE);
                                ArrayList<GoodsBean> datas = new Gson().fromJson(body, new TypeToken<ArrayList<GoodsBean>>() {
                                }.getType());
                                if (datas.size() > 0) {
                                    setdatainfo(datas);
                                }
                            }


                        } catch (IOException e) {
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

    //删除商品
    private void deleteShoppingCart(String id) {
        showWaitDialog("提交信息中...");
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.deleteShoppingCart(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {

//                        try {
                        try {
                            String body = responseBody.string();
                            body = body.replace("\"", "");
                            if ("false".equals(body)) {
                                showToast("提交失败，请重试");


                            } else {
                                loadShopList();
//                                Intent it = new Intent(GoodsDetialActivity.this, ConfirmOrderActivity.class);
//                                it.putExtra("uuid", body);
//                                startActivity(it);
                            }
                            hideWaitDialog();
                            //
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

    //提交商品订单
    private void addMyOrder(UploadShoplistBean upload) {
        showWaitDialog("提交信息中...");
        proids=proids.substring(0,proids.length()-1);
        Gson gson = new Gson();
        HashMap<String, String> map = new HashMap<>();
        map.put("table", "mall_pro_order");
        map.put("totalmoney", upload.getTotalmoney() + "");
        map.put("totaljifen", upload.getTotaljifen() + "");
        map.put("totalcount", upload.getTotalcount() + "");
        String proList = gson.toJson(upload.getProList());
        map.put("proList", proList);
        map.put("type", type + "");
        map.put("carproids", proids);
        String params = gson.toJson(map);
        params = params.replace("\\", "");
        params = params.replace("\"[", "[");
        params = params.replace("]\"", "]");
        PileApi.instance.addMyOrder(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {

//                        try {
                        try {
                            String body = responseBody.string();
                            body = body.replace("\"", "");
                            if ("false".equals(body) || "".equals(body) || body == null) {
                                showToast("提交失败，请重试");
                            } else {

                                //提交成功后的操作---------------------------------------------------------------------------------------------------------------------------------->>>
                                Intent it = new Intent(ShoppingCartActivity.this, ConfirmOrderActivity.class);
                                it.putExtra("uuid", body);
                                it.putExtra("type", type + "");
                                startActivity(it);
                            }
                            hideWaitDialog();
                            //
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

    @Override
    public void OnListener() {
        //多选的回调

        if (temp != null && temp.size() > 0) {
            sumjg = 0.00f;
            sumjg1 = 0.00f;
            xzsl = 0;
            boolean isok = false;
            for (GoodsBean goods : temp) {
                if (goods.isok()) {
                    xzsl = xzsl + 1;
                    sumjg = (float) (sumjg + (goods.getPrice() * goods.getCount()));
                    sumjg1 = (float) (sumjg1 + (goods.getPrice_jf() * goods.getCount()));
                    if (!isok) {
                        type = goods.getType();//取得第一个type
                        isok = true;
                    }
                }
            }
            if (temp.size()==xzsl){
                qxzt = true;
                qx.setImageResource(R.mipmap.bg_radio_select);
            }else {
                qxzt = false;
                qx.setImageResource(R.mipmap.gwc_wxz);
            }

            yx.setText("已选" + xzsl + "件商品");
            String format = decimalFormat.format(sumjg);
            String format1 = decimalFormat.format(sumjg1);
            zje.setText(format + "");
            zje1.setText(format1 + "");
            if (type == 1) {
                dw.setText("元");
            } else {
                dw.setText("积分");
            }
        }

    }

    private void islogin() {
        PileApi.instance.mCheckLoginState()
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
                            if (body.equals("\"true\"")) {
                            } else {
                                DialogHelper.getConfirmDialog(ShoppingCartActivity.this, "温馨提示", "当前用户未登录，是否去登录", "去登录", "取消", true, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(ShoppingCartActivity.this, LoginActivity.class));
                                    }
                                }, null).show();
                            }
                        } catch (IOException e) {
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

    @Override
    protected void onResume() {
        super.onResume();
        islogin();
        loadShopList();

        yx.setText("已选0件商品");
        zje.setText("0.00");
        zje1.setText("0.00");
        sumjg = 0.00f;
        sumjg1 = 0.00f;
    }

    @Override
    public void Ondelete(final String id) {
        //删除的回调


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ShoppingCartActivity.this)
                .setTitle("提示")
                .setMessage("确定要删除吗")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteShoppingCart(id);
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
