import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Problem4 {

    private static void mergeSortBestAvg(int[] arr1, int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            mergeSortBestAvg(arr1, low, mid);
            mergeSortBestAvg(arr1, mid + 1, high);
            merge(arr1, low, mid, high);
        }
    }

    private static void merge(int[] arr, int low, int mid, int high) {
        ArrayList<Integer> ls = new ArrayList<>();
        int i = low;
        int j = mid + 1;
        while (i <= mid && j <= high) {
            if (arr[i] <= arr[j]) {
                ls.add(arr[i]);
                i++;
            } else {
                ls.add(arr[j]);
                j++;
            }
        }

        while (i <= mid) {
            ls.add(arr[i]);
            i++;
        }

        while (j <= high) {
            ls.add(arr[j]);
            j++;
        }

        for (int k = low; k <= high; k++) {
            arr[k] = ls.get(k - low);
        }
    }

    private static void mergeSortWorst(int[] arr2, int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            mergeSortWorst(arr2, low, mid);
            mergeSortWorst(arr2, mid + 1, high);
            merge(arr2, low, mid, high);
        }
    }

    public static void main(String[] args) {
        int n = 700;
        int[] arr1 = new int[n];
        int[] arr2 = new int[n];

        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            arr1[i] = rand.nextInt(700);
            arr2[i] = arr1[i];
        }

        // Warm-up loop to let JVM optimize
        for (int i = 0; i < 5; i++) {
            mergeSortBestAvg(arr1, 0, n - 1);
        }

        // Best Case (Random array)
        long startTime = System.nanoTime();
        mergeSortBestAvg(arr1, 0, n - 1);
        long endTime = System.nanoTime();
        double mergeSortBestAvgTime = (endTime - startTime) / 1000000.0;
        System.out.println("Merge Sort Best Case Time: " + mergeSortBestAvgTime + "ms");

        // Worst Case (Pre-sorted array)
        Arrays.sort(arr2);
        startTime = System.nanoTime();
        mergeSortWorst(arr2, 0, n - 1);
        endTime = System.nanoTime();
        double mergeSortWorstCaseTime = (endTime - startTime) / 1000000.0;
        System.out.println("Merge Sort Worst Case Time: " + mergeSortWorstCaseTime + "ms");
    }
}
