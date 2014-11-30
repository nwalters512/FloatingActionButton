package com.melnykov.fab;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

/**
 * Created by Nathan on 11/29/2014.
 */
public class FloatingActionButtonWithCircularProgress extends RelativeLayout {

    private FloatingActionButton mFloatingActionButton;
    private CircularProgressView mCircularProgressView;

    public FloatingActionButtonWithCircularProgress(Context context) {
        this(context, null);
    }

    public FloatingActionButtonWithCircularProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FloatingActionButtonWithCircularProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        LayoutInflater.from(context).inflate(R.layout.fab_with_circular_progress, this, true);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.internal_fab);
        mCircularProgressView = (CircularProgressView) findViewById(R.id.internal_progress);

        if (attributeSet != null) {
            initAttributes(context, attributeSet);
        }

        // Force the progress view to display at the front
        mCircularProgressView.bringToFront();
        this.requestLayout();
        this.invalidate();
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        // Proxy the custom attributes through to the children views
        TypedArray attr = getTypedArray(context, attributeSet, R.styleable.FloatingActionButton);
        if (attr != null) {
            try {
                int colorNormal = attr.getColor(R.styleable.FloatingActionButton_fab_colorNormal,
                        getColor(R.color.material_blue_500));
                int colorPressed = attr.getColor(R.styleable.FloatingActionButton_fab_colorPressed,
                        getColor(R.color.material_blue_600));
                int colorRipple = attr.getColor(R.styleable.FloatingActionButton_fab_colorRipple,
                        getColor(android.R.color.white));
                boolean shadow = attr.getBoolean(R.styleable.FloatingActionButton_fab_shadow, true);
                int type = attr.getInt(R.styleable.FloatingActionButton_fab_type, FloatingActionButton.TYPE_NORMAL);

                mFloatingActionButton.setColorNormal(colorNormal);
                mFloatingActionButton.setColorPressed(colorPressed);
                mFloatingActionButton.setColorRipple(colorRipple);
                mFloatingActionButton.setShadow(shadow);

                // We have to use this convoluted way because the FAB expects two very specific values;
                // we have to guard against passing the wrong one.
                if (type == FloatingActionButton.TYPE_NORMAL) {
                    mFloatingActionButton.setType(FloatingActionButton.TYPE_NORMAL);
                } else if (type == FloatingActionButton.TYPE_MINI) {
                    mFloatingActionButton.setType(FloatingActionButton.TYPE_MINI);
                } else {
                    // Do nothing
                }
            } finally {
                attr.recycle();
            }
        }

        // Proxy through the "src" attribute so this can be set from XML
        attr = getTypedArray(context, attributeSet, new int[]{android.R.attr.src});
        if (attr != null) {
            try {
                int imageSourceRes = attr.getResourceId(0, 0);
                mFloatingActionButton.setImageResource(imageSourceRes);
            } finally {
                attr.recycle();
            }
        }
    }

    public FloatingActionButton getFloatingActionButton() {
        return mFloatingActionButton;
    }

    public CircularProgressView getCircularProgressView() {
        return mCircularProgressView;
    }

    private TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    private int getColor(@ColorRes int id) {
        return getResources().getColor(id);
    }

    private int getDimension(@DimenRes int id) {
        return getResources().getDimensionPixelSize(id);
    }
}
