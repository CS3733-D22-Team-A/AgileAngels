package edu.wpi.agileAngels;

public class MedicalEquip {
  private String name;
  private int amount;

  public MedicalEquip(String name, int amount) {

    this.name = name;
    this.amount = amount;
  }

  public boolean decrement() {
    if (amount != 0) {
      amount--;
      return true;
    } else {
      return false;
    }
  }

  public boolean increment() {
    amount++;
    return true;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public String getName() {
    return name;
  }
}
