package TE1;

import java.util.Random;
public class RandomizedShellSort {
    public static final int C=4;

    public static void exchange(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    
    public static void compareExchange(int[] a, int i, int j) {
        if (i < j && a[i] > a[j] || i > j && a[i] < a[j]) {
            exchange(a, i, j);
        }
    }
    
    public static void permuteRandom(int a[], Random rand) {
        for (int i=0; i<a.length; i++) {
            exchange(a, i, rand.nextInt(a.length-i) + i);
        }
    }

    public static void compareRegions(int[] a, int s, int t, int offset, Random rand) {
        int order[] = new int[offset];
        for (int count=0; count<C; count++) {
            for (int i = 0; i<offset; i++) {
                order[i] = i;
            }
            permuteRandom(order, rand);
            for (int i=0; i<offset; i++) {
                compareExchange(a, s+i, t+order[i]);
            }
        }
    }
    public static int[] randomizedShellSort(int[] a) {
        int n = a.length;
        Random rand = new Random();
        for (int offset = n/2; offset > 0; offset /= 2) {
            for (int p: a) {
                System.out.print(p + " ");
            }
            System.out.println();
            for (int i = 0; i < n - offset; i += offset) {
                compareRegions(a, i, i + offset, offset, rand);
            }
            for (int i = n - offset; i >= offset; i -= offset) {
                compareRegions(a, i - offset, i, offset, rand);
            }
            for (int i = 0; i < n - 3 * offset; i += offset) {
                compareRegions(a, i, i + 3* offset, offset, rand);
            }
            for (int i = 0; i < n - 2 * offset; i += offset) {
                compareRegions(a, i, i + 2 * offset, offset, rand);
            }
            for (int i = 0; i < n; i += 2 * offset) {
                compareRegions(a, i, i + offset, offset, rand);
            }
            for (int i = offset; i < n - offset; i += 2 * offset) {
                compareRegions(a, i, i + offset, offset, rand);
            }
        }
        for (int p: a) {
                System.out.print(p + " ");
            }
        System.out.println();
        return a;
    }
    public static void main(String[] args) {
        int[] test = {5, 3, 8, 2, 6, 7, 1, 4};
        test = randomizedShellSort(test);
    }
    //     int[] sizes = {(int) Math.pow(2, 9), (int) Math.pow(2, 13), (int) Math.pow(2, 16)};
    //     for (int size: sizes) {
    //         for (int x = 0; x < 3; x++) System.gc();
    //         Random rand  = new Random(2106632182); // generate random with seed as NPM
    //         int[] data = RandomDataGenerator.generateRandomData(size, rand);
    //         int[] sortedData = RandomDataGenerator.sortRandomData(data);
    //         int[] reverseSortedData = RandomDataGenerator.reverseSortRandomData(sortedData);

    //         for (int x = 0; x < 3; x++) System.gc();
    //         // Random Data
    //         long start = System.currentTimeMillis();
    //         long startSize = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
    //         data = randomizedShellSort(data);
    //         long finalSize = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - startSize;
    //         long end = System.currentTimeMillis();

    //         System.out.println(String.format("Time taken for Sorting data with Randomized Shell Sort of size %d with random status: %d", 
    //                                         size, (end - start)));
    //         System.out.println(String.format("Memory used for Sorting data with Randomized Shell Sort of size %d with random status: %d", 
    //                                         size, (finalSize)));

    //         for (int x = 0; x < 3; x++) System.gc();
    //         // Sorted Data
    //         start = System.currentTimeMillis();
    //         startSize = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
    //         sortedData = randomizedShellSort(sortedData);
    //         finalSize = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - startSize;
    //         end = System.currentTimeMillis();
            
    //         System.out.println(String.format("Time taken for Sorting data with Randomized Shell Sort of size %d with sorted status: %d", 
    //                                         size, (end - start)));
    //         System.out.println(String.format("Memory used for Sorting data with Randomized Shell Sort of size %d with sorted status: %d", 
    //                                         size, (finalSize)));

    //         for (int x = 0; x < 3; x++) System.gc();
    //         // Reverse Sorted Data
    //         start = System.currentTimeMillis();
    //         startSize = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
    //         reverseSortedData = randomizedShellSort(reverseSortedData);
    //         finalSize = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - startSize;
    //         end = System.currentTimeMillis();

    //         System.out.println(String.format("Time taken for Sorting data with Randomized Shell Sort of size %d with reversed sorted status: %d", 
    //                                         size, (end - start)));
    //         System.out.println(String.format("Memory used for Sorting data with Randomized Shell Sort of size %d with reversed sorted status: %d", 
    //                                         size, (finalSize)));

    //         // Sanity check to see if they are sorted
    //         int currData = data[0];
    //         boolean isSorted = true;
    //         for (int element: data) {
    //             if (element < currData) {
    //                 isSorted = false;
    //                 break;
    //             }
    //             else {
    //                 currData = element;
    //             }
    //         }
    //         if (!isSorted) {
    //             System.out.println(String.format("Sorting data with Randomized Shell Sort of size %d with random status failed", 
    //                                         size));
    //         }
    //         else {
    //             System.out.println(String.format("Sorting data with Randomized Shell Sort of size %d with random status succeed", 
    //                                         size));
    //         }

    //         currData = sortedData[0];
    //         isSorted = true;
    //         for (int element: sortedData) {
    //             if (element < currData) {
    //                 isSorted = false;
    //                 break;
    //             }
    //             else {
    //                 currData = element;
    //             }
    //         }
    //         if (!isSorted) {
    //             System.out.println(String.format("Sorting data with Randomized Shell Sort of size %d with sorted status failed", 
    //                                         size));
    //         }
    //         else {
    //             System.out.println(String.format("Sorting data with Randomized Shell Sort of size %d with sorted status succeed", 
    //                                         size));
    //         }

    //         currData = reverseSortedData[0];
    //         isSorted = true;
    //         for (int element: reverseSortedData) {
    //             if (element < currData) {
    //                 isSorted = false;
    //                 break;
    //             }
    //             else {
    //                 currData = element;
    //             }
    //         }
    //         if (!isSorted) {
    //             System.out.println(String.format("Sorting data with Randomized Shell Sort of size %d with reverse sorted status failed", 
    //                                         size));
    //         }
    //         else {
    //             System.out.println(String.format("Sorting data with Randomized Shell Sort of size %d with reverse sorted status succeed", 
    //                                         size));
    //         }
    //         for (int x = 0; x < 3; x++) System.gc();
    //     }    
    // }   
}