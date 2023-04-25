package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Main.PolicyType;

public class InsuranceSystem {
  // initiate person arraylist

  private ArrayList<Person> personList = new ArrayList<Person>();
  // get length of personlist
  int length = personList.size();

  public InsuranceSystem() {
    // Only this constructor can be used (if you need to initialise fields).
  }

  public void printDatabase() {

    length = personList.size();
    // initiate the suffixes of the print messages
    String astrix = "";
    String plural = "";
    String policySuffix = "ies";
    String end = ":";
    // if there is 0 people in data base
    if (length == 0) {
      // prints end to "."
      end = ".";
      // if theres 2 or more people change suffix for print message
    }
    if (length >= 2 || length == 0) {
      plural = "s";
    }

    int count = 0;
    // prints "Database has %s profiles:"
    MessageCli.PRINT_DB_POLICY_COUNT.printMessage(Integer.toString(length), plural, end);

    for (Person person : personList) {
      // if person is loaded change the prefix to *** else make prefix empty
      if (person.isLoaded()) {
        astrix = "*** ";

      } else {
        astrix = "";
      }

      // if there is a total of one policy change suffix to y otherwise change suffix
      // to ies
      if (person.getTotalPolicies() == 1) {
        policySuffix = "y";
      } else {
        policySuffix = "ies";
      }
      // prints "*** <index>: <name>, <age>, <num policies> policies for a total of
      // <cost>"
      // if not loaded prints"<index>: <name>, <age>, <num policies> policies for a
      // total of <cost>"
      MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage(astrix,
          Integer.toString(count + 1), person.getUserName(), person.getStringAge(),
          Integer.toString(person.getTotalPolicies()), policySuffix,
          Integer.toString(person.getTotalCost()));
      count++;

      if (person.getTotalPolicies() != 0) {

        // loop through policies and print out details of the policies
        for (Policy policies : person.getPolicies()) {
          if (policies instanceof HomePolicy) {
            // converts to homepolicy class
            HomePolicy homePolicy = (HomePolicy) policies;
            // prints Home Policy (<ADDRESS>, Sum Insured: $<SUM_INSURED>, Premium:
            // $<BASE_PREMIUM> -> $<DISCOUNTED_PREMIUM>)
            MessageCli.PRINT_DB_HOME_POLICY.printMessage(
                homePolicy.getAddress(), Integer.toString(homePolicy.getSumInsured()),
                Integer.toString(homePolicy.calcBasePremium()),
                Integer.toString((int) (homePolicy.calcBasePremium() * person.getDiscount())));
          } else if (policies instanceof CarPolicy) {
            // converts to carpolicy class
            CarPolicy carPolicy = (CarPolicy) policies;
            // prints Car Policy (<MAKE_AND_MODEL>, Sum Insured: $<SUM_INSURED>, Premium:
            // $<BASE_PREMIUM> -> $<DISCOUNTED_PREMIUM>)
            MessageCli.PRINT_DB_CAR_POLICY.printMessage(carPolicy.getMakeModel(),
                Integer.toString(carPolicy.getSumInsured()),
                Integer.toString(carPolicy.calcBasePremium()),
                Integer.toString((int) (carPolicy.calcBasePremium() * person.getDiscount())));
          } else {
            // converts to lifepolicy class
            LifePolicy lifePolicy = (LifePolicy) policies;
            // prints Life Policy (Sum Insured: $<SUM_INSURED>, Premium: $<BASE_PREMIUM> ->
            // $<DISCOUNTED_PREMIUM>)
            MessageCli.PRINT_DB_LIFE_POLICY.printMessage(
                Integer.toString(lifePolicy.getSumInsured()),
                Integer.toString(lifePolicy.calcBasePremium()),
                Integer.toString((int) (lifePolicy.calcBasePremium() * person.getDiscount())));

          }

        }
      }
    }

  }

  public void createNewProfile(String userName, String age) {

    String titledUser = setTitleCase(userName);

    for (Person person : personList) {
      // checks if there are any loaded people
      if (person.isLoaded()) {
        // if there is loaded people print Cannot create a new profile. First unload the
        // profile for %s.
        MessageCli.CANNOT_CREATE_WHILE_LOADED.printMessage(person.getUserName());
        return;
      }
    }

    Person client = new Person(titledUser, age);
    // determines length of userName and arraylist
    int nameLength = userName.length();
    // checks for duplicates

    for (Person person : personList) {
      if (titledUser.equals(person.getUserName())) {
        // if theres duplicates
        // prints Usernames must be unique. No profile was created for '%s'.
        MessageCli.INVALID_USERNAME_NOT_UNIQUE.printMessage(client.getUserName());
        return;
      }
    }
    // if age is not a positive integer print error
    if (!(age.chars().allMatch(Character::isDigit)) || client.getIntAge() <= 0) {

      // '%s' is an invalid age, please provide a positive whole number only. No
      // profile was
      // created" + " for %s."
      MessageCli.INVALID_AGE.printMessage(client.getStringAge(), client.getUserName());
      // if name is less than 3 characters print error
      return;

    } else if (nameLength < 3) {
      // prints '%s' is an invalid username, it should be at least 3 characters long.
      // No profile was
      // created."),

      MessageCli.INVALID_USERNAME_TOO_SHORT.printMessage(titledUser);
      // if name is not unique print error
      return;
    } else {

      personList.add(client);

      // Prints New profile created for <USERNAME> with age <AGE>.
      MessageCli.PROFILE_CREATED.printMessage(client.getUserName(), client.getStringAge());
      return;
    }
  }

  public void loadProfile(String userName) {

    int count = 0;
    Person tempPerson = null;
    // makes the userName TitledCase
    String titledUser = setTitleCase(userName);

    // loops through database
    for (Person person : personList) {
      // if person is loaded unload them
      if (person.isLoaded()) {
        person.changeStatus(false);
        // stores the previously store person if they were loaded
        tempPerson = person;
      }
      // if there is a userName match change status to loaded
      if (titledUser.equals(person.getUserName())) {
        person.changeStatus(true);
        count++;
      }
    }

    if (count == 0) {
      // load the previous person again if he was loaded and removed
      if (tempPerson != null) {
        tempPerson.changeStatus(true);
      }
      // print No profile found for %s. Profile not loaded
      MessageCli.NO_PROFILE_FOUND_TO_LOAD.printMessage(titledUser);
    } else {
      // prints Profile loaded for %s
      MessageCli.PROFILE_LOADED.printMessage(titledUser);
    }

  }

  public void unloadProfile() {

    // loops through database and checks if client is loaded if loaded then unloads
    // client
    for (Person person : personList) {
      // checks if there are any loaded people
      if (person.isLoaded()) {
        // if there is loaded people print Cannot create a new profile. First unload the
        // profile for %s.
        person.changeStatus(false);
        // if profile found print Profile unloaded for <USERNAME>.
        MessageCli.PROFILE_UNLOADED.printMessage(person.getUserName());
        return;
      }
    }
    // if no profile found print No profile is currently loaded.
    MessageCli.NO_PROFILE_LOADED.printMessage();
  }

  public void deleteProfile(String userName) {

    String titledUser = setTitleCase(userName);

    int index = 0;

    for (Person person : personList) {
      // if name matches and is not loaded remove person from index
      // and print Profile deleted for <USERNAME>.
      if (titledUser.equals(person.getUserName()) && person.isLoaded() == false) {
        personList.remove(index);
        MessageCli.PROFILE_DELETED.printMessage(titledUser);
        return;
        // if name matches and is loaded do not remove person from index
        // and print Cannot delete profile for <USERNAME> while loaded. No profile was
        // deleted.
      } else if (titledUser.equals(person.getUserName()) && person.isLoaded() == true) {
        MessageCli.CANNOT_DELETE_PROFILE_WHILE_LOADED.printMessage(titledUser);
        return;
      }
      index++;
    }
    // If the profile is not found, then the following message gets printed:
    MessageCli.NO_PROFILE_FOUND_TO_DELETE.printMessage(titledUser);
  }

  public void createPolicy(PolicyType type, String[] options) {
    // count tracks how many loaded
    int count = 0;
    Person loadedClient = new Person(null, null);
    Policy policy;
    for (Person person : personList) {
      // checks if there are any loaded people
      if (person.isLoaded()) {
        count++;
        loadedClient = person;
        break;
      }
    }
    if (count == 0) {
      // if no profiles loaded print Need to load a profile in order to create a
      // policy.
      MessageCli.NO_PROFILE_FOUND_TO_CREATE_POLICY.printMessage();
      return;
    }
    // switch case for each type of life insurance
    switch (type) {
      case HOME:

        policy = new HomePolicy(options[0], options[1], options[2]);
        loadedClient.addPolicy(policy);
        // when policy succesfully created print New home policy created for <USERNAME>.
        MessageCli.NEW_POLICY_CREATED.printMessage("home", loadedClient.getUserName());
        break;

      case LIFE:
        // if already has life insurance print <USERNAME> already has a life policy. No
        // new policy was created.
        if (loadedClient.getNumLifePolicy() == 1) {
          MessageCli.ALREADY_HAS_LIFE_POLICY.printMessage(loadedClient.getUserName());
          return;
        }
        // if client is over 100 years print <USERNAME> is over the age limit. No policy
        // was created.
        if (loadedClient.getIntAge() > 100) {
          MessageCli.OVER_AGE_LIMIT_LIFE_POLICY.printMessage((loadedClient.getUserName()));
          return;
        }

        policy = new LifePolicy(options[0], loadedClient.getIntAge());
        loadedClient.addPolicy(policy);
        // when policy succesfully created print New life policy created for <USERNAME>.
        MessageCli.NEW_POLICY_CREATED.printMessage("life", loadedClient.getUserName());
        break;

      case CAR:

        policy = new CarPolicy(options[0], options[1],
            options[2], options[3], loadedClient.getIntAge());
        loadedClient.addPolicy(policy);
        // when policy succesfully created print New car policy created for <USERNAME>.
        MessageCli.NEW_POLICY_CREATED.printMessage("car", loadedClient.getUserName());
        break;
    }

  }

  // sets a string to title case where the first letter is capital and the rest is
  // lower case
  public String setTitleCase(String word) {

    return word.substring(0, 1).toUpperCase()
        + word.substring(1, word.length()).toLowerCase();
  }
}
