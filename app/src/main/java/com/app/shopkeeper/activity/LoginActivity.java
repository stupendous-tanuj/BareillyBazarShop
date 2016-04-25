package com.app.shopkeeper.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.app.shopkeeper.R;
import com.app.shopkeeper.api.output.ErrorObject;
import com.app.shopkeeper.api.output.LoginResponse;
import com.app.shopkeeper.network.AppHttpRequest;
import com.app.shopkeeper.network.AppRequestBuilder;
import com.app.shopkeeper.network.AppResponseListener;
import com.app.shopkeeper.network.AppRestClient;
import com.app.shopkeeper.utils.DialogUtils;
import com.app.shopkeeper.utils.PreferenceKeeper;

public class LoginActivity extends BaseActivity {

    private EditText et_login_password;
    private EditText et_login_shopid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setHeader("Login", "");
        setUI();
        setUIListener();
    }

    private void setUI() {
        et_login_shopid = (EditText) findViewById(R.id.et_login_shopid);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
    }

    private void setUIListener() {
        findViewById(R.id.tv_login_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_login:
                loginAPI();
                break;
        }
    }


    private void loginAPI() {
        final String shopID = et_login_shopid.getText().toString().trim();
        final String password = et_login_password.getText().toString().trim();
        if (!DialogUtils.isLoginVerification(this, shopID, password)) {
            return;
        }

        showProgressBar(findViewById(R.id.tv_login_login));
        AppHttpRequest request = AppRequestBuilder.loginAPI(shopID, password, new AppResponseListener<LoginResponse>(LoginResponse.class, this) {
            @Override
            public void onSuccess(LoginResponse result) {
                PreferenceKeeper.getInstance().setIsLogin(true);
                launchActivity(HomeActivity.class);
                hideProgressBar(findViewById(R.id.tv_login_login));
            }

            @Override
            public void onError(ErrorObject error) {
                PreferenceKeeper.getInstance().setIsLogin(true);
                launchActivity(HomeActivity.class);
                hideProgressBar(findViewById(R.id.tv_login_login));
            }
        });

        AppRestClient.getClient().sendRequest(this, request, TAG);
    }
}
