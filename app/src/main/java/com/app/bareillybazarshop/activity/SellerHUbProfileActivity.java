package com.app.bareillybazarshop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.api.output.CommonResponse;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.api.output.SellerHubProfile;
import com.app.bareillybazarshop.api.output.SellerHubProfileResponse;
import com.app.bareillybazarshop.constant.AppConstant;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppResponseListener;
import com.app.bareillybazarshop.network.AppRestClient;
import com.app.bareillybazarshop.utils.DialogUtils;
import com.app.bareillybazarshop.utils.PreferenceKeeper;

public class SellerHUbProfileActivity extends BaseActivity {


    private TextView tv_seller_hub_id;
    private TextView tv_seller_hub_firm_n;
    private TextView tv_seller_hub_owner_n;
    private TextView tv_seller_hub_mobile;
    private TextView tv_seller_hub_support_cont;
    private TextView tv_seller_hub_ddress;
    private TextView tv_orderIdPrefix;
    private TextView tv_registrationStatus;
    private TextView tv_applicationStatus;
    private TextView tv_orderProfitPercentage;
    private TextView tv_ownerIdType;
    private TextView tv_ownerIdNumber;
    private TextView tv_update_profile;
    private TextView tv_emailId;
    private EditText et_emailId;
    private EditText et_mobileNumber;
    private EditText et_supportContactNumber;
    private LinearLayout ll_orderIdPrefix;
    private LinearLayout ll_registrationStatus;
    private LinearLayout ll_applicationStatus;
    private LinearLayout ll_orderProfitPercentage;
    private LinearLayout ll_ownerIdType;
    private LinearLayout ll_ownerIdNumber;
    private ScrollView scrollview_seller_hub;
    private TextView tv_seller_hub_business_type;
    String USER_TYPE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_hub);
        setHeader(getString(R.string.header_seller_hub_profile), "");
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
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
        tv_update_profile = (TextView) findViewById(R.id.tv_update_profile);
        tv_emailId = (TextView) findViewById(R.id.tv_emailId);
        tv_orderIdPrefix = (TextView) findViewById(R.id.tv_orderIdPrefix);
        tv_registrationStatus = (TextView) findViewById(R.id.tv_registrationStatus);
        tv_applicationStatus = (TextView) findViewById(R.id.tv_applicationStatus);
        tv_orderProfitPercentage = (TextView) findViewById(R.id.tv_orderProfitPercentage);
        tv_ownerIdType = (TextView) findViewById(R.id.tv_ownerIdType);
        tv_ownerIdNumber = (TextView) findViewById(R.id.tv_ownerIdNumber);
        ll_orderIdPrefix = (LinearLayout) findViewById(R.id.ll_orderIdPrefix);
        ll_registrationStatus = (LinearLayout) findViewById(R.id.ll_registrationStatus);
        ll_applicationStatus = (LinearLayout) findViewById(R.id.ll_applicationStatus);
        ll_orderProfitPercentage = (LinearLayout) findViewById(R.id.ll_orderProfitPercentage);
        ll_ownerIdType = (LinearLayout) findViewById(R.id.ll_ownerIdType);
        ll_ownerIdNumber = (LinearLayout) findViewById(R.id.ll_ownerIdNumber);
        et_emailId = (EditText) findViewById(R.id.et_emailId);
        et_mobileNumber = (EditText) findViewById(R.id.et_mobileNumber);
        et_supportContactNumber = (EditText) findViewById(R.id.et_supportContactNumber);
        if(USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE) || USER_TYPE.equals(AppConstant.UserType.DELIVERY_PERSON_TYPE)) {
            tv_update_profile.setText("");
            ll_orderIdPrefix.setVisibility(View.GONE);
            ll_registrationStatus.setVisibility(View.GONE);
            ll_applicationStatus.setVisibility(View.GONE);
            ll_orderProfitPercentage.setVisibility(View.GONE);
            ll_ownerIdType.setVisibility(View.GONE);
            ll_ownerIdNumber.setVisibility(View.GONE);
            et_emailId.setVisibility(View.GONE);
            et_mobileNumber.setVisibility(View.GONE);
            et_supportContactNumber.setVisibility(View.GONE);


        }
        else if(USER_TYPE.equals(AppConstant.UserType.SELLER_HUB_TYPE)) {
            ll_orderIdPrefix.setVisibility(View.VISIBLE);
            ll_registrationStatus.setVisibility(View.VISIBLE);
            ll_applicationStatus.setVisibility(View.VISIBLE);
            ll_orderProfitPercentage.setVisibility(View.VISIBLE);
            ll_ownerIdType.setVisibility(View.VISIBLE);
            ll_ownerIdNumber.setVisibility(View.VISIBLE);
            et_emailId.setVisibility(View.VISIBLE);
            et_mobileNumber.setVisibility(View.VISIBLE);
            et_supportContactNumber.setVisibility(View.VISIBLE);
            tv_seller_hub_mobile.setVisibility(View.GONE);
            tv_seller_hub_support_cont.setVisibility(View.GONE);
            tv_emailId.setVisibility(View.GONE);
            tv_update_profile.setVisibility(View.VISIBLE);
            setUIListener();
        }
    }

    private void setUIListener() {
        tv_update_profile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_update_profile:
                updateSellerHubProfileAPI();
                break;
        }
    }

    String mobileNumber = "";
    String supportNumber = "";
    String emailId = "";

    private boolean dataValidation() {

        if (!DialogUtils.checkForBlank(this, getString(R.string.label_Mobile_Number), mobileNumber)) {
            return false;
        }

        if (!DialogUtils.mobileNumberValidator(this, getString(R.string.label_Mobile_Number), mobileNumber)) {
            return false;
        }

        if (!DialogUtils.checkForBlank(this, getString(R.string.label_Support_Contact_Number), supportNumber)) {
            return false;
        }

        if (!DialogUtils.mobileNumberValidator(this, getString(R.string.label_Support_Contact_Number), supportNumber)) {
            return false;
        }

        if (!DialogUtils.checkForBlank(this, getString(R.string.label_Email_Id), emailId)) {
            return false;
        }

        if (!DialogUtils.emailValidator(this, getString(R.string.label_Email_Id), emailId)) {
            return false;
        }

        return true;
    }

    private void updateSellerHubProfileAPI() {

        mobileNumber = et_mobileNumber.getText().toString().trim();
        supportNumber = et_supportContactNumber.getText().toString().trim();
        emailId = et_emailId.getText().toString().trim();

        if(!dataValidation()) {
            return;
        }

        showProgressBar(findViewById(R.id.tv_update_profile));
        AppHttpRequest request = AppRequestBuilder.updateSellerHubProfileAPI(mobileNumber, supportNumber,
                emailId, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
                    @Override
                    public void onSuccess(CommonResponse result) {
                        hideProgressBar(findViewById(R.id.tv_update_profile));
                        showToast(result.getSuccessMessage());
                        finish();
                    }

                    @Override
                    public void onError(ErrorObject error) {
                        hideProgressBar(findViewById(R.id.tv_update_profile));
                    }
                });
        AppRestClient.getClient().sendRequest(this, request, TAG);
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
        tv_emailId.setText(profile.getSellerHubEmailID());
        et_emailId.setText(profile.getSellerHubEmailID());
        et_mobileNumber.setText(profile.getSellerHubMobileNumber());
        et_supportContactNumber.setText(profile.getSellerHubSupportContactNumber());
        tv_orderIdPrefix.setText(profile.getSellerHubOrderIdPrefix());
        tv_registrationStatus.setText(profile.getSellerHubRegistrationStatus());
        tv_applicationStatus.setText(profile.getApplicationStatus());
        tv_orderProfitPercentage.setText(profile.getOrderProfitPercentage());
        tv_ownerIdType.setText(profile.getSellerHubOwnerIDType());
        tv_ownerIdNumber.setText(profile.getSellerHubOwnerIDNumber());

    }
}
