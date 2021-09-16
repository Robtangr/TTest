package com.test.intproj.services.ref;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.intproj.dto.CountryCode;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.logging.Logger;

@Component
public class Sender {
    private final Logger LOG = Logger.getLogger(Sender.class.getName());
    private final ObjectMapper mapper = new ObjectMapper();

    private final List<String> codes = new ArrayList<>();
    private final List<String> names = new ArrayList<>();
    private final Map<String,CountryCode> ccByCode = new HashMap<>();

    public Sender(){

        for(CountryCode code : CountryCodes.codes){
            codes.add(code.getCountryCode());
            names.add(code.getCountryName());
            ccByCode.put(code.getCountryCode(),code);
        }

        Collections.sort(codes);
        Collections.reverse(codes);

    }

    public void send(String message) throws Exception {

        TypeReference<Map<String, Object>> typeRef
                = new TypeReference<Map<String, Object>>() {};

        Map<String, Object> input = mapper.readValue(message, typeRef);

        if(!input.containsKey("countryCode")){
            throw new Exception("Missing property [countryCode]");
        }

        if(!input.containsKey("countryName")){
            throw new Exception("Missing property [countryName]");
        }

        if(!input.containsKey("message")){
            throw new Exception("Missing property [message]");
        }

        if(!input.containsKey("target")){
            throw new Exception("Missing property [target]");
        }

        String countryCode = String.valueOf(input.get("countryCode"));
        String countryName = String.valueOf(input.get("countryName"));
        String messageText = String.valueOf(input.get("message"));
        String target = String.valueOf(input.get("target"));

        if(countryCode.equals("") || countryCode.equals("null") || !codes.contains(countryCode)){
            throw new Exception("Country Code was empty or null or invalid");
        }

        if(countryName.equals("") || countryName.equals("null") || !names.contains(countryName)){
            throw new Exception("Country Name was empty or null or invalid");
        }

        if(messageText.equals("") || messageText.equals("null") || !Receiver.messages.contains(messageText)){
            throw new Exception("Message was empty or null or invalid");
        }

        if(target.equals("") || target.equals("null")){
            throw new Exception("Target was empty or null");
        }


        if(target.startsWith("00")){
            target = target.substring(2);
        }

        if(target.startsWith("+")){
            target = target.substring(1);
        }

        boolean found = false;
        for(String code : codes){

            if(target.startsWith(code)){
                CountryCode countryCode1 = ccByCode.get(code);

                if(!countryName.equals(countryCode1.getCountryName())){
                    throw new Exception("Country with code [" + countryCode + "] did not match expected name [" + countryCode1.getCountryName() +"]");
                }

                found = true;
                break;
            }

        }

        if(!found){
            throw new Exception("Target [" + target +"] did not have an available reference country code");
        }

        LOG.info("Send Successful to [" + countryName +"] [" + target + "] with message [" + messageText +"] !");
    }


}
