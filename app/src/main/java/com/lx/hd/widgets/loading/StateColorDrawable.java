package com.lx.hd.widgets.loading;

/**
 * Created by Administrator on 2017/8/18.
 */

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

/**
 * A drawable that changes it's ColorStateList color depending on the Drawable State
 * <p>
 * Subclasses should implement {@link #onColorChange(int)} if new color != current color call this }
 * </p>
 */
@SuppressWarnings("WeakerAccess")
public abstract class StateColorDrawable extends Drawable {
    private ColorStateList mColorStateList;
    private int mColor;
    private int mAlpha = 255;

    public StateColorDrawable(ColorStateList tintStateList) {
        super();
        setColorStateList(tintStateList);
    }

    @Override
    public boolean isStateful() {
        return (mColorStateList.isStateful()) || super.isStateful();
    }

    /**
     * Update tint color
     *
     * @param state tint stats
     * @return is changed
     */
    @Override
    protected boolean onStateChange(int[] state) {
        final int color = mColorStateList.getColorForState(state, mColor);
        return changeColor(color);
    }

    @Override
    public int getAlpha() {
        return mAlpha;
    }

    /**
     * Set the alpha level for this drawable [0..255]. Note that this drawable
     * also has a color in its paint, which has an alpha as well. These two
     * values are automatically combined during drawing. Thus if the color's
     * alpha is 75% (i.e. 192) and the drawable's alpha is 50% (i.e. 128), then
     * the combined alpha that will be used during drawing will be 37.5% (i.e.
     * 96).
     */
    @Override
    public void setAlpha(int alpha) {
        mAlpha = alpha;
        invalidateSelf();
    }

    /**
     * Set color trans to ColorStateList
     *
     * @param color Color
     */
    public void setColor(int color) {
        setColorStateList(ColorStateList.valueOf(color));
    }

    /**
     * Set the Tint ColorStateList
     *
     * @param tintStateList ColorStateList
     */
    public void setColorStateList(ColorStateList tintStateList) {
        if (tintStateList == null)
            tintStateList = ColorStateList.valueOf(Color.BLACK);
        mColorStateList = tintStateList;
        int[] state = getState();
        changeColor(tintStateList.getColorForState(state, tintStateList.getDefaultColor()));
    }

    /**
     * Get the Tint ColorStateList
     *
     * @return mTintStateList
     */
    public ColorStateList getColorStateList() {
        return mColorStateList;
    }

    /**
     * Get The CurrentColor
     *
     * @return mCurrentColor
     */
    public int getColor() {
        return mColor;
    }

    /**
     * Set CurrentColor value
     *
     * @param color New Color
     * @return If new value != current value return true
     */
    protected boolean changeColor(int color) {
        boolean bFlag = mColor != color;
        if (bFlag) {
            mColor = color;
            //We've changed states
            onColorChange(color);
            invalidateSelf();
        }
        return bFlag;
    }

    protected void onColorChange(int color) {

    }
}