package TE1;

import java.util.Random;
import java.lang.Math;
import java.lang.Runtime;

public class MaxHeapSort {
    public static void heapify(int[] a, int size, int idx) {
        int largest = idx;

        int left = 2 * idx + 1;
        int right = 2 * idx + 2;

        if (left < size && a[left] > a[largest]) {
            largest = left;
        } 

        if (right < size && a[right] > a[largest]) {
            largest = right;
        }

        if (largest != idx) {
            int temp = a[idx];
            a[idx] = a[largest];
            a[largest] = temp;
            heapify(a, size, largest);
        }
    }

    public static int[] maxHeapSort(int[] a) {
        int size = a.length;
        // Build max heap
        for (int i = size / 2 - 1; i >= 0; i--) {
            heapify(a, size, i);
        }

        // Sort in-place
        for (int i = size - 1; i > 0; i--) {
            
            int temp = a[0];
            a[0] = a[i];
            a[i] = temp;
            
            heapify(a, i, 0);
        }
        return a;
    }
    public static void test() {
        int[] test = {5, 3, 8, 2, 6, 7, 1, 4};
        test = maxHeapSort(test);
        for (int p: test) {
            System.out.print(p + " ");
        }
        
    }
    public static void main(String[] args) {
        int[] sizes = {(int) Math.pow(2, 9), (int) Math.pow(2, 13), (int) Math.pow(2, 16)};
        for (int size: sizes) {
            Random rand  = new Random(2106632182); // generate random with seed as NPM
            int[] data = RandomDataGenerator.generateRandomData(size, rand);
            int[] sortedData = RandomDataGenerator.sortRandomData(data);
            int[] reverseSortedData = RandomDataGenerator.reverseSortRandomData(sortedData);

            // Random Data
            long start = System.currentTimeMillis();
            long startSize = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
            data = maxHeapSort(data);
            long finalSize = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - startSize;
            long end = System.currentTimeMillis();

            System.out.println(String.format("Time taken for Sorting data with Max Heap Sort of size %d with random status: %d", 
                                            size, (end - start)));
            System.out.println(String.format("Memory used for Sorting data with Max Heap Sort of size %d with random status: %d", 
                                            size, (finalSize)));

            // Sorted Data
            start = System.currentTimeMillis();
            startSize = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
            sortedData = maxHeapSort(sortedData);
            finalSize = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - startSize;
            end = System.currentTimeMillis();

            System.out.println(String.format("Time taken for Sorting data with Max Heap Sort of size %d with sorted status: %d", 
                                            size, (end - start)));
            System.out.println(String.format("Memory used for Sorting data with Max Heap Sort of size %d with sorted status: %d", 
                                            size, (finalSize)));
            
            // Reverse Sorted Data
            start = System.currentTimeMillis();
            startSize = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
            reverseSortedData = maxHeapSort(reverseSortedData);
            finalSize = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - startSize;
            end = System.currentTimeMillis();

            System.out.println(String.format("Time taken for Sorting data with Max Heap Sort of size %d with reversed sorted status: %d", 
                                            size, (end - start)));
            System.out.println(String.format("Memory used for Sorting data with Max Heap Sort of size %d with reversed sorted status: %d", 
                                            size, (finalSize)));

            // Sanity check to see if they are sorted
            int currData = data[0];
            boolean isSorted = true;
            for (int element: data) {
                if (element < currData) {
                    isSorted = false;
                    break;
                }
                else {
                    currData = element;
                }
            }
            if (!isSorted) {
                System.out.println(String.format("Sorting data with Max Heap Sort of size %d with random status failed", 
                                            size));
            }
            else {
                System.out.println(String.format("Sorting data with Max Heap Sort of size %d with random status succeed", 
                                            size));
            }

            currData = sortedData[0];
            isSorted = true;
            for (int element: sortedData) {
                if (element < currData) {
                    isSorted = false;
                    break;
                }
                else {
                    currData = element;
                }
            }
            if (!isSorted) {
                System.out.println(String.format("Sorting data with Max Heap Sort of size %d with sorted status failed", 
                                            size));
            }
            else {
                System.out.println(String.format("Sorting data with Max Heap Sort of size %d with sorted status succeed", 
                                            size));
            }

            currData = reverseSortedData[0];
            isSorted = true;
            for (int element: reverseSortedData) {
                if (element < currData) {
                    isSorted = false;
                    break;
                }
                else {
                    currData = element;
                }
            }
            if (!isSorted) {
                System.out.println(String.format("Sorting data with Max Heap Sort of size %d with reverse sorted status failed", 
                                            size));
            }
            else {
                System.out.println(String.format("Sorting data with Max Heap Sort of size %d with reverse sorted status succeed", 
                                            size));
            }
            for (int x = 0; x < 3; x++) System.gc();

        }
    }
    
}
