package hcc.company.study.ArrayStudy;

import java.util.Arrays;

public class reverseArray {
    public static void swap(int start, int end, int[] nums) {
        int temp = nums[start];
        nums[start] = nums[end];
        nums[end] = temp;
    }

    public static int[] reserveArray(int[] nums) {
        int start = 0, end = nums.length - 1;
        while (start < end) {
            // 交换完成更新索引
            swap(start++, end--, nums);
        }
        return nums;
    }

    public static void main(String[] args) {
        int[] arr = {12, 11, 5, 8, 3};
        System.out.println(Arrays.toString(reserveArray(arr)));
    }
}
