package com.lx.hd.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lx.hd.R;
import com.lx.hd.adapter.ShangChengLieBiaoAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.ShangChengEntity;
import com.lx.hd.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ShangChengActivity extends BackCommonActivity implements View.OnClickListener {
    private Banner banner;
    private TextView tv_fenlei_jiaju,tv_fenlei_bangongshi,tv_fenlei_huocheshipin;
    private TextView tv_fenlei,tv_gouwuche,tv_wode;
    private ImageView img_tehuichanpin,img_remaichanpin,img_cainixihuan;
    private RecyclerView act_recyclerView1,act_recyclerView2,act_recyclerView3;
    private LinearLayout linear1,linear2,linear3;
    private RelativeLayout gengduo1,gengduo2,gengduo3;
    private EditText ed_content;
    private TextView tv_sousuo;
    private ImageView img_saoma;
    private ShangChengEntity shangChengEntity;

    @Override
    protected int getContentView() {
        return R.layout.activity_shang_cheng11;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initView();

        initShuJu();
    }

    private void initView() {
        banner= (Banner) findViewById(R.id.banner);
        tv_fenlei_jiaju= (TextView) findViewById(R.id.tv_fenlei_jiaju);
        tv_fenlei_bangongshi= (TextView) findViewById(R.id.tv_fenlei_bangongshi);
        tv_fenlei_huocheshipin= (TextView) findViewById(R.id.tv_fenlei_huocheshipin);
        img_tehuichanpin= (ImageView) findViewById(R.id.img_tehuichanpin);
        img_remaichanpin= (ImageView) findViewById(R.id.img_remaichanpin);
        img_cainixihuan= (ImageView) findViewById(R.id.img_cainixihuan);
        act_recyclerView1= (RecyclerView) findViewById(R.id.act_recyclerView1);
        act_recyclerView2= (RecyclerView) findViewById(R.id.act_recyclerView2);
        act_recyclerView3= (RecyclerView) findViewById(R.id.act_recyclerView3);
        linear1= (LinearLayout) findViewById(R.id.linear1);
        linear2= (LinearLayout) findViewById(R.id.linear2);
        linear3= (LinearLayout) findViewById(R.id.linear3);
        gengduo1= (RelativeLayout) findViewById(R.id.gengduo1);
        gengduo2= (RelativeLayout) findViewById(R.id.gengduo2);
        gengduo3= (RelativeLayout) findViewById(R.id.gengduo3);
        tv_fenlei= (TextView) findViewById(R.id.tv_fenlei);
        tv_gouwuche= (TextView) findViewById(R.id.tv_gouwuche);
        tv_wode= (TextView) findViewById(R.id.tv_wode);
        ed_content= (EditText) findViewById(R.id.ed_content);
        tv_sousuo= (TextView) findViewById(R.id.tv_sousuo);
        img_saoma= (ImageView) findViewById(R.id.img_saoma);

        linear1.setOnClickListener(this);
        linear2.setOnClickListener(this);
        linear3.setOnClickListener(this);
        gengduo1.setOnClickListener(this);
        gengduo2.setOnClickListener(this);
        gengduo3.setOnClickListener(this);
        tv_fenlei.setOnClickListener(this);
        tv_gouwuche.setOnClickListener(this);
        tv_wode.setOnClickListener(this);
        tv_sousuo.setOnClickListener(this);
        img_saoma.setOnClickListener(this);
    }

    private void initShuJu() {
        PileApi.instance.selectMallIndex()
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

                            JSONObject jsonObject=new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")){
                                Gson gson=new Gson();
                                shangChengEntity = gson.fromJson(body, ShangChengEntity.class);

                                SharedPreferences sp=getSharedPreferences("fenlei", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor=sp.edit();
                                editor.putString("protypeid1", shangChengEntity.getResponse().get(0).getToptype().get(0).getId()+"");
                                editor.putString("protypeid2", shangChengEntity.getResponse().get(0).getToptype().get(1).getId()+"");
                                editor.putString("protypeid3", shangChengEntity.getResponse().get(0).getToptype().get(2).getId()+"");
                                editor.putString("proname1", shangChengEntity.getResponse().get(0).getToptype().get(0).getName());
                                editor.putString("proname2", shangChengEntity.getResponse().get(0).getToptype().get(1).getName());
                                editor.putString("proname3", shangChengEntity.getResponse().get(0).getToptype().get(2).getName());
                                editor.putString("proname", shangChengEntity.getResponse().get(0).getToptype().get(0).getName());
                                editor.apply();


                                initBanner();
                                initFenLei();
                                initLieBiao();

                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
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

    private void initLieBiao() {
        ShangChengLieBiaoAdapter adapter=new ShangChengLieBiaoAdapter(this,shangChengEntity.getResponse().get(0),1);
        GridLayoutManager manager=new GridLayoutManager(this,2){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        act_recyclerView1.setLayoutManager(manager);
        act_recyclerView1.setAdapter(adapter);
        adapter.setOnClickItem(new ShangChengLieBiaoAdapter.OnClickItem() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(ShangChengActivity.this,GoodsDetialActivity.class);
                intent.putExtra("proid",shangChengEntity.getResponse().get(0).getProdlistth().get(position).getId()+"");
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });

        ShangChengLieBiaoAdapter adapter1=new ShangChengLieBiaoAdapter(this,shangChengEntity.getResponse().get(0),2);
        GridLayoutManager manager1=new GridLayoutManager(this,2){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        act_recyclerView2.setLayoutManager(manager1);
        act_recyclerView2.setAdapter(adapter1);
        adapter1.setOnClickItem(new ShangChengLieBiaoAdapter.OnClickItem() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(ShangChengActivity.this,GoodsDetialActivity.class);
                intent.putExtra("proid",shangChengEntity.getResponse().get(0).getProdlistrm().get(position).getId()+"");
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });

        ShangChengLieBiaoAdapter adapter2=new ShangChengLieBiaoAdapter(this,shangChengEntity.getResponse().get(0),3);
        GridLayoutManager manager2=new GridLayoutManager(this,2){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        act_recyclerView3.setLayoutManager(manager2);
        act_recyclerView3.setAdapter(adapter2);
        adapter2.setOnClickItem(new ShangChengLieBiaoAdapter.OnClickItem() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(ShangChengActivity.this,GoodsDetialActivity.class);
                intent.putExtra("proid",shangChengEntity.getResponse().get(0).getProdlistcnxh().get(position).getId()+"");
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });

    }

    private void initFenLei() {
        tv_fenlei_jiaju.setText(shangChengEntity.getResponse().get(0).getToptype().get(0).getName());
        tv_fenlei_bangongshi.setText(shangChengEntity.getResponse().get(0).getToptype().get(1).getName());
        tv_fenlei_huocheshipin.setText(shangChengEntity.getResponse().get(0).getToptype().get(2).getName());
        linear1.setTag(shangChengEntity.getResponse().get(0).getToptype().get(0).getId()+"");
        linear2.setTag(shangChengEntity.getResponse().get(0).getToptype().get(1).getId()+"");
        linear3.setTag(shangChengEntity.getResponse().get(0).getToptype().get(2).getId()+"");
        String folder = shangChengEntity.getResponse().get(0).getCentpicth().get(0).getFolder();
        String autoname = shangChengEntity.getResponse().get(0).getCentpicth().get(0).getAutoname();
        Glide.with(this).load(Constant.BASE_URL+folder+autoname).into(img_tehuichanpin);
        String folder1 = shangChengEntity.getResponse().get(0).getCentpicrm().get(0).getFolder();
        String autoname1 = shangChengEntity.getResponse().get(0).getCentpicrm().get(0).getAutoname();
        Glide.with(this).load(Constant.BASE_URL+folder1+autoname1).into(img_remaichanpin);
        String folder2 = shangChengEntity.getResponse().get(0).getCentpiccnxh().get(0).getFolder();
        String autoname2 = shangChengEntity.getResponse().get(0).getCentpiccnxh().get(0).getAutoname();
        Glide.with(this).load(Constant.BASE_URL+folder2+autoname2).into(img_cainixihuan);

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (height / density);// 屏幕高度(dp)

        LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) img_tehuichanpin.getLayoutParams();
        double v = width / 720.00;
        layoutParams.height= (int) (v*340.00);
        img_tehuichanpin.setLayoutParams(layoutParams);
        img_remaichanpin.setLayoutParams(layoutParams);
        img_cainixihuan.setLayoutParams(layoutParams);

    }

    private void initBanner() {
        List<String> bannerList=new ArrayList<>();
        for (ShangChengEntity.ResponseBean.ToppicBean entity:shangChengEntity.getResponse().get(0).getToppic()){
            bannerList.add(Constant.BASE_URL+entity.getFolder()+entity.getAutoname());
        }
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(bannerList);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linear1:
                Intent intent=new Intent(this,ShangPinFenLeiActivity.class);
                intent.putExtra("protypeid1", (String) linear1.getTag());
                intent.putExtra("protypeid2", (String) linear2.getTag());
                intent.putExtra("protypeid3", (String) linear3.getTag());
                intent.putExtra("proname1", shangChengEntity.getResponse().get(0).getToptype().get(0).getName());
                intent.putExtra("proname2", shangChengEntity.getResponse().get(0).getToptype().get(1).getName());
                intent.putExtra("proname3", shangChengEntity.getResponse().get(0).getToptype().get(2).getName());

                intent.putExtra("proname", shangChengEntity.getResponse().get(0).getToptype().get(0).getName());
                startActivity(intent);
                finish();
                break;
            case R.id.linear2:
                Intent intent1=new Intent(this,ShangPinFenLeiActivity.class);
                intent1.putExtra("protypeid1", (String) linear1.getTag());
                intent1.putExtra("protypeid2", (String) linear2.getTag());
                intent1.putExtra("protypeid3", (String) linear3.getTag());
                intent1.putExtra("proname1", shangChengEntity.getResponse().get(0).getToptype().get(0).getName());
                intent1.putExtra("proname2", shangChengEntity.getResponse().get(0).getToptype().get(1).getName());
                intent1.putExtra("proname3", shangChengEntity.getResponse().get(0).getToptype().get(2).getName());

                intent1.putExtra("proname", shangChengEntity.getResponse().get(0).getToptype().get(1).getName());
                startActivity(intent1);
                finish();
                break;
            case R.id.linear3:
                Intent intent2=new Intent(this,ShangPinFenLeiActivity.class);
                intent2.putExtra("protypeid1", (String) linear1.getTag());
                intent2.putExtra("protypeid2", (String) linear2.getTag());
                intent2.putExtra("protypeid3", (String) linear3.getTag());
                intent2.putExtra("proname1", shangChengEntity.getResponse().get(0).getToptype().get(0).getName());
                intent2.putExtra("proname2", shangChengEntity.getResponse().get(0).getToptype().get(1).getName());
                intent2.putExtra("proname3", shangChengEntity.getResponse().get(0).getToptype().get(2).getName());

                intent2.putExtra("proname", shangChengEntity.getResponse().get(0).getToptype().get(2).getName());
                startActivity(intent2);
                finish();
                break;
            case R.id.gengduo1:
                Intent intent3=new Intent(this,ShangPinFenLei11Activity.class);
                intent3.putExtra("name","");
                intent3.putExtra("gengduoid","0");
                startActivity(intent3);
                break;
            case R.id.gengduo2:
                Intent intent4=new Intent(this,ShangPinFenLei11Activity.class);
                intent4.putExtra("name","");
                intent4.putExtra("gengduoid","1");
                startActivity(intent4);
                break;
            case R.id.gengduo3:
                Intent intent5=new Intent(this,ShangPinFenLei11Activity.class);
                intent5.putExtra("name","");
                intent5.putExtra("gengduoid","2");
                startActivity(intent5);
                break;

            case R.id.tv_fenlei:
                Intent intent6=new Intent(this,ShangPinFenLeiActivity.class);
                intent6.putExtra("protypeid1", (String) linear1.getTag());
                intent6.putExtra("protypeid2", (String) linear2.getTag());
                intent6.putExtra("protypeid3", (String) linear3.getTag());
                intent6.putExtra("proname1", shangChengEntity.getResponse().get(0).getToptype().get(0).getName());
                intent6.putExtra("proname2", shangChengEntity.getResponse().get(0).getToptype().get(1).getName());
                intent6.putExtra("proname3", shangChengEntity.getResponse().get(0).getToptype().get(2).getName());

                intent6.putExtra("proname", shangChengEntity.getResponse().get(0).getToptype().get(0).getName());
                startActivity(intent6);
                finish();
                break;
            case R.id.tv_gouwuche:
                Intent intent7=new Intent(this,ShoppingCartActivity.class);
                startActivity(intent7);
                finish();
                break;
            case R.id.tv_wode:
                Intent intent8=new Intent(this,OrderCenterActivity.class);
                startActivity(intent8);
                finish();
                break;
            case R.id.tv_sousuo:
                if (!ed_content.getText().toString().trim().equals("")){
                    Intent intent9=new Intent(this,ShangPinFenLei11Activity.class);
                    intent9.putExtra("name",ed_content.getText().toString().trim());
                    intent9.putExtra("gengduoid","");
                    startActivity(intent9);
                }else {
                    showToast("请输入商品名称");
                }

                break;
            case R.id.img_saoma:
                finish();
                break;
        }
    }
}
