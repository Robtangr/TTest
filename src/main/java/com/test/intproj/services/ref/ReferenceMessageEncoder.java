package com.test.intproj.services.ref;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.intproj.MessageEncoder;
import com.test.intproj.dto.OutputMessage;
import org.springframework.stereotype.Component;

@Component(value = "ReferenceMessageEncoder")
public class ReferenceMessageEncoder implements MessageEncoder {
    @Override
    public String encode(OutputMessage message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        ModdedOutputMessage newMsg = new ModdedOutputMessage();
        newMsg.countryCode = message.getCode();
        newMsg.message = message.getMsg();
        newMsg.countryName = message.getName();
        newMsg.target = message.getTarget();
        return mapper.writeValueAsString(newMsg);
    }

    private static class ModdedOutputMessage{
        private String countryCode;
        private String countryName;
        private String message;
        private String target;

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

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

    }
}
