package com.test.intproj.services.ref;

import com.test.intproj.CountryDecoder;
import com.test.intproj.dto.CountryCode;
import org.springframework.stereotype.Component;

import java.util.*;

@Component(value = "ReferenceCountryDecoder")
public class ReferenceCountryDecoder implements CountryDecoder {

    private final List<String> codes = new ArrayList<>();
    private final Map<String,CountryCode> ccByCode = new HashMap<>();

    public ReferenceCountryDecoder(){
        for(CountryCode code : CountryCodes.codes){
            codes.add(code.getCountryCode());
            ccByCode.put(code.getCountryCode(),code);
        }

        Collections.sort(codes);
        Collections.reverse(codes);
    }

    @Override
    public CountryCode decode(String message) {
        String[] split = message.split(";");
        String target = split[0];
        if(target.startsWith("00")){
            target = target.substring(2);
        }
        if(target.startsWith("+")){
            target = target.substring(1);
        }
        for(String code : codes){
            if(target.startsWith(code)){
                return ccByCode.get(code);
            }
        }
        return new CountryCode("","","");
    }
}
