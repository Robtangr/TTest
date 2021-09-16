package com.test.intproj.services.ref;

import com.test.intproj.dto.CountryCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CountryCodes {

    public static final List<CountryCode> codes = new ArrayList<>();
    public static final Random random = new Random(System.currentTimeMillis());

    static {
        codes.add(new CountryCode("Switzerland","41","ch"));
        codes.add(new CountryCode("Egypt","20","eg"));
        codes.add(new CountryCode("Greece","30","gr"));
        codes.add(new CountryCode("Denmark","45","dk"));
        codes.add(new CountryCode("Peru","51","pe"));
        codes.add(new CountryCode("Chile","56","cl"));
        codes.add(new CountryCode("United Kingdom","44","gb"));
        codes.add(new CountryCode("Jersey","44153","je"));
        codes.add(new CountryCode("Norway","47","no"));
        codes.add(new CountryCode("Algeria","213","dz"));
        codes.add(new CountryCode("Tanzania","255","tz"));
    }

    public static CountryCode getRandom(){
        return codes.get(random.nextInt(codes.size()));
    }
    public static String addStyle(String input){
        switch (random.nextInt(3)){
            case 0: return "+";
            case 1: return "00";
            default: return "";
        }
    }


}
