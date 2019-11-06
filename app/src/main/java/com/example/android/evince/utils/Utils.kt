package com.example.android.evince.utils

import android.graphics.Color
import com.google.common.base.Function
import com.google.common.base.Predicate
import com.google.common.collect.Collections2
import com.google.common.collect.FluentIterable
import java.util.*

object Utils {

    // Custom method to generate random HSV color
    // Generate a random hue value between 0 to 360
    // We make the color depth full
    // We make a full bright color
    // We avoid color transparency
    // Finally, generate the color
    // Return the color
    val randomHSVColor: Int
        get() {
            val mRandom = Random()
            val hue = mRandom.nextInt(361)
            val saturation = 1.0f
            val value = 1.0f
            val alpha = 255
            return Color.HSVToColor(alpha, floatArrayOf(hue.toFloat(), saturation, value))
        }

    val randomColor: Int
        get() {
            val rnd = Random()
            return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        }

    fun hasElement(list: List<*>?, position: Int): Boolean {
        return list != null && !list.isEmpty() && position != -1 && list.size > position
    }

    fun getRandomNumber(row: Int, column: Int): Int {
        val random = Random()
        return random.nextInt(getTotalItems(row, column) + 1)
    }

    fun getTotalItems(rows: Int, columns: Int): Int {
        return rows * columns
    }

    fun <T> getFilteredList(list: List<T>, predicate: Predicate<T>): List<T>? {
        if (Utils.isNotNullNotEmpty(list)) {
            val filter = Collections2.filter(list, predicate)
            return ArrayList(filter)
        }
        return list
    }

    fun <S, T> getFluentIterableList(sourceList: List<S>, predicate: Predicate<S>, function: Function<S, T>): List<T> {
        val result = FluentIterable.from(sourceList).filter(predicate).transform(function).toList()
        return ArrayList(result)
    }

    fun isNotNullNotEmpty(list: List<*>?): Boolean {
        return list != null && list.isNotEmpty()
    }

    fun <S, T> transformList(sourceList: MutableList<S>, function: Function<S, T>): MutableList<T> {
        val result = Collections2.transform(sourceList, function)
        return ArrayList(result)
    }

    fun <T> shuffle(list: MutableList<T>): MutableList<T> {
        list.shuffle()
        return list
    }
}
