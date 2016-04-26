package com.app.bareillybazarshop.api.output;

/**
 * Created by TANUJ on 3/20/2016.
 */
public class AssociatedShopId {

    private String additionalFields;

    public String getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(String additionalFields) {
        this.additionalFields = additionalFields;
    }


    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    private String shopID;

}
