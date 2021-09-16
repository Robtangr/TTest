package com.test.intproj;

import com.test.intproj.dto.OutputMessage;

public interface MessageEncoder {

    /***
     * Encodes a java DTO into a format the message sender can read.
     * @param message The message to be send
     * @return The encoded message
     * @throws Exception
     */
    String encode(OutputMessage message) throws Exception;
}
