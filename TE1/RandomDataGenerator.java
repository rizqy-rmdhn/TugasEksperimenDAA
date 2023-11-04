package TE1;

import java.util.Random;
import java.util.Arrays;
import java.util.Collections;

public class RandomDataGenerator {
    public static int[] generateRandomData(int size, Random rand) {
        int[] res = new int[size];
        for (int i = 0; i < size; i++) {
            res[i] = rand.nextInt();
        }
        return res;
    }
    
    public static int[] sortRandomData(int[] data) {
        int[] sortedData = data.clone();
        Arrays.sort(sortedData);
        return sortedData;
    }

    public static int[] reverseSortRandomData(int[] data) {
        Integer[] reverseSortedData = Arrays.stream(data.clone()).boxed().toArray( Integer[]::new );
        Arrays.sort(reverseSortedData, Collections.reverseOrder());
        return Arrays.stream(reverseSortedData).mapToInt(Integer::intValue).toArray();
    }
    public static void main(String[] args) {
        
    }
}
