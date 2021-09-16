package com.test.intproj;

import com.test.intproj.dto.CountryCode;

public interface CountryDecoder {

    /***
     * Extracts the county information from a target phone number
     * @param message The input message
     * @return The country information
     */
    CountryCode decode(String message);
}
