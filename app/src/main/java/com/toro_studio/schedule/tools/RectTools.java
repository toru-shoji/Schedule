package com.toro_studio.schedule.tools;

import android.graphics.RectF;


public class RectTools extends RectF {

    private RectTools(final float left, final float top, final float right, final float bottom) {
        super(left, top, right, bottom);
    }

    public static RectTools newInstance() {
        return new RectTools(0, 0, 0, 0);
    }

    public final RectTools left(final int leftValue) {
        left = leftValue;
        return this;
    }

    public final RectTools top(final int topValue) {
        top = topValue;
        return this;
    }

    public final RectTools right(final int rightValue) {
        right = rightValue;
        return this;
    }

    public final RectTools bottom(final int bottomValue) {
        bottom = bottomValue;
        return this;
    }

}