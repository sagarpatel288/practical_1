package com.example.android.evince.database;

import com.example.android.evince.pojo.Matrix;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AppDao {

    @Insert
    void insertMatrices(List<Matrix> matrixList);

    @Update
    void updateMatrix(Matrix matrix);

    @Delete
    void delete(Matrix matrix);

    @Query("SELECT * FROM Matrix WHERE isSelected = 1")
    Matrix getSelectedMatrix();

    @Query("DELETE FROM Matrix")
    void deleteMatrix();

    @Query("SELECT * FROM Matrix")
    List<Matrix> getAllMatrices();
}