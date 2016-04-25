package com.app.shopkeeper.api.output;

import java.util.List;

/**
 * Created by umesh on 1/31/16.
 */
public class AssociatedProductResponse {

    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
