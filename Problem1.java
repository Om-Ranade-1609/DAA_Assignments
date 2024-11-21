import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Problem1 {
    private static void quickSort(int[]arr1, int low, int high){
        if(low<high){
            int pivot = partition(arr1, low, high);
            quickSort(arr1, low, pivot-1);
            quickSort(arr1, pivot+1, high);
        }
    }

    private static int partition(int[]arr1, int low, int high){
        int pivot = arr1[high];
        int i = (low-1);
        for(int j = low; j<high ; j++){
            if(arr1[j]<=pivot){
                i++;
                swap(arr1, i, j);
            }
        }
        swap(arr1, i+1, high);
        return (i+1);
    }

    private static void swap(int [] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void mergeSort(int[]arr2, int low, int high){
        if(low<high){
            int mid = low + (high - low) /2;
            mergeSort(arr2, low, mid);
            mergeSort(arr2, mid+1, high);
            merge(arr2, low, mid, high);
        }
    }


    private static void merge(int[]arr2, int low, int mid, int high){
        ArrayList<Integer>ls = new ArrayList<>();
        int i = low;
        int j = mid+1;

        while(i<=mid && j<=high){
            if(arr2[i]<=arr2[j]){
                ls.add(arr2[i]);
                i++;
            }else{
                ls.add(arr2[j]);
                j++;
            }
        }

        while(i<=mid){
            ls.add(arr2[i]);
            i++;
        }

        while(j<=high){
            ls.add(arr2[j]);
            j++;
        }
        for(int k = low ; k<=high; k++){
            arr2[k] = ls.get(k-low);
        }
    }

    
    public static void main(String[] args) {
        int n = 500;

        int[]arr1= new int[n];
        int []arr2 = new int[n];


        Random rand = new Random();
        for(int i = 0 ; i<n ; i++){
            arr1[i] = rand.nextInt(600);
            arr2[i] = arr1[i];
        }

        System.out.println("Original Array: " + Arrays.toString(arr1));

        long startTime = System.nanoTime();
        quickSort(arr1, 0, n-1);
        long endTime = System.nanoTime();
        double quickSortTime = (endTime - startTime)/1000000.0;
        System.out.println("\nSorted Array: " + Arrays.toString(arr1));
        System.out.println("\nQuick Sort Time: " + quickSortTime + " ms.");

        startTime = System.nanoTime();
        mergeSort(arr2, 0, n-1);
        endTime = System.nanoTime();
        double mergeSortTime = (endTime - startTime)/1000000.0;
        System.out.println("Sorted Array Using merge sort: " + Arrays.toString(arr2));
        System.out.println("\nMerge Sort Time: " + mergeSortTime + " ms.");
    }
}
