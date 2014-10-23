package com.kokalabs.tweening.textview.example;

import com.kokalabs.svg.SvgPath;

public class Char {
    private static final String A = "M0 0l588 1468h65l576 -1468h-115l-203 516h-594l-204 -516h-113zM354 608h523l-199 527q-25 62 -60 172q-27 -96 -59 -174z";
    private static final String C = "M129 735q0 223 84.5 393t243 262.5t368.5 92.5q214 0 383 -80l-41 -92q-160 80 -336 80q-275 0 -433 -176t-158 -482q0 -313 149 -486t426 -173q184 0 338 47v-90q-145 -51 -362 -51q-308 0 -485 199t-177 556z";
    private static final String D = "M1317 745q0 -368 -193 -556.5t-567 -188.5h-350v1462h395q350 0 532.5 -183t182.5 -534zM1206 741q0 314 -159.5 472.5t-468.5 158.5h-269v-1282h242q655 0 655 651z";
    private static final String E = "M207 0v1462h799v-94h-697v-553h658v-94h-658v-627h697v-94h-799z";

    static final SvgPath from = SvgPath.from(E, 2048);
    static final SvgPath to = SvgPath.from(C, 2048);

    static final SvgPath at(int position) {
        switch (position) {
            case 0:
                return SvgPath.from(A, 2048);
            case 1:
                return SvgPath.from(C, 2048);
            case 2:
                return SvgPath.from(D, 2048);
            case 3:
                return SvgPath.from(E, 2048);
            default:
                return SvgPath.from(A, 2048);
        }
    }
}
