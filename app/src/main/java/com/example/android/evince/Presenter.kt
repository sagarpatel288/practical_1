package com.example.android.evince

import android.content.SharedPreferences

import com.example.android.evince.apputils.AppUtils
import com.example.android.evince.database.AppDao
import com.example.android.evince.pojo.Matrix
import com.example.android.evince.utils.Utils

class Presenter internal constructor(private val mainView: MainContract.MainView, private val appDao: AppDao, mSharedPrefs: SharedPreferences) : MainContract.Presenter {
    private val dataInteractor: MainContract.DataInteractor

    override var rows: Int
        get() = dataInteractor.rows
        private set(rows) {
            dataInteractor.rows = rows
        }

    override var columns: Int
        get() = dataInteractor.columns
        private set(columns) {
            dataInteractor.columns = columns
        }

    override var randomNumber: Int
        get() = dataInteractor.randomNumber
        set(randomNumber) {
            dataInteractor.randomNumber = randomNumber
        }

    override var randomColor: Int
        get() = dataInteractor.randomColor
        set(randomColor) {
            dataInteractor.randomColor = randomColor
        }

    override var randomList: List<Int>
        get() = dataInteractor.randomList
        private set(randomList) {
            dataInteractor.randomList = randomList
        }

    override var positionOfLastStoredRandomNumberIfAny: Int
        get() = dataInteractor.positionOfLastStoredRandomNumberIfAny
        set(position) {
            dataInteractor.positionOfLastStoredRandomNumberIfAny = position
        }

    init {
        this.dataInteractor = DataInterceptor(appDao, mSharedPrefs)
    }

    override fun handleViews() {
        mainView.setRows(dataInteractor.rows, true)
        mainView.setColumns(dataInteractor.columns, true)
        mainView.setRandomNumber(dataInteractor.randomNumber, true)
        mainView.setRandomColor(dataInteractor.randomColor, true)
        if (Utils.isNotNullNotEmpty(appDao.allMatrices)) {
            val mList = appDao.allMatrices
            var randomList = AppUtils.shuffleList(mList)
            var position = -1
            if (randomNumber != -1) {
                position = randomList.indexOf(randomNumber)
            }
            randomList = randomList
            positionOfLastStoredRandomNumberIfAny = position
            mainView.setDefaultData(rows, columns, mList, randomList, position)
        } else {
            val mList = AppUtils.getMatrix(rows, columns)
            var randomList = AppUtils.shuffleList(mList)
            randomList = randomList
            positionOfLastStoredRandomNumberIfAny = -1
            mainView.setDefaultData(rows, columns, mList, randomList, -1)
        }
    }

    private fun saveRows(rows: Int) {
        dataInteractor.saveRows(rows)
    }

    private fun saveColumns(columns: Int) {
        dataInteractor.saveColumns(columns)
    }

    override fun saveRandomNumber(randomNumber: Int) {
        dataInteractor.saveRandomNumber(randomNumber)
    }

    override fun saveRandomColor(randomColor: Int) {
        dataInteractor.saveRandomColor(randomColor)
    }

    override fun onClickApply(rows: Int, columns: Int) {
        saveRows(rows)
        saveColumns(columns)
        val mList = AppUtils.getMatrix(rows, columns)
        randomList = AppUtils.shuffleList(mList)
        saveMatrixList(mList)
        mainView.setRecyclerView(rows, columns, mList)
    }

    private fun saveMatrixList(matrixList: MutableList<Matrix>) {
        dataInteractor.saveMatrixList(matrixList)
    }

    override fun onClickRandom() {
        if (Utils.hasElement(randomList, positionOfLastStoredRandomNumberIfAny)) {
            var randomNumber = randomList[positionOfLastStoredRandomNumberIfAny]
            saveRandomNumber(randomNumber)
            mainView.setRandomNumber(randomNumber, true)
            var randomColor = Utils.randomColor
            saveRandomColor(randomColor)
            mainView.setRandomColor(randomColor, true)
            mainView.highLightRandomMatch()
            increasePosition()
        } else {
            mainView.onReInitRandomList()
            positionOfLastStoredRandomNumberIfAny = 0
            onClickRandom()
        }
    }

    override fun increasePosition() {
        var position = positionOfLastStoredRandomNumberIfAny
        position = position + 1
        positionOfLastStoredRandomNumberIfAny = position
    }
}
