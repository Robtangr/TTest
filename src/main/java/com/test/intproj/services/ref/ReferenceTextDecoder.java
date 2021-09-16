package com.test.intproj.services.ref;
import com.test.intproj.TextDecoder;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component(value = "ReferenceTextDecoder")
public class ReferenceTextDecoder implements TextDecoder {
    @Override
    public String decode(String decode) {
        return  new String(Base64.getDecoder().decode(decode.split(";")[1]));
    }
}
