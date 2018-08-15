package com.lx.hd.base.activity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.lx.hd.R;
import com.lx.hd.utils.DialogHelper;
import com.lx.hd.utils.TLog;

import java.util.List;

public abstract class BaseActivity extends AppCompatActivity {
    public static final String ACTION_PAY_FINISH_ALL = "com.lx.hd.action.pay.finish.all";

    protected LocalBroadcastManager mManager;
    private BroadcastReceiver mReceiver;

    protected RequestManager mImageLoader;
    private boolean mIsDestroy;
    ProgressDialog mDialog;
    private final String mPackageNameUmeng = this.getClass().getName();
    private Fragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (initBundle(getIntent().getExtras())) {
            beforSetContentView();
            setContentView(getContentView());

            initWindow();
            initWidget();
            registerPayLocalReceiver();
            initData();
        } else {
            finish();
        }
    }

    protected void addFragment(int frameLayoutId, Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (fragment.isAdded()) {
                if (mFragment != null) {
                    transaction.hide(mFragment).show(fragment);
                } else {
                    transaction.show(fragment);
                }
            } else {
                if (mFragment != null) {
                    transaction.hide(mFragment).add(frameLayoutId, fragment);
                } else {
                    transaction.add(frameLayoutId, fragment);
                }
            }
            mFragment = fragment;
            transaction.commit();
        }
    }

    protected void replaceFragment(int frameLayoutId, Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(frameLayoutId, fragment);
            transaction.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected abstract int getContentView();

    protected boolean initBundle(Bundle bundle) {
        return true;
    }

    protected void initWindow() {
    }
    protected void beforSetContentView() {
    }
    protected void initWidget() {
    }

    protected void initData() {
    }

    public synchronized RequestManager getImageLoader() {
        if (mImageLoader == null)
            mImageLoader = Glide.with(this);
        return mImageLoader;
    }

    @Override
    protected void onDestroy() {
        mIsDestroy = true;
        super.onDestroy();
        if (mManager != null) {
            if (mReceiver != null)
                mManager.unregisterReceiver(mReceiver);
        }
    }

    /**
     * 结束广播
     */
    private void registerPayLocalReceiver() {
        if (mManager == null)
            mManager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_PAY_FINISH_ALL);
        if (mReceiver == null)
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (ACTION_PAY_FINISH_ALL.equals(action)) {
                        if (intent != null) {
                            String className = intent.getStringExtra("className");
                            if (className != null) {
                                if (className.equals(mPackageNameUmeng) || className.equals("all")) {
                                    finish();
                                }
                            }
                        }
                    }
                }
            };
        mManager.registerReceiver(mReceiver, filter);
    }

    protected boolean sendPayLocalReceiver() {
        if (mManager != null) {
            Intent intent = new Intent();
            intent.setAction(ACTION_PAY_FINISH_ALL);
            return mManager.sendBroadcast(intent);
        }

        return false;
    }

    public boolean isDestroy() {
        return mIsDestroy;
    }

    public ProgressDialog showWaitDialog(String message) {
        if (mDialog == null) {
            mDialog = DialogHelper.getProgressDialog(this, message);
        }

        mDialog.setMessage(message);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        return mDialog;
    }

    /**
     * hide waitDialog
     */
    protected void hideWaitDialog() {
        ProgressDialog dialog = mDialog;
        if (dialog != null) {
            mDialog = null;
            try {
                dialog.cancel();
                // dialog.dismiss();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * showToast
     *
     * @param message
     */
    @SuppressLint("InflateParams")
    public void showToast(String message) {
        Toast toast = null;
        if (toast == null) {
            toast = initToast();
        }
        View rootView = LayoutInflater.from(this).inflate(R.layout.view_toast, null, false);
        TextView textView = (TextView) rootView.findViewById(R.id.title_tv);
        textView.setText(message);
        toast.setView(rootView);
        initToastGravity(toast);
        toast.show();
    }

    @NonNull
    private Toast initToast() {
        Toast toast;
        toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_SHORT);
        return toast;
    }

    private void initToastGravity(Toast toast) {
        toast.setGravity(Gravity.BOTTOM, 0, getResources().getDimensionPixelSize(R.dimen.toast_y_offset));

    }
}
