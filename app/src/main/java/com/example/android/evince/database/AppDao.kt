package com.example.android.evince.database

import androidx.room.*
import com.example.android.evince.pojo.Matrix

@Dao
interface AppDao {

    @get:Query("SELECT * FROM Matrix WHERE isSelected = 1")
    val selectedMatrix: Matrix

    @get:Query("SELECT * FROM Matrix")
    val allMatrices: MutableList<Matrix>

    @Insert
    fun insertMatrices(matrixList: MutableList<Matrix>)

    @Update
    fun updateMatrix(matrix: Matrix)

    @Query("UPDATE Matrix SET isSelected = 0 WHERE primaryKey IN (:primaryIds)")
    fun setIsSelectedFalse(primaryIds: List<Int>)

    @Query("UPDATE Matrix SET isSelected = 0 AND color = 0")
    fun setAllMatricesSelectedToFalse()

    @Query("UPDATE Matrix SET isSelected =:isSelected AND color =:color WHERE primaryKey =:primaryKey")
    fun updateMatrix(primaryKey: Int, isSelected: Boolean, color: Int)

    @Delete
    fun delete(matrix: Matrix)

    @Query("SELECT * FROM Matrix WHERE number = :number")
    fun getMatrix(number: Int): Matrix

    @Query("DELETE FROM Matrix")
    fun deleteMatrix()
}