package com.example.rescueagency.admin;

import com.example.rescueagency.R;

public enum OperatedCountry {
    UNITED_KINGDOM("UK", R.mipmap.fire),
    NETHERLANDS("NL",  R.mipmap.fire),
    GERMANY("DE",  R.mipmap.fire),
    SWEDEN("SE",  R.mipmap.fire);

    private final String countryCode;
    private final int icon;

    OperatedCountry(String countryCode, int icon) {
        this.countryCode = countryCode;
        this.icon = icon;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public int getIcon() {
        return icon;
    }
}
