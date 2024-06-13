package org.example.assignment;

public class Assignment4 {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, -6, 5, 4, 0};
        int[] subarrayIndex = findSubarrayIndex(numbers);
        System.out.println("[" + subarrayIndex[0] + ", " + subarrayIndex[1] + "]"); // Output: [0, 3]
    }

    public static int[] findSubarrayIndex(int[] numbers) {
        int[] subarrayIndex = new int[2];
        int current = 0;
        int sum = 0;

        for (int i = 0; i < numbers.length; i++) {
            sum += numbers[i];

            if (sum == 0) {
                subarrayIndex[0] = current;
                subarrayIndex[1] = i;
                return subarrayIndex;
            }

            if (i == numbers.length - 1 && sum != 0) {
                i = current;
                current++;
                sum = 0;
            }
        }
        return subarrayIndex;
    }
}
