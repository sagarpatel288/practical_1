package com.example.android.evince.constants

/**
 * Created by srdpatel on 2/25/2018. Contains constants of application.
 *
 *
 * All strings have been prepended by STR (Short form of string) to ease the access of variables with the help of Android Studio auto suggest tools
 *
 *
 *
 * @since 1.0
 */

class AppConstants//Private constructor
{
    companion object AppConstants {
        val STR_SHARED_PREF = "sharedPreferences"
        val STR_MSG_ERROR_SHARED_PREF_REFLECTION = "Use getInstance() method to get single instance of this class. " +
                "\nUse getSharedPref(Context mContext) method to get the single SharedPreferences instance. " +
                "\nUse getEditor(Context mContext) method to get the single SharedPreferences.Editor instance. "
        val STR_ROWS = "rows"
        val STR_COLUMNS = "columns"
        val STR_RANDOM_NUMBER = "random_number"
        val STR_RANDOM_COLOR = "random_color"
        val DEFAULT_ROW_COLUMNS = 5

        class Limits // Private constructor
        {
            companion object Limits {
                val MAX_ROW_COLUMNS = 10
            }
        }
    }
}
