package com.app.bareillybazarshop.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;


import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.adapter.AssociatedShopAdapter;
import com.app.bareillybazarshop.api.output.AssociatedShop;
import com.app.bareillybazarshop.api.output.AssociatedShopsResponse;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppRestClient;
import com.app.bareillybazarshop.network.AppResponseListener;


import java.util.List;

public class AssociatedShopsActivity extends BaseActivity {


    private RecyclerView recycleView;
    private String shopIdORSellerHubId;
    private ImageView iv_addAShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associated_shops);
        setHeader(getString(R.string.header_associated_shops), "");
        setUI();
        setRecycler();
        associateShopsAPI();
    }

    private void setUI() {
        recycleView = (RecyclerView) findViewById(R.id.recycle_view_associated_shops);
        iv_addAShop  = (ImageView) findViewById(R.id.iv_addAShop);
        findViewById(R.id.iv_addAShop).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_addAShop:
                launchActivity(AddAShopActivity.class);
                break;
        }
    }

    private void setRecycler() {
        recycleView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(mLayoutManager);
    }

    private void associateShopsAPI() {

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.associatedShopsAPI("N", new AppResponseListener<AssociatedShopsResponse>(AssociatedShopsResponse.class, this) {
            @Override
            public void onSuccess(AssociatedShopsResponse result) {
                hideProgressBar();
                setAdapterData(result.getAssociatedShops());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setAdapterData(List<AssociatedShop> associatedShop) {
        AssociatedShopAdapter adapter = new AssociatedShopAdapter(this, associatedShop, shopIdORSellerHubId);
        recycleView.setAdapter(adapter);

    }
}
