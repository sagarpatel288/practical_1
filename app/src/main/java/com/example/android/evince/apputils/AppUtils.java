package com.example.android.evince.apputils;

import android.content.Context;

import com.example.android.evince.database.AppDatabase;
import com.example.android.evince.pojo.Matrix;
import com.example.android.evince.utils.Utils;
import com.google.common.base.Function;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

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

    @SuppressWarnings("unchecked")
    public static List<Integer> shuffleList(List<Matrix> mList) {
        return Utils.shuffle(Utils.transformList(mList, new Function<Matrix, Integer>() {
            @NullableDecl
            @Override
            public Integer apply(@NullableDecl Matrix input) {
                return input.getNumber();
            }
        }));
    }

    public static void setSelectedFalseInDb(Context context) {
        AppDatabase.getInstance(context).getAppDao().setAllMatricesSelectedToFalse();
    }

    public static void setSelectedInDb(Context context, int number, int color) {
        Matrix matrix = AppDatabase.getInstance(context).getAppDao().getMatrix(number);
        if (matrix != null) {
            matrix.setColor(color);
            matrix.setSelected(true);
            AppDatabase.getInstance(context).getAppDao().updateMatrix(matrix.getPrimaryKey(), true, color);
        }
    }
}
