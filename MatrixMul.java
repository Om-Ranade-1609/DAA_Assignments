import java.util.Scanner;

public class MatrixMul {

    private static void inputMatrix(int[][] matrix, Scanner sc) {
        System.out.println("Enter the elements of the matrix:");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }
    }

    private static void displayMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void multiplySequential(int[][] A, int[][] B, int[][] resultSequential) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B[0].length; j++) {
                resultSequential[i][j] = 0;
                for (int k = 0; k < A[0].length; k++) {
                    resultSequential[i][j] += A[i][k] * B[k][j];
                }
            }
        }
    }

    private static void multiplyParallel(int[][] A, int[][] B, int[][] resultParallel) {
        int rows = A.length;
        Thread[] threads = new Thread[rows];

        for (int i = 0; i < rows; i++) {
            final int row = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < B[0].length; j++) {
                    resultParallel[row][j] = 0;
                    for (int k = 0; k < A[0].length; k++) {
                        resultParallel[row][j] += A[row][k] * B[k][j];
                    }
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of rows of the first matrix: ");
        int rowsA = sc.nextInt();

        System.out.println("Enter the number of columns of the first matrix and the number of rows of the second matrix: ");
        int colsA = sc.nextInt();

        System.out.println("Enter the number of columns of the second matrix: ");
        int colsB = sc.nextInt();

        int[][] A = new int[rowsA][colsA];
        int[][] B = new int[colsA][colsB];
        int[][] resultSequential = new int[rowsA][colsB];
        int[][] resultParallel = new int[rowsA][colsB];

        System.out.println("\nInput for Matrix A:");
        inputMatrix(A, sc);

        System.out.println("\nInput for Matrix B:");
        inputMatrix(B, sc);

        System.out.println("\nMatrix A:");
        displayMatrix(A);

        System.out.println("\nMatrix B:");
        displayMatrix(B);

        // Sequential Multiplication
        long startTime = System.nanoTime();
        multiplySequential(A, B, resultSequential);
        long endTime = System.nanoTime();
        double sequentialTime = (endTime - startTime) / 1_000_000.0;
        System.out.println("Time taken by Sequential Method: " + sequentialTime + " ms");

        System.out.println("\nResult of Sequential Multiplication:");
        displayMatrix(resultSequential);

        // Parallel Multiplication
        startTime = System.nanoTime();
        multiplyParallel(A, B, resultParallel);
        endTime = System.nanoTime();
        double parallelTime = (endTime - startTime) / 1_000_000.0;
        System.out.println("Time taken by Parallel Method: " + parallelTime + " ms");

        System.out.println("\nResult of Parallel Multiplication:");
        displayMatrix(resultParallel);

        sc.close();
    }
}
