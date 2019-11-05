package com.example.android.evince.viewutils;

import android.view.View;
import android.widget.EditText;

public final class ViewUtils {
    private ViewUtils() {
    }

    public static void setOnClickListener(View.OnClickListener onClickListener, View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                view.setOnClickListener(onClickListener);
            }
        }
    }

    public static boolean hasTextValue(EditText editText) {
        return editText != null && !editText.getText().toString().trim().isEmpty();
    }

    public static void setEnable(boolean enable, View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                view.setEnabled(enable);
            }
        }
    }
}
