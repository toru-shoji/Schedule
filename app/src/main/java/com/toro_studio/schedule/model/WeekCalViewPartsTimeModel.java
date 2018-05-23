package com.toro_studio.schedule.model;

import android.graphics.Paint;
import android.graphics.RectF;

public class WeekCalViewPartsTimeModel {

    private Paint paint;
    private Paint fontPaint;
    private Paint focusPaint;
    private RectF focusedRect;

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Paint getFontPaint() {
        return fontPaint;
    }

    public void setFontPaint(Paint fontPaint) {
        this.fontPaint = fontPaint;
    }

    public Paint getFocusPaint() {
        return focusPaint;
    }

    public void setFocusPaint(Paint focusPaint) {
        this.focusPaint = focusPaint;
    }

    public RectF getFocusedRect() {
        return focusedRect;
    }

    public void setFocusedRect(RectF focusedRect) {
        this.focusedRect = focusedRect;
    }
}
