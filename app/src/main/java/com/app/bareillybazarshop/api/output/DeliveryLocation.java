package com.app.bareillybazarshop.api.output;

/**
 * Created by TANUJ on 3/8/2016.
 */
public class DeliveryLocation {

    private String additionalFields;

    public String getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(String additionalFields) {
        this.additionalFields = additionalFields;
    }


    private String deliveryLocation;
    private String city;

    /**
     *
     * @return
     * The deliveryLocation
     */
    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    /**
     *
     * @param deliveryLocation
     * The deliveryLocation
     */
    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    /**
     *
     * @return
     * The city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(String city) {
        this.city = city;
    }

}
