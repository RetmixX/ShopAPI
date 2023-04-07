package com.example.retmix.response;

public class HelloResponse {
    private final String message;
    private final String ipAddress;

    public HelloResponse(String message, String ipAddress) {
        this.message = message;
        this.ipAddress = ipAddress;
    }

    public String getMessage() {
        return message;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
