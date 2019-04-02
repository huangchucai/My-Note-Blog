package hcc.company.study.ArrayStudy;

import java.util.Arrays;

public class threeSum {
    public static int[] threeSum(int[] nums, int target) {
        int[] result = new int[3];
        if (nums.length < 3) {
            return nums;
        }
        Arrays.sort(nums);
        // 第三个指针
        int second = nums.length - 1;
        // 遍历数组
        for (int i = 0; i < nums.length - 2; i++) {
            // 第二个指针
            int first = i + 1;
            while (first < second) {
                if (nums[first] + nums[second] == target - nums[i]) {
                    result[0] = nums[i];
                    result[1] = nums[first];
                    result[2] = nums[second];
                    return result;
                }
                if (nums[second] + nums[first] < target - nums[i]) {
                    first++;
                }
                if (nums[second] + nums[first] > target - nums[i]) {
                    second--;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {1, 7, 4, 2, 5, 6};
        int target = 9;
        System.out.println(Arrays.toString(threeSum(nums, target)));
    }

}
