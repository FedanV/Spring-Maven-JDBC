package com.foxminded.vitaliifedan.utils;

public class NumberUtils {

    private NumberUtils() {
    }

    public static int length(int number) {
        return number < 10 ? 1 : (int) Math.log10(number) + 1;
    }

    public static int[] numberToDigits(int number) {
        int lengthNumber = length(number);
        int[] result = new int[lengthNumber];
        for (int i = lengthNumber - 1; i >= 0; i--) {
            result[i] = number % 10;
            number /= 10;
        }
        return result;
    }
}
