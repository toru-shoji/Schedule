package com.toro_studio.schedule.views;

import android.content.Context;
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
import com.toro_studio.schedule.databinding.ActivityCalendarWeekBinding;
import com.toro_studio.schedule.presenters.WeekCalActivityPresenter;

import java.util.Date;


public final class WeekCalActivity extends AppCompatActivity {

    private WeekCalActivityPresenter presenter;

    public static final Intent getIntent(final Context context, final Date date) {
        if(null == context || null == date) {
            return null;
        }
        Intent intent = new Intent(context, WeekCalActivity.class);
        intent.putExtra("date", date);
        return intent;
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCalendarWeekBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_calendar_week);
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
        presenter = new WeekCalActivityPresenter(binding, getIntent());
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
        presenter.closeRealm();
    }

    @Override
    protected final void onRestart() {
        super.onRestart();
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
            finishThisActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(final int keyCode, KeyEvent event) {
        if(KeyEvent.KEYCODE_BACK == keyCode) {
            finishThisActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void finishThisActivity() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

}