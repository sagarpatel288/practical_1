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

    @Query("UPDATE Matrix SET isSelected = 0 WHERE primaryKey IN (:primaryIds)")
    void setIsSelectedFalse(List<Integer> primaryIds);

    @Query("UPDATE Matrix SET isSelected = 0 AND color = 0")
    void setAllMatricesSelectedToFalse();

    @Query("UPDATE Matrix SET isSelected =:isSelected AND color =:color WHERE primaryKey =:primaryKey")
    void updateMatrix(int primaryKey, boolean isSelected, int color);

    @Delete
    void delete(Matrix matrix);

    @Query("SELECT * FROM Matrix WHERE isSelected = 1")
    Matrix getSelectedMatrix();

    @Query("SELECT * FROM Matrix WHERE number = :number")
    Matrix getMatrix(int number);

    @Query("DELETE FROM Matrix")
    void deleteMatrix();

    @Query("SELECT * FROM Matrix")
    List<Matrix> getAllMatrices();
}