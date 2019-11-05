package com.example.android.evince;

import android.content.SharedPreferences;

import com.example.android.evince.apputils.AppUtils;
import com.example.android.evince.database.AppDao;
import com.example.android.evince.pojo.Matrix;
import com.example.android.evince.utils.Utils;

import java.util.List;

public class Presenter implements MainContract.Presenter {

    private final MainContract.MainView mainView;
    private final AppDao appDao;
    private final MainContract.DataInteractor dataInteractor;

    Presenter(MainContract.MainView mainView, AppDao appDao, SharedPreferences mSharedPrefs) {
        this.mainView = mainView;
        this.appDao = appDao;
        this.dataInteractor = new DataInterceptor(appDao, mSharedPrefs);
    }

    @Override
    public void handleViews() {
        mainView.setRows(dataInteractor.getRows(), true);
        mainView.setColumns(dataInteractor.getColumns(), true);
        mainView.setRandomNumber(dataInteractor.getRandomNumber(), true);
        mainView.setRandomColor(dataInteractor.getRandomColor(), true);
        if (Utils.isNotNullNotEmpty(appDao.getAllMatrices())) {
            List<Matrix> mList = appDao.getAllMatrices();
            List<Integer> randomList = AppUtils.shuffleList(mList);
            int position = -1;
            if (getRandomNumber() != -1) {
                position = randomList.indexOf(getRandomNumber());
            }
            setRandomList(randomList);
            setPositionOfLastStoredRandomNumberIfAny(position);
            mainView.setDefaultData(getRows(), getColumns(), mList, randomList, position);
        } else {
            List<Matrix> mList = AppUtils.getMatrix(getRows(), getColumns());
            List<Integer> randomList = AppUtils.shuffleList(mList);
            setRandomList(randomList);
            setPositionOfLastStoredRandomNumberIfAny(-1);
            mainView.setDefaultData(getRows(), getColumns(), mList, randomList, -1);
        }
    }

    private void setRows(int rows) {
        dataInteractor.setRows(rows);
    }

    private void setColumns(int columns) {
        dataInteractor.setColumns(columns);
    }

    @Override
    public void setRandomNumber(int randomNumber) {
        dataInteractor.setRandomNumber(randomNumber);
    }

    @Override
    public void setRandomColor(int randomColor) {
        dataInteractor.setRandomColor(randomColor);
    }

    private void saveRows(int rows) {
        dataInteractor.saveRows(rows);
    }

    private void saveColumns(int columns) {
        dataInteractor.saveColumns(columns);
    }

    @Override
    public void saveRandomNumber(int randomNumber) {
        dataInteractor.saveRandomNumber(randomNumber);
    }

    @Override
    public void saveRandomColor(int randomColor) {
        dataInteractor.saveRandomColor(randomColor);
    }

    @Override
    public int getRows() {
        return dataInteractor.getRows();
    }

    @Override
    public int getColumns() {
        return dataInteractor.getColumns();
    }

    @Override
    public int getRandomNumber() {
        return dataInteractor.getRandomNumber();
    }

    @Override
    public int getRandomColor() {
        return dataInteractor.getRandomColor();
    }


    private void setRandomList(List<Integer> randomList) {
        dataInteractor.setRandomList(randomList);
    }

    @Override
    public List<Integer> getRandomList() {
        return dataInteractor.getRandomList();
    }

    @Override
    public void setPositionOfLastStoredRandomNumberIfAny(int position) {
        dataInteractor.setPositionOfLastStoredRandomNumberIfAny(position);
    }

    @Override
    public int getPositionOfLastStoredRandomNumberIfAny() {
        return dataInteractor.getPositionOfLastStoredRandomNumberIfAny();
    }

    @Override
    public void onClickApply(int rows, int columns) {
        setRows(rows);
        saveRows(rows);
        setColumns(columns);
        saveColumns(columns);
        List<Matrix> mList = AppUtils.getMatrix(rows, columns);
        List<Integer> randomList = AppUtils.shuffleList(mList);
        saveMatrixList(mList);
        setRandomList(randomList);
        mainView.setRecyclerView(rows, columns, mList);
    }

    private void saveMatrixList(List<Matrix> matrixList) {
        dataInteractor.saveMatrixList(matrixList);
    }

    @Override
    public void onClickRandom() {
        if (Utils.hasElement(getRandomList(), getPositionOfLastStoredRandomNumberIfAny())) {
            int randomNumber = getRandomList().get(getPositionOfLastStoredRandomNumberIfAny());
            setRandomNumber(randomNumber);
            saveRandomNumber(randomNumber);
            mainView.setRandomNumber(randomNumber, true);
            int randomColor = Utils.getRandomColor();
            setRandomColor(randomColor);
            saveRandomColor(randomColor);
            mainView.setRandomColor(randomColor, true);
            mainView.highLightRandomMatch();
            increasePosition();
        } else {
            mainView.onReInitRandomList();
            setPositionOfLastStoredRandomNumberIfAny(0);
            onClickRandom();
        }
    }

    @Override
    public void increasePosition() {
        int position = getPositionOfLastStoredRandomNumberIfAny();
        position = position + 1;
        setPositionOfLastStoredRandomNumberIfAny(position);
    }
}
