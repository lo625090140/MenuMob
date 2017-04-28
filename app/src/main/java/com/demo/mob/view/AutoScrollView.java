package com.demo.mob.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.utils.AutoLayoutHelper;

/**
 * Created by chenjt on 2017/4/27.
 */

public class AutoScrollView extends ScrollView {
    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

    public AutoScrollView(Context context)
    {
        super(context);
    }

    public AutoScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public AutoScrollView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public AutoFrameLayout.LayoutParams generateLayoutParams(AttributeSet attrs)
    {
        return new AutoFrameLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if (!isInEditMode())
        {
            mHelper.adjustChildren();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
