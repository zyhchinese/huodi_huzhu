package com.lx.hd.ui.activity;
import com.lx.hd.R;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.base.activity.BaseActivity;

public class PileApplyActivity extends BackActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_pile_apply;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleIcon(R.mipmap.icon_title_pile);
        setTitleText("电桩申请");
    }

}
