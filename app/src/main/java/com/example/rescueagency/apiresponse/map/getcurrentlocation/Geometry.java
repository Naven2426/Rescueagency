package com.example.rescueagency.apiresponse.map.getcurrentlocation;

public class Geometry {
    private Bounds bounds;
    private Location location;
    private String location_type;
    private ViewPort viewport;

    public Bounds getBounds() {
        return bounds;
    }

    public Location getLocation() {
        return location;
    }

    public String getLocation_type() {
        return location_type;
    }

    public ViewPort getViewport() {
        return viewport;
    }
}
