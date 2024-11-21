import java.util.ArrayList;

public class Problem5 {
    private static void knapsack(int[]weight, int[]profit, int capacity, int n){
        int[][]dp = new int[n+1][capacity+1];
        for(int i = 0 ; i<=n; i++){
            for(int j = 0 ; j<=capacity; j++){
                if(i==0 || j==0){
                    dp[i][j] = 0;
                }else if(j >= weight[i-1]){
                    dp[i][j] = Math.max(dp[i-1][j], profit[i-1] + dp[i-1][j - weight[i-1]]);
                }else{
                    dp[i][j] = dp[i-1][j];
                }
            }
        }

        ArrayList<Integer>ls = new ArrayList<>();
        int w = capacity;
        for(int i = n ; i>0; i--){
            if(dp[i][w]!=dp[i-1][w]){
                ls.add(i);
                w-=weight[i-1];
            }
        }

        System.out.println("Max Profit " + dp[n][capacity]);
        System.out.println("Items to include " + ls);
    }
    public static void main(String[] args) {
        int [] weight = {2,3,4,5};
        int[]profit = {3,4,5,6};
        int capacity = 5;
        int n = weight.length;

        knapsack(weight, profit, capacity, n);
    }
}
