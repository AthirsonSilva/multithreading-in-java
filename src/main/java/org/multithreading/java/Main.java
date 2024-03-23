package org.multithreading.java;

public class Main {
    public static void main(String[] args) {
        System.out.println("Main thread started");

        Thread.startVirtualThread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.printf("%s: %s%n", Thread.currentThread().getName(), i);
            }
        });

        System.out.println("Main thread ended");
    }
}