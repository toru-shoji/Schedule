package com.toro_studio.schedule.entities;

public class HolidayCalendarInfo4MonthView {

    private int year;
    private int month;
    private int day;
    private String holidayText;

    public final int getYear() {
        return year;
    }

    public final void setYear(int year) {
        this.year = year;
    }

    public final int getMonth() {
        return month;
    }

    public final void setMonth(int month) {
        this.month = month;
    }

    public final int getDay() {
        return day;
    }

    public final void setDay(int day) {
        this.day = day;
    }

    public final String getHolidayText() {
        return holidayText;
    }

    public final void setHolidayText(String holidayText) {
        this.holidayText = holidayText;
    }

    @Override
    public String toString() {
        return "HolidayInfo{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", holidayText='" + holidayText + '\'' +
                '}';
    }
}
