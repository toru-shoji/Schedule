package com.toro_studio.schedule.views;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.toro_studio.schedule.presenters.WeekCalViewPartsTimePresenter;


public class WeekCalViewPartsTime extends View {

    private final int timeTableLength;
    private final int timeTableWidth;

    WeekCalViewPartsTimePresenter presenter;

    public WeekCalViewPartsTime(final Context context,
                                final WeekCalViewPartsTimePresenter presenter) {
        super(context);
        this.presenter = presenter;
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight =getResources().getDisplayMetrics().heightPixels;
        timeTableLength = (int)(screenHeight * 2f);
        timeTableWidth = (int)(screenWidth / 8f);
    }

    @Override
    protected final void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(timeTableWidth, timeTableLength);
        setMeasuredDimension(timeTableWidth, timeTableLength);
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
