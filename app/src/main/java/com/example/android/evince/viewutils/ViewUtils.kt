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

    /**
     * 11/6/2019
     *
     * @param editText
     * @return true if hasTextValue
     * @author srdpatel
     * @since $1.0$
     */
    fun hasTextValue(editText: EditText?): Boolean {
        return editText != null && editText.text.toString().trim { it <= ' ' }.isNotEmpty()
    }

    /**
     * 11/6/2019
     * Disables the view/s
     *
     * @author srdpatel
     * @since $1.0$
     */
    fun setEnable(enable: Boolean, vararg views: View) {
        if (views.isNotEmpty()) {
            for (view in views) {
                view.isEnabled = enable
            }
        }
    }

    /**
     * 11/6/2019
     * Parse if the integer is expected
     *
     * @author srdpatel
     * @since $1.0$
     */
    fun getInt(editText: EditText): Int {
        return Integer.parseInt(StringUtils.getString(editText, "0"))
    }
}
