package com.mullya.app.security;

import org.springframework.security.core.AuthenticationException;

public class InvalidOTPException extends AuthenticationException {

    private static final long serialVersionUID = -2196949148938451079L;

    public static final String Expired = "Expired";
    public static final String IncorrectOtp = "IncorrectOtp";
    public static final String IncorrectEmail = "IncorrectEmail";
    public static final String IncorrectPhone = "IncorrectPhone";
    public static final String IncorrectEmailOrPhone = "IncorrectEmailOrPhone";

    private String errorType;

    public InvalidOTPException(String defaultMessage, String errorType) {
        super(defaultMessage);
        this.errorType = errorType;
    }

    public InvalidOTPException(String errorType) {
        super("Invalid OTP");
        this.errorType = errorType;
    }

    public String getErrorType() {
        return this.errorType;
    }
}
