package org.example;

import org.example.app.AppManager;

public class Main {
    public static void main(String[] args) {
        AppManager appManager = AppManager.getInstance();
        appManager.start();
    }
}