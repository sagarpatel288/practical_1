package com.example.android.evince.apputils;

import com.example.android.evince.pojo.Matrix;
import com.example.android.evince.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public final class AppUtils {
    private AppUtils() {
    }

    public static List<Matrix> getMatrix(int rows, int columns) {
        List<Matrix> mList = new ArrayList<>();
        int max = Utils.getTotalItems(rows, columns);
        for (int i = 0; i < max; i++) {
            mList.add(new Matrix(i));
        }
        return mList;
    }
}
