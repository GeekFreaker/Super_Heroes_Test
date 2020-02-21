package check.out.superheroes.Models;

import java.util.ArrayList;

public class Appearance {
    private String gender;
    private String race;
    ArrayList<Object> height = new ArrayList<Object>();
    ArrayList<Object> weight = new ArrayList<Object>();
    private String eyeColor;
    private String hairColor;


    // Getter Methods

    public String getGender() {
        return gender;
    }

    public String getRace() {
        return race;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public String getHairColor() {
        return hairColor;
    }

    // Setter Methods

    public void setGender( String gender ) {
        this.gender = gender;
    }

    public void setRace( String race ) {
        this.race = race;
    }

    public void setEyeColor( String eyeColor ) {
        this.eyeColor = eyeColor;
    }

    public void setHairColor( String hairColor ) {
        this.hairColor = hairColor;
    }
}
