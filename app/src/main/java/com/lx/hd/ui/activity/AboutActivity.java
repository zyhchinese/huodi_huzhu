package com.lx.hd.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.util.Util;
import com.lx.hd.R;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackCommonActivity;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.sharesdk.onekeyshare.share.OthreShare;

public class AboutActivity extends BackCommonActivity implements View.OnClickListener{

    private LinearLayout llyNewFeature,llyCommonQuestion,llyShareToFriends,llyLikeMbt;
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("关于我们");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_about_gaiban;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        llyNewFeature=(LinearLayout)findViewById(R.id.lly_new_feature);
        llyCommonQuestion=(LinearLayout)findViewById(R.id.lly_common_question);
        llyShareToFriends=(LinearLayout)findViewById(R.id.lly_share_friends);
        llyLikeMbt=(LinearLayout)findViewById(R.id.lly_like_mbt);

        llyNewFeature.setOnClickListener(this);
        llyCommonQuestion.setOnClickListener(this);
        llyShareToFriends.setOnClickListener(this);
        llyLikeMbt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lly_new_feature:
                startActivity(new Intent(AboutActivity.this,NewFeatureIntroduceActivity.class));
                break;
            case R.id.lly_common_question:
                startActivity(new Intent(AboutActivity.this,ChangJianWenTiActivity.class));
                break;
            case R.id.lly_share_friends:
                OthreShare.showShare(AboutActivity.this,"货滴物联","欢迎使用货滴物联，您身边的物流平台", "http://www.huodiwulian.com/logo_hz.png","http://www.huodiwulian.com/hdsywz/weixin/page/xiazai.html");
              //  startActivity(new Intent(AboutActivity.this,CommonQuestionActivity.class));
                break;
            case R.id.lly_like_mbt:
                startActivity(new Intent(AboutActivity.this,LikeMbtActivity.class));
                break;
        }
    }
}
