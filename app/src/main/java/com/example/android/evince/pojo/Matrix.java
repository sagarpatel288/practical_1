package com.example.android.evince.pojo;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Matrix {

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    @PrimaryKey(autoGenerate = true)
    private int primaryKey;
    private int number;
    private int color;
    private boolean isSelected;

    @Ignore
    public Matrix(int number) {
        this.number = number;
    }

    @Ignore
    public Matrix(int number, int color) {
        this.number = number;
        this.color = color;
    }

    public Matrix(int number, int color, boolean isSelected) {
        this.number = number;
        this.color = color;
        this.isSelected = isSelected;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
