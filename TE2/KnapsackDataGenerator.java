package TE2;

import java.util.Random;

public class KnapsackDataGenerator {
    public static void main(String[] args) {
        
    }

    public static int[] generateRandomData(int size, Random rand) {
        int[] res = new int[size];
        for (int i = 0; i < size; i++) {
            int x =  rand.nextInt();
            while (x <= 0) {
                x = rand.nextInt();
            }
            res[i] = x;
        }
        return res;
    }
    public static int[] generateRandomDataWithBoundary(int size, Random rand, int bound) {
        int[] res = new int[size];
        int numOver = 0;
        for (int i = 0; i < size; i++) {
            int x =  rand.nextInt();
        
            if (numOver > size/10) {
                while (x > bound || x <= 0) {
                    x = rand.nextInt();
                }
            }
            else {
                while (x <= 0) {
                    x = rand.nextInt();
                }
            }
            if (x > bound) {
                numOver++;
            }
            res[i] = x;
        }
        return res;
    }

    public static long getFloorAverage(int[] numArray) {
        long res = 0;
        for (int num: numArray) {
            res += num;
        }
        return res/(numArray.length * numArray.length);
    }
}
