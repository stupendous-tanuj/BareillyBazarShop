package com.app.shopkeeper.api.output;

import java.util.List;

/**
 * Created by umesh on 2/14/16.
 */
public class ProductResponse {
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
