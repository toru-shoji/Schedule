package com.toro_studio.schedule.views;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.toro_studio.schedule.presenters.WeekCalViewPresenter;


public class WeekCalView extends View {

    private WeekCalViewPresenter presenter;

    public WeekCalView(final Context context, final WeekCalViewPresenter presenter) {
        super(context);
        this.presenter = presenter;
    }

    @Override
    protected final void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        int[] size = presenter.getCalendarSize();
        if(2 != size.length) {
            return;
        }
        super.onMeasure(size[0], size[1]);
        setMeasuredDimension(widthMeasureSpec, resolveSize(size[1], heightMeasureSpec));
    }

    @Override
    protected final void onDraw(final Canvas canvas) {
        if(null == canvas || null == presenter) {
            return;
        }
        super.onDraw(canvas);
        presenter.drawCalendarWeek(canvas);
    }

    @Override
    public final boolean performClick() {
        return super.performClick();
    }

}
