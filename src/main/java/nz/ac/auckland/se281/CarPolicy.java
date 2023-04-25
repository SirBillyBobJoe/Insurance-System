package nz.ac.auckland.se281;

public class CarPolicy extends Policy {

  String makeModel;
  String licencePlate;
  Boolean mechBreakdown;
  int clientAge;

  // constructor for carpolicy
  public CarPolicy(String sumInsured, String makeModel,
      String licencePlate, String mechBreakdown, int clientAge) {
    // initiates parent class
    super(Integer.parseInt(sumInsured));
    this.makeModel = makeModel;
    this.licencePlate = licencePlate;
    // if mech breakdown is yes then set to true otherwise set to false
    if (mechBreakdown.equals("yes") || mechBreakdown.equals("Yes") || mechBreakdown.equals("y")
        || mechBreakdown.equals("Y")) {
      this.mechBreakdown = true;
    } else {
      this.mechBreakdown = false;
    }
    this.clientAge = clientAge;
  }

  // returns car model
  public String getMakeModel() {
    return makeModel;
  }

  // calculates basepremium
  public int calcBasePremium() {
    // if over 25 years old then charge 15%
    if (clientAge < 25) {
      this.basePremium = 0.15 * sumInsured;
      // otherwise charge 10%
    } else {
      this.basePremium = 0.1 * sumInsured;
    } // if needs mechbreakdown coverage charge extrad 80 dollars
    if (mechBreakdown) {
      this.basePremium += 80;
    }
    return (int) this.basePremium;
  }
}
