package com.app.bareillybazarshop.api.output;

/**
 * Created by TANUJ on 3/31/2016.
 */

import java.util.ArrayList;
import java.util.List;

public class ShopOperationalTimeResponse {

    private List<ShopOperationalTime> shopOperationalTime = new ArrayList<ShopOperationalTime>();

    /**
     *
     * @return
     * The shopOperationalTime
     */
    public List<ShopOperationalTime> getShopOperationalTime() {
        return shopOperationalTime;
    }

    /**
     *
     * @param shopOperationalTime
     * The shopOperationalTime
     */
    public void setShopOperationalTime(List<ShopOperationalTime> shopOperationalTime) {
        this.shopOperationalTime = shopOperationalTime;
    }

}
