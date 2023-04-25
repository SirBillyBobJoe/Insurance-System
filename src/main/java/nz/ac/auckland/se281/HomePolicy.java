package nz.ac.auckland.se281;

public class HomePolicy extends Policy {

  private String address;
  private Boolean rental;

  // constructor for Home Policy
  public HomePolicy(String sumInsured, String address, String rental) {
    // initialises parent class
    super(Integer.parseInt(sumInsured));
    this.address = address;
    // checks if rental is yes or no and sets to true or false
    if (rental.equals("yes") || rental.equals("Yes")
        || rental.equals("y") || rental.equals("Y")) {

      this.rental = true;

    } else {
      this.rental = false;
    }

  }

  // calculates base premium
  public int calcBasePremium() {
    // if rental then charge 2%
    if (rental) {
      this.basePremium = 0.02 * sumInsured;
      // if not rental charge 1%
    } else {
      this.basePremium = 0.01 * sumInsured;

    }
    return (int) this.basePremium;
  }

  // returns address
  public String getAddress() {
    return this.address;
  }

}
