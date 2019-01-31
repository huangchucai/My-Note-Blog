package hcc.company.test;

import java.util.Arrays;

/*
*
给一个整数数组，找到两个数使得他们的和等于一个给定的数 target。

你需要实现的方法twoSum需要返回这两个数的下标, 并且第一个下标小于第二个下标。注意这里下标的范围是 0 到 n-1。

PS: 你可以假设只有一组答案。
*
* */
public class Solution {
    /**
     * @param numbers: An array of Integer
     * @param target: target = numbers[index1] + numbers[index2]
     * @return: [index1, index2] (index1 < index2)
     */
    public static int[] twoSum(int[] numbers, int target) {
        int sum;
        int[] result = new int[2];
        // 写具体实现
        label:
        for(int i = 0; i < numbers.length; i++) {
            for(int j = i+1; j < numbers.length; j++ ) {
                sum = numbers[i] + numbers[j];
                if(sum == target) {
                    result[0] = i;
                    result[1] = j;
                    break label;
                }
            }
        }
        return result;
    }
    public static void main(String[] args) {
        int[] arr = twoSum(new int[]{22, 7, 2, 15}, 9);
        int[] arr2 = twoSum(new int[]{11, 7, 2, 15, 11}, 22);
        int[] arr3 = twoSum(new int[]{23, 7, 2, 22, 11}, 45);
        int[] arr4 = twoSum(new int[]{23, 7, 2, 22, 11}, 99);
        int[] arr5 = twoSum(new int[]{111, 7, 2, 22, 11}, 122);
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(arr2));
        System.out.println(Arrays.toString(arr3));
        System.out.println(Arrays.toString(arr4));
        System.out.println(Arrays.toString(arr5));
//        System.out.println(arr[0]);
//        System.out.println(arr[1]);
    }
}
