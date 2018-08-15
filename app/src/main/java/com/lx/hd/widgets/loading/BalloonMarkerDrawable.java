package com.lx.hd.widgets.loading;

/**
 * Created by Administrator on 2017/8/18.
 */

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.SystemClock;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * BalloonMarkerDrawable
 *
 * @hide
 */
public class BalloonMarkerDrawable extends StatePaintDrawable implements Animatable {
    Path mPath = new Path();
    RectF mRect = new RectF();
    Matrix mMatrix = new Matrix();
    private float mCurrentScale = 0f;
    private Interpolator mInterpolator;
    private long mStartTime;
    private boolean mReverse = false;
    private boolean mRunning = false;
    private int mDuration = ANIMATION_DURATION;
    //size of the actual thumb drawable to use as circle state size
    private float mClosedStateSize;
    //value to store que current scale when starting an animation and interpolate from it
    private float mAnimationInitialValue;
    //extra offset directed from the View to account
    //for its internal padding between circle state and marker state
    private int mExternalOffset;
    //colors for interpolation
    private int mStartColor;
    private int mEndColor;
    private int mCurColor;

    private MarkerAnimationListener mMarkerListener;
    private final Runnable mUpdater = new Runnable() {

        @Override
        public void run() {

            long currentTime = SystemClock.uptimeMillis();
            long diff = currentTime - mStartTime;
            if (diff < mDuration) {
                float interpolation = mInterpolator.getInterpolation((float) diff / (float) mDuration);
                scheduleSelf(mUpdater, currentTime + FRAME_DURATION);
                updateAnimation(interpolation);
            } else {
                unscheduleSelf(mUpdater);
                mRunning = false;
                updateAnimation(1f);
                notifyFinishedToListener();
            }
        }
    };

    public BalloonMarkerDrawable(ColorStateList tintList, int closedSize) {
        super(tintList);
        mInterpolator = new AccelerateDecelerateInterpolator();
        mClosedStateSize = closedSize;

        getPaint().setStyle(Paint.Style.FILL);
    }

    private static int blendColors(int color1, int color2, float factor) {
        final float inverseFactor = 1f - factor;
        float a = (Color.alpha(color1) * factor) + (Color.alpha(color2) * inverseFactor);
        float r = (Color.red(color1) * factor) + (Color.red(color2) * inverseFactor);
        float g = (Color.green(color1) * factor) + (Color.green(color2) * inverseFactor);
        float b = (Color.blue(color1) * factor) + (Color.blue(color2) * inverseFactor);
        return Color.argb((int) a, (int) r, (int) g, (int) b);
    }

    public void setExternalOffset(int offset) {
        mExternalOffset = offset;
    }

    public void setClosedStateSize(float closedStateSize) {
        mClosedStateSize = closedStateSize;
    }

    /**
     * The two colors that will be used for the seek thumb.
     *
     * @param startColor Color used for the seek thumb
     * @param endColor   Color used for popup indicator
     */
    public void setColors(int startColor, int endColor) {
        mStartColor = startColor;
        mEndColor = endColor;
    }

    public void setColorStateList(ColorStateList tintStateList) {
        super.setColorStateList(tintStateList);
        mEndColor = tintStateList.getDefaultColor();
        mStartColor = tintStateList.getColorForState(new int[]{android.R.attr.state_pressed}, mEndColor);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        if (!mPath.isEmpty()) {
            paint.setColor(mCurColor);
            canvas.drawPath(mPath, paint);
        }
    }

    public Path getPath() {
        return mPath;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        computePath(bounds);
    }

    private void computePath(Rect bounds) {
        final float currentScale = mCurrentScale;
        final Path path = mPath;
        final RectF rect = mRect;
        final Matrix matrix = mMatrix;

        path.reset();
        int totalSize = Math.min(bounds.width(), bounds.height());

        float initial = mClosedStateSize;
        float currentSize = initial + ((float) totalSize - initial) * currentScale;

        float halfSize = currentSize / 2f;
        float inverseScale = 1f - currentScale;
        float cornerSize = halfSize * inverseScale;
        float[] corners = new float[]{halfSize, halfSize, halfSize, halfSize, halfSize, halfSize, cornerSize, cornerSize};
        rect.set(bounds.left, bounds.top, bounds.left + currentSize, bounds.top + currentSize);
        path.addRoundRect(rect, corners, Path.Direction.CCW);

        matrix.reset();
        matrix.postRotate(-45, bounds.left + halfSize, bounds.top + halfSize);
        matrix.postTranslate((bounds.width() - currentSize) / 2, 0);
        float hDiff = (bounds.bottom - currentSize - mExternalOffset) * inverseScale;
        matrix.postTranslate(0, hDiff);
        path.transform(matrix);
    }

    private void updateAnimation(float factor) {
        float initial = mAnimationInitialValue;
        float destination = mReverse ? 0f : 1f;
        mCurrentScale = initial + (destination - initial) * factor;
        mCurColor = blendColors(mStartColor, mEndColor, mCurrentScale);
        computePath(getBounds());
        invalidateSelf();
    }

    public void animateToPressed() {
        unscheduleSelf(mUpdater);
        mReverse = false;
        if (mCurrentScale < 1) {
            mRunning = true;
            mAnimationInitialValue = mCurrentScale;
            float durationFactor = 1f - mCurrentScale;
            mDuration = (int) (ANIMATION_DURATION * durationFactor);
            mStartTime = SystemClock.uptimeMillis();
            scheduleSelf(mUpdater, mStartTime + FRAME_DURATION);
        } else {
            notifyFinishedToListener();
        }
    }

    public void animateToNormal() {
        mReverse = true;
        unscheduleSelf(mUpdater);
        if (mCurrentScale > 0) {
            mRunning = true;
            mAnimationInitialValue = mCurrentScale;
            float durationFactor = 1f - mCurrentScale;
            mDuration = ANIMATION_DURATION - (int) (ANIMATION_DURATION * durationFactor);
            mStartTime = SystemClock.uptimeMillis();
            scheduleSelf(mUpdater, mStartTime + FRAME_DURATION);
        } else {
            notifyFinishedToListener();
        }
    }

    public void setMarkerListener(MarkerAnimationListener listener) {
        mMarkerListener = listener;
    }

    private void notifyFinishedToListener() {
        if (mMarkerListener != null) {
            if (mReverse) {
                mMarkerListener.onClosingComplete();
            } else {
                mMarkerListener.onOpeningComplete();
            }
        }
    }

    @Override
    public void start() {
        //No-Op. We control our own animation
    }

    @Override
    public void stop() {
        unscheduleSelf(mUpdater);
    }

    @Override
    public boolean isRunning() {
        return mRunning;
    }


    /**
     * A listener interface to porpagate animation events
     * This is the "poor's man" AnimatorListener for this Drawable
     */
    public interface MarkerAnimationListener {
        void onClosingComplete();

        void onOpeningComplete();
    }
}
