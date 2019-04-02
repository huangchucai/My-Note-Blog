package hcc.company.study.ArrayStudy;

import java.util.Arrays;

public class twoSum {
    public static int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        int start = 0, end = nums.length - 1;
        // 1. 对数组进行排序
        Arrays.sort(nums);
        while (start < end) {
            if (nums[start] + nums[end] == target) {
                result[0] = nums[start];
                result[1] = nums[end];
                break;
            }
            // 小于目标值，前指针后移
            if (nums[start] + nums[end] < target) {
               start++;
            }
            // 大于目标值，后指针前移
            if (nums[start] + nums[end] > target) {
               end--;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {1,7,4,2,5,6};
        int target = 9;
        System.out.println(Arrays.toString(twoSum(nums,target)));
    }
}
