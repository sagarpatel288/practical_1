package com.example.android.evince.utils;

import android.graphics.Color;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public final class Utils {

    private Utils() {
    }

    public static boolean hasElement(List list, int position){
        return list != null && !list.isEmpty() && position != -1 && list.size() > position;
    }

    public static boolean isNotNullNotEmpty(List list){
        return list != null && !list.isEmpty();
    }

    // Custom method to generate random HSV color
    public static int getRandomHSVColor(){
        Random mRandom = new Random();
        // Generate a random hue value between 0 to 360
        int hue = mRandom.nextInt(361);
        // We make the color depth full
        float saturation = 1.0f;
        // We make a full bright color
        float value = 1.0f;
        // We avoid color transparency
        int alpha = 255;
        // Finally, generate the color
        // Return the color
        return Color.HSVToColor(alpha, new float[]{hue, saturation, value});
    }

    public static int getTotalItems(int rows, int columns) {
        return rows * columns;
    }

    public static int getRandomNumber(int row, int column) {
        Random random = new Random();
        return random.nextInt(getTotalItems(row, column) + 1);
    }

    public static <T> List<T> getFilteredList(List<T> list, Predicate<T> predicate) {
        if (Utils.isNotNullNotEmpty(list)) {
            Collection<T> filter = Collections2.filter(list, predicate);
            return new ArrayList<>(filter);
        }
        return list;
    }
}
