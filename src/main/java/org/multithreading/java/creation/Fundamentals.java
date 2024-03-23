package org.multithreading.java.creation;

import java.util.List;

public class Fundamentals {

    public static void main(String[] args) {
        System.out.println("Main thread started");

        Thread.ofVirtual().start(() -> {
            for (int i = 0; i < 3; i++) {
                Thread.currentThread().setName("Virtual thread");
                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                Thread.currentThread()
                        .setUncaughtExceptionHandler((e, f) -> System.out.println("Uncaught exception: " + f));
                System.out.printf("%s: %s%n", Thread.currentThread().getName(), i);
            }
        });

        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                Thread.currentThread().setName("Physical thread");
                Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                Thread.currentThread()
                        .setUncaughtExceptionHandler((e, f) -> System.out.println("Uncaught exception: " + f));
                System.out.printf("%s: %s%n", Thread.currentThread().getName(), i);
            }
        }).start();

        System.out.println("Main thread ended");
    }

    public void MultiExecutor(List<Runnable> tasks) {
        for (Runnable task : tasks) {
            new Thread(task).start();
        }
    }

}