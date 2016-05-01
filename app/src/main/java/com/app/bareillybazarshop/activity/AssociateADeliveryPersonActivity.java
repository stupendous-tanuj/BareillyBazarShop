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
import com.app.bareillybazarshop.api.output.DeliveryPersonResponse;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppResponseListener;
import com.app.bareillybazarshop.network.AppRestClient;
import com.app.bareillybazarshop.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

public class AssociateADeliveryPersonActivity extends BaseActivity {

    private Spinner spinner_shopId;
    private Spinner spinner_deliveryPerson;
    TextView tv_associateADeliveryPerson;
    LinearLayout ll_shopId;
    String shopIdValue = "";
    String deliveryPerson = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associate_adelivery_person);
        setHeader(getString(R.string.header_associate_a_delivery_person), "");
        setUI();
        setUIListener();
        fetchAllDeliveryPersonAPI();
        fetchShopIdAPI();

    }

    private void setUI() {
        spinner_shopId = (Spinner) findViewById(R.id.spinner_shopId);
        spinner_deliveryPerson = (Spinner) findViewById(R.id.spinner_deliveryPerson);
        ll_shopId = (LinearLayout) findViewById(R.id.ll_shopId);
        tv_associateADeliveryPerson = (TextView) findViewById(R.id.tv_associateADeliveryPerson);
    }

    private void setUIListener() {
        tv_associateADeliveryPerson.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_associateADeliveryPerson:
                associateADeliveryPersonAPI();
                break;
        }
    }

    public void setDeliveryPersonDropDown(DeliveryPersonResponse response)
    {
        final List<String> deliveryPersonList = new ArrayList<>();
        for (int i = 0; i < response.getDeliveryPerson().size(); i++)
            deliveryPersonList.add(response.getDeliveryPerson().get(i).getDeliveryPersonMobileNumber()+" "+response.getDeliveryPerson().get(i).getDeliveryPersonName());

        ArrayAdapter<String> deliveryDateDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, deliveryPersonList);
        deliveryDateDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_deliveryPerson.setAdapter(deliveryDateDataAdapter);
        spinner_deliveryPerson.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                deliveryPerson = deliveryPersonList.get(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void fetchAllDeliveryPersonAPI() {

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.associatedDeliveryPersonAPI("", "1", new AppResponseListener<DeliveryPersonResponse>(DeliveryPersonResponse.class, this) {
            @Override
            public void onSuccess(DeliveryPersonResponse result) {
                hideProgressBar();
                setDeliveryPersonDropDown(result);
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
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

    private void associateADeliveryPersonAPI() {

        if (!DialogUtils.isSpinnerDefaultValue(this, shopIdValue, "Shop ID")) {
            return;
        }

        if (!DialogUtils.isSpinnerDefaultValue(this, deliveryPerson, "Delivery Person")) {
            return;
        }
        deliveryPerson = deliveryPerson.split(" ")[0];
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.associateADeliveryPersonAPI(shopIdValue, deliveryPerson, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
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
