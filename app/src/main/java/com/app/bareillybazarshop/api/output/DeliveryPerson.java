package com.app.bareillybazarshop.api.output;

public class DeliveryPerson {

    private String additionalFields;

    public String getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(String additionalFields) {
        this.additionalFields = additionalFields;
    }


    private String deliveryPersonName;
    private String deliveryPersonMobileNumber;

    public String getDeliveryPersonEmailId() {
        return deliveryPersonEmailId;
    }

    public void setDeliveryPersonEmailId(String deliveryPersonEmailId) {
        this.deliveryPersonEmailId = deliveryPersonEmailId;
    }

    public String getDeliveryPersonImageURL() {
        return deliveryPersonImageURL;
    }

    public void setDeliveryPersonImageURL(String deliveryPersonImageURL) {
        this.deliveryPersonImageURL = deliveryPersonImageURL;
    }

    private String deliveryPersonEmailId;
    private String deliveryPersonImageURL;

    public String getDeliveryPersonRegistrationStatus() {
        return deliveryPersonRegistrationStatus;
    }

    public void setDeliveryPersonRegistrationStatus(String deliveryPersonRegistrationStatus) {
        this.deliveryPersonRegistrationStatus = deliveryPersonRegistrationStatus;
    }

    public String getDeliveryPersonResidentialAddress() {
        return deliveryPersonResidentialAddress;
    }

    public void setDeliveryPersonResidentialAddress(String deliveryPersonResidentialAddress) {
        this.deliveryPersonResidentialAddress = deliveryPersonResidentialAddress;
    }

    public String getDeliveryPersonIDType() {
        return deliveryPersonIDType;
    }

    public void setDeliveryPersonIDType(String deliveryPersonIDType) {
        this.deliveryPersonIDType = deliveryPersonIDType;
    }

    public String getDeliveryPersonIDNumber() {
        return deliveryPersonIDNumber;
    }

    public void setDeliveryPersonIDNumber(String deliveryPersonIDNumber) {
        this.deliveryPersonIDNumber = deliveryPersonIDNumber;
    }

    private String deliveryPersonRegistrationStatus;
    private String deliveryPersonResidentialAddress;
    private String deliveryPersonIDType;
    private String deliveryPersonIDNumber;

    /**
     * @return The deliveryPersonName
     */
    public String getDeliveryPersonName() {
        return deliveryPersonName;
    }

    /**
     * @param deliveryPersonName The deliveryPersonName
     */
    public void setDeliveryPersonName(String deliveryPersonName) {
        this.deliveryPersonName = deliveryPersonName;
    }

    /**
     * @return The deliveryPersonMobileNumber
     */
    public String getDeliveryPersonMobileNumber() {
        return deliveryPersonMobileNumber;
    }

    /**
     * @param deliveryPersonMobileNumber The deliveryPersonMobileNumber
     */
    public void setDeliveryPersonMobileNumber(String deliveryPersonMobileNumber) {
        this.deliveryPersonMobileNumber = deliveryPersonMobileNumber;
    }

}