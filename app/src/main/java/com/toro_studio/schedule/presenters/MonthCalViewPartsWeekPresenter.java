package com.toro_studio.schedule.presenters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.TypedValue;

import com.toro_studio.schedule.model.MonthCalViewPartsWeekModel;
import com.toro_studio.schedule.tools.CalendarTools;
import com.toro_studio.schedule.tools.PaintTools;
import com.toro_studio.schedule.tools.RectTools;
import com.toro_studio.schedule.views.MonthCalViewPartsWeek;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MonthCalViewPartsWeekPresenter {

    private MonthCalViewPartsWeekModel model;
    private MonthCalViewPartsWeek view;

    public MonthCalViewPartsWeekPresenter(final Context context) {
        model = createModel(context);
        view = createView(context);
    }

    public final MonthCalViewPartsWeek getView() {
        return view;
    }

    public final void drawCanvas(final Canvas canvas) {
        if(null == canvas) {
            return;
        }
        for(int count = 0; count < model.getRectArray().size(); count++) {
            Paint tmpRectPaint;
            Paint tmpTextPaint;
            if(Calendar.SATURDAY == count + 1) {
                tmpRectPaint = model.getSaturdayBackgroundPaint();
                tmpTextPaint = model.getHolidayTextPaint();
            } else if(Calendar.SUNDAY == count + 1) {
                tmpRectPaint = model.getSundayBackgroundPaint();
                tmpTextPaint = model.getHolidayTextPaint();
            } else {
                tmpRectPaint = model.getPaint();
                tmpTextPaint = model.getTextPaint();
            }
            canvas.drawRect(model.getRectArray().get(count), tmpRectPaint);
            canvas.drawText(
                    CalendarTools.getInstance().getDayOfWeekJapaneseText(count),
                    model.getRectArray().get(count).centerX() - (model.getTextPaint().measureText(CalendarTools.getInstance().getDayOfWeekJapaneseText(count)) / 2),
                    model.getRectArray().get(count).centerY() + ((model.getTextPaint().descent() - model.getTextPaint().ascent()) / 4),
                    tmpTextPaint);
        }
    }

    private MonthCalViewPartsWeekModel createModel(final Context context) {
        if(null == context) {
            return null;
        }
        MonthCalViewPartsWeekModel model =
                new MonthCalViewPartsWeekModel();
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        int itemWidth = (int)(screenWidth / 7f);
        int areaMargin = (screenWidth - itemWidth * 7) / 2;
        model.setScreenWidth(screenWidth);
        model.setItemWidth(itemWidth);
        model.setAreaMargin(areaMargin);
        model.setRectArray(createRectList(context, itemWidth, areaMargin));
        model.setPaint(PaintTools.newInstance().color(Color.WHITE));
        model.setTextPaint(PaintTools.newInstance().color(Color.BLACK).textSize(20));
        model.setHolidayTextPaint(PaintTools.newInstance().color(Color.WHITE).textSize(20));
        model.setSaturdayBackgroundPaint(PaintTools.newInstance().color(Color.BLUE));
        model.setSundayBackgroundPaint(PaintTools.newInstance().color(Color.RED));
        return model;
    }

    private MonthCalViewPartsWeek createView(final Context context) {
        MonthCalViewPartsWeek view =
                new MonthCalViewPartsWeek(context, this);
        return view;
    }

    private List<RectF> createRectList(final Context context,
                                      final int itemWidth,
                                      final int areaMargin) {
        if(null == context) {
            return null;
        }
        int leftLocation = areaMargin;
        int rightLocation = itemWidth + areaMargin;
        List<RectF> rectList = new ArrayList<>();
        int value = (int)TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 15f,
                context.getResources().getDisplayMetrics());
        for(int count = 0; count < Calendar.DAY_OF_WEEK; count++) {
            RectF rect = RectTools.newInstance().left(leftLocation).top(0).right(rightLocation).bottom(value);
            leftLocation += itemWidth + areaMargin;
            rightLocation += itemWidth + areaMargin;
            rectList.add(rect);
        }
        return rectList;
    }

}
