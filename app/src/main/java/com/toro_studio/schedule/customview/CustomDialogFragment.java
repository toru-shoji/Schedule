package com.toro_studio.schedule.customview;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment {

    private Dialog mDialog;

    public CustomDialogFragment() {
        super();
        mDialog = null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return mDialog;
    }

    public final void setDialog(Dialog dialog) {
        mDialog = dialog;
    }

}
