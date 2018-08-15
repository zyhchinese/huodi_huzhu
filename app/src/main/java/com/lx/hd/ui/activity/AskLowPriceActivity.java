package com.lx.hd.ui.activity;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.CarType;
import com.lx.hd.mvp.AskLowPriceContract;
import com.lx.hd.mvp.AskLowPricePresenter;
import com.lx.hd.utils.TDevice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AskLowPriceActivity extends BackCommonActivity implements AskLowPriceContract.View {

    private LinearLayout rlyCarType;
    private EditText etCity,etName,etPhone;
    private TextView tvCarType,tvConfirm;
    private RecyclerView mCarTypeRecyclerView;
    private String carTypeId="";
    private AskLowPriceContract.Presenter askLowPricePresenter;
    private View mCarTypeDialog,mCarTitleView;
    private PopupWindow pop;
    private myAdapter adapter;
    private ListView listView;
    private List<String> names;
    private List<String> ids;
    private boolean isShowSeach=false;
    private EditText etSearch;
    private List<CarType> allCarTypes;
    private boolean hasFooter=false;

    @Override
    protected int getContentView() {
        return R.layout.activity_ask_low_price;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        askLowPricePresenter=new AskLowPricePresenter(this);

        setTitleText("询底价");
        rlyCarType=(LinearLayout)findViewById(R.id.rly_root_cartype);
        etCity=(EditText)findViewById(R.id.et_city);
        etName=(EditText)findViewById(R.id.et_name);
        etPhone=(EditText)findViewById(R.id.tv_tel);
        tvConfirm=(TextView) findViewById(R.id.tv_confirm);
        tvCarType=(TextView) findViewById(R.id.tv_car_type);

        listView=new ListView(AskLowPriceActivity.this);
        listView.setDivider(ContextCompat.getDrawable(AskLowPriceActivity.this,R.color.white));
        listView.setDividerHeight(1);

        LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View menuView =mLayoutInflater.inflate(R.layout.layout_search_car_type, null, true);
    //    listView.addFooterView(menuView);
        etSearch=(EditText)menuView.findViewById(R.id.et_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                List<String> listName=new ArrayList<String>();
                List<String> listId=new ArrayList<String>();
                if(allCarTypes!=null){
                    for(int i=0;i<allCarTypes.size();i++){
                        if(allCarTypes.get(i).getGroupname().indexOf(s.toString())!=-1){
                            listName.add(allCarTypes.get(i).getGroupname());
                            listId.add(allCarTypes.get(i).getId());
                        }
                    }
                    names=listName;
                    ids=listId;
                    adapter.notifyDataSetChanged();
                }


            }
        });
        pop=new PopupWindow();
   //     pop.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(AskLowPriceActivity.this,R.color.swiperefresh_color2)));

        rlyCarType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isShowSeach && rlyCarType.getChildCount()==2){
                    rlyCarType.removeViewAt(1);
                    isShowSeach=false;
                }else {

                    rlyCarType.addView(menuView);
                    LinearLayout.LayoutParams  layoutParams=(LinearLayout.LayoutParams)menuView.getLayoutParams();
                    layoutParams.width=rlyCarType.getWidth();
                    layoutParams.height=ViewGroup.LayoutParams.WRAP_CONTENT;
                    isShowSeach=true;

                }

               int a= rlyCarType.getChildCount();
                if(a>2){
                    rlyCarType.removeViewAt(2);
                }
                    askLowPricePresenter.getCarType();


            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etCityTemp=etCity.getText().toString().trim();
                String etNameTemp=etName.getText().toString().trim();
                String etPhoneTemp=etPhone.getText().toString().trim();
                if(carTypeId.length()==0||etCityTemp.length()==0||etNameTemp.length()==0||etPhoneTemp.length()==0){
                    showToast("请填写完整信息");
                    return;
                }
                if(!isMobile(etPhoneTemp)){
                    showToast("手机号格式不正确，请重新输入");
                    return;
                }
                HashMap<String,String> map=new HashMap<String, String>();
                map.put("groupid",carTypeId);
                map.put("cityname",etCityTemp);
                map.put("custname",etNameTemp);
                map.put("custphone",etPhoneTemp);
                askLowPricePresenter.toConfirm(map);
            }
        });
    }

    @Override
    public void setPresenter(AskLowPriceContract.Presenter presenter) {
        askLowPricePresenter=presenter;
    }

    @Override
    public void showNetworkError(int strId) {

    }

    @Override
    public void showCarType(final List<CarType> carTypes) {
        allCarTypes=carTypes;
        names=new ArrayList<>();
        ids=new ArrayList<>();
        for (int i=0;i<carTypes.size();i++){
            names.add(carTypes.get(i).getGroupname());
            ids.add(carTypes.get(i).getId());
        }
//        ViewGroup vg =  (ViewGroup) rlyCarType.getParent();
//        if(vg!=null){
//            vg.removeAllViews();
//        }

        pop.setContentView(listView);
        pop.setWidth(rlyCarType.getWidth());
        if(carTypes.size()>7){
            int a= (int)TDevice.getScreenHeight();
            if(a==1920){
                pop.setHeight(570);
            }else {
                pop.setHeight(300);
            }

        }else {
            pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        pop.setOutsideTouchable(false);
        pop.setBackgroundDrawable(new ColorDrawable(0));
        pop.setFocusable(false);
        pop.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        adapter=new myAdapter();
        LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View menuView =mLayoutInflater.inflate(R.layout.layout_listview_footer, null, true);
       if(!hasFooter){
       //    listView.addFooterView(menuView);
           hasFooter=true;
       }

        listView.setAdapter(adapter);

        if(pop.isShowing()){
            pop.dismiss();
        }else {
            pop.showAsDropDown(rlyCarType);
        }





//        if(pop!=null){
//            if(adapter==null){
//                adapter=new myAdapter();
//
//                pop.setContentView(listView);
//                listView.setAdapter(adapter);
//                pop.showAsDropDown(rlyCarType);
//                isShow = true;
//            }
//        }
//        else if(isShow){
//            pop.dismiss();
//            isShow = false;
//        }else if(!isShow){
//            pop.showAsDropDown(rlyCarType);
//            isShow = true;
//        }
//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                ((ViewGroup) rlyCarType.getParent()).removeView(rlyCarType);
//            }
//        });
//        mCarTitleView=LayoutInflater.from(this).inflate(R.layout.dialog_car_title,null,false);
//        mCarTypeDialog = LayoutInflater.from(this).inflate(R.layout.dialog_recyclerview_car_type, null, false);
//        TextView cittTitle=(TextView)mCarTitleView.findViewById(R.id.tv_title_city);
//        TextView next=(TextView)mCarTitleView.findViewById(R.id.tv_next);
//        TextView cancle=(TextView)mCarTitleView.findViewById(R.id.tv_cancle);
//
//        mCarTypeRecyclerView=(RecyclerView)mCarTypeDialog.findViewById(R.id.rv_city);
//
//        CarTypeSelectAdapter carTypeSelectAdapter=new CarTypeSelectAdapter(this,carTypes);
//        mCarTypeRecyclerView.setAdapter(carTypeSelectAdapter);
//        mCarTypeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder .setCustomTitle(mCarTitleView)
//                .setView(mCarTypeDialog);
//        final AlertDialog dialog=builder.create();
//        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
//        dialog.show();
//        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
//        params.width =(int)(TDevice.getScreenWidth()*0.8);
//        params.height = (int)(TDevice.getScreenHeight()*0.7) ;
//        dialog.getWindow().setAttributes(params);
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        cancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        carTypeSelectAdapter.setOnItemClickLitener(new CarTypeSelectAdapter.OnItemClickLitener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                tvCarType.setText(carTypes.get(position).getGroupname().toString());
//                carTypeId=carTypes.get(position).getId();
//                dialog.dismiss();
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//
//            }
//        });

    }

    @Override
    public void showConfirmResult(boolean b) {
        if(b){
            showToast("提交成功");
            finish();
        }else {
            showToast("提交失败，请稍后重试");
        }
    }

    //判断手机格式是否正确
    public boolean isMobile(String mobiles) {

        Pattern p = Pattern.compile("1(3|4|5|7|8)[0-9]{9}");
        Matcher m = p.matcher(mobiles);
        return m.matches();

    }
    class myAdapter extends BaseAdapter {
        LayoutInflater mInflater;
        public myAdapter() {
            mInflater=LayoutInflater.from(AskLowPriceActivity.this);
            // TODO Auto-generated constructor stub
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return names.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Holder holder=null;
            final String name = names.get(position);
            final String idTemp = ids.get(position);
            if(convertView==null){
                convertView=mInflater.inflate(R.layout.popup, null);
                holder=new Holder();
                holder.view=(TextView)convertView.findViewById(R.id.mQQ);
                convertView.setTag(holder);
            }
            else{
                holder=(Holder) convertView.getTag();
            }
            if(holder!=null){
                convertView.setId(position);
                holder.setId(position);
                holder.view.setText(name);
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isShowSeach && rlyCarType.getChildCount()==2){
                            etSearch.setText("");
                            rlyCarType.removeViewAt(1);
                            isShowSeach=false;
                        }
                        pop.dismiss();
                        tvCarType.setText(name);
                        carTypeId=idTemp;
                    }
                });

            }
            return convertView;
        }

        class Holder{
            TextView view;

            void setId(int position){
                view.setId(position);
            }
        }

    }

}
