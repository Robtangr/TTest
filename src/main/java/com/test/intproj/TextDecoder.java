package com.test.intproj;

public interface TextDecoder {

    /***
     * Decodes a text string from a message with an encoded body
     * @param message The input message
     * @return The decoded text
     */
    String decode(String message);
}
