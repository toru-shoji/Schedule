package com.toro_studio.schedule.presenters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.MotionEvent;

import com.toro_studio.schedule.views.MonthCalActivity;
import com.toro_studio.schedule.views.WeekCalActivity;
import com.toro_studio.schedule.R;
import com.toro_studio.schedule.entities.Constants;
import com.toro_studio.schedule.entities.HolidayCalendarInfo4MonthView;
import com.toro_studio.schedule.entities.HolidayViewInfo4MonthView;
import com.toro_studio.schedule.entities.Schedule;
import com.toro_studio.schedule.entities.WeekDayInfo4MonthView;
import com.toro_studio.schedule.model.MonthCalViewModel;
import com.toro_studio.schedule.tools.CalendarTools;
import com.toro_studio.schedule.tools.Holiday;
import com.toro_studio.schedule.tools.PaintTools;
import com.toro_studio.schedule.tools.RectTools;
import com.toro_studio.schedule.views.MonthCalView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.toro_studio.schedule.entities.Constants.CALENDAR_START_VALUE;
import static com.toro_studio.schedule.entities.Constants.LEFT_MARGIN;

public class MonthCalViewPresenter {

    private Realm realm;
    private MonthCalViewModel model;
    private MonthCalView views;

    public MonthCalViewPresenter(final Context context, final Date date) {
        realm = Realm.getDefaultInstance();
        model = createModel(context, date);
        views = createView(context);
    }

    public final MonthCalView getView() {
        return views;
    }

    public final void drawCanvas(final Canvas canvas) {
        if(null == canvas) {
            return;
        }
        if(null != model.getDayList() && !model.getDayList().isEmpty()) {
            for(WeekDayInfo4MonthView item : model.getDayList()) {
                if((Calendar.SATURDAY - CALENDAR_START_VALUE) == item.getDayOfWeek()) {
                    canvas.drawText(String.valueOf(item.getDayValue()), item.getX(), item.getY(), model.getSaturdayPaint());
                } else if((Calendar.SUNDAY - CALENDAR_START_VALUE) == item.getDayOfWeek()) {
                    canvas.drawText(String.valueOf(item.getDayValue()), item.getX(), item.getY(), model.getSaturdayPaint());
                } else {
                    canvas.drawText(String.valueOf(item.getDayValue()), item.getX(), item.getY(), model.getDayPaint());
                }
            }
        }
        if(null != model.getHolidayList() && !model.getHolidayList().isEmpty()) {
            for(HolidayViewInfo4MonthView item : model.getHolidayList()) {
                canvas.save();
                canvas.translate(item.getX(), item.getY());
                item.getStaticLayout().draw(canvas);
                canvas.restore();
            }
        }
        if(null != model.getPointList() && !model.getPointList().isEmpty()) {
            for(Point point : model.getPointList()) {
                canvas.drawCircle(point.x, point.y, 10, model.getPointPaint());
            }
        }
    }

    public final boolean touchAction(final MotionEvent event) {
        int action = event.getActionMasked();
        if(MotionEvent.ACTION_DOWN == action) {
            model.setCachedX(event.getX());
            model.setCachedY(event.getY());
            return true;
        }
        if(MotionEvent.ACTION_UP == action) {
            int releaseX = (int)Math.abs(event.getX() - model.getCachedX());
            int releaseY = (int)Math.abs(event.getY() - model.getCachedY());
            if(Constants.TOUCH_ERROR_VALUE < releaseX || Constants.TOUCH_ERROR_VALUE < releaseY) {
                return false;
            }
            int first = model.getFirst();
            for(int count = 0; count < model.getRectList().size(); count++) {
                if(model.getRectList().get(count).contains((int)event.getX(), (int)event.getY())) {
                    if(0 <= count - first & count - first < model.getDayList().size()) {
                        WeekDayInfo4MonthView weekDayInfo = model.getDayList().get(count - first);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(model.getDate());
                        calendar.set(Calendar.DATE, weekDayInfo.getDayValue());
                        Intent intent = WeekCalActivity.getIntent(views.getContext(), calendar.getTime());
                        ((MonthCalActivity)views.getContext()).gotoNextActivity(intent);
                        break;
                    }
                }
            }
        }
        return false;
    }

    public final void closeRealm() {
        realm.close();
    }

    private MonthCalViewModel createModel(final Context context, final Date date) {
        if(null == context || null == date) {
            return null;
        }
        MonthCalViewModel model = new MonthCalViewModel();
        model.setDate(date);
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        model.setScreenWidth(screenWidth);
        List<RectF> rectList = createDayRect(screenWidth);
        model.setRectList(rectList);
        model.setDayPaint(PaintTools.newInstance().color(Color.BLACK).textSize(24));
        model.setSaturdayPaint(PaintTools.newInstance().color(Color.BLUE).textSize(24));
        model.setSundayPaint(PaintTools.newInstance().color(Color.RED).textSize(24));
        model.setPointPaint(PaintTools.newInstance().color(R.color.transRed).style(Paint.Style.FILL_AND_STROKE));
        Date[] dateArray = CalendarTools.getInstance().createFirstLastMonthDate(date);
        if(null == dateArray || 0 == dateArray.length) {
            return null;
        }
        Date firstDate = dateArray[0];
        Date lastDate = dateArray[1];
        if(null == firstDate || null == lastDate) {
            return null;
        }
        int first = CalendarTools.getInstance().getFirstWeekDay(date);
        int max = CalendarTools.getInstance().getMonthOfDateValue(lastDate) + first;
        if(-1 != first & -1 != max) {
            model.setFirst(first);
            List<Point> pointList = createPointList(rectList, firstDate, lastDate, first);
            if(null != pointList && !pointList.isEmpty()) {
                model.setPointList(pointList);
            }
            List<WeekDayInfo4MonthView> dayList =
                    createDayInfoList(
                            rectList,
                            first,
                            max,
                            model.getDayPaint(),
                            model.getSaturdayPaint(),
                            model.getSundayPaint());
            if(null != dayList && !dayList.isEmpty()) {
                model.setDayList(dayList);
            }
            List<HolidayCalendarInfo4MonthView> holidayInfoList = getHolidayInfoList(date);
            List<HolidayViewInfo4MonthView> holidayViewInfoList = null;
            if(null != holidayInfoList && 0 != holidayInfoList.size()) {
                holidayViewInfoList = createHolidayInfoList(rectList, holidayInfoList, first, model.getDayPaint());
            }
            if(null != holidayViewInfoList && !holidayInfoList.isEmpty()) {
                model.setHolidayList(holidayViewInfoList);

            }
        }
        return model;
    }

    private MonthCalView createView(final Context context) {
        if(null == context) {
            return null;
        }
        return new MonthCalView(context, this);
    }

    private List<RectF> createDayRect(final int screenWidth) {
        int week = Constants.WEEK;
        int row = Constants.ROW;
        int total = Constants.TOTAL;
        List<RectF> rectList = new ArrayList<>();
        int widthSize = screenWidth / week;
        int heightSize = screenWidth / row;
        int adjustStartXPosition = (int)((screenWidth - widthSize * week) / 2f);
        int startX = adjustStartXPosition;
        int startY = 0;
        for(int count = 0; count < total; count++) {
            startX += widthSize;
            if(0 == count % week) {
                startX = adjustStartXPosition;
                if(0 != count) {
                    startY += heightSize;
                }
            }
            rectList.add(RectTools.newInstance()
                    .left(startX)
                    .top(startY)
                    .right(startX + widthSize)
                    .bottom(startY + heightSize));
        }
        return rectList;
    }

    private List<WeekDayInfo4MonthView> createDayInfoList(
            final List<RectF> rectList,
            final int first,
            final int max,
            final Paint dayPaint,
            final Paint saturdayPaint,
            final Paint sundayPaint) {
        List<WeekDayInfo4MonthView> dayList = new ArrayList<>();
        for(int count = first; count < max; count++) {
            RectF rect = rectList.get(count);
            Paint.FontMetrics fontMetrics;
            int dayOfWeek = -1;
            if((Calendar.SATURDAY - CALENDAR_START_VALUE) == count % Calendar.DAY_OF_WEEK) {
                fontMetrics = saturdayPaint.getFontMetrics();
                dayOfWeek = count % Calendar.DAY_OF_WEEK;
            } else if((Calendar.SUNDAY - CALENDAR_START_VALUE) == count % Calendar.DAY_OF_WEEK) {
                fontMetrics = sundayPaint.getFontMetrics();
                dayOfWeek = count % Calendar.DAY_OF_WEEK;
            } else {
                fontMetrics = dayPaint.getFontMetrics();
            }
            float height = fontMetrics.descent - fontMetrics.ascent;
            WeekDayInfo4MonthView day = new WeekDayInfo4MonthView(
                    (int)rect.left + LEFT_MARGIN,
                    (int)rect.top + (int)height,
                    (count - first) + 1,
                    dayOfWeek);
            dayList.add(day);
        }
        return dayList;
    }

    private List<HolidayViewInfo4MonthView> createHolidayInfoList(
            final List<RectF> rectList,
            final List<HolidayCalendarInfo4MonthView> holidayInfoList,
            final int first,
            final Paint dayPaint) {
        List<HolidayViewInfo4MonthView> holidayList = new ArrayList<>();
        for(HolidayCalendarInfo4MonthView info : holidayInfoList) {
            if(null == info.getHolidayText()) {
                continue;
            }
            int dayValue = info.getDay() + first -1;
            RectF rect = rectList.get(dayValue);
            TextPaint textPaint = new TextPaint(PaintTools.newInstance().color(Color.RED).textSize(18));
            float startX = rect.left + dayPaint.measureText(String.valueOf(dayValue)) + LEFT_MARGIN;
            StaticLayout holidayLayout = new StaticLayout(
                    info.getHolidayText(),
                    textPaint,
                    (int)(rect.width() - dayPaint.measureText(String.valueOf(dayValue))),
                    Layout.Alignment.ALIGN_NORMAL,
                    1.0f,
                    0.0f,
                    false);
            HolidayViewInfo4MonthView holiday = new HolidayViewInfo4MonthView(
                    (int)startX,
                    (int)rect.top + (int)textPaint.getFontMetrics().descent,
                    info.getDay() + LEFT_MARGIN,
                    info.getHolidayText(),
                    holidayLayout);
            holidayList.add(holiday);
        }
        return holidayList;
    }

    private List<HolidayCalendarInfo4MonthView> getHolidayInfoList(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        ArrayList<HolidayCalendarInfo4MonthView> holidayList = null;
        Date[] dateArray =
                Holiday.listHoliDayDates(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
        if(null != dateArray && 0 != dateArray.length) {
            holidayList = new ArrayList<>();
        }
        Calendar tmpCalendar = Calendar.getInstance();
        for(Date dateItem : dateArray) {
            HolidayCalendarInfo4MonthView holidayInfo = new HolidayCalendarInfo4MonthView();
            tmpCalendar.setTime(dateItem);
            holidayInfo.setYear(tmpCalendar.get(Calendar.YEAR));
            holidayInfo.setMonth(tmpCalendar.get(Calendar.MONTH));
            holidayInfo.setDay(tmpCalendar.get(Calendar.DATE));
            holidayInfo.setHolidayText(com.toro_studio.schedule.tools.Holiday.queryHoliday(dateItem));
            holidayList.add(holidayInfo);
        }
        return holidayList;
    }

    private List<Point> createPointList(final List<RectF> rectList,
                                        final Date firstDate,
                                        final Date lastDate,
                                        final int first) {
        if(null == rectList && rectList.isEmpty() || null == firstDate || null == lastDate) {
            return null;
        }
        RealmResults<Schedule> realmResults = realm.where(Schedule.class)
                .between("date", firstDate, lastDate)
                .findAll();
        if(null == realmResults || realmResults.isEmpty()) {
            return null;
        }
        List<Point> pointList = new ArrayList<>();
        for (Schedule schedule : realmResults) {
            Date tmpDate = schedule.getDate();
            Calendar tmpCalendar = Calendar.getInstance();
            tmpCalendar.setTime(tmpDate);
            int dayValue = tmpCalendar.get(Calendar.DAY_OF_MONTH) + first -1;
            RectF rect = rectList.get(dayValue);
            Point point = new Point((int)rect.centerX(), (int)rect.centerY());
            pointList.add(point);
        }
        return pointList;
    }

}
