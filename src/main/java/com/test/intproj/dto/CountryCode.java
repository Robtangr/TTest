package com.test.intproj.dto;

public class CountryCode {

    private String countryName;
    private String countryCode;
    private String iso2;

    public CountryCode(String name, String code, String iso2){
        this.countryName = name;
        this.countryCode = code;
        this.iso2 = iso2;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString(){
        return "[" + countryCode +", "+ iso2 + "] " + countryName;
    }
}
