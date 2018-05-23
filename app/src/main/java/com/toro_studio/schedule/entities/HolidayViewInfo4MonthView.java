package com.toro_studio.schedule.entities;

import android.text.StaticLayout;

public class HolidayViewInfo4MonthView {

    private int x;
    private int y;
    private int dayValue;
    private String holidayName;
    private StaticLayout staticLayout;

    public HolidayViewInfo4MonthView(final int x,
                                     final int y,
                                     final int dayValue,
                                     final String holidayName,
                                     final StaticLayout staticLayout) {
        this.x = x;
        this.y = y;
        this.dayValue = dayValue;
        this.holidayName = holidayName;
        this.staticLayout = staticLayout;
    }

    public final int getX() {
        return x;
    }

    public final void setX(int x) {
        this.x = x;
    }

    public final int getY() {
        return y;
    }

    public final void setY(int y) {
        this.y = y;
    }

    public final int getDayValue() {
        return dayValue;
    }

    public final void setDayValue(int dayValue) {
        this.dayValue = dayValue;
    }

    public final String getHolidayName() {
        return holidayName;
    }

    public final void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public final StaticLayout getStaticLayout() {
        return staticLayout;
    }

    public final void setStaticLayout(StaticLayout staticLayout) {
        this.staticLayout = staticLayout;
    }

    @Override
    public String toString() {
        return "Holiday{" +
                "x=" + x +
                ", y=" + y +
                ", dayValue=" + dayValue +
                ", holidayName='" + holidayName + '\'' +
                '}';
    }

}
