package com.example.android.evince.constants;

/**
 * Created by srdpatel on 2/25/2018. Contains constants of application.
 * <p>
 * All strings have been prepended by STR (Short form of string) to ease the access of variables with the help of Android Studio auto suggest tools
 * <p>
 *
 * @since 1.0
 */

public class AppConstants {
    public static final String STR_SHARED_PREF = "sharedPreferences";
    public static final String STR_MSG_ERROR_SHARED_PREF_REFLECTION
            = "Use getInstance() method to get single instance of this class. " +
            "\nUse getSharedPref(Context mContext) method to get the single SharedPreferences instance of this class. " +
            "\nUse getEditor(Context mContext) method to get the single SharedPreferences.Editor instance of this class. ";
    public static final String STR_ROWS = "rows";
    public static final String STR_COLUMNS = "columns";
    public static final String STR_RANDOM_NUMBER = "random_number";
    public static final String STR_RANDOM_COLOR = "random_color";
    public static final int DEFAULT_ROW_COLUMNS = 5;
}