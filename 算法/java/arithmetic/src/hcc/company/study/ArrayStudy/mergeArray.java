package hcc.company.study.ArrayStudy;

import java.util.Arrays;

public class mergeArray {
    public static int[] merge(int[] num1, int[] num2) {
        int[] result = new int[num1.length + num2.length];
        int index = 0, index1 = 0, index2 = 0;
        // 判定index1 index2必须要在num1 和 num2
        while (index1 < num1.length && index2 < num2.length) {
            if (num1[index1] < num2[index2]) {
                result[index++] = num1[index1++];
            } else {
                result[index++] = num2[index2++];
            }
        }
        for (int i = index1; i < num1.length; i++) {
            result[index++] = num1[i];
        }
        for (int i = index2; i < num2.length; i++) {
            result[index++] = num2[i];
        }
        return result;
    }
    public static void main(String[] args) {
        int[] num1 = {12, 11, 5, 8, 3};
        int[] num2 = {12, 11, 5, 8, 3};
        System.out.println(Arrays.toString(merge(num1, num2)));
    }
}
