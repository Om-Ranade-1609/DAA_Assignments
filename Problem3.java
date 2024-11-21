import java.util.Arrays;
import java.util.Random;

public class Problem3 {
    private static void quickSortBestAvg(int[]arr1, int low, int high){
        if(low<high){
            int pivot = partition(arr1, low, high);
            quickSortBestAvg(arr1, low, pivot-1);
            quickSortBestAvg(arr1, pivot+1, high);
        }
    }

    private static int partition(int[]arr1, int low, int high){
        int mid = low + (high-low) /2;
        int pivot = arr1[mid];
        int i = low;
        int j = high;

        while(i<=j){
            while(arr1[i]<pivot){
                i++;
            }
            while(arr1[j]>pivot){
                j--;
            }
            
            if(i<=j){
                swap(arr1, i, j);
                i++;
                j--;
            }

        }
        return i;
    }

    private static void swap(int[]arr, int i , int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void quickWorstCase(int[]arr2, int low, int high){
        if(low<high){
            int pivot = partitionWorst(arr2, low, high);
            quickWorstCase(arr2, low, pivot-1);
            quickWorstCase(arr2, pivot+1, high);
        }
    }

    private static int partitionWorst(int[]arr2, int low, int high){
        int pivot = arr2[low];
        int i = low+1;
        int j = high;
        while(i<=j){
            while(i<=high && arr2[i]<=pivot){
                i++;
            }
            while(j>=low && arr2[j]>pivot){
                j--;
            }
            if(i<j){
                swap(arr2, i, j);
                i++;
                j--;
            }
        }
        swap(arr2, low, j);
        return j;   
    }
    public static void main(String[] args) {
        int n = 700;

        int[]arr1 = new int[n];
        int [] arr2 = new int[n];

        Random rand = new Random();

        for(int i = 0 ; i <n ; i++){
            arr1[i] = rand.nextInt(800);
            arr2[i] = arr1[i];
        }

        long start = System.nanoTime();
        quickSortBestAvg(arr1, 0, n-1);
        long end = System.nanoTime();
        double quickSortTime = (end - start) / 1000000.0;
        System.out.println("Quick sort time: " + quickSortTime + " ms");

        Arrays.sort(arr2);
        start = System.nanoTime();
        quickWorstCase(arr2, 0, n-1);
        end = System.nanoTime();
        double worstCaseTime = (end - start) / 1000000.0;
        System.out.println("\nWorst case quick sort time: " + worstCaseTime + " ms");
    }
}
