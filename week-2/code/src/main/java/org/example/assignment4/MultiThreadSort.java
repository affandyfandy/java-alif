package org.example.assignment4;

public class MultiThreadSort implements Runnable {
    private int[] array;
    private int start;
    private int end;

    public MultiThreadSort(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        if (start < end) {
            int mid = (start + end) / 2;

            MultiThreadSort left = new MultiThreadSort(array, start, mid);
            MultiThreadSort right = new MultiThreadSort(array, mid + 1, end);

            Thread leftThread = new Thread(left);
            Thread rightThread = new Thread(right);

            leftThread.start();
            rightThread.start();

            try {
                leftThread.join();
                rightThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            merge(start, mid, end);
        }
    }

    private void merge(int start, int mid, int end) {
        int[] temp = new int[end - start + 1];

        int i = start;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= end) {
            if (array[i] <= array[j]) {
                temp[k] = array[i];
                i++;
            } else {
                temp[k] = array[j];
                j++;
            }
            k++;
        }

        while (i <= mid) {
            temp[k] = array[i];
            i++;
            k++;
        }

        while (j <= end) {
            temp[k] = array[j];
            j++;
            k++;
        }

        if (end + 1 - start >= 0) System.arraycopy(temp, 0, array, start, end + 1 - start);
    }

    public static void main(String[] args) {
        int[] array = {5, 3, 2, 1, 4};
        MultiThreadSort sorter = new MultiThreadSort(array, 0, array.length - 1);

        Thread sortThread = new Thread(sorter);
        sortThread.start();

        try {
            sortThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i : array) {
            System.out.print(i + " ");
        }
    }
}