package com.app.bareillybazarshop.api.output;

/**
 * Created by TANUJ on 3/31/2016.
 */
public class ShopOperationalTime {

    private String shopId;
    private String closingDate;

    public String getShopOpeningTime() {
        return shopOpeningTime;
    }

    public void setShopOpeningTime(String shopOpeningTime) {
        this.shopOpeningTime = shopOpeningTime;
    }

    public String getShopClosingTime() {
        return shopClosingTime;
    }

    public void setShopClosingTime(String shopClosingTime) {
        this.shopClosingTime = shopClosingTime;
    }

    private String shopOpeningTime;
    private String shopClosingTime;

    private String additionalFields;

    public String getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(String additionalFields) {
        this.additionalFields = additionalFields;
    }


    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }



}
