package com.example.DS_Assignment1_B1_User.controller.errorHandler;

public class Error {

    private String errorMessage;

    public Error(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
