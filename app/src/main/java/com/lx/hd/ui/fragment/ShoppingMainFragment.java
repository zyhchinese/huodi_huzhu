package com.lx.hd.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lx.hd.R;
import com.lx.hd.adapter.CarPartsAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.fragment.BaseFragment;
import com.lx.hd.bean.PageBean;
import com.lx.hd.bean.PrimaryBanner;
import com.lx.hd.bean.PrimaryNews;
import com.lx.hd.ui.activity.EtcActivity;
import com.lx.hd.ui.activity.GoodsDetialActivity;
import com.lx.hd.ui.activity.OrderCenterActivity;
import com.lx.hd.ui.activity.ShoppingActivity;
import com.lx.hd.ui.activity.ShoppingCartActivity;
import com.lx.hd.utils.DividerGridItemDecoration;
import com.lx.hd.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingMainFragment extends BaseFragment {
    List<String> list;
    List<PrimaryBanner> primaryBanner;
    private Banner banner;
    private ImageView glsc, gwc, menu, jfsc, shopping3, shopping4,xny_car;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shoppingmain_backup;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        banner = findView(R.id.banner);
        gwc = findView(R.id.gwc);
        menu = findView(R.id.menu);
        jfsc = findView(R.id.jfsc);
        xny_car=findView(R.id.xny_car);
        shopping3 = findView(R.id.shopping3);
        shopping4 = findView(R.id.shopping4);
        shopping3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("敬请期待");
            }
        });
        shopping4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showToast("敬请期待");
                Intent intent=new Intent(getActivity(), EtcActivity.class);
                startActivity(intent);
            }
        });
        gwc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ShoppingCartActivity.class));
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), OrderCenterActivity.class));
            }
        });
        jfsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shop = new Intent(getActivity(), ShoppingActivity.class);
                shop.putExtra("type", 2);
                startActivity(shop);
            }
        });
        xny_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shop = new Intent(getActivity(), ShoppingActivity.class);
                shop.putExtra("newcar", 2);
                startActivity(shop);
            }
        });
        LinearLayout indicator = (LinearLayout) banner.findViewById(com.youth.banner.R.id.circleIndicator);
        glsc = findView(R.id.msc);
        glsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shop = new Intent(getActivity(), ShoppingActivity.class);
                shop.putExtra("type", 1);
                startActivity(shop);
            }
        });
        ViewGroup viewParent = (ViewGroup) indicator.getParent();
//        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) viewParent.getLayoutParams();
//        linearParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        linearParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        viewParent.setLayoutParams(linearParams);
        loadBanner();

    }

    private void loadBanner() {

        PileApi.instance.getIndexImageWx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PrimaryBanner>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull List<PrimaryBanner> primaryBanners) {

                        list = new ArrayList<>();
                        primaryBanner = primaryBanners;
                        for (int i = 0; i < primaryBanners.size(); i++) {
                            String url = Constant.BASE_URL + primaryBanners.get(i).getFolder() + primaryBanners.get(i).getAutoname();
                            list.add(url);
                        }

                        //设置banner样式
                        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                        //设置图片加载器
                        banner.setImageLoader(new GlideImageLoader());
                        //设置图片集合
                        banner.setImages(list);
                        //设置banner动画效果
                        banner.setBannerAnimation(Transformer.Default);
                        //设置自动轮播，默认为true
                        banner.isAutoPlay(true);
                        //设置轮播时间
                        banner.setDelayTime(3000);
                        //设置指示器位置（当banner模式中有指示器时）
                        banner.setIndicatorGravity(BannerConfig.RIGHT);
                        //banner设置方法全部调用完毕时最后调用

                        banner.start();


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


//    private void loadNews() {
//        PileApi.instance.getNewsList(1, "8")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<PageBean<PrimaryNews>>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull final PageBean<PrimaryNews> activesBeanPageBean) {
//
//
//
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
}
