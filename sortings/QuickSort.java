import java.util.Random;

/**
 * Created by dauren on 1/19/2015.
 */
public class QuickSort {

    static void showArr(int[] a) {
        for (int arr : a) {
            System.out.print("[" + arr + "] ");
        }
    }

    static int[] createRandom(int n) {
        int[] r = new int[n];
        for (int i = 0; i < r.length; i++) {
            r[i] =  new Random().nextInt(n);
        }
        return r;
    }

    static void sort(int[] a) {
        if (a == null || a.length == 0) {
            System.out.println("error input");
            return;
        }

        quicksort(a, 0, a.length - 1);
    }

    private static void quicksort(int[] a, int start, int end) {
        int i = start;
        int j = end;

        int pivot = a[(start + end) / 2];
        while (i <= j) {
            while (pivot > a[i]) {
                i++;
            }

            while (pivot < a[j]) {
                j--;
            }

            if (i <= j) {
                int temp = a[j];
                a[j] = a[i];
                a[i] = temp;
                i++;
                j--;
            }
        }

        if (start < j) {
            quicksort(a, start, j);
        }
        if (i < end) {
            quicksort(a, i, end);
        }
    }

    public static void main(String[] args) {
        int[] a = createRandom(10);

        showArr(a);
        System.out.println();
        System.out.println();
        sort(a);
        showArr(a);
    }
}
