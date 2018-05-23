package com.toro_studio.schedule.tools;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class CalendarTools {

    private CalendarTools() {}

    private static final class CalendarToolsHolder {
        private static final CalendarTools instance = new CalendarTools();
    }

    public static CalendarTools getInstance() {
        return CalendarToolsHolder.instance;
    }

    public final boolean isSameDay(Date date1, Date date2) {
        if(null == date1 | null == date2) {
            return false;
        }
        date1 = DateTools.newInstance().now(date1).hour(0).minute(0).second(0).milliSecond(0);
        date2 = DateTools.newInstance().now(date2).hour(0).minute(0).second(0).milliSecond(0);
        if(date1.equals(date2)) {
            return true;
        }
        return false;
    }

    public final String getDayOfWeekJapaneseText(final int dayOfWeekNo) {
        if(dayOfWeekNo < 0 | 6 < dayOfWeekNo) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        int errorValue = (dayOfWeekNo + 1) - calendar.get(Calendar.DAY_OF_WEEK); //7
        calendar.add(Calendar.DAY_OF_MONTH, errorValue);
        return getDayOfWeekJapaneseText(calendar.getTime());
    }

    public final String getDayOfWeekJapaneseText(final Date date) {
        if(null == date) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(
                "E",
                new Locale("ja", "jp", "jp"));
        return sdf.format(date);
    }

    public final int getFirstWeekDay(final Date date) {
        if(null == date) {
            return -1;
        }
        Date tmpDate = createFirstMonthDate(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tmpDate);
        return calendar.get(Calendar.DAY_OF_WEEK) -1;
    }

    public final int getMonthOfDateValue(final Date date) {
        if(null == date) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public final Date[] createFirstLastMonthDate(final Date date) {
        if(null == date) {
            return null;
        }
        Date firstDate = createFirstMonthDate(date);
        Date lastDate = createLastMonthDate(date);
        if(null != firstDate & null != lastDate) {
            return new Date[]{firstDate, lastDate};
        }
        return null;
    }

    public final Date createClearHourDate(final Date date) {
        if(null == date) {
            return null;
        }
        return DateTools.newInstance().now(date).hour(0).minute(0).second(0).milliSecond(0);
    }

    public final Date createClearMinuteDate(final Date date) {
        if(null == date) {
            return null;
        }
        return DateTools.newInstance().now(date).minute(0).second(0).milliSecond(0);
    }

    private Date createFirstMonthDate(final Date date) {
        if(null == date) {
            return null;
        }
        return DateTools.newInstance().now(date).day(1).hour(0).minute(0).second(0).milliSecond(0);
    }

    private Date createLastMonthDate(final Date date) {
        if(null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int lastValue = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return DateTools.newInstance().day(lastValue).hour(23).minute(59).second(59).milliSecond(999);
    }

    public static class DateTools extends Date {

        public static DateTools newInstance() {
            return new DateTools();
        }

        public final DateTools now(final Date date) {
            Calendar calendar = Calendar.getInstance();
            if(null == date) {
                setTime(calendar.getTime().getTime());
            } else {
                setTime(date.getTime());
            }
            return DateTools.this;
        }

        public final DateTools month(final int monthValue) {
            Calendar calnedar = Calendar.getInstance();
            calnedar.setTime(this);
            calnedar.set(Calendar.MONTH, monthValue);
            this.setTime(calnedar.getTime().getTime());
            return this;
        }

        public final DateTools day(final int dayValue) {
            Calendar calnedar = Calendar.getInstance();
            calnedar.setTime(this);
            calnedar.set(Calendar.DAY_OF_MONTH, dayValue);
            this.setTime(calnedar.getTime().getTime());
            return this;
        }

        public final DateTools hour(final int hour24Value) {
            Calendar calnedar = Calendar.getInstance();
            calnedar.setTime(this);
            calnedar.set(Calendar.HOUR_OF_DAY, hour24Value);
            this.setTime(calnedar.getTime().getTime());
            return this;
        }

        public final DateTools minute(final int minuteValue) {
            Calendar calnedar = Calendar.getInstance();
            calnedar.setTime(this);
            calnedar.set(Calendar.MINUTE, minuteValue);
            this.setTime(calnedar.getTime().getTime());
            return this;
        }

        public final DateTools second(final int secondValue) {
            Calendar calnedar = Calendar.getInstance();
            calnedar.setTime(this);
            calnedar.set(Calendar.SECOND, secondValue);
            this.setTime(calnedar.getTime().getTime());
            return this;
        }

        public final DateTools milliSecond(final int milliSecondValue) {
            Calendar calnedar = Calendar.getInstance();
            calnedar.setTime(this);
            calnedar.set(Calendar.MILLISECOND, milliSecondValue);
            this.setTime(calnedar.getTime().getTime());
            return this;
        }

    }

}
