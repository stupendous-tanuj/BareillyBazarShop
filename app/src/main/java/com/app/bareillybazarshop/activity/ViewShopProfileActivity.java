package com.app.bareillybazarshop.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.api.output.DeliveryMethod;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.api.output.PaymentMethod;
import com.app.bareillybazarshop.api.output.ShopProfile;
import com.app.bareillybazarshop.api.output.ShopProfileResponse;
import com.app.bareillybazarshop.constant.AppConstant;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppResponseListener;
import com.app.bareillybazarshop.network.AppRestClient;

import java.util.ArrayList;
import java.util.List;

public class ViewShopProfileActivity extends BaseActivity {

    TextView tv_shoId = null;
    TextView tv_shopName = null;
    TextView tv_registartionStatus = null;
    TextView tv_shopOwnerName = null;
    TextView tv_shopAddress = null;
    TextView tv_shopAreaSector = null;
    TextView tv_shopCity = null;
    TextView tv_shopPincode = null;
    TextView tv_shopState = null;
    TextView tv_shopLandmark = null;
    TextView tv_ownerContactNumber = null;
    TextView tv_supportContactNumber = null;
    TextView tv_orderProcessingNumber = null;
    TextView tv_emailId = null;
    TextView tv_minimumAcceptedOrder = null;
    TextView tv_deliveryCharge = null;
    TextView tv_ownerIdType = null;
    TextView tv_ownerIdNumber = null;
    TextView tv_associatedTo = null;
    TextView tv_shopRating = null;
    Spinner spinner_profile_delivery_type = null;
    Spinner spinner_profile_payment_method = null;
    String deliveryType = "";
    String paymentMethod = "";
    String shopId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_shop_profile);
        setUI();
        setUIListener();
        setHeader(getString(R.string.header_view_shop_profile), "");
        shopId = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.SHOP_ID);
        fetchShopKeeperProfileApi();
    }


    public void setUI()
    {
        tv_shoId = (TextView) findViewById(R.id.tv_shoId);
        tv_shopName = (TextView) findViewById(R.id.tv_shopName);
        tv_registartionStatus = (TextView) findViewById(R.id.tv_registartionStatus);
        tv_shopOwnerName = (TextView) findViewById(R.id.tv_shopOwnerName);
        tv_shopAddress = (TextView) findViewById(R.id.tv_shopAddress);
        tv_shopAreaSector = (TextView) findViewById(R.id.tv_shopAreaSector);
        tv_shopCity = (TextView) findViewById(R.id.tv_shopCity);
        tv_shopPincode = (TextView) findViewById(R.id.tv_shopPincode);
        tv_shopState = (TextView) findViewById(R.id.tv_shopState);
        tv_shopLandmark = (TextView) findViewById(R.id.tv_shopLandmark);
        tv_ownerContactNumber = (TextView) findViewById(R.id.tv_ownerContactNumber);
        tv_supportContactNumber = (TextView) findViewById(R.id.tv_supportContactNumber);
        tv_orderProcessingNumber = (TextView) findViewById(R.id.tv_orderProcessingNumber);
        tv_emailId = (TextView) findViewById(R.id.tv_emailId);
        tv_minimumAcceptedOrder = (TextView) findViewById(R.id.tv_minimumAcceptedOrder);
        tv_deliveryCharge = (TextView) findViewById(R.id.tv_deliveryCharge);
        tv_ownerIdType = (TextView) findViewById(R.id.tv_ownerIdType);
        tv_ownerIdNumber = (TextView) findViewById(R.id.tv_ownerIdNumber);
        tv_associatedTo = (TextView) findViewById(R.id.tv_associatedTo);
        tv_shopRating = (TextView) findViewById(R.id.tv_shopRating);
        spinner_profile_delivery_type = (Spinner) findViewById(R.id.spinner_profile_delivery_type);
        spinner_profile_payment_method = (Spinner) findViewById(R.id.spinner_profile_payment_method);
    }


    private void setUIListener()
    {
        tv_ownerContactNumber.setOnClickListener(this);
        tv_supportContactNumber.setOnClickListener(this);
        tv_orderProcessingNumber.setOnClickListener(this);
        tv_emailId.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_ownerContactNumber:
                callPhone(tv_ownerContactNumber.getText().toString());
                break;
            case R.id.tv_supportContactNumber:
                callPhone(tv_supportContactNumber.getText().toString());
                break;
            case R.id.tv_orderProcessingNumber:
                callPhone(tv_orderProcessingNumber.getText().toString());
                break;
            case R.id.tv_emailId:
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/html");
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{tv_emailId.getText().toString()});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Message From "+this.getString(R.string.app_name));
                this.startActivity(Intent.createChooser(intent, "Send Email"));
                break;
        }
    }


    public void setShopProfileData(ShopProfile data)
    {
        tv_shoId.setText(data.getShopID());
        tv_shopName.setText(data.getShopName());
        tv_registartionStatus.setText(data.getShopRegistrationStatus());
        tv_shopOwnerName.setText(data.getShopOwnerName());
        tv_shopAddress.setText(data.getShopAddress());
        tv_shopAreaSector.setText(data.getShopAddressAreaSector());
        tv_shopCity.setText(data.getShopAddressCity());
        tv_shopPincode.setText(data.getShopAddressPincode());
        tv_shopState.setText(data.getShopAddressState());
        tv_shopLandmark.setText(data.getShopAddressLandmark());
        tv_ownerContactNumber.setText(data.getShopOwnerContactNumber());
        tv_supportContactNumber.setText(data.getShopSupportContactNumber());
        tv_orderProcessingNumber.setText(data.getShopOrderProcessingContactNumber());
        tv_emailId.setText(data.getShopEmailId());
        tv_minimumAcceptedOrder.setText(data.getShopMinimumAcceptedOrder());
        tv_deliveryCharge.setText(data.getShopDeliveryCharges());
        tv_ownerIdType.setText(data.getShopOwnerIDType());
        tv_ownerIdNumber.setText(data.getShopOwnerIDNumber());
        tv_associatedTo.setText(data.getShopCreatedFor());
        tv_shopRating.setText(data.getShopRating());
    }



    private void setSpinnerDeliveryMethod(ShopProfileResponse result) {
        List<DeliveryMethod> deliveryType1 = result.getDeliveryMethods();
        String shopDeliveryMethod = result.getShopProfile().get(0).getShopDeliveryTypeSupported();
        final List<String> categories = new ArrayList<>();
        int i = 0;
        for (DeliveryMethod method : deliveryType1) {
            categories.add(method.getDeliveryMethod());
            if(shopDeliveryMethod.equals(method.getDeliveryMethod()))
                i++;
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_profile_delivery_type.setAdapter(dataAdapter);
        spinner_profile_delivery_type.setSelection(i);
        spinner_profile_delivery_type.setEnabled(false);
        spinner_profile_delivery_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                deliveryType = categories.get(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setSpinnerPaymentMethod(ShopProfileResponse result) {
        List<PaymentMethod> paymentMethods = result.getPaymentMethods();
        String shopPaymentMethod = result.getShopProfile().get(0).getShopPaymentMethodSupported();
        int i = 0;
        final List<String> categories = new ArrayList<>();
        for (PaymentMethod method : paymentMethods) {
            categories.add(method.getPaymentMethod());
            if(shopPaymentMethod.equals(method.getPaymentMethod()))
                i++;
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_profile_payment_method.setAdapter(dataAdapter);
        spinner_profile_payment_method.setSelection(i);
        spinner_profile_payment_method.setEnabled(false);
        spinner_profile_payment_method.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                paymentMethod = categories.get(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void fetchShopKeeperProfileApi() {

        showProgressBar(findViewById(R.id.tv_update_profile));
        AppHttpRequest request = AppRequestBuilder.fetchShopKeeperProfileApi(shopId, new AppResponseListener<ShopProfileResponse>(ShopProfileResponse.class, this) {
            @Override
            public void onSuccess(ShopProfileResponse result) {
                hideProgressBar(findViewById(R.id.tv_update_profile));
                setShopProfileData(result.getShopProfile().get(0));
                setSpinnerDeliveryMethod(result);
                setSpinnerPaymentMethod(result);
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar(findViewById(R.id.tv_update_profile));
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

}
