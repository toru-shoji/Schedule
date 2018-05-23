package com.toro_studio.schedule.tools;

import android.graphics.Paint;


public class PaintTools extends Paint {

    private PaintTools() {
        setAntiAlias(true);
    }

    public static PaintTools newInstance() {
        return new PaintTools();
    }

    public final PaintTools color(final int color) {
        setColor(color);
        return this;
    }

    public final PaintTools textSize(final int textSize) {
        setTextSize(textSize);
        return this;
    }

    public final PaintTools style(final Paint.Style style) {
        if(null == style) {
            return this;
        }
        setStyle(style);
        return this;
    }

}
