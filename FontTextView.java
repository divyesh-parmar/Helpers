package com.div.sadvideostatus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint({"AppCompatCustomView"})
public class FontTextView extends TextView {
    public FontTextView(Context context) {
        super(context);
        setStyle();
    }

    public FontTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setStyle();
    }

    public FontTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setStyle();
    }

    public void setStyle() {
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), "ABeeZee-Regular_0.ttf"), 1);
    }
}
