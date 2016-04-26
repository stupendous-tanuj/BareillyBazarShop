package com.app.bareillybazarshop.api.output;

public class ProductCategory {

    private String productCategoryName;
    private String productCategoryDescription;
    private String productCategoryImageName;

    /**
     * @return The productCategoryName
     */
    public String getProductCategoryName() {
        return productCategoryName;
    }

    /**
     * @param productCategoryName The productCategoryName
     */
    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    /**
     * @return The productCategoryDescription
     */
    public String getProductCategoryDescription() {
        return productCategoryDescription;
    }

    /**
     * @param productCategoryDescription The productCategoryDescription
     */
    public void setProductCategoryDescription(String productCategoryDescription) {
        this.productCategoryDescription = productCategoryDescription;
    }

    /**
     * @return The productCategoryImageName
     */
    public String getProductCategoryImageName() {
        return productCategoryImageName;
    }

    /**
     * @param productCategoryImageName The productCategoryImageName
     */
    public void setProductCategoryImageName(String productCategoryImageName) {
        this.productCategoryImageName = productCategoryImageName;
    }

}