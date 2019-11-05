package com.example.android.evince.utils;

import android.widget.EditText;

public final class StringUtils {
    private StringUtils() {

    }

    public static String getDefaultString(String value, String defaultValue) {
        return StringUtils.isNotNullNotEmpty(value) ? value : defaultValue;
    }

    public static boolean isNotNullNotEmpty(String value) {
        return !isNullOrEmpty(value);
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty() || value.equalsIgnoreCase("null");
    }

    public static String getString(EditText editText, String defaultReturn) {
        if (editText != null && !editText.getText().toString().trim().isEmpty()) {
            return editText.getText().toString();
        }
        return defaultReturn;
    }
}
