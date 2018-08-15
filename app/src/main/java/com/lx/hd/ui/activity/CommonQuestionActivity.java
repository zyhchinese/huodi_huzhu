package com.lx.hd.ui.activity;

import com.lx.hd.R;
import com.lx.hd.base.activity.BackCommonActivity;

public class CommonQuestionActivity extends BackCommonActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_common_question;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("常见问题");
    }
}
