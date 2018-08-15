/*
 * 技术支持QQ: 320346009
 */

package com.lx.hd.wxapi;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.lx.hd.base.Constant;
import com.lx.hd.ui.activity.CustomerPositionActivity;
import com.lx.hd.ui.activity.PayActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信客户端回调activity示例
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    protected LocalBroadcastManager mManager;
    private IWXAPI api;
    private BroadcastReceiver mReceiver;
    //TODO　这里需要替换你的APP_ID
    private String APP_ID = Constant.APP_ID; //这里需要替换你的APP_ID

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);
        if (mManager == null)
            mManager = LocalBroadcastManager.getInstance(this);
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }


    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle(R.string.app_tip);
//			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//			builder.show();

            //以下是自定义微信支付广播的发送，微信支付广播请自己定义

            //    Toast.makeText(WXPayEntryActivity.this, "发送广播", Toast.LENGTH_SHORT).show();
            if (mManager != null) {
                Intent intent = new Intent();
                intent.setAction(TAG);
                intent.putExtra("result", resp.errCode);
                mManager.sendBroadcast(intent);
            }
            finish();

        }

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if (PayActivity.instance != null) {
                    PayActivity.instance.finish();
                }
                Toast.makeText(WXPayEntryActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                //  startActivity(new Intent(WXPayEntryActivity.this, CustomerPositionActivity.class));
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Toast.makeText(WXPayEntryActivity.this, "取消支付", Toast.LENGTH_SHORT).show();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                break;
            default:
                break;
        }
    }
}
