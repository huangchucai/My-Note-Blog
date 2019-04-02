package hcc.company.study.ArrayStudy;

import java.util.Arrays;

public class oddEvenSort {
    public static int[] oddEvenSort(int[] nums) {
        int start = 0, end = nums.length - 1;
        while (start < end) {
            // start 为奇数, 就偏移指针只到为偶数
            while (start < end && nums[start] % 2 != 0) {
                start++;
            }
            // end 为偶数
            while (start < end && nums[end] % 2 == 0) {
                end--;
            }
            if(start < end ) {
                swap(start++, end--, nums);
            }
        }
        return nums;
    }

    public static void swap(int start, int end, int[] nums) {
        int temp = nums[start];
        nums[start] = nums[end];
        nums[end] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {12, 11, 5, 8, 3};
        System.out.println(Arrays.toString(oddEvenSort(arr)));
    }
}
