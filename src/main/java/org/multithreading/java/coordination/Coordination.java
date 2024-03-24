package org.multithreading.java.coordination;

import java.math.BigInteger;

public class Coordination {

  public static void main(String[] args) {
    var thread1 = new Thread(new BlockingTask());
    thread1.start();
    thread1.interrupt();

    var thread2 = new Thread(new LongComputationTask(
        new BigInteger("200000"),
        new BigInteger("50000000")));

    thread2.start();
    thread2.interrupt();
  }

  private static class BlockingTask implements Runnable {
    @Override
    public void run() {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        System.out.println("Interrupted");
      }
    }
  }

  private static class LongComputationTask implements Runnable {
    private BigInteger base;
    private BigInteger power;

    public LongComputationTask(BigInteger base, BigInteger power) {
      this.base = base;
      this.power = power;
    }

    @Override
    public void run() {
      System.out.println(pow(base, power));
    }

    private BigInteger pow(BigInteger base, BigInteger power) {
      BigInteger result = BigInteger.ONE;

      for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
        if (Thread.currentThread().isInterrupted()) {
          System.out.println("Task interrupted");
          return result;
        }
        result = result.multiply(base);
      }

      return result;
    }
  }

}
