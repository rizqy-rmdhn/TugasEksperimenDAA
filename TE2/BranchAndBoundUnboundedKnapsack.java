package TE2;

import java.util.*;
import java.lang.Math;

public class BranchAndBoundUnboundedKnapsack {

    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000};
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
    public static void test() {
        int[] value = {15, 6, 9, 1, 3, 6, 15, 2};
        int[] weight = {5, 5, 7, 1, 2, 3, 10, 9};
        int W = 9;
        for (int x = 0; x < 5; x++) System.gc();
        long start = System.currentTimeMillis();
        long startSize = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long knapsackSolution = unboundedKnapsack(value, weight, W);
        long finalSize = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - startSize;
        long end = System.currentTimeMillis();
        for (int x = 0; x < 5; x++) System.gc();
        System.out.println(String.format("The solution for the knapsack problem is %d", knapsackSolution));
        System.out.println(String.format("Time taken for solving unbounded knapsack with size=8 and W=%d : %d", 
                                        W, (end - start)));
        System.out.println(String.format("Memory used for solving unbounded knapsack with size=8 and W=%d : %d", 
                                        W, (finalSize)));
    }
    public static long unboundedKnapsack(int[] value, int[] weight, int W) {
        List<Integer> n_new = eliminateDominant(value, weight);
        
        int[] weightNew = new int[n_new.size()];
        int[] valueNew = new int[n_new.size()];

        n_new.sort(new DensityComparator(value, weight));
        for (int i = 0; i < n_new.size(); i++) {
            weightNew[i] = weight[n_new.get(i)];
            valueNew[i] = value[n_new.get(i)];
        }
   
        int[] x_bar = new int[n_new.size()];
        int[] x = new int[n_new.size()];
        int i = 0;
        long z_bar = 0;
        long[][] M = new long[n_new.size()][W+1];
        x[0] = W/weightNew[0];
        long V_N = ( (long) valueNew[0]) * x[0];
        int W_1 = W - weightNew[0] * x[0];
        long U = findU(valueNew, weightNew, V_N, W_1, i);
        z_bar = V_N;
        x_bar = x.clone();

        int[] m = new int[n_new.size()];
        for (int index = 0; index < m.length; index++) {
            m[index] = (int) Integer.MAX_VALUE;
        }
        Map<Integer, Integer> weightMap = new HashMap<>();
        for (int index = 0; index < weightNew.length; index++) {
            weightMap.put(index, weightNew[index]);
        }
        List<Map.Entry<Integer, Integer>> orderedWeight = new ArrayList<>(weightMap.entrySet());
        orderedWeight.sort(Map.Entry.comparingByValue());
        for (Map.Entry<Integer, Integer> entry: orderedWeight) {
            for (int index = 0; index < m.length; index++) {
                if (entry.getValue() < m[index] && entry.getKey() > index) {
                    m[index] = entry.getValue();
                }
            }
        }
        return develop(W_1, m, x, x_bar, z_bar, V_N, U, i, M, weightNew, valueNew);
    }

    public static long develop(int W_1, int[] m, int[] x, int[] x_bar, long z_bar, long V_N, long U, int i, long[][] M, int[] weightNew, int[] valueNew) {
        if (W_1 < m[i]) {
            if (z_bar < V_N) {
                z_bar = V_N;
                x_bar = x.clone();
                if (z_bar == U) {
                    return finishStep(z_bar);
                }
            }
            return backtrack(W_1, m, x, x_bar, z_bar, V_N, U, i, M, weightNew, valueNew);
        }
        else {
            int j = -1;
            for (int index = i + 1; index < weightNew.length; index++) {
                if (weightNew[index] <= W_1) {
                    j = index;
                    break;
                }
            }
            if ((j == -1) || (V_N + findU(valueNew, weightNew, V_N, W_1, j) <= z_bar)) {
                return backtrack(W_1, m, x, x_bar, z_bar, V_N, U, i, M, weightNew, valueNew);
            }
            if (M[i][W_1] >= V_N) {
                return backtrack(W_1, m, x, x_bar, z_bar, V_N, U, i, M, weightNew, valueNew);
            }
            x[j] = W_1/weightNew[j];
            V_N = V_N + ((long) valueNew[j]) * x[j];
            W_1 = W_1 - weightNew[j] * x[j];
            M[i][W_1] = V_N;
            i = j;
            return develop(W_1, m, x, x_bar, z_bar, V_N, U, i, M, weightNew, valueNew);
        }
    }

    public static long backtrack(int W_1, int[] m, int[] x, int[] x_bar, long z_bar, long V_N, long U, int i, long[][] M, int[] weightNew, int[] valueNew) {
        int j = -1;
        for (int index = i; index >= 0; index--) {
            if ((index <= i) && (x[index] > 0)) {
                j = index;
                break;
            }
        }
        if (j == -1) {
            return finishStep(z_bar);
        }
        i = j;
        x[i] = x[i] - 1;
        V_N = V_N - valueNew[i];
        W_1 = W_1 + weightNew[i];
        if (W_1 < m[i]) {
            return backtrack(W_1, m, x, x_bar, z_bar, V_N, U, i, M, weightNew, valueNew);
        }
        if (V_N + (W_1 * valueNew[i+1])/weightNew[i+1] <= z_bar) {
            V_N = V_N - ((long) valueNew[i]) * x[i];
            W_1 = W_1 + weightNew[i] * x[i];
            x[i] = 0;
            return backtrack(W_1, m, x, x_bar, z_bar, V_N, U, i, M, weightNew, valueNew);
        }
        if (W_1 >= m[i]) {
            return develop(W_1, m, x, x_bar, z_bar, V_N, U, i, M, weightNew, valueNew);
        }
        return replace(W_1, m, x, x_bar, z_bar, W_1, j, i, M, weightNew, valueNew);
    }

    public static long replace(int W_1, int[] m, int[] x, int[] x_bar, long z_bar, long V_N, int U, int i, long[][] M, int[] weightNew, int[] valueNew) {
        int j = i;
        int h = j + 1;
        while (true) {
            if (z_bar >= (V_N + (W_1 * valueNew[h])/weightNew[h])) {
                return backtrack(W_1, m, x, x_bar, z_bar, V_N, U, i, M, weightNew, valueNew);
            }
            if (weightNew[h] >= weightNew[j]) {
                if (weightNew[h] == weightNew[j] || weightNew[h] > W_1 || z_bar >= V_N + valueNew[h]) {
                    h++;
                    continue;
                }
                z_bar = V_N + valueNew[h];
                x_bar = x.clone();
                x[h] = 1;
                
                if (z_bar == findU(valueNew, weightNew, V_N, W_1, h)) {
                    return finishStep(z_bar);
                }
                j = h;
                h++;
                continue;
            }
            else {
                if (W_1 - weightNew[h] < m[h-1]) {
                    h++;
                    continue;
                }
                i = h;
                x[i] = W_1/weightNew[i];
                V_N = V_N + ((long) valueNew[i]) * x[i];
                W_1 = W_1 - weightNew[i] * x[i];
                return develop(W_1, m, x, x_bar, z_bar, V_N, U, i, M, weightNew, valueNew);
            }
        }
    }

    public static long finishStep(long z_bar) {
        return z_bar;
    }

    public static List<Integer> eliminateDominant(int[] value, int[] weight) {
        int n = value.length;
        List<Integer> n_new = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            n_new.add(i);
        }
        int j = 0;
        while (j < n_new.size() - 1) {
            int k = j + 1;
            while (k < n_new.size()) {
                if ((weight[n_new.get(k)]/weight[n_new.get(j)]) * (long) value[n_new.get(j)] >= value[n_new.get(k)]) {
                    n_new.remove(k);
                }
                else if ((weight[n_new.get(j)]/weight[n_new.get(k)]) * (long) value[n_new.get(k)] >= value[n_new.get(j)]) {
                    n_new.remove(j);
                    k = n_new.size();
                }
                else {
                    k++;
                }
            }
            j++;
        }
        return n_new;
    }

    public static long findU(int[] value, int[] weight, long V_N, int W_1, int j) {
        if (j + 2 >= value.length) {
            return V_N;
        }
        long z_1 = V_N + W_1/weight[j+1] * ((long) value[j+1]);
        int W_2 = W_1 - W_1/weight[j+1] * weight[j+1];
        long U_1 = z_1 + (W_2 * ((long) value[j+2]))/weight[j+2];
        long U_2 = (long) (z_1 + ( 
                            Math.floor((W_2 
                                        + Math.ceil((weight[j+1] - W_2) * 1.0/weight[j]) 
                                        * weight[j]
                                        )
                                        * (value[j+1] * 1.0)/weight[j+1]
                                        - Math.ceil((weight[j+1] - W_2) * 1.0/weight[j]) 
                                        * value[j]
                                        )
                            ));
        if (U_1 >= U_2) {
            return U_1;
        }
        return U_2;
    }
}
class DensityComparator implements Comparator<Integer> {
        int[] value;
        int[] weight;

        public DensityComparator(int[] value, int[] weight) {
            this.value = value;
            this.weight = weight;
        }

        @Override
        public int compare(Integer i, Integer j) {
            return Double.compare(value[j] * 1.0/weight[j], value[i] * 1.0/weight[i]);
        }
    }