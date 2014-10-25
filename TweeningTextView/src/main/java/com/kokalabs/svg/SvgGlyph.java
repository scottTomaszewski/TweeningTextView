package com.kokalabs.svg;

import com.google.common.collect.Lists;

import java.util.List;

public abstract class SvgGlyph {
    public static SvgGlyph from(final List<CubicBezierCurve> path) {
        return new SvgGlyph() {
            @Override
            public List<CubicBezierCurve> getPath() {
                return path;
            }
        };
    }

    public static SvgGlyph from(final String pathDescriptions, final double unitsPerEm) {
        return new SvgGlyph() {
            List<CubicBezierCurve> cached;

            @Override
            public List<CubicBezierCurve> getPath() {
                if (cached == null) {
                    SvgCommandAsCubicHandler toCubicBezierCurve = new SvgCommandAsCubicHandler();
                    new SvgGlyphParser(pathDescriptions).parseUsing(toCubicBezierCurve);
                    cached = Lists.newArrayList();
                    for (CubicBezierCurve c : toCubicBezierCurve.getPathAsCubicBezierCurves()) {
                        cached.add(c.normalizeWithMax(unitsPerEm * 2).flipAlongHorizontal());
                    }
                }
                return cached;
            }
        };
    }


    public static SvgGlyph origin() {
        return SvgGlyph.from(Lists.newArrayList(CubicBezierCurve.origin()));
    }

    public abstract List<CubicBezierCurve> getPath();
}
