package com.toro_studio.schedule.model;

import android.graphics.Paint;
import android.graphics.RectF;

import java.util.List;

public class MonthCalViewPartsWeekModel {

    private int screenWidth;

    private int itemWidth;
    private int areaMargin;
    private List<RectF> rectArray;

    private Paint paint;
    private Paint textPaint;
    private Paint holidayTextPaint;
    private Paint saturdayBackgroundPaint;
    private Paint sundayBackgroundPaint;

    public final int getScreenWidth() {
        return screenWidth;
    }

    public final void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public final int getItemWidth() {
        return itemWidth;
    }

    public final void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
    }

    public final int getAreaMargin() {
        return areaMargin;
    }

    public final void setAreaMargin(int areaMargin) {
        this.areaMargin = areaMargin;
    }

    public final List<RectF> getRectArray() {
        return rectArray;
    }

    public final void setRectArray(List<RectF> rectArray) {
        this.rectArray = rectArray;
    }

    public final Paint getPaint() {
        return paint;
    }

    public final void setPaint(Paint paint) {
        this.paint = paint;
    }

    public final Paint getTextPaint() {
        return textPaint;
    }

    public final void setTextPaint(Paint textPaint) {
        this.textPaint = textPaint;
    }

    public final Paint getHolidayTextPaint() {
        return holidayTextPaint;
    }

    public final void setHolidayTextPaint(Paint holidayTextPaint) {
        this.holidayTextPaint = holidayTextPaint;
    }

    public final Paint getSaturdayBackgroundPaint() {
        return saturdayBackgroundPaint;
    }

    public final void setSaturdayBackgroundPaint(Paint saturdayBackgroundPaint) {
        this.saturdayBackgroundPaint = saturdayBackgroundPaint;
    }

    public final Paint getSundayBackgroundPaint() {
        return sundayBackgroundPaint;
    }

    public final void setSundayBackgroundPaint(Paint sundayBackgroundPaint) {
        this.sundayBackgroundPaint = sundayBackgroundPaint;
    }
}
