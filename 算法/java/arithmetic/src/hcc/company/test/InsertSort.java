package hcc.company.test;
import java.util.Arrays;

public class InsertSort {
    public static int[] isort(int[] num) {
        for (int i = 1; i < num.length; i++) {
            int tmp = num[i];
            int j;
            // 已经排好序的数组
            for (j = i - 1; j >= 0 && tmp < num[j]; j--) {
                num[j+1] = num[j];
            }
            num[j+1] = tmp;
        }
        return num;
    }
    public static void main(String[] args) {
        int[] arr = {3,6,2,1,2,5};
        System.out.println(Arrays.toString(isort(arr)));
    }
}
