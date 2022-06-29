package org.mafideju.API_Kargopolov.exception;

public enum ErrorMessagesEnum {

    MISSING_REQUIRED_FIELDS("MISSING_REQUIRED_FIELDS"),
    RECORD_ALREADY_EXISTS("RECORD_ALREADY_EXISTS"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),
    NO_RECORD_FOUND("NO_RECORD_FOUND"),
    AUTHENTICATION_FAILED("AUTHENTICATION_FAILED"),
    COULD_NOT_UPDATE_RECORD("COULD_NOT_UPDATE_RECORD"),
    COULD_NOT_DELETE_RECORD("COULD_NOT_DELETE_RECORD"),
    EMAIL_ADDRESS_NOT_VERIFIED("EMAIL_ADDRESS_NOT_VERIFIED");

    private String errorMessage;

    ErrorMessagesEnum(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
