package org.example.assignment;

public class Assignment4 {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, -6, 5, 4, 0};
        int[] subarrayIndex = findSubarrayIndex(numbers);
        System.out.println("[" + subarrayIndex[0] + ", " + subarrayIndex[1] + "]"); // Output: [0, 3]
    }

    public static int[] findSubarrayIndex(int[] numbers) {
        int[] subarrayIndex = new int[2];
        int sum = 0;
        for (int i = 0; i < numbers.length; i++) {
            sum = 0;
            for (int j = i; j < numbers.length; j++) {
                sum += numbers[j];
                if (sum == 0) {
                    subarrayIndex[0] = i;
                    subarrayIndex[1] = j;
                    return subarrayIndex;
                }
            }
        }
        return subarrayIndex;
    }
}
