package com.app.bareillybazarshop.api.output;

import java.util.List;

/**
 * Created by umesh on 2/16/16.
 */
public class OrderUnitResponse {

    private List<OrderUnit> orderUnits;

    public List<OrderUnit> getOrderUnits() {
        return orderUnits;
    }

    public void setOrderUnits(List<OrderUnit> orderUnits) {
        this.orderUnits = orderUnits;
    }
}
