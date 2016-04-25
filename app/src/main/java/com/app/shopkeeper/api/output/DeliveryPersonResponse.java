package com.app.shopkeeper.api.output;

import java.util.List;

/**
 * Created by umesh on 2/1/16.
 */
public class DeliveryPersonResponse {

    private List<DeliveryPerson> deliveryPerson;

    public List<DeliveryPerson> getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(List<DeliveryPerson> deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }
}
