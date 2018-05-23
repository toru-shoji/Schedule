package com.toro_studio.schedule.views;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.toro_studio.schedule.R;
import com.toro_studio.schedule.databinding.ActivityCalendarMonthBinding;
import com.toro_studio.schedule.entities.Constants;
import com.toro_studio.schedule.presenters.MonthCalActivityPresenter;
import com.toro_studio.schedule.tools.DialogTools;
import com.toro_studio.schedule.contract.ICalendarMonthView;


public class MonthCalActivity extends AppCompatActivity implements ICalendarMonthView {

    private MonthCalActivityPresenter presenter;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCalendarMonthBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_calendar_month);
        presenter = new MonthCalActivityPresenter(this, binding);

        binding.toolbar.setTitle(R.string.app_name);
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setSubtitleTextColor(Color.WHITE);
        Drawable overflowIcon = binding.toolbar.getOverflowIcon();
        if(null != overflowIcon) {
            overflowIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }
        Drawable navigationIcon = binding.toolbar.getNavigationIcon();
        if(null != navigationIcon) {
            navigationIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }
        setSupportActionBar(binding.toolbar);
    }

    @Override
    protected final void onStart() {
        super.onStart();
    }

    @Override
    protected final void onResume() {
        super.onResume();
    }

    @Override
    protected final void onPause() {
        super.onPause();
    }

    @Override
    protected final void onStop() {
        super.onStop();
    }

    @Override
    protected final void onDestroy() {
        super.onDestroy();
        presenter.getViewPresenter().closeRealm();
    }

    @Override
    protected final void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onActivityResult(
            final int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.REQUEST_CODE & resultCode == RESULT_OK) {
            presenter.notifyCalendar();
        }
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calendar_week, menu);
        return true;
    }

    @Override
    public final boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();
        if (R.id.action_settings == id) {
            return true;
        }
        if(android.R.id.home == id) {
            confirmActivityFinish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        if(KeyEvent.KEYCODE_BACK == keyCode) {
            confirmActivityFinish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void dismissDialog(final Dialog dialog) {
        if(null == dialog) {
            return;
        }
        DialogTools.getInstance().dismisssDialog(getSupportFragmentManager());
    }

    @Override
    public final void gotoNextActivity(final Intent intent) {
        if(null == intent) {
            return;
        }
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    @Override
    public void finishThisActivity() {
        finish();
    }

    private void confirmActivityFinish() {
        DialogTools dialogTools = DialogTools.getInstance();
        Dialog dialog = dialogTools.createConfirmFinishDialog(this);
        dialogTools.displayDialog(getSupportFragmentManager(), dialog);
    }
}
