package com.example.android.evince.apputils

import android.content.Context
import com.example.android.evince.database.AppDatabase
import com.example.android.evince.pojo.Matrix
import com.example.android.evince.utils.Utils
import com.google.common.base.Function
import java.util.*

object AppUtils {

    /**
     * 11/6/2019
     * Get the mutable list of matrix object for the given input rows and columns
     *
     * @author srdpatel
     * @since $1.0$
     */
    fun getMatrix(rows: Int, columns: Int): MutableList<Matrix> {
        val mList = ArrayList<Matrix>()
        val max = Utils.getTotalItems(rows, columns)
        for (i in 0 until max) {
            mList.add(Matrix(i))
        }
        return mList
    }

    /**
     * 11/6/2019
     * Shuffles the list
     *
     * @author srdpatel
     * @since $1.0$
     */
    fun shuffleList(mList: MutableList<Matrix>): MutableList<Int> {
        return Utils.shuffle(Utils.transformList(mList, Function { input -> input!!.number }))
    }

    /**
     * 11/6/2019
     * Re-set each item in db for isSelected as false and color to 0
     *
     * @author srdpatel
     * @since $1.0$
     */
    fun setSelectedFalseInDb(context: Context) {
        AppDatabase.getDatabase(context)!!.getAppDao().setAllMatricesSelectedToFalse()
    }

    /**
     * 11/6/2019
     * Set number and color for the matched matrix object in db
     *
     * @author srdpatel
     * @since $1.0$
     */
    fun setSelectedInDb(context: Context, number: Int, color: Int) {
        val matrix = AppDatabase.getDatabase(context)!!.getAppDao().getMatrix(number)
        if (matrix != null) {
            matrix.color = color
            matrix.isSelected = true
            AppDatabase.getDatabase(context)!!.getAppDao().updateMatrix(matrix.primaryKey, true, color)
        }
    }
}
