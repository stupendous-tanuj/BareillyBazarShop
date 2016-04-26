package com.app.bareillybazarshop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.api.output.CommonResponse;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppResponseListener;
import com.app.bareillybazarshop.network.AppRestClient;
import com.app.bareillybazarshop.utils.DialogUtils;
import com.app.bareillybazarshop.utils.PreferenceKeeper;


public class ContactUsActivity extends BaseActivity {

    private TextView tv_shop_id;
    private TextView tv_contact_us_name;
    private EditText et_contact_us_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        setHeader(getString(R.string.header_contact_us), "");
        setUI();
        setUIListener();
    }

    private void setUIListener() {
        findViewById(R.id.tv_contact_us_send).setOnClickListener(this);
    }

    private void setUI() {
        tv_shop_id = (TextView) findViewById(R.id.tv_shop_id);
        et_contact_us_message = (EditText) findViewById(R.id.et_contact_us_message);
        tv_shop_id.setText(PreferenceKeeper.getInstance().getUserId());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_contact_us_send:
                contactUsSendMessageApi();
                break;
        }
    }

    private void contactUsSendMessageApi() {
        String message = et_contact_us_message.getText().toString().trim();

        if (!DialogUtils.checkForBlank(this, getString(R.string.label_Message), message)) {
            return;
        }

        showProgressBar(findViewById(R.id.tv_contact_us_send));
        AppHttpRequest request = AppRequestBuilder.contactUsSendMessageApi(message, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar(findViewById(R.id.tv_contact_us_send));
                showToast(result.getSuccessMessage());
                // ContactUsActivity.this.finish();
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar(findViewById(R.id.tv_contact_us_send));
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }
}
