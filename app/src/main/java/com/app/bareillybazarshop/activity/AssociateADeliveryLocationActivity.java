package com.app.bareillybazarshop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.api.output.AssociatedShopId;
import com.app.bareillybazarshop.api.output.AssociatedShopIdResponse;
import com.app.bareillybazarshop.api.output.CommonResponse;
import com.app.bareillybazarshop.api.output.DeliveryLocation;
import com.app.bareillybazarshop.api.output.DeliveryLocationResponse;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.constant.AppConstant;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppResponseListener;
import com.app.bareillybazarshop.network.AppRestClient;
import com.app.bareillybazarshop.utils.DialogUtils;
import com.app.bareillybazarshop.utils.PreferenceKeeper;

import java.util.ArrayList;
import java.util.List;

public class AssociateADeliveryLocationActivity extends BaseActivity {

    private Spinner spinner_available_delivery_locations;
    private TextView  tv_add_delivery_location;
    private TextView tv_available_delivery_locations;
    String deliveryLocation = "";
    private Spinner spinner_shopId;
    LinearLayout ll_shopId;
    String shopIdValue = "ALL";
    String USER_TYPE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delivery_location);
        setHeader(getString(R.string.header_associate_a_delivery_location), "");
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        setUI();
        fetchAvailableDeliveryLocationsApi();
    }


    private void setDeliveryLocationDropDown(DeliveryLocationResponse result) {
        final List<String> deliveryLocationList = new ArrayList<>();
        for (DeliveryLocation method : result.getDeliveryLocations()) {
            deliveryLocationList.add(method.getDeliveryLocation()+","+method.getCity());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, deliveryLocationList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_available_delivery_locations.setAdapter(dataAdapter);
        spinner_available_delivery_locations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                deliveryLocation = deliveryLocationList.get(pos).split(",")[0];
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_delivery_location:
                associateADeliveryLocationAPI();
                break;
        }
    }

    private void setUI() {
        tv_add_delivery_location = (TextView) findViewById(R.id.tv_add_delivery_location);
        spinner_available_delivery_locations = (Spinner) findViewById(R.id.spinner_available_delivery_locations);
        tv_available_delivery_locations = (TextView) findViewById(R.id.tv_available_delivery_locations);
        findViewById(R.id.tv_add_delivery_location).setOnClickListener(this);
        spinner_shopId = (Spinner) findViewById(R.id.spinner_shopId);
        ll_shopId = (LinearLayout) findViewById(R.id.ll_shopId);
        tv_available_delivery_locations.setVisibility(View.GONE);
        spinner_available_delivery_locations.setVisibility(View.GONE);
        if((USER_TYPE.equals(AppConstant.UserType.DELIVERY_PERSON_TYPE)) || (USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE))) {
            ll_shopId.setVisibility(View.GONE);
            tv_available_delivery_locations.setVisibility(View.VISIBLE);
            spinner_available_delivery_locations.setVisibility(View.VISIBLE);
            fetchAvailableDeliveryLocationsApi();
        }
        else {
            ll_shopId.setVisibility(View.VISIBLE);
            fetchShopIdAPI();
        }

    }

    private void setShopIdSpinner(List<AssociatedShopId> associatedShopId)
    {
        final List<String> shopId = new ArrayList<>();
        shopId.add(getString(R.string.please_select));
        for (int i = 0; i < associatedShopId.size(); i++)
            shopId.add(associatedShopId.get(i).getShopID());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, shopId);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_shopId.setAdapter(dataAdapter);
        spinner_shopId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                shopIdValue = shopId.get(pos);
                if(!shopIdValue.equals(getString(R.string.please_select))) {
                    tv_available_delivery_locations.setVisibility(View.VISIBLE);
                    spinner_available_delivery_locations.setVisibility(View.VISIBLE);
                    fetchAvailableDeliveryLocationsApi();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void fetchShopIdAPI() {

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.associatedShopId(new AppResponseListener<AssociatedShopIdResponse>(AssociatedShopIdResponse.class, this) {
            @Override
            public void onSuccess(AssociatedShopIdResponse result) {
                hideProgressBar();
                setShopIdSpinner(result.getAssociatedShops());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void fetchAvailableDeliveryLocationsApi() {

        if(USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE)) {
            shopIdValue = PreferenceKeeper.getInstance().getUserId();
        }
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.fetchAvailableDeliveryLocationAPI(shopIdValue, new AppResponseListener<DeliveryLocationResponse>(DeliveryLocationResponse.class, this) {
            @Override
            public void onSuccess(DeliveryLocationResponse result) {
                hideProgressBar();
                setDeliveryLocationDropDown(result);
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void associateADeliveryLocationAPI() {

        if (!DialogUtils.isSpinnerDefaultValue(this, shopIdValue, getString(R.string.label_Shop_ID))) {
            return;
        }

        if(USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE)) {
            shopIdValue = PreferenceKeeper.getInstance().getUserId();
        }

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.associateADeliveryLocationAPI(shopIdValue, deliveryLocation, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar();
                showToast(result.getSuccessMessage());
                finish();
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }


}
