package com.toro_studio.schedule.views;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.toro_studio.schedule.R;
import com.toro_studio.schedule.databinding.PartsViewpagerBinding;
import com.toro_studio.schedule.model.WeekCalActivityModel;
import com.toro_studio.schedule.entities.Schedule;
import com.toro_studio.schedule.entities.Todo;
import com.toro_studio.schedule.presenters.WeekCalActivityPresenter;
import com.toro_studio.schedule.presenters.WeekCalViewPresenter;
import com.toro_studio.schedule.presenters.WeekCalViewPartsWeekPresenter;
import com.toro_studio.schedule.customview.CustomScrollView;

import java.util.Date;
import java.util.List;

import io.realm.Realm;


public class CustomViewPagerAdapter extends PagerAdapter {


    private Context context;
    private WeekCalActivityModel model;
    private Realm realm;
    private WeekCalActivityPresenter presenter;

    private ScrollView scrollView;

    private WeekCalViewPartsWeekPresenter partsPresenter;
    private WeekCalViewPresenter viewPresenter;

    public CustomViewPagerAdapter(
            final Context context,
            final WeekCalActivityPresenter presenter,
            final Realm realm,
            final WeekCalActivityModel model) {
        this.context = context;
        this.presenter = presenter;
        this.realm = realm;
        this.model = model;
    }

    @Override
    public final Object instantiateItem(final ViewGroup container, final int position) {
        Date date = model.getDateList().get(position);
        PartsViewpagerBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), R.layout.parts_viewpager, container, false);
        List<Todo> todoList = null;
        Schedule schedule = realm.where(Schedule.class).equalTo("date", date).findFirst();
        if(null != schedule) {
            todoList = schedule.getTodoList();
        }
        viewPresenter = new WeekCalViewPresenter(context, model, realm, date, todoList, position);
        binding.calendarbase.addView(viewPresenter.getCalendarWeekView());

        partsPresenter = new WeekCalViewPartsWeekPresenter(context, model, date);
        binding.daybase.addView(partsPresenter.getWeekTableView());

        partsPresenter.checkIsFocused(model.getFocusedDate());
        viewPresenter.checkIsFocused(model.getFocusedDate());

        binding.scrollView.setListener(new CustomScrollView.OnScrollViewListener() {
            @Override
            public void onScrollChanged(View view, int left, int top, int oldLeft, int oldTop) {
                presenter.getPartsPresenter().setScrollY(top);
            }
        });

        scrollView = binding.scrollView;
        container.addView(binding.getRoot());
        if(0 != model.getScrollY() & null != scrollView) {
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.scrollTo(0, model.getScrollY());
                }
            });
        }

        return binding.getRoot();
    }

    @Override
    public final void destroyItem(final ViewGroup container, final int position,
                                  final Object object) {
        container.removeView((View)object);
    }

    @Override
    public final int getItemPosition(final Object object) {
        int index = model.getDateList().indexOf(object);
        if(-1 == index) {
            return POSITION_NONE;
        } else {
            return index;
        }
    }

    @Override
    public final int getCount() {
        if(null != model.getDateList()) {
            return model.getDateList().size();
        }
        return 0;
    }

    @Override
    public final float getPageWidth(final int position) {
        return 1f / 7f;
    }

    @Override
    public final boolean isViewFromObject(final View view, final Object object) {
        return view == object;
    }

    public final void setFocusedTime(final int focusedTime) {
        model.setFocusedTime(focusedTime);
        if(null != viewPresenter) {
            viewPresenter.setFocusedTime(model.getFocusedTime());
        }
    }

}