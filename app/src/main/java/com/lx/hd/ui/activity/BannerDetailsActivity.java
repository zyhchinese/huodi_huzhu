package com.lx.hd.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.ImgAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BaseActivity;
import com.lx.hd.bean.BannerDetailsEntity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

import static com.lx.hd.R.id.ed_phone;

public class BannerDetailsActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_yuyue, btn_top;
    private RecyclerView act_recyclerView;
    private ScrollView scrollView;
    private WebView webView;
    private List<BannerDetailsEntity> detailsEntityList;

    private int year, month, day;
    private TextView textView1;
    private EditText editText;
    private EditText editText1;
    private ImageView img2;
    private AlertDialog dialog = null;
    private String proid;
    private int i;
    private int n;
    private boolean isOk;
    private String date;
    private String date1;

    private int i1;
    private int i2;
    private int i3;
    private int i4;
    private int i5;
    private int i6;


    @Override
    protected int getContentView() {
        return R.layout.activity_banner_details;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        Intent intent = getIntent();
        proid = intent.getStringExtra("proid");
        btn_yuyue = (Button) findViewById(R.id.btn_yuyue);
        btn_top = (Button) findViewById(R.id.btn_top);
        img2 = (ImageView) findViewById(R.id.img2);
        act_recyclerView = (RecyclerView) findViewById(R.id.act_recyclerView);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        webView = (WebView) findViewById(R.id.tv_note);
        initpanduan();

        initView();

        btn_top.setOnClickListener(this);
        btn_yuyue.setOnClickListener(this);
        img2.setOnClickListener(this);

    }

    private void initpanduan() {
        HashMap<String, String> map = new HashMap<>();
        map.put("proid", proid);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.isApply(params)
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


                            if (body.indexOf("false") != -1 || body.length() < 10) {
                                showToast("获取信息失败，请重试");
                            } else {
                                body = body.substring(8, body.length() - 2);
                                i = Integer.parseInt(body);
                                System.out.println(body);


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

    private void initView() {
        HashMap<String, String> map = new HashMap<>();
        map.put("proid", proid);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.getProductDeatilBanner(params)
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
                            if (body.indexOf("false") != -1 || body.length() < 10) {
                                showToast("此车暂无试驾！");
                            } else {
                                Gson gson = new Gson();
                                detailsEntityList = gson.fromJson(body, new TypeToken<List<BannerDetailsEntity>>() {
                                }.getType());

                                ImgAdapter adapter = new ImgAdapter(BannerDetailsActivity.this, detailsEntityList);
                                LinearLayoutManager manager = new LinearLayoutManager(BannerDetailsActivity.this);
                                act_recyclerView.setLayoutManager(manager);
                                act_recyclerView.setAdapter(adapter);
//                                webView.loadDataWithBaseURL(Constant.BASE_URL, getNewContent(detailsEntityList.get(0).getNote()), "text/html", "utf-8", null);

                                webView.loadDataWithBaseURL(Constant.BASE_URL, getNewContent(detailsEntityList.get(0).getNote()), "text/html", "utf-8", null);

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

    public static String getNewContent(String htmltext) {
        try {
//            Document doc = Jsoup.parse(htmltext);
//            Elements elements = doc.getElementsByTag("img");
//            for (Element element : elements) {
//                element.attr("width", "100%").attr("height", "auto");
//            }
//            Elements elements1 = doc.getElementsByTag("p");
//            for (Element element : elements1) {
//                element.attr("word-wrap", "break-word");
//            }
            htmltext = htmltext + "<style type=\"text/css\"> img {width:100%;height:auto;}p {word-wrap:break-word;}</style>";
            return htmltext;
        } catch (Exception e) {
            return htmltext;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_top:
                scrollView.scrollTo(0, 0);
                break;
            case R.id.btn_yuyue:


                if (i == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    View view = LayoutInflater.from(this).inflate(R.layout.loyout_reservation, null, false);
                    builder.setView(view);
                    dialog = builder.create();

                    TextView textView = (TextView) view.findViewById(R.id.tv_car);
                    textView.setText(detailsEntityList.get(0).getSpecification());

                    editText = (EditText) view.findViewById(R.id.ed_name);
                    editText1 = (EditText) view.findViewById(ed_phone);


                    //时间
                    textView1 = (TextView) view.findViewById(R.id.btn_time);
                    textView1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            date();
                            dateDialog();

                        }
                    });

                    initEditText();

                    Button button = (Button) view.findViewById(R.id.btn_save);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (n > 2 && !textView1.getText().toString().equals("请选您的到店时间")) {
//                                if (i1>=i4){
//                                    if (i2>=i5){
//                                        isOk = true;
//                                    }
//                                }
                                if (i1 == i4) {
                                    if (i2 == i5) {
                                        if (i3 >= i6) {
                                            isOk = true;
                                        } else {
                                            Toast.makeText(BannerDetailsActivity.this, "你选择的时间有误，请重新选择", Toast.LENGTH_SHORT).show();
                                        }
                                    } else if (i2 > i5 && i2 - 1 == i5 && i3 <= i6) {
                                        isOk = true;
                                    } else {
                                        Toast.makeText(BannerDetailsActivity.this, "你选择的时间有误，请重新选择", Toast.LENGTH_SHORT).show();
                                    }
                                } else if (i1 > i4 && i1 - 1 == i4 && i5 == 12 && i2 == 1 && i3 <= i6) {
                                    isOk = true;
                                } else {
                                    Toast.makeText(BannerDetailsActivity.this, "你选择的时间有误，请重新选择", Toast.LENGTH_SHORT).show();
                                }
                            }
                            if (!isOk) {

                                if (n == 1) {
                                    Toast.makeText(BannerDetailsActivity.this, "请输入您的姓名", Toast.LENGTH_SHORT).show();
                                } else if (n == 2) {
                                    Toast.makeText(BannerDetailsActivity.this, "请输入您的正确联系方式", Toast.LENGTH_SHORT).show();
                                } else if (textView1.getText().toString().equals("请选您的到店时间")) {
                                    Toast.makeText(BannerDetailsActivity.this, "请选您的到店时间", Toast.LENGTH_SHORT).show();
                                }
//                                else if (i1<i4){
//                                    Toast.makeText(BannerDetailsActivity.this, "你选择的时间有误，请重新选择", Toast.LENGTH_SHORT).show();
//                                }else if (i2<i5){
//                                    Toast.makeText(BannerDetailsActivity.this, "你选择的时间有误，请重新选择", Toast.LENGTH_SHORT).show();
//                                }
                            } else {

                                HashMap<String, String> map = new HashMap<>();
                                map.put("id", proid);
                                map.put("unname", editText.getText().toString());
                                map.put("unphone", editText1.getText().toString());
                                map.put("createtime", textView1.getText().toString());

                                Gson gson = new Gson();
                                final String data = gson.toJson(map);
                                PileApi.instance.addApply(data)
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
                                                    if (body.indexOf("false") != -1 || body.length() < 6) {
                                                        dialog.dismiss();
                                                        showToast("您选择的时间错误，请重试");
                                                    } else {
                                                        dialog.dismiss();
                                                        Toast.makeText(BannerDetailsActivity.this, "申请成功", Toast.LENGTH_SHORT).show();
                                                        initpanduan();

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
                    });

                    dialog.show();
                } else {
                    Toast.makeText(this, "此车已经预约，请选择别的车辆", Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.img2:
                finish();
                break;
        }
    }

    private void initEditText() {
        n = 1;
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                n = 1;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                n = 2;
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().indexOf(" ") != -1) {
                    n = 1;
                    isOk = false;
                } else {
                    editText1.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            n = 2;
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            n = 3;
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                            boolean phone = phone(s.toString());

                            if (s.toString().indexOf(" ") != -1 || !phone) {
                                n = 2;


                                isOk = false;
                            } else {
                                n = 3;
                            }

                        }
                    });
                }


            }
        });
    }


    private boolean phone(String mobiles) {
        Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    private void dateDialog() {
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                date = String.format("%d-%d-%d", year, month + 1, dayOfMonth);
                textView1.setText(date);

                String[] split = date.split("-");
                String s1 = split[0];
                i1 = Integer.parseInt(s1);
                String s2 = split[1];
                i2 = Integer.parseInt(s2);
                String s3 = split[2];
                i3 = Integer.parseInt(s3);
                System.out.println("+++++++++" + s1 + "+++" + s2 + "++++" + s3);


            }
        }, year, month, day).show();
    }

    private void date() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        date1 = String.format("%d-%d-%d", year, month + 1, day);
        String[] split = date1.split("-");
        String s4 = split[0];
        i4 = Integer.parseInt(s4);
        String s5 = split[1];
        i5 = Integer.parseInt(s5);
        String s6 = split[2];
        i6 = Integer.parseInt(s6);
        System.out.println("+++++++++" + s4 + "+++" + s5 + "++++" + s6);


    }

}
