package com.app.shopkeeper.api;

/**
 * Created by umesh on 13/11/15.
 */
public class Error {
    private String errorMessage;

    public Error(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
