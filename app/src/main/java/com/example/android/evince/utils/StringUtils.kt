package com.example.android.evince.utils

import android.widget.EditText

object StringUtils {

    fun getDefaultString(value: String, defaultValue: String): String {
        return if (StringUtils.isNotNullNotEmpty(value)) value else defaultValue
    }

    fun isNotNullNotEmpty(value: String): Boolean {
        return !isNullOrEmpty(value)
    }

    fun isNullOrEmpty(value: String?): Boolean {
        return value == null || value.trim { it <= ' ' }.isEmpty() || value.equals("null", ignoreCase = true)
    }

    fun getString(editText: EditText?, defaultReturn: String): String {
        return if (editText != null && !editText.text.toString().trim { it <= ' ' }.isEmpty()) {
            editText.text.toString()
        } else defaultReturn
    }
}
