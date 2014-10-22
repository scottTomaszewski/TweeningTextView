package com.kokalabs.svg;

import com.google.common.collect.Lists;

import java.util.List;

public abstract class SvgPath {
    public static SvgPath from(final List<CubicBezierCurve> path) {
        return new SvgPath() {
            @Override
            public List<CubicBezierCurve> getPath() {
                return path;
            }
        };
    }

    public static SvgPath from(final String pathDescriptions, final double unitsPerEm) {
        return new SvgPath() {
            List<CubicBezierCurve> cached;

            @Override
            public List<CubicBezierCurve> getPath() {
                if (cached == null) {
                    SvgCommandAsCubicHandler toCubicBezierCurve = new SvgCommandAsCubicHandler();
                    new SvgPathParser(pathDescriptions).parseUsing(toCubicBezierCurve);
                    cached = Lists.newArrayList();
                    for (CubicBezierCurve c : toCubicBezierCurve.getPathAsCubicBezierCurves()) {
                        cached.add(c.normalizeWithMax(unitsPerEm * 2).flipAlongHorizontal());
                    }
                }
                return cached;
            }
        };
    }


    public static SvgPath origin() {
        return SvgPath.from(Lists.newArrayList(CubicBezierCurve.origin()));
    }

    public abstract List<CubicBezierCurve> getPath();
}
