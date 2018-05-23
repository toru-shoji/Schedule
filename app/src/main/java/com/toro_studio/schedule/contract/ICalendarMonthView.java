package com.toro_studio.schedule.contract;

import android.app.Dialog;
import android.content.Intent;

public interface ICalendarMonthView {
    void dismissDialog(Dialog dialog);
    void gotoNextActivity(Intent intent);
    void finishThisActivity();
}
