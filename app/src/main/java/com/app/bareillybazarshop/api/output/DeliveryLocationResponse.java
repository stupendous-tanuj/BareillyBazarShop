package com.app.bareillybazarshop.api.output;

import java.util.List;

/**
 * Created by umesh on 2/1/16.
 */
public class DeliveryLocationResponse {

    private List<DeliveryLocation> deliveryLocations;

    public List<DeliveryLocation> getDeliveryLocations() {
        return deliveryLocations;
    }

    public void setDeliveryLocations(List<DeliveryLocation> deliveryLocation) {
        this.deliveryLocations = deliveryLocation;
    }
}
