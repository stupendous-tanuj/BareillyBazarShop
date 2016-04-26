package com.app.bareillybazarshop.activity;

import android.os.Bundle;

import com.app.bareillybazarshop.R;

public class UpdateOrderDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order_detail);
        setHeader("Update Order Detail", "");
    }
}
