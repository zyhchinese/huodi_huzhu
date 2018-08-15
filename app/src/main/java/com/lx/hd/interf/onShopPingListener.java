package com.lx.hd.interf;

/**
 * Created by TB on 2017/10/27 0027.
 * 用于监听购物车勾选，勾选后通知刷新总价格
 */

public interface onShopPingListener {
    public void OnListener();

    public void Ondelete(String id);
}
