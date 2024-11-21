import java.util.Arrays;
import java.util.Random;

public class Problem2 {

    private static void quickSort1(int[] arr1, int low, int high) {
        if (low < high) {
            int pivot = partition2(arr1, low, high);
            quickSort1(arr1, low, pivot - 1);
            quickSort1(arr1, pivot + 1, high);
        }
    }

    private static int partition1(int[] arr1, int low, int high) {
        int pivot = arr1[low];  // First element as pivot
        int i = low + 1;
        int j = high;

        while (i <= j) {
            while (i <= high && arr1[i] <= pivot) i++;
            while (j >= low && arr1[j] > pivot) j--;
            if (i <= j) {
                swap(arr1, i, j);
                i++;
                j--;
            }
        }
        swap(arr1, low, j);  // Place pivot in correct position
        return j;
    }

    private static int partition2(int[] arr1, int low, int high) {
        int mid = low + (high - low) / 2;  // Middle element as pivot
        int i = low;
        int j = high;
        int pivot = arr1[mid];

        while (i <= j) {
            while (arr1[i] < pivot) i++;
            while (arr1[j] > pivot) j--;
            if (i <= j) {
                swap(arr1, i, j);
                i++;
                j--;
            }
        }
        return i;
    }

    private static int partition3(int[] arr1, int low, int high) {
        int pivot = arr1[high];  // Last element as pivot
        int i = low;
        int j = high - 1;
        while (i <= j) {
            while (i <= j && arr1[i] <= pivot) i++;
            while (i <= j && arr1[j] > pivot) j--;
            if (i <= j) {
                swap(arr1, i, j);
                i++;
                j--;
            }
        }
        swap(arr1, i, high);  // Place pivot in correct position
        return i;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void quickSort2(int[] arr2, int low, int high) {
        if (low < high) {
            int pivot = randomPartition(arr2, low, high);
            quickSort2(arr2, low, pivot - 1);
            quickSort2(arr2, pivot + 1, high);
        }
    }

    private static int randomPartition(int[] arr2, int low, int high) {
        int pivotIndex = low + (int) (Math.random() * (high - low + 1));
        swap(arr2, pivotIndex, high);
        return partition3(arr2, low, high);
    }

    public static void main(String[] args) {
        int n = 500;
        int[] arr1 = new int[n];
        int[] arr2 = new int[n];
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            arr1[i] = rand.nextInt(600);
            arr2[i] = arr1[i];
        }

        System.out.println("Original Array: " + Arrays.toString(arr1));
        
        long startTime = System.nanoTime();
        quickSort1(arr1, 0, n - 1);
        long endTime = System.nanoTime();
        double quickSortTime = (endTime - startTime) / 1000000.0;
        System.out.println("\nQuicksort time (First element as pivot): " + quickSortTime + " ms");
        System.out.println("\nSorted Array: " + Arrays.toString(arr1));

        startTime = System.nanoTime();
        quickSort2(arr2, 0, n - 1);
        endTime = System.nanoTime();
        double quickSortTime2 = (endTime - startTime) / 1000000.0;
        System.out.println("\nQuicksort time (Randomized quick Sort): " + quickSortTime2 + " ms");
        System.out.println("\nSorted Array: " + Arrays.toString(arr2));
    }
}
