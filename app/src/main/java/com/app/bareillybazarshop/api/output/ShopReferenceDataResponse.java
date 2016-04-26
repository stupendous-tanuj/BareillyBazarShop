package com.app.bareillybazarshop.api.output;

import java.util.List;

/**
 * Created by TANUJ on 4/1/2016.
 */
public class ShopReferenceDataResponse {

    private List<DeliveryMethod> deliveryMethods;
    private List<PaymentMethod> paymentMethods;
    private List<SupportedIDType> supportedIDType;

    public List<SupportedIDType> getSupportedIDType() {
        return supportedIDType;
    }

    public void setSupportedIDType(List<SupportedIDType> supportedIDType) {
        this.supportedIDType = supportedIDType;
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
