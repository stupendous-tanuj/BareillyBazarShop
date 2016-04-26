package com.app.bareillybazarshop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.api.output.LoginResponse;
import com.app.bareillybazarshop.constant.AppConstant;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppResponseListener;
import com.app.bareillybazarshop.network.AppRestClient;
import com.app.bareillybazarshop.utils.DialogUtils;
import com.app.bareillybazarshop.utils.PreferenceKeeper;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity {

    private EditText et_login_password;
    private EditText et_login_shopid;
    private Spinner spinner_userType;
    String userType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setHeader(getString(R.string.header_user_login), "");
        setUI();
        setUIListener();
    }

    private void setUI() {
        et_login_shopid = (EditText) findViewById(R.id.et_login_shopid);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        spinner_userType = (Spinner) findViewById(R.id.spinner_userType);

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

    private void setUIListener() {
        findViewById(R.id.tv_login_login).setOnClickListener(this);
        findViewById(R.id.tv_forgetPassword).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_login:
                loginAPI();
                break;
            case R.id.tv_forgetPassword:
                launchActivity(ForgetPasswordActivity.class);
                break;
        }
    }

    private void loginAPI() {
        final String userId = et_login_shopid.getText().toString().trim();
        final String password = et_login_password.getText().toString().trim();
        if (!DialogUtils.isLoginVerification(this, userId, password)) {
            return;
        }

        showProgressBar(findViewById(R.id.tv_login_login));
        AppHttpRequest request = AppRequestBuilder.loginAPI(userId, userType, password, new AppResponseListener<LoginResponse>(LoginResponse.class, this) {
            @Override
            public void onSuccess(LoginResponse result) {

                if (result.getFirstLogin().equals("1")) {
                    launchActivity(ChangePasswordActivity.class);
                    PreferenceKeeper.getInstance().setIsLogin(true);
                    PreferenceKeeper.getInstance().setUserId(userId);
                    PreferenceKeeper.getInstance().setUserType(userType);
                    PreferenceKeeper.getInstance().setLocale(AppConstant.DEFAULT_LOCALE);

                } else if (result.getFirstLogin().equals("0")) {
                    launchActivity(HomeActivity.class);
                    PreferenceKeeper.getInstance().setIsLogin(true);
                    PreferenceKeeper.getInstance().setUserId(userId);
                    PreferenceKeeper.getInstance().setUserType(userType);
                    PreferenceKeeper.getInstance().setLocale(AppConstant.DEFAULT_LOCALE);
                }

                hideProgressBar(findViewById(R.id.tv_login_login));
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar(findViewById(R.id.tv_login_login));
            }
        });

        AppRestClient.getClient().sendRequest(this, request, TAG);
    }
}
