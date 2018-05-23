package com.toro_studio.schedule.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {

    private OnScrollViewListener listener;

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        listener.onScrollChanged(this, l, t, oldl, oldt);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void setListener(CustomScrollView.OnScrollViewListener listener) {
        this.listener = listener;
    }

    public interface OnScrollViewListener {
        void onScrollChanged(View view, int left, int top, int oldLeft, int oldTop);
    }

    @Override
    public int computeVerticalScrollOffset() {
        return super.computeVerticalScrollOffset();
    }
}
