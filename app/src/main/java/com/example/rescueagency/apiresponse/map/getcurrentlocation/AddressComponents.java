package com.example.rescueagency.apiresponse.map.getcurrentlocation;

import java.util.List;

public class AddressComponents {
    private String long_name;
    private String short_name;
    private List<String> types;

    public String getLong_name() {
        return long_name;
    }

    public String getShort_name() {
        return short_name;
    }

    public List<String> getTypes() {
        return types;
    }
}
