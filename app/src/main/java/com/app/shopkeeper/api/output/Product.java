package com.app.shopkeeper.api.output;

public class Product {


    private String productSKUID;
    private String productNameEnglish;
    private String productNameHindi;
    private String productDescription;
    private String productOrderUnit;
    private String productPriceForUnits;
    private String productImageName;
    private String productPrice;
    private String productOfferedPrice;

    public String getProductSKUID() {
        return productSKUID;
    }

    public void setProductSKUID(String productSKUID) {
        this.productSKUID = productSKUID;
    }

    public String getProductNameEnglish() {
        return productNameEnglish;
    }

    public void setProductNameEnglish(String productNameEnglish) {
        this.productNameEnglish = productNameEnglish;
    }

    public String getProductNameHindi() {
        return productNameHindi;
    }

    public void setProductNameHindi(String productNameHindi) {
        this.productNameHindi = productNameHindi;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductOrderUnit() {
        return productOrderUnit;
    }

    public void setProductOrderUnit(String productOrderUnit) {
        this.productOrderUnit = productOrderUnit;
    }

    public String getProductPriceForUnits() {
        return productPriceForUnits;
    }

    public void setProductPriceForUnits(String productPriceForUnits) {
        this.productPriceForUnits = productPriceForUnits;
    }

    public String getProductImageName() {
        return productImageName;
    }

    public void setProductImageName(String productImageName) {
        this.productImageName = productImageName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductOfferedPrice() {
        return productOfferedPrice;
    }

    public void setProductOfferedPrice(String productOfferedPrice) {
        this.productOfferedPrice = productOfferedPrice;
    }
}