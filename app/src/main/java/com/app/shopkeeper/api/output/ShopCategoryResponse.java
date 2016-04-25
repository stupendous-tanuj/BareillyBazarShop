package com.app.shopkeeper.api.output;

import java.util.List;

/**
 * Created by umesh on 2/14/16.
 */
public class ShopCategoryResponse {


    private List<ShopCategory> shopCategories;

    public List<ShopCategory> getShopCategories() {
        return shopCategories;
    }

    public void setShopCategories(List<ShopCategory> shopCategories) {
        this.shopCategories = shopCategories;
    }
}
