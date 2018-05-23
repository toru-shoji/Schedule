package com.toro_studio.schedule.views;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.toro_studio.schedule.presenters.MonthCalViewPartsWeekPresenter;


public class MonthCalViewPartsWeek extends View {

    private MonthCalViewPartsWeekPresenter presenter;

    public MonthCalViewPartsWeek(final Context context,
                                 final MonthCalViewPartsWeekPresenter presenter) {
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

}
