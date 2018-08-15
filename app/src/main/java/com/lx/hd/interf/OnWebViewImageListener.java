package com.lx.hd.interf;

/**
 * 监听webview上的图片
 */
public interface OnWebViewImageListener {

    /**
     * 点击webview上的图片，传入该缩略图的大图Url
     *
     * @param bigImageUrl
     */
    void showImagePreview(String bigImageUrl);

}
