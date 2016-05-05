package com.app.bareillybazarshop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.api.output.CommonResponse;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.api.output.SupportedIdTypeResponse;
import com.app.bareillybazarshop.constant.AppConstant;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppResponseListener;
import com.app.bareillybazarshop.network.AppRestClient;
import com.app.bareillybazarshop.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

public class ForgetPasswordActivity extends BaseActivity {

    //EditText et_name = null;
    EditText et_emailId = null;
    EditText et_mobileNumber = null;
    //EditText et_idNumber = null;
    TextView tv_resetPassword = null;
    private EditText et_userId;
    private Spinner spinner_userType;
    //private Spinner spinner_idType;
    String idType = "";
    String userType  = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        setHeader(getString(R.string.header_forget_password), "");
        setUI();
        setUIListener();
        setUserTypeSpinner();
        //supportedIdTypeSpinner();
    }

    public void setUI()
    {
      //  et_name = (EditText) findViewById(R.id.et_oldPassword);
        et_emailId = (EditText) findViewById(R.id.et_emailId);
        et_mobileNumber = (EditText) findViewById(R.id.et_mobileNumber);
      //  et_idNumber = (EditText) findViewById(R.id.et_idNumber);
        tv_resetPassword = (TextView) findViewById(R.id.tv_resetPassword);
        et_userId = (EditText) findViewById(R.id.et_userId);
        spinner_userType = (Spinner) findViewById(R.id.spinner_userType);
        //spinner_idType = (Spinner) findViewById(R.id.spinner_idType);
    }

    public void setUIListener()
    {
        findViewById(R.id.tv_resetPassword).setOnClickListener(this);
    }

    private void setUserTypeSpinner() {
        final List<String> userTypeList = new ArrayList<>();
        userTypeList.add(0, AppConstant.UserType.SELLER_HUB_TYPE);
        userTypeList.add(1, AppConstant.UserType.SHOP_TYPE);
        userTypeList.add(2, AppConstant.UserType.DELIVERY_PERSON_TYPE);

        ArrayAdapter<String> deliveryDateDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, userTypeList);
        deliveryDateDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_userType.setAdapter(deliveryDateDataAdapter);
        spinner_userType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                userType = spinner_userType.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_resetPassword:
                resetPasswordAPI();
                break;
        }
    }

    private void supportedIdTypeSpinner() {
        AppHttpRequest request = AppRequestBuilder.supportedPersonIdTypeApi(new AppResponseListener<SupportedIdTypeResponse>(SupportedIdTypeResponse.class, this) {
            @Override
            public void onSuccess(SupportedIdTypeResponse result) {
                //setSpinnerPersonIdType(result.getSupportedIDType());
            }

            @Override
            public void onError(ErrorObject error) {

            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

/*
    private void setSpinnerPersonIdType(List<SupportedIDType> orderUnits) {
        final List<String> categories = new ArrayList<>();
        for (SupportedIDType method : orderUnits) {
            categories.add(method.getIdType());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_idType.setAdapter(dataAdapter);
        spinner_idType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                idType = categories.get(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
*/

    private void resetPasswordAPI() {

        //String name = et_name.getText().toString().trim();
        String emailId = et_emailId.getText().toString().trim();
        String mobileNumber = et_mobileNumber.getText().toString().trim();
 //       String idNumber = et_idNumber.getText().toString().trim();
        String userId =  et_userId .getText().toString().trim();


        if (!DialogUtils.isResetPasswordVerification(this, emailId, mobileNumber, userId)) {
            return;
        }

        showProgressBar(findViewById(R.id.tv_resetPassword));
        AppHttpRequest request = AppRequestBuilder.resetPasswordAPI(emailId, mobileNumber,userId,userType, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar();
                showToast(result.getSuccessMessage());
                finish();
                launchActivity(LoginActivity.class);
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });

        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

}
