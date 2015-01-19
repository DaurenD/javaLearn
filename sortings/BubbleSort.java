import java.util.Date;
import java.util.Random;

public class BubbleSort {


    static int[] createRandom(int n) {
        int[] r = new int[n];

        for (int i = 0; i < r.length; i++) {
            r[i] =  new Random().nextInt(n);
        }

        return r;
    }

    static void showArr(int[] a) {
        for (int arr : a) {
            System.out.print("[" + arr + "] ");
        }
    }
    static void sort(int[] a) {
        for (int k = a.length - 1; k >= 0; k--) {
            for (int i = 0; i < a.length - 1; i++) {
                if (a[i] > a[i+1]) {
                    int temp = a[i + 1];
                    a[i + 1] = a[i];
                    a[i] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {

        int[] a = createRandom(10);

        showArr(a);

        sort(a);
        System.out.println("\n\t\tAfter sorting:");
        showArr(a);

    }
}
