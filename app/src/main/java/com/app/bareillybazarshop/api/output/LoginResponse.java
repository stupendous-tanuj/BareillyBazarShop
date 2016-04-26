package com.app.bareillybazarshop.api.output;

/**
 * Created by umesh on 15/12/15.
 */
public class LoginResponse {

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(String firstLogin) {
        this.firstLogin = firstLogin;
    }
    private String firstLogin;
    private String successMessage;


}
