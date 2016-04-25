package com.app.shopkeeper.api.output;

import java.util.List;

/**
 * Created by umesh on 2/14/16.
 */
public class ProductCategoryResponse {

    private List<ProductCategory> productCategories;

    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }
}
