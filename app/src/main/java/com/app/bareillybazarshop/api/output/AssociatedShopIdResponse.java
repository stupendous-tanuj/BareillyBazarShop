package com.app.bareillybazarshop.api.output;

import java.util.List;

/**
 * Created by TANUJ on 3/20/2016.
 */
public class AssociatedShopIdResponse {

    private List<AssociatedShopId> associatedShops;

    public List<AssociatedShopId> getAssociatedShops() {
        return associatedShops;
    }

    public void setAssociatedShops(List<AssociatedShopId> associatedShop) {
        this.associatedShops = associatedShop;
    }

}
