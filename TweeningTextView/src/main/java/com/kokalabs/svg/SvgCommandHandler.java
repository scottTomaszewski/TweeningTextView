package com.kokalabs.svg;

import com.google.common.base.Preconditions;

abstract class SvgCommandHandler {
    private double lastX = -1.0;
    private double lastY = -1.0;
    private double firstX = -1.0;
    private double firstY = -1.0;
    private PointD nextControlPoint;

    protected final double lastX() {
        return lastX;
    }

    protected final double lastY() {
        return lastY;
    }

    protected final double firstX() {
        return firstX;
    }

    protected final double firstY() {
        return firstY;
    }

    protected final double nextControlX() {
        return nextControlPoint.x;
    }

    protected final double nextControlY() {
        return nextControlPoint.y;
    }

    final void process_M(double endX, double endY) {
        handle_M(endX, endY);
        lastX = endX;
        lastY = endY;
        firstX = endX;
        firstY = endY;
        nextControlPoint = new PointD(endX, endY);
    }

    final void process_m(double d_endX, double d_endY) {
        checkHasLast();
        handle_m(d_endX, d_endY);
        lastX = lastX + d_endX;
        lastY = lastY + d_endY;
        firstX = lastX + d_endX;
        firstY = lastY + d_endY;
        nextControlPoint = new PointD(lastX + d_endX, lastY + d_endY);
    }

    final void process_L(double endX, double endY) {
        handle_L(endX, endY);
        lastX = endX;
        lastY = endY;
        nextControlPoint = new PointD(endX, endY);
    }

    final void process_l(double d_endX, double d_endY) {
        checkHasLast();
        handle_l(d_endX, d_endY);
        lastX = lastX + d_endX;
        lastY = lastY + d_endY;
        nextControlPoint = new PointD(lastX + d_endX, lastY + d_endY);
    }

    final void process_H(double endX) {
        process_L(endX, lastY);
    }

    final void process_h(double d_endX) {
        checkHasLast();
        process_H(lastX + d_endX);
    }

    final void process_V(double endY) {
        process_L(lastX, endY);
    }

    final void process_v(double d_endY) {
        checkHasLast();
        process_V(lastY + d_endY);
    }

    final void process_Z() {
        checkHasFirst();
        process_L(firstX(), firstY());
    }

    final void process_C(double control1X, double control1Y,
                         double control2X, double control2Y,
                         double endX, double endY) {
        handle_C(control1X, control1Y, control2X, control2Y, endX, endY);
        lastX = endX;
        lastY = endY;
        nextControlPoint = new PointD(endX * 2 - control2X, endY * 2 - control2Y);
    }

    final void process_c(double d_control1X, double d_control1Y,
                         double d_control2X, double d_control2Y,
                         double d_endX, double d_endY) {
        checkHasLast();
        process_C(lastX + d_control1X, lastY + d_control1Y,
                lastX + d_control2X, lastY + d_control2Y,
                lastX + d_endX, lastY + d_endY);
    }

    final void process_S(double control2X, double control2Y, double endX, double endY) {
        checkHasLastControlPoint();
        handle_S(control2X, control2Y, endX, endY);
        lastX = endX;
        lastY = endY;
        nextControlPoint = new PointD(endX * 2 - control2X, endY * 2 - control2Y);
    }

    final void process_s(double d_control2X, double d_control2Y, double d_endX, double d_endY) {
        checkHasLastControlPoint();
        checkHasLast();
        process_S(lastX + d_control2X, lastY + d_control2Y, lastX + d_endX, lastY + d_endY);
    }

    final void process_Q(double controlX, double controlY, double endX, double endY) {
        handle_Q(controlX, controlY, endX, endY);
        nextControlPoint = new PointD(endX * 2 - controlX, endY * 2 - controlY);
        lastX = endX;
        lastY = endY;
    }

    final void process_q(double d_controlX, double d_controlY, double d_endX, double d_endY) {
        checkHasLast();
        process_Q(lastX + d_controlX, lastY + d_controlY, lastX + d_endX, lastY + d_endY);
    }

    final void process_T(double endX, double endY) {
        checkHasLastControlPoint();
        handle_T(endX, endY);
        lastX = endX;
        lastY = endY;
        nextControlPoint = new PointD(endX * 2 - nextControlPoint.x, endY * 2 - nextControlPoint.y);
    }

    final void process_t(double d_endX, double d_endY) {
        checkHasLastControlPoint();
        checkHasLast();
        process_T(lastX + d_endX, lastY + d_endY);
    }

    private void checkHasLast() {
        Preconditions.checkArgument(lastX != -1);
        Preconditions.checkArgument(lastY != -1);
    }

    private void checkHasFirst() {
        Preconditions.checkArgument(firstX != -1);
        Preconditions.checkArgument(firstY != -1);
    }

    private void checkHasLastControlPoint() {
        Preconditions.checkNotNull(nextControlPoint);
    }

    // implementations

    protected abstract void handle_M(double endX, double endY);

    protected abstract void handle_m(double d_endX, double d_endY);

    protected abstract void handle_L(double endX, double endY);

    protected abstract void handle_l(double d_endX, double d_endY);

    protected abstract void handle_C(double control1X, double control1Y,
                                     double control2x, double control2Y,
                                     double endX, double endY);

    protected abstract void handle_S(double control2x, double control2Y, double endX, double endY);

    protected abstract void handle_Q(double controlX, double controlY, double endX, double endY);

    protected abstract void handle_T(double endX, double endY);
}

