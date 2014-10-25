package com.kokalabs.svg;

import android.animation.TypeEvaluator;

import com.google.common.collect.Lists;

import java.util.List;

public class SvgGlyphTweenViaInterpolation implements TypeEvaluator<SvgGlyph> {
    @Override
    public SvgGlyph evaluate(float fraction, SvgGlyph startPath, SvgGlyph endPath) {
        List<CubicBezierCurve> starts = startPath.getPath();
        List<CubicBezierCurve> ends = endPath.getPath();

        int max = Math.max(starts.size(), ends.size());
        fill(starts, max);
        fill(ends, max);

        List<CubicBezierCurve> tweened = Lists.newArrayList();
        for (int i = 0; i < max; i++) {
            tweened.add(tween(fraction, starts.get(i), ends.get(i)));
        }
        return SvgGlyph.from(tweened);
    }

    private void fill(List<CubicBezierCurve> path, int toFill) {
        CubicBezierCurve last = path.get(path.size() - 1);
        CubicBezierCurve lastPoint = new CubicBezierCurve(
                last.endX, last.endY, last.endX, last.endY, last.endX, last.endY, last.endX, last.endY);
        CubicBezierCurve filler = path.size() != 0 ? lastPoint : CubicBezierCurve.origin();
        while (path.size() < toFill) {
            path.add(filler);
        }
    }

    private CubicBezierCurve tween(float fraction, CubicBezierCurve start, CubicBezierCurve end) {
        double sX = tween(fraction, start.startX, end.startX);
        double sY = tween(fraction, start.startY, end.startY);
        double c1X = tween(fraction, start.control1X, end.control1X);
        double c1Y = tween(fraction, start.control1Y, end.control1Y);
        double c2X = tween(fraction, start.control2X, end.control2X);
        double c2Y = tween(fraction, start.control2Y, end.control2Y);
        double eX = tween(fraction, start.endX, end.endX);
        double eY = tween(fraction, start.endY, end.endY);
        return new CubicBezierCurve(sX, sY, c1X, c1Y, c2X, c2Y, eX, eY);
    }

    private double tween(double fraction, double start, double end) {
        return start + fraction * (end - start);
    }
}