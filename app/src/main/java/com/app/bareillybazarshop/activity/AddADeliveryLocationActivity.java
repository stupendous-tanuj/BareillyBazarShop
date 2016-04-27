package com.app.bareillybazarshop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.adapter.AssociatedDeliveryLocationAdapter;
import com.app.bareillybazarshop.api.output.AssociatedShopId;
import com.app.bareillybazarshop.api.output.AssociatedShopIdResponse;
import com.app.bareillybazarshop.api.output.CommonResponse;
import com.app.bareillybazarshop.api.output.DeliveryLocation;
import com.app.bareillybazarshop.api.output.DeliveryLocationResponse;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.api.output.Product;
import com.app.bareillybazarshop.api.output.ProductCategory;
import com.app.bareillybazarshop.api.output.ProductCategoryResponse;
import com.app.bareillybazarshop.api.output.ProductResponse;
import com.app.bareillybazarshop.api.output.ShopCategory;
import com.app.bareillybazarshop.api.output.ShopCategoryResponse;
import com.app.bareillybazarshop.constant.AppConstant;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppResponseListener;
import com.app.bareillybazarshop.network.AppRestClient;
import com.app.bareillybazarshop.utils.DialogUtils;
import com.app.bareillybazarshop.utils.PreferenceKeeper;


import java.util.ArrayList;
import java.util.List;

public class AddADeliveryLocationActivity extends BaseActivity {

    String USER_TYPE = "";
    EditText et_deliveryLocation;
    TextView tv_addDeliveryLocation;
    Spinner spinner_city;
    String city = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adelivery_location);
        setHeader(getString(R.string.header_add_a_delivery_location), "");
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        setUI();
        setCitySpinner();
    }

    private void setUI() {
        et_deliveryLocation = (EditText) findViewById(R.id.et_deliveryLocation);
        tv_addDeliveryLocation = (TextView) findViewById(R.id.tv_addDeliveryLocation);
        spinner_city = (Spinner) findViewById(R.id.spinner_city);
        findViewById(R.id.tv_addDeliveryLocation).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_addDeliveryLocation:
                addADeliveryLocationAPI();
                break;
        }
    }

    private void setCitySpinner()
    {
        final List<String> cityList = new ArrayList<>();
        cityList.add(AppConstant.CITY.CITY_BAREILLY);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cityList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_city.setAdapter(dataAdapter);
        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                city = cityList.get(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    private void addADeliveryLocationAPI() {

        String deliveryLocation = et_deliveryLocation.getText().toString();

        if (!DialogUtils.checkForBlank(this, getString(R.string.label_Enter_Delivery_Location), deliveryLocation)) {
            return;
        }

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.addADeliveryLocationAPI(deliveryLocation,city,new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar();
                showToast(result.getSuccessMessage());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    }
