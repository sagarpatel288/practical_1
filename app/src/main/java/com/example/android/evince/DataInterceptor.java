package com.example.android.evince;

import android.content.SharedPreferences;

import com.example.android.evince.constants.AppConstants;
import com.example.android.evince.database.AppDao;
import com.example.android.evince.pojo.Matrix;

import java.util.ArrayList;
import java.util.List;

public class DataInterceptor implements MainContract.DataInteractor {

    private final AppDao appDao;
    private final SharedPreferences mSharedPrefs;
    private List<Integer> randomList = new ArrayList<>();
    private int positionOfLastStoredRandomNumberIfAny = -1;

    DataInterceptor(AppDao appDao, SharedPreferences mSharedPrefs) {
        this.appDao = appDao;
        this.mSharedPrefs = mSharedPrefs;
    }

    @Override
    public void setRows(int rows) {

    }

    @Override
    public void setColumns(int columns) {

    }

    @Override
    public void setRandomNumber(int randomNumber) {

    }

    @Override
    public void setRandomColor(int randomColor) {

    }

    @Override
    public void saveRows(int rows) {
        mSharedPrefs.edit().putInt(AppConstants.STR_ROWS, rows).apply();
    }

    @Override
    public void saveColumns(int columns) {
        mSharedPrefs.edit().putInt(AppConstants.STR_COLUMNS, columns).apply();
    }

    @Override
    public void saveRandomNumber(int randomNumber) {
        mSharedPrefs.edit().putInt(AppConstants.STR_RANDOM_NUMBER, randomNumber).apply();
    }

    @Override
    public void saveRandomColor(int randomColor) {
        mSharedPrefs.edit().putInt(AppConstants.STR_RANDOM_COLOR, randomColor).apply();
    }

    @Override
    public int getRows() {
        return mSharedPrefs.getInt(AppConstants.STR_ROWS, -1) != -1 ?
                mSharedPrefs.getInt(AppConstants.STR_ROWS, -1) :AppConstants.DEFAULT_ROW_COLUMNS;
    }

    @Override
    public int getColumns() {
        return mSharedPrefs.getInt(AppConstants.STR_COLUMNS, -1) != -1 ?
                mSharedPrefs.getInt(AppConstants.STR_COLUMNS, -1) :AppConstants.DEFAULT_ROW_COLUMNS;
    }

    @Override
    public int getRandomNumber() {
        return mSharedPrefs.getInt(AppConstants.STR_RANDOM_NUMBER, -1);
    }

    @Override
    public int getRandomColor() {
        return mSharedPrefs.getInt(AppConstants.STR_RANDOM_COLOR, -1);
    }

    @Override
    public void saveMatrixList(List<Matrix> matrixList) {
        appDao.insertMatrices(matrixList);
    }

    @Override
    public void setRandomList(List<Integer> randomList) {
        this.randomList = randomList;
    }

    @Override
    public List<Integer> getRandomList() {
        return randomList;
    }

    @Override
    public void setPositionOfLastStoredRandomNumberIfAny(int position) {
        this.positionOfLastStoredRandomNumberIfAny = position;
    }

    @Override
    public int getPositionOfLastStoredRandomNumberIfAny() {
        return positionOfLastStoredRandomNumberIfAny;
    }
}
