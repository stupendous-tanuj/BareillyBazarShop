package com.app.shopkeeper.api.output;

import java.util.List;

/**
 * Created by umesh on 11/1/16.
 */
public class RecentOrderResponse {

    private List<OrderDetail> orderDetails ;
    private List<CartDetail> cartDetails;

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public List<CartDetail> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(List<CartDetail> cartDetails) {
        this.cartDetails = cartDetails;
    }
}
