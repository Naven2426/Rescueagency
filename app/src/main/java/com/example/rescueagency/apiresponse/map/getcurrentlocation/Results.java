package com.example.rescueagency.apiresponse.map.getcurrentlocation;

import java.util.List;

public class Results {
    private List<AddressComponents> address_components;
    private String formatted_address;
    private Geometry geometry;
    private String place_id;
    private List<String> types;

    public List<AddressComponents> getAddress_components() {
        return address_components;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public String getPlace_id() {
        return place_id;
    }

    public List<String> getTypes() {
        return types;
    }
}
