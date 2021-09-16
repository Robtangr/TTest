package com.test.intproj;


public interface TargetDecoder {
    /***
     * Extracts the target address from a message
     * @param message The input message
     * @return The target the messages should be sent to
     */
    String decode(String message);
}
