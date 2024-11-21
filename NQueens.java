import java.util.Scanner;

public class NQueens {
    private static void solveNQueens(int[]board, int row){
        int n = board.length;
        if(row==n){
            printBoard(board);
            System.out.println();
            return;
        }

        for(int col = 0 ; col<n ; col++){
            if(isSafe(board, row, col)){
                board[row] = col;
                solveNQueens(board, row+1);
                board[row] = -1;
            }
        }
    }

    private static boolean isSafe(int[]board, int row, int col){
        for(int i = 0; i<row; i++){
            if(board[i]==col  || Math.abs(board[i] - col)==Math.abs(i-row)){
                return false;
            }
        }

        return true;
    }

    private static void printBoard(int[]board){
        int n = board.length;
        for(int i = 0 ; i<n ; i++){
            for(int j = 0 ; j<n ; j++){
                if(board[i]==j){
                    System.out.print("Q ");
                }else{
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        int[]board = new int[n];
        for(int i = 0 ; i<board.length; i++){
            board[i] = -1;
        }

        long startTime = System.nanoTime();
        solveNQueens(board, 0 );
        long endTime = System.nanoTime();
        double totalTime = (endTime - startTime) / 1e6;
        System.out.println("Time taken: " + totalTime + " ms");

    }
}