package com.app.shopkeeper.api.output;

import java.util.List;

/**
 * Created by umesh on 2/16/16.
 */
public class SupportedIdTypeResponse {
    private List<SupportedIDType> supportedIDType;

    public List<SupportedIDType> getSupportedIDType() {
        return supportedIDType;
    }

    public void setSupportedIDType(List<SupportedIDType> supportedIDType) {
        this.supportedIDType = supportedIDType;
    }
}
