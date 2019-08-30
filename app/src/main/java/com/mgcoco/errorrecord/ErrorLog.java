package com.mgcoco.errorrecord;

public class ErrorLog {

    String time;
    String message;

    public ErrorLog(String time, String message) {
        this.time = time;
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }
}
