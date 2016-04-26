package com.app.bareillybazarshop.api.output;

/**
 * Created by TANUJ on 3/17/2016.
 */
public class AssociatedShop {

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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopDescription() {
        return shopDescription;
    }

    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
    }

    public String getShopAddressAreaSector() {
        return shopAddressAreaSector;
    }

    public void setShopAddressAreaSector(String shopAddressAreaSector) {
        this.shopAddressAreaSector = shopAddressAreaSector;
    }

    public String getShopAddressLandmark() {
        return shopAddressLandmark;
    }

    public void setShopAddressLandmark(String shopAddressLandmark) {
        this.shopAddressLandmark = shopAddressLandmark;
    }

    private String shopID;
    private String shopName;
    private String shopDescription;
    private String shopAddressAreaSector;
    private String shopAddressLandmark;
}
