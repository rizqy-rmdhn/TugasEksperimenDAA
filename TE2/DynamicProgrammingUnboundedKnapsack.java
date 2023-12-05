package TE2;

import java.lang.Math;
import java.util.Random;

public class DynamicProgrammingUnboundedKnapsack {
    public static long unboundedKnapsack(int[] value, int[] weight, int W) {
        int n = value.length; 

        long dp[] = new long[W + 1]; 

        // Fill dp[] using above recursive formula 
        for(int i = 0; i <= W; i++){ 
            for(int j = 0; j < n; j++){ 
                if(weight[j] <= i){ 
                    dp[i] = Math.max(dp[i], dp[i - weight[j]] + value[j]); 
                } 
            } 
        } 
        return dp[W];
    }
    
    public static void test() {
        int[] value = {15, 6, 9, 1, 3, 6, 15, 2};
        int[] weight = {5, 5, 7, 1, 2, 3, 10, 9};
        int W = 9;
        
        long start = System.currentTimeMillis();
        long startSize = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long knapsackSolution = unboundedKnapsack(value, weight, W);
        long finalSize = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - startSize;
        long end = System.currentTimeMillis();

        System.out.println(String.format("The solution for the knapsack problem is %d", knapsackSolution));
        System.out.println(String.format("Time taken for solving unbounded knapsack with size 8 and W=%d : %d", 
                                        W, (end - start)));
        System.out.println(String.format("Memory used for solving unbounded knapsack with size 8 and W=%d : %d", 
                                        W, (finalSize)));
    }
    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000}; // 100, 1000, 10000
        int W = 1008053;
        for (int x = 0; x < 5; x++) System.gc();
        for (int size: sizes) {
            for (int x = 0; x < 5; x++) System.gc();
            Random rand  = new Random(2106632182); // generate random with seed as NPM
            int[] value = KnapsackDataGenerator.generateRandomData(size, rand);
            int[] weight = KnapsackDataGenerator.generateRandomDataWithBoundary(size, rand, W);
            
            for (int x = 0; x < 5; x++) System.gc();
            long start = System.currentTimeMillis();
            long startSize = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
            long knapsackSolution = unboundedKnapsack(value, weight, W);
            long finalSize = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - startSize;
            long end = System.currentTimeMillis();
            for (int x = 0; x < 5; x++) System.gc();
            System.out.println(String.format("The solution for the knapsack problem is %d", knapsackSolution));
            System.out.println(String.format("Time taken for solving unbounded knapsack with size %d and with W=%d : %d", 
                                            size, W, (end - start)));
            System.out.println(String.format("Memory used for solving unbounded knapsack with size %d and with W=%d : %d", 
                                            size, W, (finalSize)));
            for (int x = 0; x < 5; x++) System.gc();
        }
    }
}
