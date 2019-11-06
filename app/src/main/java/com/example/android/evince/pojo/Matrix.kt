package com.example.android.evince.pojo

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Matrix {

    @PrimaryKey(autoGenerate = true)
    var primaryKey: Int = 0
    var number: Int = 0
    var color: Int = 0
    var isSelected: Boolean = false

    @Ignore
    constructor(number: Int) {
        this.number = number
    }

    @Ignore
    constructor(number: Int, color: Int) {
        this.number = number
        this.color = color
    }

    constructor(number: Int, color: Int, isSelected: Boolean) {
        this.number = number
        this.color = color
        this.isSelected = isSelected
    }
}
