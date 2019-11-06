package com.example.android.evince.apputils

import android.content.Context
import com.example.android.evince.database.AppDatabase
import com.example.android.evince.pojo.Matrix
import com.example.android.evince.utils.Utils
import com.google.common.base.Function
import java.util.*

object AppUtils {

    fun getMatrix(rows: Int, columns: Int): MutableList<Matrix> {
        val mList = ArrayList<Matrix>()
        val max = Utils.getTotalItems(rows, columns)
        for (i in 0 until max) {
            mList.add(Matrix(i))
        }
        return mList
    }

    fun shuffleList(mList: MutableList<Matrix>): MutableList<Int> {
        return Utils.shuffle(Utils.transformList(mList, Function { input -> input!!.number }))
    }

    fun setSelectedFalseInDb(context: Context) {
        AppDatabase.getDatabase(context)!!.getAppDao().setAllMatricesSelectedToFalse()
    }

    fun setSelectedInDb(context: Context, number: Int, color: Int) {
        val matrix = AppDatabase.getDatabase(context)!!.getAppDao().getMatrix(number)
        if (matrix != null) {
            matrix.color = color
            matrix.isSelected = true
            AppDatabase.getDatabase(context)!!.getAppDao().updateMatrix(matrix.primaryKey, true, color)
        }
    }
}
