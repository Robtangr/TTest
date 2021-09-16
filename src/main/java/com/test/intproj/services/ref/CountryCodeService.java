package com.test.intproj.services.ref;

import com.test.intproj.dto.CountryCode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CountryCodeService {
    public List<CountryCode> getCodes(){
        return CountryCodes.codes;
    }
}
