package com.app.bareillybazarshop.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.adapter.AssociateDeliveryPersonAdapter;
import com.app.bareillybazarshop.api.output.DeliveryPerson;
import com.app.bareillybazarshop.api.output.DeliveryPersonResponse;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppResponseListener;
import com.app.bareillybazarshop.network.AppRestClient;

import java.util.List;

public class AssociateDeliveryPersonActivity extends BaseActivity {


    private RecyclerView recycleView;
    private String shopIdORSellerHubId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associate_delivery_person);
        setHeader("Associated Delivery Person", "");
        setUI();
        setRecycler();
        associateDeliveryPersonAPI();
    }

    private void setUI() {
        recycleView = (RecyclerView) findViewById(R.id.recycle_view_associate_delivery_person);

        findViewById(R.id.iv_add_ass_deleivery_person).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_ass_deleivery_person:
                launchActivity(AddDeliveryPersonActivity.class);
                break;
        }
    }

    private void setRecycler() {
        recycleView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(mLayoutManager);
    }

    private void associateDeliveryPersonAPI() {

        shopIdORSellerHubId = "SP1001";
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.associateDeliveryPersonAPI(shopIdORSellerHubId, new AppResponseListener<DeliveryPersonResponse>(DeliveryPersonResponse.class, this) {
            @Override
            public void onSuccess(DeliveryPersonResponse result) {
                hideProgressBar();
                setAdapterData(result.getDeliveryPerson());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setAdapterData(List<DeliveryPerson> deliveryPerson) {
        AssociateDeliveryPersonAdapter adapter = new AssociateDeliveryPersonAdapter(this, deliveryPerson, shopIdORSellerHubId);
        recycleView.setAdapter(adapter);
    }
}
