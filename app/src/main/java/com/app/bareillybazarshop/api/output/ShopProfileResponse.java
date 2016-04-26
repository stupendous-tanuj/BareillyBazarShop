package com.app.bareillybazarshop.api.output;

import java.util.List;

/**
 * Created by umesh on 1/31/16.
 */
public class ShopProfileResponse {
    private List<ShopProfile> shopProfile;

    private List<DeliveryMethod> deliveryMethods;
    private List<PaymentMethod> paymentMethods;

    public List<ShopProfile> getShopProfile() {
        return shopProfile;
    }

    public void setShopProfile(List<ShopProfile> shopProfile) {
        this.shopProfile = shopProfile;
    }

    public List<DeliveryMethod> getDeliveryMethods() {
        return deliveryMethods;
    }

    public void setDeliveryMethods(List<DeliveryMethod> deliveryMethods) {
        this.deliveryMethods = deliveryMethods;
    }

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}
