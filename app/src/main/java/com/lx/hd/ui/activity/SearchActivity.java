package com.lx.hd.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import com.lx.hd.R;
import com.lx.hd.base.activity.BackCommonActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SearchActivity extends BackCommonActivity {

    private TextView tvBeginTime, tvFinishTime, tvSearch;

    @Override
    protected int getContentView() {
        return R.layout.activity_search;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("搜索");
        final Calendar now = Calendar.getInstance();
        tvBeginTime = (TextView) findViewById(R.id.tv_time_begin);
        tvFinishTime = (TextView) findViewById(R.id.tv_time_finish);
        tvSearch = (TextView) findViewById(R.id.tv_confirm);
        tvBeginTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SearchActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tvBeginTime.setText(String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth));
                    }
                }, now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        tvFinishTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SearchActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tvFinishTime.setText(String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth));
                    }
                }, now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempBeginTime = tvBeginTime.getText().toString().trim();
                String tempFinishTime = tvFinishTime.getText().toString().trim();
                if (tempBeginTime.length() == 0 || tempFinishTime.length() == 0) {
                    showToast("请选择开始日期和结束日期");
                    return;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分
                java.util.Date stdate = null, eddate = null;
                try {
                    stdate = sdf.parse(tempBeginTime);
                    eddate = sdf.parse(tempFinishTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (stdate.getTime() > eddate.getTime()) {
                    showToast("结束日期不能大于开始日期！");
                    return;
                }
                Intent resultIntent = new Intent();
                resultIntent.putExtra("tempBeginTime", tempBeginTime);
                resultIntent.putExtra("tempFinishTime", tempFinishTime);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

}
