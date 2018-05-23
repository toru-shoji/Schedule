package com.toro_studio.schedule.entities;

public class WeekDayInfo4MonthView {

    private int x;
    private int y;
    private int dayValue;
    private int dayOfWeek;

    public WeekDayInfo4MonthView(final int x, final int y, final int dayValue, final int dayOfWeek) {
        this.x = x;
        this.y = y;
        this.dayValue = dayValue;
        this.dayOfWeek = dayOfWeek;
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

    public final int getDayOfWeek() {
        return dayOfWeek;
    }

    public final void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public String toString() {
        return "Day{" +
                "x=" + x +
                ", y=" + y +
                ", dayValue=" + dayValue +
                ", dayOfWeek=" + dayOfWeek +
                '}';
    }
}
