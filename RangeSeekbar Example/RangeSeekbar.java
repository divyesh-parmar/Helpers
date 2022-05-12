package com.div.slowmotionvideomaker.bryanchor;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RadialGradient;
import android.graphics.Region.Op;
import android.graphics.Shader.TileMode;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.AccelerateInterpolator;

import androidx.core.view.ViewCompat;

import com.div.slowmotionvideomaker.R;

import java.util.concurrent.TimeUnit;

public class RangeSeekbar extends View {
    private static final int DEFAULT_EMPTY_COLOR;
    private static final int DEFAULT_FILLED_COLOR;
    private static final float DEFAULT_SLIDER_RADIUS_PERCENT = 0.25f;
    private static final float DEFAULT_SLOT_RADIUS_PERCENT = 0.125f;
    private static final long RIPPLE_ANIMATION_DURATION_MS = TimeUnit.MILLISECONDS.toMillis(700);
    private static final String TAG = RangeSeekbar.class.getSimpleName();
    private int barHeight;
    private float barHeightPercent;
    private int currentIndex;
    private float currentSlidingX;
    private float currentSlidingY;
    private float downX;
    private float downY;
    private int emptyColor;
    private int filledColor;
    private boolean gotSlot;
    private Path innerPath;
    private int layoutHeight;
    private OnSlideListener listener;
    private Path outerPath;
    protected Paint paint;
    protected float radius;
    private int rangeCount;
    protected Paint ripplePaint;
    public float rippleRadius;
    private float selectedSlotX;
    private float selectedSlotY;
    private float sliderRadiusPercent;
    private float[] slotPositions;
    protected float slotRadius;
    private float slotRadiusPercent;

    public interface OnSlideListener {
        void onSlide(int i);
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new C12021();
        int saveIndex;

        static class C12021 implements Creator<SavedState> {
            C12021() {
            }

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.saveIndex = parcel.readInt();
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.saveIndex);
        }
    }

    static {
        String str = "#ffffff";
        DEFAULT_EMPTY_COLOR = Color.parseColor(str);
        DEFAULT_FILLED_COLOR = Color.parseColor(str);
    }

    public RangeSeekbar(Context context) {
        this(context, null);
    }

    public RangeSeekbar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public RangeSeekbar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.gotSlot = false;
        this.filledColor = DEFAULT_FILLED_COLOR;
        this.emptyColor = DEFAULT_EMPTY_COLOR;
        this.barHeightPercent = 0.1f;
        this.rangeCount = 9;
        this.rippleRadius = 0.0f;
        this.innerPath = new Path();
        this.outerPath = new Path();
        this.slotRadiusPercent = DEFAULT_SLOT_RADIUS_PERCENT;
        this.sliderRadiusPercent = DEFAULT_SLIDER_RADIUS_PERCENT;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RangeSliderView);
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, new int[]{16842997});
            try {
                this.layoutHeight = obtainStyledAttributes2.getLayoutDimension(0, -2);
                this.rangeCount = obtainStyledAttributes.getInt(0, 9);
                this.filledColor = obtainStyledAttributes.getColor(1, DEFAULT_FILLED_COLOR);
                this.emptyColor = obtainStyledAttributes.getColor(2, DEFAULT_FILLED_COLOR);
                this.barHeightPercent = obtainStyledAttributes.getFloat(3, 0.1f);
                this.barHeightPercent = obtainStyledAttributes.getFloat(3, 0.1f);
                this.slotRadiusPercent = obtainStyledAttributes.getFloat(4, DEFAULT_SLOT_RADIUS_PERCENT);
                this.sliderRadiusPercent = obtainStyledAttributes.getFloat(5, DEFAULT_SLIDER_RADIUS_PERCENT);
            } finally {
                obtainStyledAttributes.recycle();
                obtainStyledAttributes2.recycle();
            }
        }
        setBarHeightPercent(this.barHeightPercent);
        setRangeCount(this.rangeCount);
        setSlotRadiusPercent(this.slotRadiusPercent);
        setSliderRadiusPercent(this.sliderRadiusPercent);
        this.slotPositions = new float[this.rangeCount];
        this.paint = new Paint(1);
        this.paint.setStrokeWidth(2.0f);
        this.paint.setStyle(Style.FILL_AND_STROKE);
        this.ripplePaint = new Paint(1);
        this.ripplePaint.setStrokeWidth(2.0f);
        this.ripplePaint.setStyle(Style.FILL_AND_STROKE);
        getViewTreeObserver().addOnPreDrawListener(new C12001());
        this.currentIndex = 4;
    }

    class C12001 implements OnPreDrawListener {
        C12001() {
        }

        public boolean onPreDraw() {
            RangeSeekbar.this.getViewTreeObserver().removeOnPreDrawListener(this);
            RangeSeekbar rangeSeekbar = RangeSeekbar.this;
            rangeSeekbar.updateRadius(rangeSeekbar.getHeight());
            RangeSeekbar.this.preComputeDrawingPosition();
            return true;
        }
    }

    class C12012 implements AnimatorListener {
        public void onAnimationCancel(Animator animator) {
        }

        public void onAnimationRepeat(Animator animator) {
        }

        public void onAnimationStart(Animator animator) {
        }

        C12012() {
        }

        public void onAnimationEnd(Animator animator) {
            RangeSeekbar.this.rippleRadius = 0.0f;
        }
    }

    public void updateRadius(int i) {
        float f = (float) i;
        this.barHeight = (int) (this.barHeightPercent * f);
        this.radius = this.sliderRadiusPercent * f;
        this.slotRadius = f * this.slotRadiusPercent;
    }

    public int getRangeCount() {
        return this.rangeCount;
    }

    public void setRangeCount(int i) {
        if (i >= 2) {
            this.rangeCount = i;
            return;
        }
        throw new IllegalArgumentException("rangeCount must be >= 2");
    }

    public float getBarHeightPercent() {
        return this.barHeightPercent;
    }

    public void setBarHeightPercent(float f) {
        double d = (double) f;
        if (d <= 0.0d || d > 1.0d) {
            throw new IllegalArgumentException("Bar height percent must be in (0, 1]");
        }
        this.barHeightPercent = f;
    }

    public float getSlotRadiusPercent() {
        return this.slotRadiusPercent;
    }

    public void setSlotRadiusPercent(float f) {
        double d = (double) f;
        if (d <= 0.0d || d > 1.0d) {
            throw new IllegalArgumentException("Slot radius percent must be in (0, 1]");
        }
        this.slotRadiusPercent = f;
    }

    public float getSliderRadiusPercent() {
        return this.sliderRadiusPercent;
    }

    public void setSliderRadiusPercent(float f) {
        double d = (double) f;
        if (d <= 0.0d || d > 1.0d) {
            throw new IllegalArgumentException("Slider radius percent must be in (0, 1]");
        }
        this.sliderRadiusPercent = f;
    }

    public void setRadius(float f) {
        this.rippleRadius = f;
        float f2 = this.rippleRadius;
        if (f2 > 0.0f) {
            Paint paint2 = this.ripplePaint;
            RadialGradient radialGradient = new RadialGradient(this.downX, this.downY, f2 * 3.0f, 0, ViewCompat.MEASURED_STATE_MASK, TileMode.MIRROR);
            paint2.setShader(radialGradient);
        }
        invalidate();
    }

    public void setOnSlideListener(OnSlideListener onSlideListener) {
        this.listener = onSlideListener;
    }

    public void preComputeDrawingPosition() {
        int widthWithPadding = getWidthWithPadding() / this.rangeCount;
        float paddingTop = (float) (getPaddingTop() + (getHeightWithPadding() / 2));
        this.currentSlidingY = paddingTop;
        this.selectedSlotY = paddingTop;
        int paddingLeft = getPaddingLeft() + (widthWithPadding / 2);
        for (int i = 0; i < this.rangeCount; i++) {
            float f = (float) paddingLeft;
            this.slotPositions[i] = f;
            if (i == this.currentIndex) {
                this.currentSlidingX = f;
                this.selectedSlotX = f;
            }
            paddingLeft += widthWithPadding;
        }
    }

    public void setInitialIndex(int i) {
        if (i < 0 || i >= this.rangeCount) {
            StringBuilder sb = new StringBuilder();
            sb.append("Attempted to set index=");
            sb.append(i);
            sb.append(" out of range [0,");
            sb.append(this.rangeCount);
            sb.append("]");
            throw new IllegalArgumentException(sb.toString());
        }
        this.currentIndex = i;
        float f = this.slotPositions[this.currentIndex];
        this.selectedSlotX = f;
        this.currentSlidingX = f;
        invalidate();
    }

    public int getFilledColor() {
        return this.filledColor;
    }

    public void setFilledColor(int i) {
        this.filledColor = i;
        invalidate();
    }

    public int getEmptyColor() {
        return this.emptyColor;
    }

    public void setEmptyColor(int i) {
        this.emptyColor = i;
        invalidate();
    }

    public void onMeasure(int i, int i2) {
        setMeasuredDimension(measureWidth(i), measureHeight(i2));
    }

    private int measureHeight(int i) {
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        if (mode == 1073741824) {
            return size;
        }
        int i2 = this.layoutHeight;
        if (i2 == -2) {
            i2 = dpToPx(getContext(), 50.0f);
        } else if (i2 == -1) {
            i2 = getMeasuredHeight();
        }
        int paddingTop = getPaddingTop() + i2 + getPaddingBottom() + 4;
        return mode == Integer.MIN_VALUE ? Math.min(paddingTop, size) : paddingTop;
    }

    private int measureWidth(int i) {
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        if (mode == 1073741824) {
            return size;
        }
        int paddingLeft = getPaddingLeft() + size + getPaddingRight() + 4 + ((int) (this.radius * 2.0f));
        if (mode == Integer.MIN_VALUE) {
            paddingLeft = Math.min(paddingLeft, size);
        }
        return paddingLeft;
    }

    private void updateCurrentIndex() {
        int i = 0;
        float f = Float.MAX_VALUE;
        for (int i2 = 0; i2 < this.rangeCount; i2++) {
            float abs = Math.abs(this.currentSlidingX - this.slotPositions[i2]);
            if (abs < f) {
                i = i2;
                f = abs;
            }
        }
        if (i != this.currentIndex) {
            OnSlideListener onSlideListener = this.listener;
            if (onSlideListener != null) {
                onSlideListener.onSlide(i);
            }
        }
        this.currentIndex = i;
        this.currentSlidingX = this.slotPositions[i];
        float f2 = this.currentSlidingX;
        this.selectedSlotX = f2;
        this.downX = f2;
        this.downY = this.currentSlidingY;
        animateRipple();
        invalidate();
    }

    private void animateRipple() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "radius", new float[]{0.0f, this.radius});
        ofFloat.setInterpolator(new AccelerateInterpolator());
        ofFloat.setDuration(RIPPLE_ANIMATION_DURATION_MS);
        ofFloat.start();
        ofFloat.addListener(new C12012());
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float y = motionEvent.getY();
        float x = motionEvent.getX();
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.gotSlot = isInSelectedSlot(x, y);
            this.downX = x;
            this.downY = y;
        } else if (actionMasked != 1) {
            if (actionMasked == 2 && this.gotSlot) {
                float[] fArr = this.slotPositions;
                if (x >= fArr[0] && x <= fArr[this.rangeCount - 1]) {
                    this.currentSlidingX = x;
                    this.currentSlidingY = y;
                    invalidate();
                }
            }
        } else if (this.gotSlot) {
            this.gotSlot = false;
            this.currentSlidingX = x;
            this.currentSlidingY = y;
            updateCurrentIndex();
        }
        return true;
    }

    private boolean isInSelectedSlot(float f, float f2) {
        float f3 = this.selectedSlotX;
        float f4 = this.radius;
        if (f3 - f4 <= f && f <= f3 + f4) {
            float f5 = this.selectedSlotY;
            if (f5 - f4 <= f2 && f2 <= f5 + f4) {
                return true;
            }
        }
        return false;
    }

    private void drawEmptySlots(Canvas canvas) {
        this.paint.setColor(this.emptyColor);
        int paddingTop = getPaddingTop() + (getHeightWithPadding() >> 1);
        for (int i = 0; i < this.rangeCount; i++) {
            canvas.drawCircle(this.slotPositions[i], (float) paddingTop, this.slotRadius, this.paint);
        }
    }

    public int getHeightWithPadding() {
        return (getHeight() - getPaddingBottom()) - getPaddingTop();
    }

    public int getWidthWithPadding() {
        return (getWidth() - getPaddingLeft()) - getPaddingRight();
    }

    private void drawFilledSlots(Canvas canvas) {
        this.paint.setColor(this.filledColor);
        int paddingTop = getPaddingTop() + (getHeightWithPadding() >> 1);
        for (int i = 0; i < this.rangeCount; i++) {
            float[] fArr = this.slotPositions;
            if (fArr[i] <= this.currentSlidingX) {
                canvas.drawCircle(fArr[i], (float) paddingTop, this.slotRadius, this.paint);
            }
        }
    }

    private void drawBar(Canvas canvas, int i, int i2, int i3) {
        this.paint.setColor(i3);
        int i4 = this.barHeight >> 1;
        int paddingTop = getPaddingTop() + (getHeightWithPadding() >> 1);
        canvas.drawRect((float) i, (float) (paddingTop - i4), (float) i2, (float) (paddingTop + i4), this.paint);
    }

    private void drawRippleEffect(Canvas canvas) {
        if (this.rippleRadius != 0.0f) {
            canvas.save();
            this.ripplePaint.setColor(-7829368);
            this.outerPath.reset();
            this.outerPath.addCircle(this.downX, this.downY, this.rippleRadius, Direction.CW);
            canvas.clipPath(this.outerPath);
            this.innerPath.reset();
            this.innerPath.addCircle(this.downX, this.downY, this.rippleRadius / 3.0f, Direction.CW);
            canvas.clipPath(this.innerPath, Op.DIFFERENCE);
            canvas.drawCircle(this.downX, this.downY, this.rippleRadius, this.ripplePaint);
            canvas.restore();
        }
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft() + ((getWidthWithPadding() / this.rangeCount) >> 1);
        int paddingTop = getPaddingTop() + (getHeightWithPadding() >> 1);
        drawEmptySlots(canvas);
        drawFilledSlots(canvas);
        float[] fArr = this.slotPositions;
        drawBar(canvas, (int) fArr[0], (int) fArr[this.rangeCount - 1], this.emptyColor);
        drawBar(canvas, paddingLeft, (int) this.currentSlidingX, this.filledColor);
        this.paint.setColor(this.filledColor);
        canvas.drawCircle(this.currentSlidingX, (float) paddingTop, this.radius, this.paint);
        drawRippleEffect(canvas);
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.saveIndex = this.currentIndex;
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            super.onRestoreInstanceState(savedState.getSuperState());
            this.currentIndex = savedState.saveIndex;
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    static int pxToDp(Context context, float f) {
        return (int) (f / context.getResources().getDisplayMetrics().density);
    }

    static int dpToPx(Context context, float f) {
        return (int) (context.getResources().getDisplayMetrics().density * f);
    }
}