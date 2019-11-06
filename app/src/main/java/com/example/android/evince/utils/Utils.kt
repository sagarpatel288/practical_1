package com.example.android.evince.utils

import android.graphics.Color
import com.google.common.base.Function
import com.google.common.base.Predicate
import com.google.common.collect.Collections2
import java.util.*

object Utils {

    val randomColor: Int
        get() {
            val rnd = Random()
            return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        }

    /**
     * 11/6/2019
     * Check if the element you are trying to access exists in the list
     *
     * @author srdpatel
     * @since $1.0$
     */
    fun hasElement(list: List<*>?, position: Int): Boolean {
        return list != null && list.isNotEmpty() && position != -1 && list.size > position
    }

    fun getTotalItems(rows: Int, columns: Int): Int {
        return rows * columns
    }

    fun <T> getFilteredList(list: List<T>, predicate: Predicate<T>): List<T>? {
        if (isNotNullNotEmpty(list)) {
            val filter = Collections2.filter(list, predicate)
            return ArrayList(filter)
        }
        return list
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
