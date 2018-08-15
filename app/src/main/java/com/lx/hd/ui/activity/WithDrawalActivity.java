package com.lx.hd.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.base.activity.BaseActivity;
import com.lx.hd.bean.WithDrawalBean;
import com.lx.hd.utils.DialogHelper;

import java.io.IOException;
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

public class WithDrawalActivity extends BaseActivity {
    private EditText tv_equipmentno, je, et_note;
    private String zd;
    private TextView tv_orderno, tx;

    protected RelativeLayout mTitleBar;
    protected ImageView imgBack;
    protected TextView tvTitle, tvStory;

    @Override
    protected int getContentView() {
        return R.layout.activity_with_drawal;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mTitleBar = (RelativeLayout) findViewById(R.id.lly_title_root);
        imgBack = (ImageView) findViewById(R.id.img_back1);
        tvTitle = (TextView) findViewById(R.id.tv_title1);
        tvStory = (TextView) findViewById(R.id.tv_story);
        tv_orderno = (TextView) findViewById(R.id.tv_orderno);
        tv_equipmentno = (EditText) findViewById(R.id.tv_equipmentno);
        je = (EditText) findViewById(R.id.je);
        et_note = (EditText) findViewById(R.id.et_note);
        tx = (TextView) findViewById(R.id.tx);
        tx.setSelected(true);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WithDrawalActivity.this, TiXianStoryActivity.class));
            }
        });
        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tempNote = et_note.getText().toString().trim();

                String orderno = tv_orderno.getText().toString().trim();
                if (orderno.length() == 0) {
                    showToast("申请人不能为空");
                    return;
                }
                String tv_equipmentno1 = tv_equipmentno.getText().toString().trim();
                if (tv_equipmentno1.length() == 0) {
                    showToast("卡号不能为空");
                    return;
                }
                String je1 = je.getText().toString().trim();
                if (je1.length() == 0) {
                    showToast("提现金额不能为空");
                    return;
                }
                if (Float.valueOf(je1) <= 0) {
                    showToast("提现金额不能小于或等于0");
                    return;
                }
                if (tempNote.length() == 0) {
                    showToast("提现备注不能为空");
                    return;
                }
                Pattern pattern = Pattern.compile("^\\d{16}|\\d{19}$");
                Matcher matcher = pattern.matcher(tv_equipmentno1);
                if (!matcher.matches()) {
                    showToast("请输入正确的银行卡号");
                    return;
                }
                if (Float.parseFloat(je.getText().toString()) > Float.parseFloat(zd)) {
                    DialogHelper.getConfirmDialog(WithDrawalActivity.this, "错误", "提现金额不能超过余额！请检查后重试。", "确定", "", false, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }, null).show();
                    return;
                } else {
                    addCashInfoInfo();
                }


            }
        });
        getCashInfoInfo();
    }


    private void updateInfo(WithDrawalBean bean) {
        if (bean != null) {
            if (!"".equals(bean.getCustname())) {
                tv_orderno.setText(bean.getCustname());
            }
            if (!"".equals(bean.getBankcard())) {
                tv_equipmentno.setText(bean.getBankcard());
            }
            if (!"".equals(bean.getBalance())) {
                je.setHint("你的最大提现额度为:¥" + bean.getBalance() + "元");
                zd = bean.getBalance();
            }
            if (!"".equals(bean.getNote())) {
                et_note.setText(bean.getNote());
            }
            if (bean.getIsvalid().equals("0")) {
                tx.setEnabled(false);
                tx.setSelected(false);
                DialogHelper.getConfirmDialog(WithDrawalActivity.this, "错误", "您已提交过审核,请等待审核结束", "确定", "", false, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }, null).show();
            }
        }

    }

    private void addCashInfoInfo() {
        showWaitDialog("提交中...");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("custname", tv_orderno.getText().toString());
        map.put("bankcard", tv_equipmentno.getText().toString());
        map.put("money", je.getText().toString());
        map.put("note", et_note.getText().toString());
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.addGetCashInfo(params)
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
                            if (body.indexOf("true") != -1) {
                                startActivity(new Intent(WithDrawalActivity.this, TiXianStoryActivity.class));
                                hideWaitDialog();
                                showToast("提现申请成功！");
                                finish();
                            } else {
                                showToast("提交申请失败,请重试");
                                hideWaitDialog();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showToast(e.toString());
                        hideWaitDialog();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getCashInfoInfo() {
        showWaitDialog("加载中...");
        PileApi.instance.getCashInfo()
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
                                showToast("获取信息失败，请登录后重试");
                            } else {
                                Gson gson = new Gson();
                                List<WithDrawalBean> WithDrawalList = gson.fromJson(body, new TypeToken<List<WithDrawalBean>>() {
                                }.getType());
                                updateInfo(WithDrawalList.get(0));

                            }
                            hideWaitDialog();
                        } catch (IOException e) {

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showToast(e.toString());
                        hideWaitDialog();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
