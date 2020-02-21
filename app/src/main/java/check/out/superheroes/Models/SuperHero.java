package check.out.superheroes.Models;


import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SuperHero {

    @SerializedName("id")
    private float id;

    @SerializedName("name")
    private String name;

    @SerializedName("slug")
    private String slug;

    @SerializedName("powerstats")
    private Powerstats powerstats;

    @SerializedName("appearance")
    private Appearance appearance;

    @SerializedName("biography")
    private Biography biography;

    @SerializedName("work")
    private Work work;

    @SerializedName("connections")
    private Connections connections;

    @SerializedName("images")
    private Images images;


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public Powerstats getPowerStats() {
        return powerstats;
    }

    public Appearance getAppearance() {
        return appearance;
    }

    public Biography getBiography() {
        return biography;
    }

    public Work getWork() {
        return work;
    }

    public Connections getConnections() {
        return connections;
    }

    public Images getImages() {
        return images;
    }

}

