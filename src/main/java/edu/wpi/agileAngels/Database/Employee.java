package edu.wpi.agileAngels.Database;

public class Employee {

  private String name;
  private String password;
  private int permissionLevel;
  private String floorOnDuty;

  public Employee(String name, String password, String floorOnDuty, int permissionLevel) {
    this.name = name;
    this.password = password;
    this.floorOnDuty = floorOnDuty;
    this.permissionLevel = permissionLevel;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPermissionLevel() {
    return permissionLevel;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String pass) {
    this.password = pass;
  }

  public String getFloorOnDuty() {
    return floorOnDuty;
  }

  public void setFloorOnDuty(String floorOnDuty) {
    this.floorOnDuty = floorOnDuty;
  }

  @Override
  public String toString() {
    return getName();
  }

  public String initialsMaker() {
    String initials;

    // Is this name empty? Initials ain't applicable...
    if (name.isEmpty()) {
      initials = "NA";
    }
    // Not empty? Not illegal? Run the actual method.
    else {
      // If the name has a space, 2+ names were given and need to be broken up.
      String firstInitial = ("" + name.charAt(0)).toUpperCase();
      if (name.contains(" ")) {
        int lastSpaceIndex = name.lastIndexOf(" ");
        String secondInitial = ("" + name.charAt(lastSpaceIndex + 1)).toUpperCase();

        initials = "" + firstInitial + secondInitial;
        System.out.println("test");
      }
      // Else, 1 name was given, throw the first character.
      else {
        initials = "" + firstInitial;
      }
    }
    return initials;
  }
}
