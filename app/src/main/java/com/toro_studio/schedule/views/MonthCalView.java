package com.toro_studio.schedule.views;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.toro_studio.schedule.presenters.MonthCalViewPresenter;


public class MonthCalView extends View {

    private MonthCalViewPresenter presenter;

    public MonthCalView(final Context context, final MonthCalViewPresenter presenter) {
        super(context);
        this.presenter = presenter;
    }

    @Override
    protected final void onDraw(final Canvas canvas) {
        if(null == canvas) {
            return;
        }
        super.onDraw(canvas);
        presenter.drawCanvas(canvas);
    }

    @Override
    public final boolean onTouchEvent(final MotionEvent event) {
        return presenter.touchAction(event);
    }

}
