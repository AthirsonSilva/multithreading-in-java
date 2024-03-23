package org.multithreading.java.creation;

import java.util.ArrayList;
import java.util.Random;

public class ThreadInheritance {

  public static final int MAX_PASSWORD = 9999;

  public static void main(String[] args) {
    System.out.println("Main thread started");

    var random = new Random();
    var vault = new Vault(random.nextInt(MAX_PASSWORD));
    var threads = new ArrayList<Thread>();
    threads.add(new AscendingHackerThread(vault));
    threads.add(new DescendingHackerThread(vault));
    threads.add(new PoliceThread());

    for (var thread : threads) {
      thread.start();
    }

    System.out.println("Main thread ended");
  }

  private static class Vault {
    private int password;

    public Vault(int password) {
      password = password;
    }

    public boolean isCorrectPassword(int guess) {
      try {
        Thread.sleep(5);
      } catch (InterruptedException e) {
      }

      return guess == password;
    }
  }

  public static abstract class HackerThread extends Thread {
    public Vault vault;

    public HackerThread(Vault vault) {
      this.vault = vault;
      setName(getClass().getSimpleName());
    }

    @Override
    public void start() {
      System.out.println("Starting thread: " + getName());
      super.start();
    }
  }

  private static class AscendingHackerThread extends HackerThread {
    public AscendingHackerThread(Vault vault) {
      super(vault);
    }

    @Override
    public void run() {
      for (int i = 0; i < MAX_PASSWORD; i++) {
        if (vault.isCorrectPassword(i)) {
          System.out.println("Found password: " + i);
          break;
        }
      }
    }
  }

  private static class DescendingHackerThread extends HackerThread {
    public DescendingHackerThread(Vault vault) {
      super(vault);
    }

    @Override
    public void run() {
      for (int i = MAX_PASSWORD; i > 0; i++) {
        if (vault.isCorrectPassword(i)) {
          System.out.println("Found password: " + i);
          break;
        }
      }
    }
  }

  private static class PoliceThread extends Thread {
    @Override
    public void run() {
      for (int i = 0; i < 5; i++) {
        System.out.println(getName() + ": " + i);
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.printf("%d seconds remaining%n", 4 - i);
      }

      System.out.println("Game over");
      System.exit(0);
    }
  }

}
