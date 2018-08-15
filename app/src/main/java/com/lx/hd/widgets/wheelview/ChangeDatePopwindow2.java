package com.lx.hd.widgets.wheelview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.widgets.wheelview.adapter.AbstractWheelTextAdapter1;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Author:  Chen.yuan
 * Email:   hubeiqiyuan2010@163.com
 * Date:    2016/7/28 17:37
 * Description:日期选择window
 */
public class ChangeDatePopwindow2 extends PopupWindow implements View.OnClickListener {

    private WheelView wvYear;
    private WheelView wvDay;
    private Button btnSure;
    private Button btnCancel;
    private ArrayList<String> arry_years = new ArrayList<String>();
    private ArrayList<String> arry_days = new ArrayList<String>();
    private CalendarTextAdapter mYearAdapter;
    private CalendarTextAdapter mDaydapter;

    private String month;
    private String day;

    private String currentYear = getYear() + "";
    private String currentMonth = getMonth() + "";
    private String currentDay = getDay() + "";
private RelativeLayout rv_topbar;
    private int maxTextSize = 24;
    private int minTextSize = 14;

    private boolean issetdata = false;

    private String selectYear;
    private String selectMonth;
    private String selectDay;

    private OnBirthListener onBirthListener;

    public ChangeDatePopwindow2(final Context context) {
        super(context);
        final View view = View.inflate(context, R.layout.dialog_myinfo_changebirth2, null);
        wvYear = (WheelView) view.findViewById(R.id.wv_birth_year);
        wvDay = (WheelView) view.findViewById(R.id.wv_birth_day);
        btnSure = (Button) view.findViewById(R.id.btnSubmit);
        btnCancel= (Button) view.findViewById(R.id.btnCancel);
        rv_topbar=(RelativeLayout) view.findViewById(R.id.rv_topbar);
        rv_topbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        btnSure.setTextColor(Color.parseColor("#000000"));
        btnCancel.setTextColor(Color.parseColor("#000000"));
//		btnCancel = (TextView) view.findViewById(R.id.btn_myinfo_cancel);
        //设置SelectPicPopupWindow的View
        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//		this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        btnSure.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

        // 单击弹出窗以外处 关闭弹出窗
        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = view.findViewById(R.id.ly_myinfo_changeaddress_child).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        if (!issetdata) {
            initData();
        }
        initYears();
        mYearAdapter = new CalendarTextAdapter(context, arry_years, setYear(currentYear), maxTextSize, minTextSize);
        wvYear.setVisibleItems(5);
        wvYear.setViewAdapter(mYearAdapter);
        wvYear.setCurrentItem(setYear(currentYear));
        initDays();
        mDaydapter = new CalendarTextAdapter(context, arry_days, Integer.parseInt(currentDay) - 1, maxTextSize, minTextSize);
        wvDay.setVisibleItems(5);
        wvDay.setViewAdapter(mDaydapter);
        wvDay.setCurrentItem(Integer.parseInt(currentDay) - 1);

        wvYear.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                selectYear = currentText;
                setTextviewSize(currentText, mYearAdapter);
                currentYear = currentText.substring(0, currentText.length() - 1).toString();
                Log.d("currentYear==", currentYear);
                setYear(currentYear);
                //calDays(currentYear, month);
            }
        });

        wvYear.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, mYearAdapter);
            }
        });
        wvDay.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) mDaydapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, mDaydapter);
                selectDay = currentText;
            }
        });

        wvDay.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) mDaydapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, mDaydapter);
            }
        });
    }

    /*
    初始化年月日  今天~未来30天
     */
    public void initYears() {
        int maxday = 0, year = 0, month = 0, day = 0;
        year = getYear();
        month = getMonth();
        day = getDay();
        maxday = calDays(year + "", month + "");
        if (day == maxday) {
            day = 1;
            if (month < 12) {
                month = month + 1;
            } else {
                month = 1;
            }
        } else {
            day = day + 1;
        }
        String month1 = month + "";
        String day1 = day + "";
        for (int i = 1; i <= 30; i++) {
            month1 = month + "";
            day1 = day + "";
            if (month < 10) {
                month1 = "0" + month;
            }
            if (day < 10) {
                day1 = "0" + day;
            }
            arry_years.add(year + "-" + month1 + "-" + day1);
            if (day < maxday) {
                day = day + 1;
            } else {
                if (month < 12) {
                    month = month + 1;
                    day = 1;
                } else {
                    month = 1;
                    year = year + 1;
                    day = 1;
                }
                maxday = calDays(year + "", month + "");
            }
        }

    }


    public void initDays() {
        arry_days.clear();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                arry_days.add("0" + i + ":00");
            } else {
                arry_days.add(i + ":00");

            }
        }
    }

    private class CalendarTextAdapter extends AbstractWheelTextAdapter1 {
        ArrayList<String> list;

        protected CalendarTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }

    public void setBirthdayListener(OnBirthListener onBirthListener) {
        this.onBirthListener = onBirthListener;
    }

    @Override
    public void onClick(View v) {

        if (v == btnSure) {
            if (onBirthListener != null) {
                onBirthListener.onClick(selectYear, selectMonth, selectDay);
                Log.d("cy", "" + selectYear + "" + selectMonth + "" + selectDay);
            }
        }

        dismiss();
    }

    public interface OnBirthListener {
        public void onClick(String year, String month, String day);
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(maxTextSize);
            } else {
                textvew.setTextSize(minTextSize);
            }
        }
    }

    public int getYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    public int getMonth() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH) + 1;
    }

    public int getDay() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DATE);
    }

    public void initData() {
        setDate(getYear() + "", getMonth() + "", getDay() + "");
        this.currentDay = 1 + "";
        this.currentMonth = 1 + "";
    }

    /**
     * 设置年月日
     *
     * @param year
     * @param month
     * @param day
     */
    public void setDate(String year, String month, String day) {
        int maxday = 0;
        maxday = calDays(year + "", month + "");
        if(Integer.parseInt(day)==maxday){
            if(month=="12"){
                year=Integer.parseInt(year)+1+"";
                month="1";
            }else{
                year=Integer.parseInt(year)+1+"";
            }
        }else{
            day=Integer.parseInt(day)+1+"";
        }
        if(Integer.parseInt(month)<10){
            month="0"+month;
        }
        if(Integer.parseInt(day)<10){
            day="0"+day;
        }
        selectYear = year + "-" + month + "-" + day;
        selectMonth = day;
        selectDay = "00:00";
        issetdata = true;
        this.currentYear = year;
        this.currentMonth = month;
        this.currentDay = day;
        if (year == getYear() + "") {
            this.month = getMonth() + "";
        } else {
            this.month = 12 + "";
        }
        calDays(year, month);
    }

    /**
     * 设置年份
     *
     * @param year
     */
    public int setYear(String year) {
        int i = 0;
        for (String yearIndex : arry_years) {
            if (yearIndex.equals(year)) {
                return i + 1;
            }
            i++;
        }
        return i;
    }

//        if (!year.equals(getYear())) {
//            this.month = 12 + "";
//        } else {
//            this.month = getMonth() + "";
//        }
//        for (int i = Integer.parseInt(getYear() + ""); i > 1950; i--) {
//            if (i == Integer.parseInt(year)) {
//                return yearIndex;
//            }
//            yearIndex++;
//        }

    /**
     * 计算每月多少天
     *
     * @param month
     * @param year
     */
    public int calDays(String year, String month) {
        boolean leayyear = false;
        int retday = 0;
        if (Integer.parseInt(year) % 4 == 0 && Integer.parseInt(year) % 100 != 0) {
            leayyear = true;
        } else {
            leayyear = false;
        }
        for (int i = 1; i <= 12; i++) {
            switch (Integer.parseInt(month)) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    this.day = 31 + "";
                    retday = 31;
                    break;
                case 2:
                    if (leayyear) {
                        this.day = 29 + "";
                        retday = 29;
                    } else {
                        this.day = 28 + "";
                        retday = 28;
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    this.day = 30 + "";
                    retday = 30;
                    break;
            }
        }
        if (year.equals(getYear()) && month.equals(getMonth())) {
            this.day = getDay() + "";
        }
        return retday;
    }
}