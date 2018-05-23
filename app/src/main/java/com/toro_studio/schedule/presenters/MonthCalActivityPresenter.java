package com.toro_studio.schedule.presenters;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.toro_studio.schedule.R;
import com.toro_studio.schedule.views.CustomRecyclerViewAdapter;
import com.toro_studio.schedule.databinding.PartsCalendarRecyclerviewItemBinding;
import com.toro_studio.schedule.model.MonthCalActivityModel;
import com.toro_studio.schedule.databinding.ActivityCalendarMonthBinding;
import com.toro_studio.schedule.contract.ICalendarMonthView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.toro_studio.schedule.entities.Constants.CALENDAR_BACK;
import static com.toro_studio.schedule.entities.Constants.CALENDAR_INIT;
import static com.toro_studio.schedule.entities.Constants.CALENDAR_NEXT;
import static com.toro_studio.schedule.entities.Constants.TODAY;


public class MonthCalActivityPresenter {

    private MonthCalActivityModel model;
    private ICalendarMonthView views;
    private ActivityCalendarMonthBinding binding;
    private static MonthCalViewPresenter viewPresenter;

    public MonthCalActivityPresenter(final ICalendarMonthView views,
                                          final ActivityCalendarMonthBinding binding) {
        this.views = views;
        this.binding = binding;
        model = new MonthCalActivityModel();
        init();
    }

    @BindingAdapter("display")
    public static void setDisplay(final View view, final Date date) {
        if(null == date) {
            return;
        }
        ViewDataBinding binding = DataBindingUtil.findBinding(view);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        if(null != binding) {
            ((ActivityCalendarMonthBinding)binding).setText(
                    String.format(Locale.JAPAN,
                            view.getContext().getString(R.string.display_year_month_date),
                            year,
                            month));
        }
    }

    @BindingAdapter("calendar")
    public static void setCalendar(final View view, final Date date) {
        viewPresenter = new MonthCalViewPresenter(view.getContext(), date);
        ViewDataBinding binding = DataBindingUtil.getBinding(view);
        if(null != binding) {
            ((PartsCalendarRecyclerviewItemBinding)binding).calendarbase.removeAllViews();
            ((PartsCalendarRecyclerviewItemBinding)binding).calendarbase.addView(viewPresenter.getView());
        }
    }

    public final void notifyCalendar() {
        binding.recyclerView.getAdapter().notifyDataSetChanged();
    }

    public final MonthCalViewPresenter getViewPresenter() {
        return viewPresenter;
    }

    private void init() {
        model.setDateList(createMonthDateList(TODAY.getTime(), CALENDAR_INIT));
        initRecyclerView();
    }

    private List<Date> createMonthDateList(final Date date, final int direction) {
        if(null == date) {
            return null;
        }
        List<Date> tmpDateList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int createCount = -1;
        if(CALENDAR_BACK == direction) {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 5);
            createCount = 5;
        }
        if(CALENDAR_INIT == direction) {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 3);
            createCount = 7;
        }
        if(CALENDAR_NEXT == direction) {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
            createCount = 3;
        }
        for(int count = 0; count < createCount; count++) {
            tmpDateList.add(calendar.getTime());
            calendar.add(Calendar.MONTH, 1);
        }
        return tmpDateList;
    }

    private void initRecyclerView() {
        MonthCalViewPartsWeekPresenter presenter =
                new MonthCalViewPartsWeekPresenter(binding.getRoot().getContext());
        binding.weekdayArea.addView(presenter.getView());
        binding.recyclerView.setAdapter(new CustomRecyclerViewAdapter() {
            @Override
            protected Object getObjectForPosition(final int position) {
                return model.getDateList().get(position);
            }
            @Override
            protected int getLayoutIdForPosition(final int position) {
                return R.layout.parts_calendar_recyclerview_item;
            }
            @Override
            public int getItemCount() {
                return model.getDateList().size();
            }
        });
        binding.recyclerView.addOnScrollListener(new Scroll());
        binding.recyclerView.getLayoutManager().scrollToPosition(3);
    }

    private class Scroll extends RecyclerView.OnScrollListener {
        private int scrollValue;
        private boolean loading = false;
        private void fixCalendarTitle(final RecyclerView recyclerView, final int scrollValue) {
            if(null == recyclerView) {
                return;
            }
            GridLayoutManager mgr = (GridLayoutManager) recyclerView.getLayoutManager();
            int firstPosition = mgr.findFirstVisibleItemPosition();
            int lastPosition = mgr.findLastVisibleItemPosition();
            int firstCompPosition = mgr.findFirstCompletelyVisibleItemPosition();
            int lastCompPosition = mgr.findLastCompletelyVisibleItemPosition();
            Date date = null;
            if(-1 != firstCompPosition & -1 != lastCompPosition & firstCompPosition == lastCompPosition) {
                date = model.getDateList().get(firstCompPosition);
            }
            if((-1 == firstPosition & -1 == lastCompPosition) & 0 < scrollValue) {
                date = model.getDateList().get(firstPosition);
            }
            if((-1 == firstPosition & -1 == lastCompPosition) & scrollValue < 0) {
                date = model.getDateList().get(lastPosition);
            }
            binding.setDate(date);
        }

        @Override
        public void onScrollStateChanged(final RecyclerView recyclerView, final int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(RecyclerView.SCROLL_STATE_SETTLING == newState) {
                if(!loading) {
                    loading = true;
                }
                fixCalendarTitle(recyclerView, scrollValue);
            }
            if(RecyclerView.SCROLL_STATE_IDLE == newState) {
                if(!loading) {
                    loading = true;
                }
                fixCalendarTitle(recyclerView, scrollValue);
                scrollValue = 0;
            }
            if(RecyclerView.SCROLL_STATE_DRAGGING == newState) {
                fixCalendarTitle(recyclerView, scrollValue);
            }
        }

        @Override
        public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
            super.onScrolled(recyclerView, dx, dy);
            scrollValue += dy;
            int first = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            int last = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            if(0 == first) {
                if(loading) {
                    loading = false;
                    Date date = model.getDateList().get(first);
                    List<Date> tmpDateList = createMonthDateList(date, -1);
                    if(null != tmpDateList && !tmpDateList.isEmpty()) {
                        model.getDateList().addAll(0, tmpDateList);
                    }
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }
                    });
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((GridLayoutManager) recyclerView.getLayoutManager())
                                    .scrollToPositionWithOffset(7, 0);
                        }
                    }, 50);
                }
            }
            if((recyclerView.getAdapter().getItemCount()-1) == last) {
                Date date = model.getDateList().get(last);
                List<Date> dateList = createMonthDateList(date, 1);
                if(null != dateList && !dateList.isEmpty()) {
                    model.getDateList().addAll(dateList);
                }
                recyclerView.getLayoutManager().scrollToPosition(-3);
            }
            fixCalendarTitle(recyclerView, scrollValue);
        }
    }

}
