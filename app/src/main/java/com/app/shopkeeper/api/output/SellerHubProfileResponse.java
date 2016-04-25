package com.app.shopkeeper.api.output;

import java.util.List;

/**
 * Created by umesh on 1/31/16.
 */
public class SellerHubProfileResponse {
    private List<SellerHubProfile> sellerHubProfile;

    public List<SellerHubProfile> getSellerHubProfile() {
        return sellerHubProfile;
    }

    public void setSellerHubProfile(List<SellerHubProfile> sellerHubProfile) {
        this.sellerHubProfile = sellerHubProfile;
    }
}
