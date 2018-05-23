package com.toro_studio.schedule.presenters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.toro_studio.schedule.R;
import com.toro_studio.schedule.databinding.PartsViewpagerBinding;
import com.toro_studio.schedule.entities.Schedule;
import com.toro_studio.schedule.entities.Todo;
import com.toro_studio.schedule.model.WeekCalActivityModel;
import com.toro_studio.schedule.model.WeekCalViewModel;
import com.toro_studio.schedule.tools.CalendarTools;
import com.toro_studio.schedule.tools.PaintTools;
import com.toro_studio.schedule.views.WeekCalView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;


public class WeekCalViewPresenter {

    private WeekCalActivityModel activityModel;
    private WeekCalViewModel model;
    private Realm realm;
    private WeekCalView view;

    public WeekCalViewPresenter(
            final Context context,
            final WeekCalActivityModel activityModel,
            final Realm realm,
            final Date date,
            final List<Todo> todoList,
            final int position) {
        this.activityModel = activityModel;
        this.realm = realm;
        model = createModel(context, activityModel, date, todoList);
        view = createCalendarWeekView(context, position);
    }

    public final WeekCalView getCalendarWeekView() {
        return this.view;
    }

    public final void drawCalendarWeek(final Canvas canvas) {
        if(null == canvas) {
            return;
        }
        if(null != activityModel.getRectList()) {
            for(RectF rect : activityModel.getRectList()) {
                canvas.drawRect(rect, model.getPaint());
            }
        }
        if(null != model.getPointList()) {
            for(Point point : model.getPointList()) {
                canvas.drawCircle(point.x, point.y, 10, model.getPointPaint());
            }
        }
        if(model.isFocused()) {
            canvas.drawRect(
                    0,
                    0,
                    activityModel.getRectList().get(0).right,
                    activityModel.getRectList().get(activityModel.getRectList().size()-1).bottom,
                    model.getFocusedPaint());
        }
        if(null != model.getFocusedTimeRect()) {
            canvas.drawRect(model.getFocusedTimeRect(), model.getFocusedPaint());
        }
    }

    public final void checkIsFocused(final Date date) {
        if(null == date) {
            return;
        }
        if(CalendarTools.getInstance().isSameDay(date, model.getDisplayDate())) {
            model.setFocused(true);
        }
    }

    public final void setFocusedTime(final int focusedTime) {
        if(-1 == focusedTime) {
            model.setFocusedTimeRect(null);
        }
        if(-1 != focusedTime) {
            model.setFocusedTimeRect(activityModel.getRectList().get(focusedTime));
        }
    }

    public final int[] getCalendarSize() {
        return new int[]{model.getCalendarWidth(), model.getCalendarHeight()};
    }

    private WeekCalViewModel createModel(
            final Context context, final WeekCalActivityModel activityModel,
                           final Date date, final List<Todo> todoList) {
        if(null == context) {
            return null;
        }
        WeekCalViewModel model = new WeekCalViewModel();
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        int screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        model.setCalendarWidth((int)((float)screenWidth / 7f));
        model.setCalendarHeight((int)((float)screenHeight * 2f));
        List<RectF> rectList = activityModel.getRectList();
        if(null != rectList && !rectList.isEmpty()) {
            model.setPointList(createPointList(rectList, todoList));
            int focusedTime = activityModel.getFocusedTime();
            if(-1 == focusedTime) {
                model.setFocusedTimeRect(null);
            } else {
                model.setFocusedTimeRect(rectList.get(focusedTime));
            }
        }
        model.setDisplayDate(date);
        model.setPaint(PaintTools.newInstance().color(R.color.gray).style(Paint.Style.STROKE));
        model.setPointPaint(PaintTools.newInstance().color(R.color.transRed).style(Paint.Style.FILL_AND_STROKE));
        model.setFocusedPaint(PaintTools.newInstance().color(R.color.transYellow).style(Paint.Style.FILL));
        return model;
    }

    private WeekCalView createCalendarWeekView(final Context context, final int position) {
        if(null == context) {
            return null;
        }
        WeekCalView calendarWeekView = new WeekCalView(context, this);
        calendarWeekView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                if(MotionEvent.ACTION_DOWN == action) {
                    model.setCachedX((int)event.getX());
                    model.setCachedY((int)event.getY());
                }
                if(MotionEvent.ACTION_UP == action) {
                    if(Math.abs(model.getCachedX() - event.getX()) < 50 && Math.abs(model.getCachedY() - event.getY()) < 50) {
                        Date date = activityModel.getDateList().get(position);
                        List<RectF> rectList = activityModel.getRectList();
                        int timeValue = -1;
                        for(int count = 0; count < rectList.size(); count++) {
                            RectF item = rectList.get(count);
                            if(item.contains((int)event.getX(), (int)event.getY())) {
                                timeValue = count;
                                break;
                            }
                        }
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        calendar.set(Calendar.HOUR_OF_DAY, timeValue);
                        toggledCell(CalendarTools.getInstance().createClearMinuteDate(calendar.getTime()));
                        return false;
                    }
                }
                return true;
            }
        });
        return calendarWeekView;
    }

    private void toggledCell(Date date) {
        if(null == date) {
            return;
        }
        Date hourRefreshDate = CalendarTools.getInstance().createClearHourDate(date);
        Date minuteRefreshDate = CalendarTools.getInstance().createClearMinuteDate(date);
        RealmQuery<Schedule> realmQuery = realm.where(Schedule.class).equalTo("date", hourRefreshDate);
        boolean isInputDate = false;
        outer : for(Schedule schedule : realmQuery.findAll()) {
            for(Todo todo : schedule.getTodoList()) {
                if(!todo.getDate().equals(minuteRefreshDate)) {
                    continue;
                }
                if(todo.getDate().equals(minuteRefreshDate)) {
                    isInputDate = true;
                    break outer;
                }
            }
        }
        if(isInputDate) {
            deleteTodoList(hourRefreshDate, minuteRefreshDate);
        } else {
            saveTodoList(hourRefreshDate, minuteRefreshDate, "test");
        }
    }

    private void saveTodoList(final Date hourRefreshDate, final Date minuteRefreshDate, final String planName) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Todo todo = new Todo();
                todo.setDate(minuteRefreshDate);
                todo.setTodoName(planName);
                Todo todoManage = realm.copyToRealm(todo);
                Schedule schedule = realm.where(Schedule.class).equalTo("date", hourRefreshDate).findFirst();
                if(null == schedule) {
                    schedule = realm.createObject(Schedule.class);
                }
                schedule.setDate(hourRefreshDate);
                schedule.getTodoList().add(todoManage);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                PartsViewpagerBinding binding = DataBindingUtil.findBinding(view);
                if(null == binding) {
                    return;
                }
                notifyAdapter(view);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                System.out.println("保存処理が失敗しました。");
            }
        });
    }

    private void deleteTodoList(final Date hourRefreshDate, final Date minuteRefreshDate) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Todo todo = realm.where(Todo.class).equalTo("date", minuteRefreshDate).findFirst();
                if (null != todo) {
                    todo.deleteFromRealm();
                }
                Schedule schedule =
                        realm.where(Schedule.class).equalTo("date", hourRefreshDate).findFirst();
                if(null != schedule) {
                    if(null == schedule.getTodoList() || schedule.getTodoList().isEmpty()) {
                        schedule.deleteFromRealm();
                    }
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                notifyAdapter(view);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d("save function", "削除に失敗しました。");
            }
        });
    }

    private void notifyAdapter(final View view) {
        PartsViewpagerBinding binding = DataBindingUtil.findBinding(view);
        if(null == binding) {
            return;
        }
        ViewPager viewPager = (ViewPager)binding.getRoot().getParent();
        if(null == viewPager) {
            return;
        }
        PagerAdapter pagerAdapter = viewPager.getAdapter();
        if(null == pagerAdapter) {
            return;
        }
        pagerAdapter.notifyDataSetChanged();
    }

    private List<Point> createPointList(final List<RectF> rectList, final List<Todo> todoList) {
        if(null == todoList || todoList.isEmpty()) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        List<Point> pointList = new ArrayList<>();
        for(Todo todo : todoList) {
            Date date = todo.getDate();
            calendar.setTime(date);
            int loc = calendar.get(Calendar.HOUR_OF_DAY);
            pointList.add(new Point((int)rectList.get(loc).centerX(), (int)rectList.get(loc).centerY()));
        }
        return pointList;
    }

}
