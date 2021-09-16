package com.test.intproj.services.ref;

import com.test.intproj.TargetDecoder;
import org.springframework.stereotype.Component;

@Component(value = "ReferenceTargetDecoder")
public class ReferenceTargetDecoder implements TargetDecoder {
    @Override
    public String decode(String message) {
        return message.split(";")[0];
    }
}
