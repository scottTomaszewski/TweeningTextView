package com.kokalabs.tweening.textview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;

import com.kokalabs.svg.CubicBezierCurve;
import com.kokalabs.svg.PointD;
import com.kokalabs.svg.SvgPath;
import com.kokalabs.svg.SvgPathTweenViaInterpolation;

public class TweeningTextView extends View {
    private static final double SCALE = 0.5;
    private static final Paint PAINT = paint();

    private static final Property<TweeningTextView, SvgPath> PATH_POINTS =
            new Property<TweeningTextView, SvgPath>(SvgPath.class, "path") {
                @Override
                public SvgPath get(TweeningTextView view) {
                    return view.path;
                }

                @Override
                public void set(TweeningTextView view, SvgPath value) {
                    view.path = value;
                    view.invalidate();
                }
            };

    private SvgPath path;

    private static Paint paint() {
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(Color.BLACK);
        p.setStrokeWidth(5.0f);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
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

    public ObjectAnimator animate(SvgPath start, SvgPath end) {
        return ObjectAnimator.ofObject(this, PATH_POINTS, new SvgPathTweenViaInterpolation(), start, end);
    }

    public ObjectAnimator animate(SvgPath end) {
        return animate(SvgPath.origin(), end);
    }

    private void drawTweenedText(Canvas canvas) {
        if (path == null) {
            return;
        }
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        Adjustment adjust = new Adjustment((float) (height > width ? width : height));

        Path toDraw = new Path();
        toDraw.reset();
        toDraw.setFillType(Path.FillType.EVEN_ODD);
        PointD last = new PointD(-1, -1);
        for (CubicBezierCurve c : path.getPath()) {
            if (last.x != c.startX || last.y != c.startY) {
                toDraw.close();
                toDraw.moveTo(adjust.d(c.startX), adjust.d(c.startY));
            }
            toDraw.cubicTo(
                    adjust.d(c.control1X), adjust.d(c.control1Y),
                    adjust.d(c.control2X), adjust.d(c.control2Y),
                    adjust.d(c.endX), adjust.d(c.endY));
            last = new PointD(c.endX, c.endY);
        }
        toDraw.close();
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
