package com.toro_studio.schedule.model;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import com.toro_studio.schedule.entities.HolidayViewInfo4MonthView;
import com.toro_studio.schedule.entities.WeekDayInfo4MonthView;

import java.util.Date;
import java.util.List;

public class MonthCalViewModel {

    private int screenWidth;
    private int first;
    private int max;

    private float cachedX;
    private float cachedY;

    private Date date;

    private List<RectF> rectList;
    private List<WeekDayInfo4MonthView> dayList;
    private List<HolidayViewInfo4MonthView> holidayList;

    private List<Point> pointList;

    private Paint dayPaint;
    private Paint saturdayPaint;
    private Paint sundayPaint;
    private Paint pointPaint;

    public final int getScreenWidth() {
        return screenWidth;
    }

    public final void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public final int getFirst() {
        return first;
    }

    public final void setFirst(int first) {
        this.first = first;
    }

    public final int getMax() {
        return max;
    }

    public final void setMax(int max) {
        this.max = max;
    }

    public final float getCachedX() {
        return cachedX;
    }

    public final void setCachedX(float cachedX) {
        this.cachedX = cachedX;
    }

    public final float getCachedY() {
        return cachedY;
    }

    public final void setCachedY(float cachedY) {
        this.cachedY = cachedY;
    }

    public final Date getDate() {
        return date;
    }

    public final void setDate(Date date) {
        this.date = date;
    }

    public final List<RectF> getRectList() {
        return rectList;
    }

    public final void setRectList(List<RectF> rectList) {
        this.rectList = rectList;
    }

    public final List<WeekDayInfo4MonthView> getDayList() {
        return dayList;
    }

    public final void setDayList(List<WeekDayInfo4MonthView> dayList) {
        this.dayList = dayList;
    }

    public final List<HolidayViewInfo4MonthView> getHolidayList() {
        return holidayList;
    }

    public final void setHolidayList(List<HolidayViewInfo4MonthView> holidayList) {
        this.holidayList = holidayList;
    }

    public final List<Point> getPointList() {
        return pointList;
    }

    public final void setPointList(List<Point> pointList) {
        this.pointList = pointList;
    }

    public final Paint getDayPaint() {
        return dayPaint;
    }

    public final void setDayPaint(Paint dayPaint) {
        this.dayPaint = dayPaint;
    }

    public final Paint getSaturdayPaint() {
        return saturdayPaint;
    }

    public final void setSaturdayPaint(Paint saturdayPaint) {
        this.saturdayPaint = saturdayPaint;
    }

    public final Paint getSundayPaint() {
        return sundayPaint;
    }

    public final void setSundayPaint(Paint sundayPaint) {
        this.sundayPaint = sundayPaint;
    }

    public final Paint getPointPaint() {
        return pointPaint;
    }

    public final void setPointPaint(Paint pointPaint) {
        this.pointPaint = pointPaint;
    }

}
