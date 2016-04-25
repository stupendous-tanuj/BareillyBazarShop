package com.app.shopkeeper.api.output;

import java.util.List;

/**
 * Created by umesh on 1/10/16.
 */
public class MyOrderDetailResponse {

    private List<OrderDetail> orderDetails;

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
