package check.out.superheroes.Models;

import java.util.ArrayList;

public class Biography {
    private String fullName;
    private String alterEgos;
    ArrayList<Object> aliases = new ArrayList<Object>();
    private String placeOfBirth;
    private String firstAppearance;
    private String publisher;
    private String alignment;


    // Getter Methods

    public String getFullName() {
        return fullName;
    }

    public String getAlterEgos() {
        return alterEgos;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public String getFirstAppearance() {
        return firstAppearance;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getAlignment() {
        return alignment;
    }

    // Setter Methods

    public void setFullName( String fullName ) {
        this.fullName = fullName;
    }

    public void setAlterEgos( String alterEgos ) {
        this.alterEgos = alterEgos;
    }

    public void setPlaceOfBirth( String placeOfBirth ) {
        this.placeOfBirth = placeOfBirth;
    }

    public void setFirstAppearance( String firstAppearance ) {
        this.firstAppearance = firstAppearance;
    }

    public void setPublisher( String publisher ) {
        this.publisher = publisher;
    }

    public void setAlignment( String alignment ) {
        this.alignment = alignment;
    }
}
