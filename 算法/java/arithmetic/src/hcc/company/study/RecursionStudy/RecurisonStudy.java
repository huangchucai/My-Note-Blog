package hcc.company.study.RecursionStudy;

public class Fobonacci {
    public static int fobonacci(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }
        return fobonacci(n-1) + fobonacci(n-2);
    }

    public static void main(String[] args) {
        System.out.println(fobonacci(6));
    }
}
