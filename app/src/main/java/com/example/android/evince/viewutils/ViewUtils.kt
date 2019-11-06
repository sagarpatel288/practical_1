package com.example.android.evince.viewutils

import android.view.View
import android.widget.EditText

import com.example.android.evince.utils.StringUtils

object ViewUtils {

    fun setOnClickListener(onClickListener: View.OnClickListener, vararg views: View) {
        if (views.isNotEmpty()) {
            for (view in views) {
                view.setOnClickListener(onClickListener)
            }
        }
    }

    fun hasTextValue(editText: EditText?): Boolean {
        return editText != null && editText.text.toString().trim { it <= ' ' }.isNotEmpty()
    }

    fun setEnable(enable: Boolean, vararg views: View) {
        if (views.isNotEmpty()) {
            for (view in views) {
                view.isEnabled = enable
            }
        }
    }

    fun getInt(editText: EditText): Int {
        return Integer.parseInt(StringUtils.getString(editText, "0"))
    }
}
