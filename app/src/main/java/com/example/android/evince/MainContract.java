package com.example.android.evince;

import com.example.android.evince.pojo.Matrix;

import java.util.List;

public interface MainContract {

     interface MainView {
         void setRows(int mRows, boolean setViewValue);

         void setColumns(int mColumns, boolean setViewValue);

         void setRandomNumber(int randomNumber, boolean setViewValue);

         void setRandomColor(int randomColor, boolean setViewValue);

         void setDefaultData(int rows, int columns, List<Matrix> matrixList, List<Integer> randomList, int positionOfLastStoredRandomNumberIfAny);

         void setRecyclerView(int rows, int columns, List<Matrix> mList);

         void onReInitRandomList();

         void highLightRandomMatch();
    }

     interface Presenter {
         void handleViews();

         void setRandomNumber(int randomNumber);

         void setRandomColor(int randomColor);

         void saveRandomNumber(int randomNumber);

         void saveRandomColor(int randomColor);

         int getRows();

         int getColumns();

         int getRandomNumber();

         int getRandomColor();

         List<Integer> getRandomList();

         void setPositionOfLastStoredRandomNumberIfAny(int position);

         int getPositionOfLastStoredRandomNumberIfAny();

         void onClickApply(int rows, int columns);

         void onClickRandom();

         void increasePosition();
    }

     interface DataInteractor {

         void setRows(int rows);

         void setColumns(int columns);

         void setRandomNumber(int randomNumber);

         void setRandomColor(int randomColor);

         void saveRows(int rows);

         void saveColumns(int columns);

         void saveRandomNumber(int randomNumber);

         void saveRandomColor(int randomColor);

         int getRows();

         int getColumns();

         int getRandomNumber();

         int getRandomColor();

         void saveMatrixList(List<Matrix> matrixList);

         void setRandomList(List<Integer> randomList);

         List<Integer> getRandomList();

         void setPositionOfLastStoredRandomNumberIfAny(int position);

         int getPositionOfLastStoredRandomNumberIfAny();
    }
}
