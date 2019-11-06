package com.example.android.evince

import com.example.android.evince.pojo.Matrix

interface MainContract {

    interface MainView {
        fun setRows(mRows: Int, setViewValue: Boolean)

        fun setColumns(mColumns: Int, setViewValue: Boolean)

        fun setRandomNumber(randomNumber: Int, setViewValue: Boolean)

        fun setRandomColor(randomColor: Int, setViewValue: Boolean)

        fun setDefaultData(rows: Int, columns: Int, matrixList: MutableList<Matrix>, randomList: MutableList<Int>, positionOfLastStoredRandomNumberIfAny: Int)

        fun setRecyclerView(rows: Int, columns: Int, mList: MutableList<Matrix>)

        fun onReInitRandomList()

        fun highLightRandomMatch()
    }

    interface Presenter {

        val rows: Int

        val columns: Int

        var randomNumber: Int

        var randomColor: Int

        val randomList: List<Int>

        var positionOfLastStoredRandomNumberIfAny: Int
        fun handleViews()

        fun saveRandomNumber(randomNumber: Int)

        fun saveRandomColor(randomColor: Int)

        fun onClickApply(rows: Int, columns: Int)

        fun onClickRandom()

        fun increasePosition()
    }

    interface DataInteractor {

        var rows: Int

        var columns: Int

        var randomNumber: Int

        var randomColor: Int

        var randomList: List<Int>

        var positionOfLastStoredRandomNumberIfAny: Int

        fun saveRows(rows: Int)

        fun saveColumns(columns: Int)

        fun saveRandomNumber(randomNumber: Int)

        fun saveRandomColor(randomColor: Int)

        fun saveMatrixList(matrixList: MutableList<Matrix>)
    }
}
