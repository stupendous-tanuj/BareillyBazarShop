package com.app.shopkeeper.api.output;

public class DeliveryPerson {

    private String deliveryPersonName;
    private String deliveryPersonMobileNumber;

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