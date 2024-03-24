package org.multithreading.java.coordination;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ComplexCalculation {

  public static void main(String[] args) {
    ComplexCalculation complexCalculation = new ComplexCalculation();
    BigInteger result = complexCalculation.calculateResult(BigInteger.valueOf(10), BigInteger.valueOf(2),
        BigInteger.valueOf(44),
        BigInteger.valueOf(3));

    System.out.printf("\nResult: %d\n", result);
  }

  public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) {
    List<BigInteger> resultList = new ArrayList<BigInteger>();
    List<PowerCalculatingThread> threads = new ArrayList<PowerCalculatingThread>();
    List<BigInteger> baseList = List.of(base1, base2);
    List<BigInteger> powerList = List.of(power1, power2);

    for (int i = 0; i < baseList.size(); i++) {
      threads.add(new PowerCalculatingThread(baseList.get(i), powerList.get(i)));
    }

    System.out.printf("\nStarting %d threads\n", threads.size());

    for (Thread thread : threads) {
      thread.start();
    }

    for (Thread thread : threads) {
      try {
        thread.join(2000);
      } catch (InterruptedException e) {
        System.out.println("Thread interrupted");
      }
    }

    for (int i = 0; i < baseList.size(); i++) {
      PowerCalculatingThread thread = threads.get(i);
      if (thread.getIsFinished()) {
        System.out.printf("\nThread %d finished with result %d\n", i, thread.getResult());
        resultList.add(thread.getResult());
      } else {
        System.out.printf("\nThread %d did not finish\n", i);
      }
    }

    return resultList.get(0).add(resultList.get(1));
  }

  private static class PowerCalculatingThread extends Thread {
    private BigInteger result = BigInteger.ONE;
    private BigInteger base;
    private BigInteger power;
    private Boolean isFinished = false;

    public PowerCalculatingThread(BigInteger base, BigInteger power) {
      this.base = base;
      this.power = power;
    }

    @Override
    public void run() {
      this.result = pow(base, power);
      this.isFinished = true;
    }

    public BigInteger pow(BigInteger base, BigInteger power) {
      System.out.println("Calculating " + base + "^" + power);
      Double newBase = base.doubleValue();
      Double newPower = power.doubleValue();
      Double pow = Math.pow(newBase, newPower);
      return BigInteger.valueOf(pow.longValue());
    }

    public BigInteger getResult() {
      return result;
    }

    public Boolean getIsFinished() {
      return isFinished;
    }
  }
}