package com.app.bareillybazarshop.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.adapter.AssociatedShopAdapter;
import com.app.bareillybazarshop.api.output.AssociatedShop;
import com.app.bareillybazarshop.api.output.AssociatedShopsResponse;
import com.app.bareillybazarshop.api.output.DeliveryPerson;
import com.app.bareillybazarshop.api.output.DeliveryPersonResponse;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppResponseListener;
import com.app.bareillybazarshop.network.AppRestClient;


public class AllDeliveryPersonsActivity extends BaseActivity {


    private RecyclerView recycleView;
    private String shopIdORSellerHubId;
    private Spinner spinner_deliveryPersons;
    LinearLayout ll_deliveryPersons;
    String deliveryPersonValue = "ALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_delivery_persons);
        setHeader(getString(R.string.header_all_delivery_persons), "");
        setUI();
        setRecycler();
        fetchAllDeliveryPersonAPI();

    }

    private void setUI() {
        recycleView = (RecyclerView) findViewById(R.id.recycle_view_associate_shops);
        findViewById(R.id.iv_add_ass_deleivery_person).setOnClickListener(this);
        spinner_deliveryPersons = (Spinner) findViewById(R.id.spinner_deliveryPersons);
        ll_deliveryPersons = (LinearLayout) findViewById(R.id.ll_deliveryPersons);
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


    private void setDeliveryPersonSpinner(List<DeliveryPerson> deliveryPersonList)
    {
        final List<String> deliveryPerson = new ArrayList<>();
        deliveryPerson.add(getString(R.string.please_select));
        for (int i = 0; i < deliveryPersonList.size(); i++)
            deliveryPerson.add(deliveryPersonList.get(i).getDeliveryPersonMobileNumber()+" "+deliveryPersonList.get(i).getDeliveryPersonName());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, deliveryPerson);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_deliveryPersons.setAdapter(dataAdapter);
        spinner_deliveryPersons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                deliveryPersonValue = deliveryPerson.get(pos);
                if (!deliveryPersonValue.equals(getString(R.string.please_select)))
                    fetchAssociatedShopsAPI();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void fetchAssociatedShopsAPI() {

        deliveryPersonValue = deliveryPersonValue.split(" ")[0];

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.associatedShopsAPI(deliveryPersonValue, "N",new AppResponseListener<AssociatedShopsResponse>(AssociatedShopsResponse.class, this) {
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

    private void fetchAllDeliveryPersonAPI() {

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.associatedDeliveryPersonAPI("", "1", new AppResponseListener<DeliveryPersonResponse>(DeliveryPersonResponse.class, this) {
            @Override
            public void onSuccess(DeliveryPersonResponse result) {
                hideProgressBar();
                setDeliveryPersonSpinner(result.getDeliveryPerson());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

}
