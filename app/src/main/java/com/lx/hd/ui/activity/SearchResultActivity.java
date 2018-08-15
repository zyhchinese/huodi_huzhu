package com.lx.hd.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lx.hd.R;
import com.lx.hd.adapter.SearchResultAdapter;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.HuoDiSearch;
import com.lx.hd.bean.HuoDiSearch1;
import com.lx.hd.bean.HuoDiSearch2;

import java.util.List;

public class SearchResultActivity extends BackCommonActivity {

    private RecyclerView act_recyclerView;
    private List<HuoDiSearch> huoDiSearchList;
    private List<HuoDiSearch1> huoDiSearchList1;
    private List<HuoDiSearch2> huoDiSearchList2;
    private String type;


    @Override
    protected int getContentView() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("订单搜索结果");
        huoDiSearchList= (List<HuoDiSearch>) getIntent().getSerializableExtra("shuju");
        huoDiSearchList1= (List<HuoDiSearch1>) getIntent().getSerializableExtra("shuju1");
        huoDiSearchList2= (List<HuoDiSearch2>) getIntent().getSerializableExtra("shuju2");
        type = getIntent().getStringExtra("type");
        act_recyclerView= (RecyclerView) findViewById(R.id.act_recyclerView);
        initRecyclerView();
    }

    private void initRecyclerView() {
        SearchResultAdapter adapter=new SearchResultAdapter(this,huoDiSearchList,huoDiSearchList1,huoDiSearchList2,type);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        adapter.setOnClickItem(new SearchResultAdapter.OnClickItem() {
            @Override
            public void onClick(int position) {
                if (type.equals("0")){
                    Intent intent=new Intent(SearchResultActivity.this, LogisticsOrderDetailsActivity.class);
                    intent.putExtra("orderno",huoDiSearchList.get(position).getOrderno());
                    intent.putExtra("line","1");
                    startActivity(intent);
                }else if (type.equals("1")){
                    Intent intent=new Intent(SearchResultActivity.this, LogisticsOrderDetailsActivity1.class);
                    intent.putExtra("orderno",huoDiSearchList1.get(position).getOwner_orderno());
                    intent.putExtra("line","0");
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(SearchResultActivity.this, LogisticsOrderDetailsActivity2.class);
                    intent.putExtra("orderno",huoDiSearchList2.get(position).getOrderno());
                    intent.putExtra("line","2");
                    startActivity(intent);
                }
            }
        });
    }
}
