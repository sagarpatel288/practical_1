package com.example.android.evince.utils

import android.widget.EditText

object StringUtils {

    fun getString(editText: EditText?, defaultReturn: String): String {
        return if (editText != null && editText.text.toString().trim { it <= ' ' }.isNotEmpty()) {
            editText.text.toString()
        } else defaultReturn
    }
}
