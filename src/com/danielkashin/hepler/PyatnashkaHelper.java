package com.danielkashin.hepler;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Кашин on 05.02.2017.
 */
public class PyatnashkaHelper {

    public static int[] generateRandomNumbers(int k) {
        ArrayList<Integer> arrayList = new ArrayList<>(k);
        for (int i = 0; i < k; ++i) {
            arrayList.add(i);
        }

        int[] output = new int[k];
        for (int i = 0; i < k; ++i) {
            int randomIndex = new Random().nextInt(arrayList.size());
            output[i] = arrayList.remove(randomIndex);
        }

        return output;
    }

    public static int[] generateRandomValidNumbers(int k) {
        int[] output = generateRandomNumbers(k);
        while (!canBeSolved(output)) {
            output = generateRandomNumbers(k);
        }

        return output;
    }

    // taken from wikipedia
    public static boolean canBeSolved(int[] currentIndexes) {
        int[] invariants = new int[16];
        for (int i = 0; i < 16; ++i) {
            invariants[currentIndexes[i]] = i;
        }

        int sum = 0;
        for (int i = 0; i < 16; i++) {
            if (invariants[i] == 0) {
                sum += i / 4;
                continue;
            }

            for (int j = i + 1; j < 16; j++) {
                if (invariants[j] < invariants[i])
                    sum++;
            }
        }

        return sum % 2 == 0;
    }
}
