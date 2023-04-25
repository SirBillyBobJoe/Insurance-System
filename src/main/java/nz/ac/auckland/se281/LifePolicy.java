package nz.ac.auckland.se281;

public class LifePolicy extends Policy {

  int clientAge;

  // constructor for life policy
  public LifePolicy(String sumInsured, int clientAge) {
    // initiates the parent class
    super(Integer.parseInt(sumInsured));
    this.clientAge = clientAge;
  }

  // calculates base premium
  public int calcBasePremium() {

    return (int) ((1 + ((double) this.clientAge / 100)) / 100 * this.sumInsured);

  }

}
