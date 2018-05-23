package com.toro_studio.schedule.presenters;

import android.content.Context;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;

import com.toro_studio.schedule.R;
import com.toro_studio.schedule.views.CustomViewPagerAdapter;
import com.toro_studio.schedule.databinding.ActivityCalendarWeekBinding;

import com.toro_studio.schedule.model.WeekCalActivityModel;
import com.toro_studio.schedule.tools.CalendarTools;
import com.toro_studio.schedule.tools.RectTools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;


public class WeekCalActivityPresenter {

    private Realm realm;
    private WeekCalActivityModel model;
    private ActivityCalendarWeekBinding binding;
    private WeekCalViewPartsTimePresenter partsPresenter;

    public WeekCalActivityPresenter(final ActivityCalendarWeekBinding binding, Intent intent) {
        this.binding = binding;
        realm = Realm.getDefaultInstance();
        Date date;
        if(null == intent || null == intent.getExtras() || null == intent.getExtras().get("date")) {
            Calendar calendar = Calendar.getInstance();
            date = calendar.getTime();
        } else {
            date = (Date)intent.getExtras().get("date");
        }
        model = createModel(binding, date);
        partsPresenter = new WeekCalViewPartsTimePresenter(binding, model);

        binding.timetable.addView(partsPresenter.getTimeTableView());

        scrolledTime(date);
        initCalendarWeekActivityToolbar(binding, date);
        addViewPagerAdapter();
        addViewPagerPageChangeListener();
    }

    private void initCalendarWeekActivityToolbar(final ActivityCalendarWeekBinding binding,
                                                 final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Context context = binding.getRoot().getContext();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        binding.toolbar.setTitle(String.format(Locale.JAPAN,context.getString(R.string.display_year_month_date), year, month));
        binding.toolbar.setSubtitle(String.format(Locale.JAPAN, context.getString(R.string.display_week_number), week));
    }

    public final WeekCalViewPartsTimePresenter getPartsPresenter() {
        return partsPresenter;
    }

    public final void scrolledTime(final Date date) {
        if(null == date) {
            return;
        }
        Calendar scrolledTime = Calendar.getInstance();
        scrolledTime.setTime(date);
        List<RectF> rectList = model.getRectList();
        int hourValue = scrolledTime.get(Calendar.HOUR_OF_DAY);
        int scrollPosition = (int)rectList.get(hourValue).top;
        partsPresenter.setScrollY(scrollPosition);
    }

    public final void closeRealm() {
        realm.close();
    }

    private WeekCalActivityModel createModel(final ActivityCalendarWeekBinding binding,
                                             final Date date) {
        if(null == binding || null == date) {
            return null;
        }
        WeekCalActivityModel model = new WeekCalActivityModel();
        Calendar todayCalendar = Calendar.getInstance();
        model.setFocusedDate(date);
        model.setFocusedTime(todayCalendar.get(Calendar.HOUR_OF_DAY));
        model.setRectList(createRectList(binding.getRoot().getContext()));
        model.setDateList(createDateList(date, 0));
        return model;
    }

    private void addViewPagerAdapter() {
        Context context = binding.getRoot().getContext();
        binding.viewPager.setAdapter(new CustomViewPagerAdapter(context, this, realm, model));
    }

    private void addViewPagerPageChangeListener() {
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int position = -1;
            @Override
            public void onPageScrolled(final int position, final float positionOffset,
                                       final int positionOffsetPixels) {
                this.position = position;
            }
            @Override
            public void onPageSelected(final int position) {
                this.position = position;
                if(0 == position) {
                    Date date = model.getDateList().get(position);
                    List<Date> dateList = model.getDateList();
                    List<Date> addList = createDateList(date, -1);
                    if(null != addList && !(addList.isEmpty())) {
                        dateList.addAll(0, addList);
                    }
                    model.setDateList(dateList);
                    ViewPager viewPager = binding.viewPager;
                    if(null != viewPager.getAdapter()) {
                        viewPager.getAdapter().notifyDataSetChanged();
                    }
                    binding.viewPager.invalidate();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            binding.viewPager.setCurrentItem(7, false);
                        }
                    }, 100);
                } else if(model.getDateList().size() - 7 <= position) {
                    Date date = model.getDateList().get(
                            position - Math.abs(model.getDateList().size() - 7 - position));
                    List<Date> dateList = model.getDateList();
                    List<Date> addList = createDateList(date, 1);
                    if(null != addList && !(addList.isEmpty())) {
                        dateList.addAll(addList);
                    }
                    model.setDateList(dateList);
                    binding.viewPager.invalidate();
                    binding.viewPager.getAdapter().notifyDataSetChanged();
                } else {
                    Date date = model.getDateList().get(position);
                    initCalendarWeekActivityToolbar(binding, date);
                }
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        int scrollY = model.getScrollY();
                        partsPresenter.setScrollY(scrollY);
                    }
                });
            }
            @Override
            public void onPageScrollStateChanged(final int state) {
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.viewPager.setCurrentItem(6, false);
            }
        }, 100);
    }

    private List<RectF> createRectList(final Context context) {
        if(null == context) {
            return null;
        }
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels / 8;
        int height = (metrics.heightPixels * 2) / 24;
        List<RectF> list = new ArrayList<>();
        int top = 0;
        for(int count = 0; count < 24; count++) {
            RectF rect = RectTools.newInstance().left(0).top(top).right(width).bottom(top + height);
            list.add(rect);
            top += height;
        }
        return list;
    }

    private List<Date> createDateList(final Date date, final int direction) {
        if(date == null) {
            return null;
        }
        int startDay = -1;
        int createCount = -1;
        if(-1 == direction) {
            startDay = -7;
            createCount = 7;
        }
        if(0 == direction) {
            startDay = -9;
            createCount = 21;
        }
        if(1 == direction) {
            startDay = 7;
            createCount = 7;
        }
        List<Date> tmpDateList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, startDay);
        for(int count = 0; count < createCount; count++) {
            Date tmpDate = CalendarTools.getInstance().createClearHourDate(calendar.getTime());
            tmpDateList.add(tmpDate);
            calendar.add(Calendar.DATE, 1);
        }
        return tmpDateList;
    }

}