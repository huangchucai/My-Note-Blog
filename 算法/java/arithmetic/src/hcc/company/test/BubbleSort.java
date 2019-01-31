package hcc.company.test;

import java.util.Arrays;

public class BubbleSort {
    public static int[] isort(int[] arr)  {
        for(int i = 0; i < arr.length; i++) {
            // 从0开始到已经排好序的值
            for(int j = 0; j < arr.length - i -1; j++) {
                if (arr[j+1] < arr[j]) {
                    swap(arr, j, j+1);
                }
            }
        }
        return arr;
    }
    public static void swap(int[] arr, int indexA, int indexB ) {
        int temp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = temp;
    }
    public static void main(String[] args) {
        int[] arr = {3,6,2,1,5,2};
        System.out.println(Arrays.toString(isort(arr)));
    }
}
