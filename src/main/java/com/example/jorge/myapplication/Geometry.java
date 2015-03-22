package com.example.jorge.myapplication;

import java.util.List;

/**
 * Created by Jorge on 22/03/2015.
 */
public class Geometry {
    private String type;
    private List<String> coordinates;

    public Geometry() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<String> coordinates) {
        this.coordinates = coordinates;
    }
}
