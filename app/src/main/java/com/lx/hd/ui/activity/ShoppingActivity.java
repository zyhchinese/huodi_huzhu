package com.lx.hd.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.account.LoginActivity;
import com.lx.hd.adapter.CarPartsAdapter;
import com.lx.hd.adapter.MainAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.base.activity.BaseActivity;
import com.lx.hd.base.fragment.BaseFragment;
import com.lx.hd.bean.CarParts;
import com.lx.hd.bean.PageBean;
import com.lx.hd.bean.PrimaryBanner;
import com.lx.hd.bean.PrimaryNews;
import com.lx.hd.bean.ShopListBean;
import com.lx.hd.bean.ShopListType;
import com.lx.hd.bean.walletBean;
import com.lx.hd.ui.activity.GoodsDetialActivity;
import com.lx.hd.utils.DialogHelper;
import com.lx.hd.utils.DividerGridItemDecoration;
import com.lx.hd.utils.GlideImageLoader;
import com.lx.hd.widgets.toprecyclerview.ItemOrderIn;
import com.lx.hd.widgets.toprecyclerview.ItemOrderTop;
import com.lx.hd.widgets.toprecyclerview.MyAdapter;
import com.lx.hd.widgets.toprecyclerview.OrderContent;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingActivity extends BaseActivity {
    List<String> list;
    //    private Banner banner;
    private RecyclerView recyclerView;
    private ImageView gwc;
    PageBean<ShopListBean> activesBeanPageBean;
    List<OrderContent> orderContents = new ArrayList<OrderContent>();
    List<ItemOrderIn> orderIns = new ArrayList<ItemOrderIn>();
    ImageView img_back1;
    TextView tv_title1;
    private MyAdapter adapter;
    private boolean isLogin = false;
    private int type = 0;//类型  1=普通商城  2=积分商城
    private int newcar;


    @Override
    protected void initWidget() {
        super.initWidget();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        activesBeanPageBean = new PageBean<>();
        tv_title1 = (TextView) findViewById(R.id.tv_title1); //标题
        gwc = (ImageView) findViewById(R.id.gwc);
        img_back1 = (ImageView) findViewById(R.id.img_back1);
        img_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        type = getIntent().getIntExtra("type", 0);
        newcar = getIntent().getIntExtra("newcar", 1);

        if (type == 1) {
            tv_title1.setText("货滴商城");
        } else {
            tv_title1.setText("积分商城");
        }
        if (newcar == 2) {
            tv_title1.setText("新能源汽车");
        }
        gwc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //判断登录
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

                                        isLogin = true;

                                        //跳转
                                        startActivity(new Intent(ShoppingActivity.this, ShoppingCartActivity.class));
                                    } else {
                                        isLogin = false;
                                        DialogHelper.getConfirmDialog(ShoppingActivity.this, "温馨提示", "当前用户未登录，是否去登录", "去登录", "取消", true, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(ShoppingActivity.this, LoginActivity.class));
                                            }
                                        }, null).show();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                isLogin = false;
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
        loadType();
    }

    private void loadType() {
        PileApi.instance.getAllProductType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<ShopListType>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull final ArrayList<ShopListType> activesBeanPageBean1) {
                        if (activesBeanPageBean1.size() > 0) {
                            for (int i = 0; i < activesBeanPageBean1.size(); i++) {
                                if (type == 1) {
                                    if (!activesBeanPageBean1.get(i).getId().equals(552 + "")) {
                                        if (!activesBeanPageBean1.get(i).getId().equals(537 + "") && !activesBeanPageBean1.get(i).getId().equals(538 + "")) {
                                            loadShopList(activesBeanPageBean1.get(i));
                                        }
                                    }
                                } else if (type == 2) {
                                    if (activesBeanPageBean1.get(i).getId().equals(552 + "")) {
                                        loadShopList(activesBeanPageBean1.get(i));
                                    }
                                }
                                if (newcar == 2) {
                                    if (activesBeanPageBean1.get(i).getId().equals(537 + "") || activesBeanPageBean1.get(i).getId().equals(538 + "")) {
                                        loadShopList(activesBeanPageBean1.get(i));
                                    }
                                }
                            }
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


    private void loadShopList(final ShopListType ftype) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", ftype.getId());
        map.put("appstatus","0");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.getMallProduct(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            ResponseBody responseBody1 = responseBody;
                            String body = responseBody.string();
                            if (body.length() > 10) {
                                ArrayList<ShopListBean> datas = new Gson().fromJson(body, new TypeToken<ArrayList<ShopListBean>>() {
                                }.getType());
                                // orderContents.clear();
                                if (datas.size() > 0) {
                                    if (type == 1 || newcar == 2) {
                                        ItemOrderTop itemOrderTop = new ItemOrderTop(ftype.getProtypename());
                                        orderContents.add(itemOrderTop);
                                        type = 1;
                                    }
                                    ItemOrderIn orderIMiddle;
                                    for (int i = 0; i < datas.size(); i++) {
                                        datas.get(i).setSptype(type);
                                        datas.get(i).setType(ftype.getId());
                                        if (i + 1 < datas.size()) {
                                            datas.get(i + 1).setType(ftype.getId());
                                            orderIMiddle = new ItemOrderIn(datas.get(i), datas.get(i + 1));
                                            datas.get(i + 1).setSptype(type);
                                            i = i + 1;

                                        } else {
                                            orderIMiddle = new ItemOrderIn(datas.get(i), null);
                                        }
                                        orderContents.add(orderIMiddle);
                                        orderIns.add(orderIMiddle);
                                    }
                                    initRecyclerView();
                                    //adapter.updateList(orderContents);

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

    private void initRecyclerView() {

        adapter = new MyAdapter(ShoppingActivity.this, orderContents, orderIns);
        //MyAdapter2 adapter2 = new MyAdapter2(orderContents2);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ShoppingActivity.this, 1) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
//        adapter.setOnItemClickListener(new MyAdapter.OnItemClickLitener() {
//            @Override
//            public void onItemClick(int position, long itemId, ItemOrderIn orderContent) {
//                Intent itt = new Intent(ShoppingActivity.this, GoodsDetialActivity.class);
//                String test = orderContent.getSopBean().getId() + "";
//                itt.putExtra("proid", orderContent.getSopBean().getId());
//                startActivity(itt);
//            }
//        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_shopping_backup;
    }
}
