package nz.ac.auckland.se281;

import java.util.ArrayList;

public class Person {

  private String userName;
  private String age;
  private Boolean loaded = false;
  private ArrayList<Policy> policyList = new ArrayList<Policy>();

  // each person type will have a userName and age
  // constructor for person
  public Person(String userName, String age) {

    this.userName = userName;

    this.age = age;
  }

  // returns age as integer
  public int getIntAge() {
    return Integer.parseInt(age);
  }

  // returns age as a string
  public String getStringAge() {
    return age;
  }

  // check to see if person is loaded
  public Boolean isLoaded() {
    return this.loaded;
  }

  // changes the status of whether they are loaded
  public void changeStatus(Boolean load) {
    this.loaded = load;
  }

  // returns username
  public String getUserName() {
    return userName;
  }

  // adds policy to persons policy arraylist
  public void addPolicy(Policy policy) {
    policyList.add(policy);
  }

  // method returns number of policies for chosen policy
  public int getNumLifePolicy() {
    int count = 0;
    for (Policy policies : policyList) {
      if (policies instanceof LifePolicy) {
        count++;
      }
    }
    return count;
  }

  // gets total cost of all policies
  public int getTotalCost() {
    int sum = 0;
    // loops through and adds up cost of all the policies
    for (Policy policies : policyList) {
      sum += (int) policies.calcBasePremium();
    }
    // discounts 10% if 2 policies
    if (policyList.size() == 2) {
      sum *= 0.9;
      // discounts 20% if theres 3 policies
    } else if (policyList.size() >= 3) {
      sum *= 0.8;
    }
    // return cost after policies
    return sum;

  }

  // returns number of policies
  public int getTotalPolicies() {
    return policyList.size();
  }

  // returns num policies
  public ArrayList<Policy> getPolicies() {
    return policyList;
  }

  // determines discount for customer
  public double getDiscount() {
    if (policyList.size() == 1) {
      return 1;

    } else if (policyList.size() == 2) {
      return 0.9;
    } else {
      return 0.8;
    }
  }
}
