package com.kokalabs.tweening.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class TweeningTextView extends View {
    private static final double SCALE = 0.5;

    public TweeningTextView(Context context) {
        super(context);
    }

    public TweeningTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TweeningTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heightWithoutPadding = height - getPaddingTop() - getPaddingBottom();
        int maxWidth = (int) (heightWithoutPadding * SCALE);
        int maxHeight = (int) (widthWithoutPadding / SCALE);
        if (widthWithoutPadding > maxWidth) {
            width = maxWidth + getPaddingLeft() + getPaddingRight();
        } else {
            height = maxHeight + getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(width, height);
    }
}
