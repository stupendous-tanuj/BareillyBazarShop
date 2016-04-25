package com.app.shopkeeper.api.output;

public class ShopCategory {

    private String shopCategoryName;
    private String popularCategory;
    private String shopCategoryImageURL;

    /**
     * @return The shopCategoryName
     */
    public String getShopCategoryName() {
        return shopCategoryName;
    }

    /**
     * @param shopCategoryName The shopCategoryName
     */
    public void setShopCategoryName(String shopCategoryName) {
        this.shopCategoryName = shopCategoryName;
    }

    /**
     * @return The popularCategory
     */
    public String getPopularCategory() {
        return popularCategory;
    }

    /**
     * @param popularCategory The popularCategory
     */
    public void setPopularCategory(String popularCategory) {
        this.popularCategory = popularCategory;
    }

    /**
     * @return The shopCategoryImageURL
     */
    public String getShopCategoryImageURL() {
        return shopCategoryImageURL;
    }

    /**
     * @param shopCategoryImageURL The shopCategoryImageURL
     */
    public void setShopCategoryImageURL(String shopCategoryImageURL) {
        this.shopCategoryImageURL = shopCategoryImageURL;
    }

}