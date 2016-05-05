package com.app.bareillybazarshop.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.adapter.AssociatedDeliveryPersonAdapter;
import com.app.bareillybazarshop.api.output.AssociatedShopId;
import com.app.bareillybazarshop.api.output.AssociatedShopIdResponse;
import com.app.bareillybazarshop.api.output.DeliveryPerson;
import com.app.bareillybazarshop.api.output.DeliveryPersonResponse;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.api.output.OrderDetail;
import com.app.bareillybazarshop.constant.AppConstant;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppResponseListener;
import com.app.bareillybazarshop.network.AppRestClient;
import com.app.bareillybazarshop.utils.DialogUtils;
import com.app.bareillybazarshop.utils.PreferenceKeeper;

import java.util.ArrayList;
import java.util.List;

public class AssociatedDeliveryPersonActivity extends BaseActivity {


    private RecyclerView recycleView;
    private String shopIdORSellerHubId;
    private Spinner spinner_shopId;
    LinearLayout ll_shopId;
    String shopIdValue = "ALL";
    String USER_TYPE = "";
    private TextView no_data_available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associate_delivery_person);
        setHeader(getString(R.string.header_associated_delivery_person), "");
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        setUI();
        setRecycler();

    }

    private void setUI() {
        recycleView = (RecyclerView) findViewById(R.id.recycle_view_associate_delivery_person);
        no_data_available = (TextView) findViewById(R.id.no_data_available);
        findViewById(R.id.iv_add_ass_deleivery_person).setOnClickListener(this);
        spinner_shopId = (Spinner) findViewById(R.id.spinner_shopId);
        ll_shopId = (LinearLayout) findViewById(R.id.ll_shopId);
        if((USER_TYPE.equals(AppConstant.UserType.DELIVERY_PERSON_TYPE)) || (USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE))) {
            ll_shopId.setVisibility(View.GONE);
            associateDeliveryPersonAPI();
        }
        else {
            ll_shopId.setVisibility(View.VISIBLE);
            fetchShopIdAPI();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_ass_deleivery_person:
                launchActivity(AssociateADeliveryPersonActivity.class);
                break;
        }
    }

    private void setRecycler() {
        recycleView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(mLayoutManager);
    }

    private void setVisibleUI(List<DeliveryPerson> deliveryPerson) {
        if (deliveryPerson == null || deliveryPerson.size() == 0) {
            recycleView.setVisibility(View.GONE);
            no_data_available.setVisibility(View.VISIBLE);
            no_data_available.setText(getString(R.string.msg_No_Delivery_Person_Found));
        } else {
            recycleView.setVisibility(View.VISIBLE);
            no_data_available.setVisibility(View.GONE);
            setAdapterData(deliveryPerson);
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
                if(!shopIdValue.equals(getString(R.string.please_select)))
                    associateDeliveryPersonAPI();
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


    private void associateDeliveryPersonAPI() {

        if(USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE)) {
            shopIdValue = PreferenceKeeper.getInstance().getUserId();
        }

        if (!DialogUtils.isSpinnerDefaultValue(this, shopIdValue, getString(R.string.label_Shop_ID)) || (shopIdValue.equals(""))) {
            return;
        }

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.associatedDeliveryPersonAPI(shopIdValue, "0", new AppResponseListener<DeliveryPersonResponse>(DeliveryPersonResponse.class, this) {
            @Override
            public void onSuccess(DeliveryPersonResponse result) {
                hideProgressBar();
                setVisibleUI(result.getDeliveryPerson());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setAdapterData(List<DeliveryPerson> deliveryPerson) {
        AssociatedDeliveryPersonAdapter adapter = new AssociatedDeliveryPersonAdapter(this, deliveryPerson, shopIdORSellerHubId);
        recycleView.setAdapter(adapter);
    }
}
