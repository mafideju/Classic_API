package org.mafideju.API_Kargopolov.exception;

import java.util.Date;

public class ErrorMessageString {
    private Date timestamp;
    private String message;

    public ErrorMessageString() {}

    public ErrorMessageString(Date timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
