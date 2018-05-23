package com.toro_studio.schedule.presenters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import com.toro_studio.schedule.R;
import com.toro_studio.schedule.views.CustomViewPagerAdapter;
import com.toro_studio.schedule.databinding.ActivityCalendarWeekBinding;
import com.toro_studio.schedule.model.WeekCalActivityModel;
import com.toro_studio.schedule.model.WeekCalViewPartsTimeModel;
import com.toro_studio.schedule.tools.PaintTools;
import com.toro_studio.schedule.views.WeekCalViewPartsTime;

import java.util.Locale;


public class WeekCalViewPartsTimePresenter {

    private ActivityCalendarWeekBinding binding;
    private WeekCalActivityModel activityModel;
    private WeekCalViewPartsTimeModel model;
    private WeekCalViewPartsTime view;

    public WeekCalViewPartsTimePresenter(final ActivityCalendarWeekBinding binding,
                                         final WeekCalActivityModel activityModel) {
        this.binding = binding;
        this.activityModel = activityModel;
        model = createModel();
        view = createView(binding.getRoot().getContext());
    }

    public final void drawCanvas(final Canvas canvas) {
        if(null == canvas) {
            return;
        }
        for(int count = 0; count < activityModel.getRectList().size(); count++) {
            RectF item = activityModel.getRectList().get(count);
            float itemWidth = model.getFontPaint().measureText(String.format(Locale.JAPAN, "%d:00", count));
            canvas.drawRect(item, model.getPaint());
            canvas.drawText(
                    String.format(Locale.JAPAN, "%d:00", count),
                    item.centerX() - (itemWidth / 2),
                    item.top + (item.height() - model.getFontPaint().getFontMetrics().descent - model.getFontPaint().getFontMetrics().ascent) / 2, model.getFontPaint());

        }
        if(null != model.getFocusedRect()) {
            canvas.drawRect(model.getFocusedRect(), model.getFocusPaint());
        }
    }

    public final void setScrollY(final int scrollY) {
        activityModel.setScrollY(scrollY);
        for(int count = 0; count < binding.viewPager.getChildCount(); count++) {
            if(binding.viewPager.getChildAt(count) != binding.viewPager.getParent() &
                    View.VISIBLE == binding.viewPager.getChildAt(count).getVisibility()) {
                binding.viewPager.getChildAt(count).findViewById(R.id.scrollView).scrollTo(0, scrollY);
                view.scrollTo(0, scrollY);
            }
        }
    }

    public final View getTimeTableView() {
        return view;
    }

    private final void setFocusedRect(final RectF focusedRect) {
        model.setFocusedRect(focusedRect);
    }

    private WeekCalViewPartsTimeModel createModel() {
        WeekCalViewPartsTimeModel model = new WeekCalViewPartsTimeModel();
        model.setPaint(PaintTools.newInstance().color(R.color.gray).style(Paint.Style.FILL_AND_STROKE));
        model.setFontPaint(PaintTools.newInstance().color(Color.BLACK).textSize(24));
        model.setFocusPaint(PaintTools.newInstance().color(R.color.transYellow).style(Paint.Style.FILL));
        return model;
    }

    private WeekCalViewPartsTime createView(final Context context) {
        if(null == context) {
            return null;
        }
        WeekCalViewPartsTime timeTableView =
                new WeekCalViewPartsTime(context, this);
        timeTableView.setOnTouchListener(new View.OnTouchListener() {
            float x;
            float y;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_DOWN == event.getActionMasked()) {
                    x = event.getX();
                    y = event.getY();
                    return true;
                }
                if(MotionEvent.ACTION_UP == event.getActionMasked()) {
                    if(Math.abs(x - event.getX()) < 50 & Math.abs(y - event.getY()) < 50) {
                        for(int count = 0; count < activityModel.getRectList().size(); count++) {
                            RectF rect = activityModel.getRectList().get(count);
                            if(rect.contains((int)event.getX(), (int)event.getY() + activityModel.getScrollY())) {
                                if(count != activityModel.getFocusedTime()) {
                                    focusedTimeArea(count);
                                    break;
                                }
                                if(count == activityModel.getFocusedTime()) {
                                    focusedTimeArea(-1);
                                    break;
                                }
                            }
                        }
                    }
                }
                return false;
            }
        });
        return timeTableView;
    }

    public final void focusedTimeArea(int focusedTime) {
        if(23 < focusedTime) {
            return;
        }
        if(-1 == focusedTime) {
            setFocusedRect(null);
            view.invalidate();
        }
        if(-1 != focusedTime) {
            RectF rect = activityModel.getRectList().get(focusedTime);
            setFocusedRect(rect);
            view.invalidate();
            setScrollY((int)rect.top);
        }
        activityModel.setFocusedTime(focusedTime);
        ((CustomViewPagerAdapter)binding.viewPager.getAdapter()).setFocusedTime(focusedTime);
        binding.viewPager.getAdapter().notifyDataSetChanged();
    }


}
