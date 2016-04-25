package com.app.shopkeeper.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.shopkeeper.R;
import com.app.shopkeeper.api.output.ErrorObject;
import com.app.shopkeeper.api.output.MyOrderDetailResponse;
import com.app.shopkeeper.api.output.SellerHubProfile;
import com.app.shopkeeper.api.output.SellerHubProfileResponse;
import com.app.shopkeeper.network.AppHttpRequest;
import com.app.shopkeeper.network.AppRequestBuilder;
import com.app.shopkeeper.network.AppResponseListener;
import com.app.shopkeeper.network.AppRestClient;

public class SellerHUbProfileActivity extends BaseActivity {


    private TextView tv_seller_hub_id;
    private TextView tv_seller_hub_firm_n;
    private TextView tv_seller_hub_owner_n;
    private TextView tv_seller_hub_mobile;
    private TextView tv_seller_hub_support_cont;
    private TextView tv_seller_hub_ddress;
    private TextView tv_seller_hub_business_type;
    private ScrollView scrollview_seller_hub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_hub);
        setHeader("Seller Hub Profile", "");
        setUI();
        fetchSellerHubApi();
    }

    private void setUI() {
        scrollview_seller_hub = (ScrollView) findViewById(R.id.scrollview_seller_hub);
        scrollview_seller_hub.setVisibility(View.INVISIBLE);
        tv_seller_hub_id = (TextView) findViewById(R.id.tv_seller_hub_id);
        tv_seller_hub_firm_n = (TextView) findViewById(R.id.tv_seller_hub_firm_n);
        tv_seller_hub_owner_n = (TextView) findViewById(R.id.tv_seller_hub_owner_n);
        tv_seller_hub_support_cont = (TextView) findViewById(R.id.tv_seller_hub_support_cont);
        tv_seller_hub_mobile = (TextView) findViewById(R.id.tv_seller_hub_mobile);
        tv_seller_hub_ddress = (TextView) findViewById(R.id.tv_seller_hub_ddress);
        tv_seller_hub_business_type = (TextView) findViewById(R.id.tv_seller_hub_business_type);
    }

    private void fetchSellerHubApi() {
        showProgressBar(findViewById(R.id.tv_update_profile));
        AppHttpRequest request = AppRequestBuilder.fetchSellerHubProfileAPI(new AppResponseListener<SellerHubProfileResponse>(SellerHubProfileResponse.class, this) {
            @Override
            public void onSuccess(SellerHubProfileResponse result) {
                hideProgressBar(findViewById(R.id.tv_update_profile));
                scrollview_seller_hub.setVisibility(View.VISIBLE);
                setSellerHubProfileData(result.getSellerHubProfile().get(0));
            }

            @Override
            public void onError(ErrorObject error) {
                scrollview_seller_hub.setVisibility(View.VISIBLE);
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setSellerHubProfileData(SellerHubProfile profile) {
        tv_seller_hub_id.setText(profile.getSellerHubID());
        tv_seller_hub_firm_n.setText(profile.getSellerHubFirmName());
        tv_seller_hub_owner_n.setText(profile.getSellerHubOwnerName());
        tv_seller_hub_mobile.setText(profile.getSellerHubMobileNumber());
        tv_seller_hub_support_cont.setText(profile.getSellerHubSupportContactNumber());
        tv_seller_hub_ddress.setText(profile.getSellerHubAddress());
        tv_seller_hub_business_type.setText(profile.getSellerHubIndividualOrBusiness());
    }
}
