package com.toro_studio.schedule.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;

import com.toro_studio.schedule.R;
import com.toro_studio.schedule.customview.CustomDialogFragment;
import com.toro_studio.schedule.contract.ICalendarMonthView;


public class DialogTools {

    private CustomDialogFragment dialogFragment;

    private static final class DialogToolsHolder {
        private static final DialogTools instance = new DialogTools();
    }

    public static DialogTools getInstance() {
        return DialogToolsHolder.instance;
    }

    public final void displayDialog(final FragmentManager fragmentManager, final Dialog dialog) {
        if(null != dialogFragment || null == dialog) {
            return;
        }
        dialogFragment = new CustomDialogFragment();
        dialogFragment.setDialog(dialog);
        dialogFragment.setCancelable(false);
        dialogFragment.show(fragmentManager, "dialog");
    }

    public final void dismisssDialog(final FragmentManager fragmentManager) {
        if(null == dialogFragment) {
            return;
        }
        dialogFragment.dismiss();
        fragmentManager.beginTransaction().remove(dialogFragment).commitNowAllowingStateLoss();
        dialogFragment = null;
    }

    public final Dialog createConfirmFinishDialog(final Activity activity) {
        if(null == activity) {
            return null;
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.app_name);
        builder.setMessage(
                String.format(
                        activity.getString(R.string.finish_schedule_app),
                        activity.getString(R.string.app_name)
                ));
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((ICalendarMonthView)activity).dismissDialog(builder.create());
                ((ICalendarMonthView)activity).finishThisActivity();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((ICalendarMonthView)activity).dismissDialog(builder.create());
            }
        });
        return builder.create();
    }

}
