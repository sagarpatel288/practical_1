package com.example.android.evince

import android.content.SharedPreferences
import com.example.android.evince.constants.AppConstants
import com.example.android.evince.database.AppDao
import com.example.android.evince.pojo.Matrix
import java.util.*

class DataInterceptor internal constructor(private val appDao: AppDao, private val mSharedPrefs: SharedPreferences) : MainContract.DataInteractor {
    override fun clearnSharedPrefs() {
        mSharedPrefs.edit().clear().apply()
    }

    override var randomList: List<Int> = ArrayList()
        get() = field
    override var positionOfLastStoredRandomNumberIfAny = -1

    override var rows: Int
        get() = if (mSharedPrefs.getInt(AppConstants.STR_ROWS, -1) != -1)
            mSharedPrefs.getInt(AppConstants.STR_ROWS, -1)
        else
            AppConstants.DEFAULT_ROW_COLUMNS
        set(rows) {

        }

    override var columns: Int
        get() = if (mSharedPrefs.getInt(AppConstants.STR_COLUMNS, -1) != -1)
            mSharedPrefs.getInt(AppConstants.STR_COLUMNS, -1)
        else
            AppConstants.DEFAULT_ROW_COLUMNS
        set(columns) {

        }

    override var randomNumber: Int
        get() = mSharedPrefs.getInt(AppConstants.STR_RANDOM_NUMBER, -1)
        set(randomNumber) {

        }

    override var randomColor: Int
        get() = mSharedPrefs.getInt(AppConstants.STR_RANDOM_COLOR, -1)
        set(randomColor) {

        }

    override fun saveRows(rows: Int) {
        mSharedPrefs.edit().putInt(AppConstants.STR_ROWS, rows).apply()
    }

    override fun saveColumns(columns: Int) {
        mSharedPrefs.edit().putInt(AppConstants.STR_COLUMNS, columns).apply()
    }

    override fun saveRandomNumber(randomNumber: Int) {
        mSharedPrefs.edit().putInt(AppConstants.STR_RANDOM_NUMBER, randomNumber).apply()
    }

    override fun saveRandomColor(randomColor: Int) {
        mSharedPrefs.edit().putInt(AppConstants.STR_RANDOM_COLOR, randomColor).apply()
    }

    override fun saveMatrixList(matrixList: MutableList<Matrix>) {
        appDao.deleteMatrix()
        appDao.insertMatrices(matrixList)
    }
}
