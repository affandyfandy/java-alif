package org.example.assignment7;

import java.util.Arrays;
import java.util.List;

public class PagingData<T> {
    private List<T> dataList;
    private int pageSize;
    private int currentPage;

    public static void main(String[] args) {
        // Sample list of employees
        List<Employee> employees = Arrays.asList(
                new Employee(101, "Harry", 41, 70000),
                new Employee(102, "Ivy", 31, 55000),
                new Employee(103, "Jack", 34, 60000),
                new Employee(104, "Kevin", 36, 65000),
                new Employee(105, "Alice", 31, 55000)
        );

        // Create PagingData instance for employees
        PagingData<Employee> pagingData = new PagingData<>(employees, 2);

        // Display current page data
        System.out.println("Page " + pagingData.getCurrentPageNumber() + ":");
        pagingData.getCurrentPageData().forEach(System.out::println);

        // Move to next page
        pagingData.nextPage();
        System.out.println("\nPage " + pagingData.getCurrentPageNumber() + ":");
        pagingData.getCurrentPageData().forEach(System.out::println);

        // Move to previous page
        pagingData.previousPage();
        System.out.println("\nPage " + pagingData.getCurrentPageNumber() + ":");
        pagingData.getCurrentPageData().forEach(System.out::println);

        // Display total pages
        System.out.println("\nTotal Pages: " + pagingData.getTotalPages());
    }

    public PagingData(List<T> dataList, int pageSize) {
        this.dataList = dataList;
        this.pageSize = pageSize;
        this.currentPage = 1;
    }

    public List<T> getCurrentPageData() {
        int fromIndex = (currentPage - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, dataList.size());
        return dataList.subList(fromIndex, toIndex);
    }

    public void nextPage() {
        if (currentPage < getTotalPages()) {
            currentPage++;
        }
    }

    public void previousPage() {
        if (currentPage > 1) {
            currentPage--;
        }
    }

    public List<T> getAllData() {
        return dataList;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCurrentPageNumber() {
        return currentPage;
    }


    public int getTotalPages() {
        return (int) Math.ceil((double) dataList.size() / pageSize);
    }
}