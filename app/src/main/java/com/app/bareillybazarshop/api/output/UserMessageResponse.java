package com.app.bareillybazarshop.api.output;

import java.util.List;

/**
 * Created by TANUJ on 5/2/2016.
 */
public class UserMessageResponse {

    private List<UserMessages> userMessages;

    public List<UserMessages> getUserMessages() {
        return userMessages;
    }

    public void setUserMessages(List<UserMessages> userMessages) {
        this.userMessages = userMessages;
    }
}
