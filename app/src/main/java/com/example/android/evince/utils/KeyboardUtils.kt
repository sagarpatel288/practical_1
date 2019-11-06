package com.example.android.evince.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * We don't want this class to be extended by any other class
 * See https://gist.github.com/felHR85/6070f643d25f5a0b3674
 * @since 1.0
 */
object KeyboardUtils {

    /**
     * hides soft keyboard
     *
     * @param activity reference to activity for a new instance of view to get the window token from.
     */
    fun hideSoftKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
