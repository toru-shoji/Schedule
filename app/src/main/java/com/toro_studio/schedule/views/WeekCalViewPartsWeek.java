package com.toro_studio.schedule.views;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.toro_studio.schedule.presenters.WeekCalViewPartsWeekPresenter;


public class WeekCalViewPartsWeek extends View {

    private WeekCalViewPartsWeekPresenter presenter;

    public WeekCalViewPartsWeek(final Context context,
                                final WeekCalViewPartsWeekPresenter presenter) {
        super(context);
        this.presenter = presenter;
    }

    @Override
    protected final void onDraw(final Canvas canvas) {
        if(null == canvas || null == presenter) {
            return;
        }
        super.onDraw(canvas);
        presenter.drawCanvas(canvas);
    }

    @Override
    public final boolean performClick() {
        return super.performClick();
    }
}