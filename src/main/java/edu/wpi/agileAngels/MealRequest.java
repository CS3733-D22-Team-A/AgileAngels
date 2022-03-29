package edu.wpi.agileAngels;

public class MealRequest extends Request{

    private String dietaryRestrictions;

    public MealRequest(String employeeName, String location, String type, String dietaryRestrictions) {
        super(employeeName, location, type);
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public String getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(String dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

}
