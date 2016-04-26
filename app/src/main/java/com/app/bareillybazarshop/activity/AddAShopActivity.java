package com.app.bareillybazarshop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.bareillybazarshop.api.output.DeliveryMethod;
import com.app.bareillybazarshop.api.output.PaymentMethod;
import com.app.bareillybazarshop.api.output.ShopProfile;
import com.app.bareillybazarshop.api.output.ShopReferenceDataResponse;
import com.app.bareillybazarshop.api.output.SupportedIDType;
import com.app.bareillybazarshop.constant.AppConstant;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppResponseListener;
import com.app.bareillybazarshop.network.AppRestClient;
import com.app.bareillybazarshop.utils.DialogUtils;
import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.api.output.CommonResponse;
import com.app.bareillybazarshop.api.output.ErrorObject;
import java.util.ArrayList;
import java.util.List;

public class AddAShopActivity extends BaseActivity {

    EditText et_shopName = null;
    EditText et_shopOwnerName = null;
    EditText et_shopAddress = null;
    EditText et_shopAreaSector = null;
    EditText et_shopPincode = null;
    Spinner spinner_city = null;
    Spinner spinner_state = null;
    EditText et_shopLandmark = null;
    EditText et_ownerContactNumber = null;
    EditText et_supportContactNumber = null;
    EditText et_orderContactNumber = null;
    EditText et_emailId = null;
    EditText et_minimumAcceptedOrder = null;
    EditText et_deliveryCharge = null;
    EditText et_ownerIdNumber = null;
    EditText et_shopDescription = null;
    Spinner spinner_ownerIdType = null;
    Spinner spinner_deliveryType = null;
    Spinner spinner_paymentMethod = null;
    TextView tv_addShop = null;
    String ownerIdType = "";
    String deliveryType = "";
    String paymentMethod = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ashop);
        setUI();
        setHeader(getString(R.string.header_add_a_shop), "");
        setUIListener();
        shopReferenceDataAPI();
        setStateSpinner();
    }
    private void setUI() {
        et_shopName = (EditText) findViewById(R.id.et_shopName);
        et_shopOwnerName = (EditText) findViewById(R.id.et_shopOwnerName);
        et_shopAddress = (EditText) findViewById(R.id.et_shopAddress);
        et_shopAreaSector = (EditText) findViewById(R.id.et_areaSector);
        et_shopPincode = (EditText) findViewById(R.id.et_pincode);
        et_shopLandmark = (EditText) findViewById(R.id.et_landmark);
        et_ownerContactNumber = (EditText) findViewById(R.id.et_ownerContactNumber);
        et_supportContactNumber = (EditText) findViewById(R.id.et_supportContactNumber);
        et_orderContactNumber = (EditText) findViewById(R.id.et_orderContactNumber);
        et_emailId = (EditText) findViewById(R.id.et_emailId);
        et_minimumAcceptedOrder = (EditText) findViewById(R.id.et_minAcceptedOrder);
        et_deliveryCharge = (EditText) findViewById(R.id.et_deliveryCharges);
        et_ownerIdNumber = (EditText) findViewById(R.id.et_ownerIdNumber);
        et_shopDescription = (EditText) findViewById(R.id.et_shopDescription);
        spinner_ownerIdType = (Spinner) findViewById(R.id.spinner_ownerIdType);
        spinner_deliveryType = (Spinner) findViewById(R.id.spinner_deliveryType);
        spinner_paymentMethod = (Spinner) findViewById(R.id.spinner_paymentMethod);
        spinner_city = (Spinner) findViewById(R.id.spinner_city);
        spinner_state = (Spinner) findViewById(R.id.spinner_state);
        tv_addShop = (TextView) findViewById(R.id.tv_addShop);
    }

    private void setUIListener() {
        tv_addShop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_addShop:
                addAShopAPI();
                break;
        }
    }

    private void setStateSpinner()
    {
        final List<String> stateList = AppConstant.fetchState();
        
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stateList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_state.setAdapter(dataAdapter);
        spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                setCitySpinner(spinner_state.getSelectedItem().toString());
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void setCitySpinner(String state)
    {
        final List<String> cityList = AppConstant.fetchCities(state);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cityList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_city.setAdapter(dataAdapter);
        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private ShopProfile setData()
    {
        ShopProfile shop = new ShopProfile();
        shop.setShopName(et_shopName.getText().toString());
        shop.setShopDescription(et_shopDescription.getText().toString());
        shop.setShopRegistrationStatus(AppConstant.STATUS.STATUS_REGISTERED);
        shop.setShopOwnerName(et_shopOwnerName.getText().toString());
        shop.setShopAddress(et_shopAddress.getText().toString());
        shop.setShopAddressAreaSector(et_shopAreaSector.getText().toString());
        shop.setShopAddressCity(spinner_city.getSelectedItem().toString());
        shop.setShopAddressPincode(et_shopPincode.getText().toString());
        shop.setShopAddressState(spinner_state.getSelectedItem().toString());
        shop.setShopAddressCountry(AppConstant.COUNTRY);
        shop.setShopAddressLandmark(et_shopLandmark.getText().toString());
        shop.setShopOwnerContactNumber(et_ownerContactNumber.getText().toString());
        shop.setShopSupportContactNumber(et_supportContactNumber.getText().toString());
        shop.setShopOrderProcessingContactNumber(et_orderContactNumber.getText().toString());
        shop.setShopEmailId(et_emailId.getText().toString());
        shop.setShopMinimumAcceptedOrder(et_minimumAcceptedOrder.getText().toString());
        shop.setShopDeliveryCharges(et_deliveryCharge.getText().toString());
        shop.setShopOwnerIDType(ownerIdType);
        shop.setShopOwnerIDNumber(et_ownerIdNumber.getText().toString());
        shop.setShopDeliveryTypeSupported(deliveryType);
        shop.setShopPaymentMethodSupported(paymentMethod);
        return shop;
    }


    private boolean dataValidation(ShopProfile shop)
    {

        if (!DialogUtils.checkForBlank(this, getString(R.string.label_Shop_Name), shop.getShopName())) {
            return false;
        }

        if (!DialogUtils.checkForBlank(this, getString(R.string.label_Shop_Description), shop.getShopDescription())) {
            return false;
        }

        if (!DialogUtils.checkForBlank(this, getString(R.string.label_Shop_Owner_Name), shop.getShopOwnerName())) {
            return false;
        }

        if (!DialogUtils.checkForBlank(this, getString(R.string.label_Shop_Address), shop.getShopAddress())) {
            return false;
        }

        if (!DialogUtils.checkForBlank(this, getString(R.string.label_Area_Sector), shop.getShopAddressAreaSector())) {
            return false;
        }

        if (!DialogUtils.checkForBlank(this, getString(R.string.label_City), shop.getShopAddressCity())) {
            return false;
        }

        if (!DialogUtils.checkForBlank(this, getString(R.string.label_Pincode), shop.getShopAddressPincode())) {
            return false;
        }

        if (!DialogUtils.integerValidator(this, getString(R.string.label_Pincode), shop.getShopAddressPincode())) {
            return false;
        }

        if (!DialogUtils.lengthValidator(this, getString(R.string.label_Pincode), shop.getShopAddressPincode(),6 )) {
            return false;
        }

        if (!DialogUtils.checkForBlank(this, getString(R.string.label_State), shop.getShopAddressState())) {
            return false;
        }

        if (!DialogUtils.checkForBlank(this, getString(R.string.label_Landmark), shop.getShopAddressLandmark())) {
            return false;
        }

        if (!DialogUtils.checkForBlank(this, getString(R.string.label_Owner_Contact_Number), shop.getShopOwnerContactNumber())) {
            return false;
        }

        if (!DialogUtils.mobileNumberValidator(this, getString(R.string.label_Owner_Contact_Number), shop.getShopOwnerContactNumber())) {
            return false;
        }

        if (!DialogUtils.checkForBlank(this, getString(R.string.label_Support_Contact_Number), shop.getShopSupportContactNumber())) {
            return false;
        }

        if (!DialogUtils.mobileNumberValidator(this, getString(R.string.label_Support_Contact_Number), shop.getShopSupportContactNumber())) {
            return false;
        }

        if (!DialogUtils.checkForBlank(this, getString(R.string.label_Order_Processing_Contact_Number), shop.getShopOrderProcessingContactNumber())) {
            return false;
        }

        if (!DialogUtils.mobileNumberValidator(this, getString(R.string.label_Order_Processing_Contact_Number), shop.getShopOrderProcessingContactNumber())) {
            return false;
        }

        if (!DialogUtils.checkForBlank(this, getString(R.string.label_Email_Id), shop.getShopEmailId())) {
            return false;
        }

        if (!DialogUtils.emailValidator(this, getString(R.string.label_Email_Id), shop.getShopEmailId())) {
            return false;
        }

        if (!DialogUtils.checkForBlank(this, getString(R.string.label_Minimum_Accepted_Order), shop.getShopMinimumAcceptedOrder())) {
            return false;
        }

        if (!DialogUtils.integerValidator(this, getString(R.string.label_Minimum_Accepted_Order), shop.getShopMinimumAcceptedOrder())) {
            return false;
        }

        if (!DialogUtils.checkForBlank(this, getString(R.string.label_Delivery_Charges), shop.getShopDeliveryCharges())) {
            return false;
        }

        if (!DialogUtils.integerValidator(this, getString(R.string.label_Delivery_Charges), shop.getShopDeliveryCharges())) {
            return false;
        }

        if (!DialogUtils.checkForBlank(this, getString(R.string.label_Owner_Id_Number), shop.getShopOwnerIDNumber())) {
            return false;
        }

        return true;

    }


    private void addAShopAPI() {

        ShopProfile shopProfile =  setData();

        if(!dataValidation(shopProfile)) {
            return;
        }
        else {

            showProgressBar(findViewById(R.id.tv_update_profile));
            AppHttpRequest request = AppRequestBuilder.addAShopAPI(shopProfile, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
                @Override
                public void onSuccess(CommonResponse result) {
                    hideProgressBar(findViewById(R.id.tv_addShop));
                    showToast(result.getSuccessMessage());
                    finish();
                }

                @Override
                public void onError(ErrorObject error) {
                    hideProgressBar(findViewById(R.id.tv_addShop));
                }
            });
            AppRestClient.getClient().sendRequest(this, request, TAG);
        }
    }

    private void setSpinnerDeliveryMethod(List<DeliveryMethod> deliveryType1) {
        final List<String> categories = new ArrayList<>();
        for (DeliveryMethod method : deliveryType1) {
            categories.add(method.getDeliveryMethod());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_deliveryType.setAdapter(dataAdapter);
        spinner_deliveryType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                deliveryType = categories.get(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void setSpinnerPaymentMethod(List<PaymentMethod> paymentMethods) {
        final List<String> categories = new ArrayList<>();
        for (PaymentMethod method : paymentMethods) {
            categories.add(method.getPaymentMethod());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_paymentMethod.setAdapter(dataAdapter);
        spinner_paymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                paymentMethod = categories.get(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void shopReferenceDataAPI() {
        AppHttpRequest request = AppRequestBuilder.shopReferenceDataAPI(new AppResponseListener<ShopReferenceDataResponse>(ShopReferenceDataResponse.class, this) {
            @Override
            public void onSuccess(ShopReferenceDataResponse result) {
                setSpinnerPaymentMethod(result.getPaymentMethods());
                setSpinnerDeliveryMethod(result.getDeliveryMethods());
                setSpinnerSupportedIDType(result.getSupportedIDType());
            }

            @Override
            public void onError(ErrorObject error) {

            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }


    private void setSpinnerSupportedIDType(List<SupportedIDType> supportedIDTypes) {
        final List<String> categories = new ArrayList<>();
        for (SupportedIDType method : supportedIDTypes) {
            categories.add(method.getIdType());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ownerIdType.setAdapter(dataAdapter);
        spinner_ownerIdType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                ownerIdType = categories.get(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


}
