
package nz.ac.auckland.se281;

public abstract class Policy {

  protected int sumInsured;
  protected double basePremium;

  // constructor for Policy
  public Policy(int sumInsured) {

    this.sumInsured = sumInsured;

  }

  // abstract method for calculating base premium
  public abstract int calcBasePremium();

  // returns the sumInsured
  public int getSumInsured() {
    return sumInsured;
  }
}
