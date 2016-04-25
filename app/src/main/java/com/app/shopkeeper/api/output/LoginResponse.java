package com.app.shopkeeper.api.output;

/**
 * Created by umesh on 15/12/15.
 */
public class LoginResponse {

    private String customerMobileNumber;
    private String OTP;
    private String customerMobileNumberExist;
    private String customerDetailsExist;

    public String getCustomerMobileNumber() {
        return customerMobileNumber;
    }

    public void setCustomerMobileNumber(String customerMobileNumber) {
        this.customerMobileNumber = customerMobileNumber;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public String getCustomerMobileNumberExist() {
        return customerMobileNumberExist;
    }

    public void setCustomerMobileNumberExist(String customerMobileNumberExist) {
        this.customerMobileNumberExist = customerMobileNumberExist;
    }

    public String getCustomerDetailsExist() {
        return customerDetailsExist;
    }

    public void setCustomerDetailsExist(String customerDetailsExist) {
        this.customerDetailsExist = customerDetailsExist;
    }
}
