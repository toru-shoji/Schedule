package com.toro_studio.schedule.model;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import java.util.Date;
import java.util.List;

public class WeekCalViewModel {

    private int calendarWidth;
    private int calendarHeight;

    private int cachedX;
    private int cachedY;
    private Date displayDate;

    private List<Point> pointList;
    private RectF focusedTimeRect;
    private boolean isFocused;
    private Paint paint;
    private Paint pointPaint;
    private Paint focusedPaint;

    public final int getCalendarWidth() {
        return calendarWidth;
    }

    public final void setCalendarWidth(int calendarWidth) {
        this.calendarWidth = calendarWidth;
    }

    public final int getCalendarHeight() {
        return calendarHeight;
    }

    public final void setCalendarHeight(int calendarHeight) {
        this.calendarHeight = calendarHeight;
    }

    public final int getCachedX() {
        return cachedX;
    }

    public final void setCachedX(int cachedX) {
        this.cachedX = cachedX;
    }

    public final int getCachedY() {
        return cachedY;
    }

    public final void setCachedY(int cachedY) {
        this.cachedY = cachedY;
    }

    public final Date getDisplayDate() {
        return displayDate;
    }

    public final void setDisplayDate(Date displayDate) {
        this.displayDate = displayDate;
    }

    public final List<Point> getPointList() {
        return pointList;
    }

    public final void setPointList(List<Point> pointList) {
        this.pointList = pointList;
    }

    public final RectF getFocusedTimeRect() {
        return focusedTimeRect;
    }

    public final void setFocusedTimeRect(RectF focusedTimeRect) {
        this.focusedTimeRect = focusedTimeRect;
    }

    public final boolean isFocused() {
        return isFocused;
    }

    public final void setFocused(boolean focused) {
        isFocused = focused;
    }

    public final Paint getPaint() {
        return paint;
    }

    public final void setPaint(Paint paint) {
        this.paint = paint;
    }

    public final Paint getPointPaint() {
        return pointPaint;
    }

    public final void setPointPaint(Paint pointPaint) {
        this.pointPaint = pointPaint;
    }

    public final Paint getFocusedPaint() {
        return focusedPaint;
    }

    public final void setFocusedPaint(Paint focusedPaint) {
        this.focusedPaint = focusedPaint;
    }

}
