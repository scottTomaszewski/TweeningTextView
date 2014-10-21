package com.kokalabs.tweening.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.kokalabs.svg.CubicBezierCurve;

public class TweeningTextView extends View {
    private static final double SCALE = 0.5;
    private static final Paint PAINT = paint();

    private static Paint paint() {
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(Color.BLUE);
        p.setStrokeWidth(5.0f);
        p.setStyle(Paint.Style.STROKE);
        return p;
    }

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
        drawTweenedText(canvas);
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

    private void drawTweenedText(Canvas canvas) {
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        Adjustment adjust = new Adjustment((float) (height > width ? width : height));

        Path toDraw = new Path();
        toDraw.reset();
        for (CubicBezierCurve c : path.getPath()) {
            toDraw.moveTo(adjust.d(c.startX), adjust.d(c.startY));
            toDraw.cubicTo(
                    adjust.d(c.control1X), adjust.d(c.control1Y),
                    adjust.d(c.control2X), adjust.d(c.control2Y),
                    adjust.d(c.endX), adjust.d(c.endY));
        }
        canvas.drawPath(toDraw, PAINT);
    }

    private static class Adjustment {
        private final double minDimension;

        private Adjustment(double minDimension) {
            this.minDimension = minDimension;
        }

        private float d(double toAdjust) {
            return (float) (minDimension * toAdjust);
        }
    }
}
