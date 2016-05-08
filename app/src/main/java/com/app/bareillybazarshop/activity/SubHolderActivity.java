package com.app.bareillybazarshop.activity;

import com.app.bareillybazarshop.listner.ScrollTabHolder;

/**
 * Created by umesh on 19/1/16.
 */
public abstract class SubHolderActivity extends BaseActivity implements ScrollTabHolder {
    protected ScrollTabHolder mScrollTabHolder;

    public void setScrollTabHolder(ScrollTabHolder scrollTabHolder) {
        mScrollTabHolder = scrollTabHolder;
    }
}
