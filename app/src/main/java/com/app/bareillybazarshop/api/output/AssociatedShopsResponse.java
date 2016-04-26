package com.app.bareillybazarshop.api.output;

import java.util.List;

/**
 * Created by TANUJ on 3/17/2016.
 */
public class AssociatedShopsResponse {

    private List<AssociatedShop> associatedShops;

    public List<AssociatedShop> getAssociatedShops() {
        return associatedShops;
    }

    public void setAssociatedShops(List<AssociatedShop> associatedShop) {
        this.associatedShops = associatedShop;
    }
    
}
