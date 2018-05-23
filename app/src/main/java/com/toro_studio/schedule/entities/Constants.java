package com.toro_studio.schedule.entities;

import java.util.Calendar;

public class Constants {

    private Constants() {}

    public static Calendar TODAY = Calendar.getInstance();

    public static final int REQUEST_CODE = 200;

    public static final int CALENDAR_BACK = -1;
    public static final int CALENDAR_INIT = 0;
    public static final int CALENDAR_NEXT = 1;

    public static final int WEEK = 7;
    public static final int ROW = 6;
    public static final int TOTAL = WEEK * ROW;

    public static final int TOUCH_ERROR_VALUE = 50;
    public static final int CALENDAR_START_VALUE = 1;

    public static final int LEFT_MARGIN = 4;

}
