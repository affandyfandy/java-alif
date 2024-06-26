package org.example.assignment7;

import java.util.ArrayList;
import java.util.List;

public class WildcardDemo {
    // Unbounded wildcard
    public static void printList(List<?> list) {
        for (Object elem : list) {
            System.out.print(elem + " ");
        }
        System.out.println();
    }

    // Upper bounded wildcard
    public static void printNumbers(List<? extends Number> list) {
        for (Number num : list) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    // Lower bounded wildcard
    public static void addIntegers(List<? super Integer> list) {
        list.add(10);
        list.add(20);
        list.add(30);
    }

    public static void main(String[] args) {

        System.out.println("Unbounded wildcard demo:");
        // Unbounded wildcard
        List<String> stringList = new ArrayList<>();
        stringList.add("Hello");
        stringList.add("World");
        printList(stringList);

        System.out.println("\nUpper bounded wildcard demo:");
        // Upper bounded wildcard
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        printNumbers(integerList);

        List<Double> doubleList = new ArrayList<>();
        doubleList.add(1.1);
        doubleList.add(2.2);
        doubleList.add(3.3);
        printNumbers(doubleList);

        System.out.println("\nLower bounded wildcard demo:");
        // Lower bounded wildcard
        List<Number> numberList = new ArrayList<>();
        addIntegers(numberList);
        printList(numberList);
    }
}
