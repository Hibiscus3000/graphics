package ru.nsu.fit.icg.lab1;

import java.util.Arrays;

public class ArrayConcatenation {

    public static <T> T[] concatArrays(T[] arr1, T[] arr2) {
        T[] result = Arrays.copyOf(arr1, arr1.length + arr2.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }
}
