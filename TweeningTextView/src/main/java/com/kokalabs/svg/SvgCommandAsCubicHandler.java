package com.kokalabs.svg;

import com.google.common.collect.Lists;

import java.util.List;

public class SvgCommandAsCubicHandler extends SvgCommandHandler {
    private final List<CubicBezierCurve> all = Lists.newArrayList();

    @Override
    protected void handle_M(double endX, double endY) {
    }

    @Override
    protected void handle_m(double d_endX, double d_endY) {
    }

    @Override
    protected void handle_L(double endX, double endY) {
        all.add(new CubicBezierCurve(lastX(), lastY(), lastX(), lastY(),
                endX, endY, endX, endY));
    }

    @Override
    protected void handle_l(double d_endX, double d_endY) {
        all.add(new CubicBezierCurve(lastX(), lastY(), lastX(), lastY(),
                lastX() + d_endX, lastY() + d_endY, lastX() + d_endX, lastY() + d_endY));
    }

    @Override
    protected void handle_C(double control1X, double control1Y,
                            double control2X, double control2Y,
                            double endX, double endY) {
        all.add(new CubicBezierCurve(lastX(), lastY(), control1X, control1Y,
                control2X, control2Y, endX, endY));
    }


    @Override
    protected void handle_S(double control2X, double control2Y, double endX, double endY) {
        all.add(new CubicBezierCurve(lastX(), lastY(), nextControlX(), nextControlY(),
                control2X, control2Y, endX, endY));
    }

    @Override
    protected void handle_Q(double controlX, double controlY, double endX, double endY) {
        double c1X = lastX() + (2.0 / 3.0) * (controlX - lastX());
        double c1Y = lastY() + (2.0 / 3.0) * (controlY - lastY());
        double c2X = endX + (2.0 / 3.0) * (controlX - endX);
        double c2Y = endY + (2.0 / 3.0) * (controlY - endY);
        all.add(new CubicBezierCurve(lastX(), lastY(), c1X, c1Y,
                c2X, c2Y, endX, endY));
    }


    @Override
    protected void handle_T(double endX, double endY) {
        handle_Q(nextControlX(), nextControlY(), endX, endY);
    }

    public List<CubicBezierCurve> getPathAsCubicBezierCurves() {
        return all;
    }
}
