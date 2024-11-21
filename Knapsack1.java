import java.util.ArrayList;

public class Knapsack1{
    private static void Knapsack(int[]weight, int[]profit, int capacity, int n){
        int[][]dp = new int[n+1][capacity+1];
        for(int i = 0 ; i<=n ; i++){
            for(int j = 0 ; j<=capacity ; j++){
                if(i==0 || j==0){
                    dp[i][j]= 0;
                }else if(j >= weight[i-1]){
                    dp[i][j] = Math.max(dp[i-1][j], profit[i-1] + dp[i-1][j - weight[i-1]]);
                }else{
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        System.out.println("Max Profit is: " + dp[n][capacity]);
        ArrayList<Integer>ls = new ArrayList<>();
        int w = capacity;
        for(int i = n; i>0 ; i--){
            if(dp[i][w]!=dp[i-1][w]){
                ls.add(i);
                w-=weight[i-1];
            }
        }
        System.out.println("\nItem nos to include for max profit: " + ls);
    }
    public static void main(String[] args) {
        int[]weight = {2,4,6,9};
        int[]profit = {10,10,12,18};
        int capacity = 15;
        int n = weight.length;

        Knapsack(weight, profit, capacity, n);
    }
}