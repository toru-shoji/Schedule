package com.toro_studio.schedule.presenters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.toro_studio.schedule.R;
import com.toro_studio.schedule.entities.Constants;
import com.toro_studio.schedule.model.WeekCalActivityModel;
import com.toro_studio.schedule.model.WeekCalViewPartsWeekModel;
import com.toro_studio.schedule.tools.CalendarTools;
import com.toro_studio.schedule.tools.Holiday;
import com.toro_studio.schedule.tools.PaintTools;
import com.toro_studio.schedule.tools.RectTools;
import com.toro_studio.schedule.databinding.PartsViewpagerBinding;
import com.toro_studio.schedule.views.WeekCalViewPartsWeek;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class WeekCalViewPartsWeekPresenter {

    private WeekCalActivityModel activityModel;
    private WeekCalViewPartsWeekModel model;
    private WeekCalViewPartsWeek view;

    public WeekCalViewPartsWeekPresenter(final Context context,
                                         final WeekCalActivityModel activityModel,
                                         final Date date) {
        this.activityModel = activityModel;
        model = createModel(context, date);
        view = createView(context, date);
    }

    public final void drawCanvas(final Canvas canvas) {
        if(null == canvas) {
            return;
        }
        canvas.drawRect(model.getDayRect(), model.getDayPaint());
        canvas.drawText(model.getDayCount(),
                model.getDayRect().centerX() - model.getDayCenterX() / 2,
                model.getDayRect().top + model.getDayCenterY(),
                model.getDayTextPaint());
        canvas.drawRect(model.getWeekRect(), model.getWeekPaint());
        canvas.drawText(model.getWeekCount(),
                model.getWeekRect().centerX() - model.getWeekCenterX() / 2,
                model.getWeekRect().top + model.getWeekCenterY(),
                model.getWeekTextPaint());
        if(model.isFocused()) {
            canvas.drawRect(
                    RectTools.newInstance()
                            .left(0)
                            .top(0)
                            .right((int) model.getDayRect().right)
                            .bottom((int) model.getWeekRect().bottom),
                    model.getFocusedPaint());
        }
    }

    public final void checkIsFocused(final Date date) {
        if(CalendarTools.getInstance().isSameDay(date, model.getDisplayDate())) {
            model.setFocused(true);
        }
    }

    public final View getWeekTableView() {
        return view;
    }

    private WeekCalViewPartsWeekModel createModel(final Context context, final Date date) {
        WeekCalViewPartsWeekModel model = new WeekCalViewPartsWeekModel();
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int dayHeight =
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48f, metrics);
        int weekHeight =
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, metrics);
        int itemWidth = (int)(screenWidth / 8f);
        model.setDayRect(RectTools.newInstance().left(0).top(0).right(itemWidth).bottom(dayHeight));
        model.setWeekRect(RectTools.newInstance().left(0).top(dayHeight).right(itemWidth).bottom(dayHeight + weekHeight));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        model.setDisplayDate(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        boolean isHoliday = Holiday.isHoliday(date);
        model.setDayCount(String.valueOf(calendar.get(Calendar.DATE)));
        model.setWeekCount(CalendarTools.getInstance().getDayOfWeekJapaneseText(calendar.getTime()));
        model.setHoliday(isHoliday);
        model.setDayPaint(PaintTools.newInstance().color(R.color.colorPrimary).style(Paint.Style.FILL_AND_STROKE));
        model.setDayTextPaint(PaintTools.newInstance().color(Color.WHITE).textSize(48));
        model.setFocusedPaint(PaintTools.newInstance().color(R.color.transYellow).style(Paint.Style.FILL));
        model.setClearPaint(PaintTools.newInstance().color(android.R.color.transparent).style(Paint.Style.FILL));
        if(Calendar.SUNDAY == week | isHoliday) {
            model.setWeekPaint(PaintTools.newInstance().color(Color.RED).style(Paint.Style.FILL_AND_STROKE));
            model.setWeekTextPaint(PaintTools.newInstance().color(Color.WHITE).textSize(24));
        } else if(Calendar.SATURDAY == week) {
            model.setWeekPaint(PaintTools.newInstance().color(Color.BLUE).style(Paint.Style.FILL_AND_STROKE));
            model.setWeekTextPaint(PaintTools.newInstance().color(Color.WHITE).textSize(24));
        } else {
            model.setWeekPaint(PaintTools.newInstance().color(Color.WHITE).style(Paint.Style.FILL_AND_STROKE));
            model.setWeekTextPaint(PaintTools.newInstance().color(Color.BLACK).textSize(24));
        }
        model.setDayCenterX(model.getDayTextPaint().measureText(model.getDayCount()));
        model.setDayCenterY((dayHeight - model.getDayTextPaint().descent() - model.getDayTextPaint().ascent()) / 2);
        model.setWeekCenterX(model.getWeekTextPaint().measureText(model.getWeekCount()));
        model.setWeekCenterY((weekHeight - model.getWeekTextPaint().descent() - model.getWeekTextPaint().ascent()) /2);
        return model;
    }

    private WeekCalViewPartsWeek createView(final Context context, final Date date) {
        if(null == context || null == date) {
            return null;
        }
        final WeekCalViewPartsWeek partsView =
                new WeekCalViewPartsWeek(context,this);
        partsView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, final MotionEvent event) {
                if(MotionEvent.ACTION_DOWN == event.getActionMasked()) {
                    model.setCachedX((int)event.getX());
                    model.setCachedY((int)event.getY());
                    return true;
                }
                if(MotionEvent.ACTION_UP == event.getActionMasked()) {
                    int releaseX = (int)Math.abs(event.getX() - model.getCachedX());
                    int releaseY = (int)Math.abs(event.getY() - model.getCachedY());
                    if(Constants.TOUCH_ERROR_VALUE < releaseX || Constants.TOUCH_ERROR_VALUE < releaseY) {
                        return false;
                    }
                    List<Date> dateList = activityModel.getDateList();
                    for(int count = 0; count < dateList.size(); count++) {
                        if(CalendarTools.getInstance().isSameDay(dateList.get(count), model.getDisplayDate())) {
                            if(CalendarTools.getInstance().isSameDay(model.getDisplayDate(), date)) {
                                if(model.isFocused()) {
                                    model.setFocused(false);
                                    activityModel.setFocusedDate(null);
                                } else {
                                    activityModel.setFocusedDate(date);
                                    setScrollX(view, count);
                                    model.setFocused(true);
                                }
                            } else {
                                model.setFocused(false);
                            }
                        }
                    }
                    notifyAdapter(view);
                }
                return false;
            }
        });
        return partsView;
    }

    private void setScrollX(final View view, final int position) {
        if(null == view) {
            return;
        }
        PartsViewpagerBinding binding = DataBindingUtil.findBinding(view);
        if(null == binding) {
            return;
        }
        ViewPager viewPager = (ViewPager)binding.getRoot().getParent();
        if(null == viewPager) {
            return;
        }
        viewPager.setCurrentItem(position - 3);
    }

    private void notifyAdapter(final View view) {
        if(null == view) {
            return;
        }
        PartsViewpagerBinding binding = DataBindingUtil.findBinding(view);
        if(null == binding) {
            return;
        }
        ViewPager viewPager = (ViewPager)binding.getRoot().getParent();
        if(null == viewPager) {
            return;
        }
        PagerAdapter pagerAdapter = viewPager.getAdapter();
        if(null == pagerAdapter) {
            return;
        }
        pagerAdapter.notifyDataSetChanged();

    }

}
