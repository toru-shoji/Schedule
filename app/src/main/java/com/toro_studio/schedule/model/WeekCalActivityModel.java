package com.toro_studio.schedule.model;

import android.graphics.RectF;

import java.util.Date;
import java.util.List;

public class WeekCalActivityModel {

    private Date focusedDate;

    private List<RectF> rectList;
    private List<Date> dateList;

    private int focusedTime;
    private int scrollY;

    public final Date getFocusedDate() {
        return focusedDate;
    }

    public final void setFocusedDate(Date focusedDate) {
        this.focusedDate = focusedDate;
    }

    public final List<RectF> getRectList() {
        return rectList;
    }

    public final void setRectList(List<RectF> rectList) {
        this.rectList = rectList;
    }

    public final List<Date> getDateList() {
        return dateList;
    }

    public final void setDateList(List<Date> dateList) {
        this.dateList = dateList;
    }

    public final int getFocusedTime() {
        return focusedTime;
    }

    public final void setFocusedTime(int focusedTime) {
        this.focusedTime = focusedTime;
    }

    public final int getScrollY() {
        return scrollY;
    }

    public final void setScrollY(int scrollY) {
        this.scrollY = scrollY;
    }

}
