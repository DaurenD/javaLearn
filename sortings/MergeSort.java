import java.util.Random;

/**
 * Created by dauren on 1/18/2015.
 */
public class MergeSort {

    private static int[] helper;
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
        helper = new int[a.length];
        mergesort(a, 0, a.length - 1);
    }

    private static void mergesort(int[] a, int start, int end) {
        if (start < end) {
            mergesort(a, start, (start + end) / 2);
            mergesort(a, (start + end) / 2 + 1, end);
            merge(a, start, (start + end) / 2, end);
        }
    }

    private static void merge(int[] a, int start, int mid, int end) {
        System.arraycopy(a, 0, helper, 0, a.length);

        int i = start;
        int j = mid + 1;
        int k = start;
        for (; i <= mid && j <= end; k++) {
            if (helper[i] <= helper[j]) {
                a[k] = helper[i];
                i++;
            } else {
                a[k] = helper[j];
                j++;
            }
        }

        while (i <= mid) {
            a[k] = helper[i];
            k++;
            i++;
        }
    }

    public static void main(String[] args) {
        int[] a = createRandom(20);

        showArr(a);
        System.out.println();

        sort(a);
        showArr(a);

    }
}
