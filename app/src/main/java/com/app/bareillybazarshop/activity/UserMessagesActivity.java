package com.app.bareillybazarshop.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.adapter.AssociatedProductAdapter;
import com.app.bareillybazarshop.adapter.UserMessagesAdapter;
import com.app.bareillybazarshop.api.output.OrderDetail;
import com.app.bareillybazarshop.api.output.UserMessageResponse;
import com.app.bareillybazarshop.api.output.UserMessages;
import com.app.bareillybazarshop.api.output.AssociatedShopId;
import com.app.bareillybazarshop.api.output.AssociatedShopIdResponse;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.api.output.Product;
import com.app.bareillybazarshop.constant.AppConstant;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppResponseListener;
import com.app.bareillybazarshop.network.AppRestClient;
import com.app.bareillybazarshop.utils.DialogUtils;
import com.app.bareillybazarshop.utils.PreferenceKeeper;

import java.util.ArrayList;
import java.util.List;

public class UserMessagesActivity extends BaseActivity {

    private ListView lv_customer_messages;
    String userType = "";
    private Spinner spinner_userType;
    LinearLayout ll_userType;
    String USER_TYPE = "";
    private TextView no_data_available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_messages);
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        setHeader(getString(R.string.header_User_Messages), "");
        setUI();
        setUserTypeSpinner();
    }

    // update product
    // AssociateProduct api for addition and update 7
    @Override
    protected void onResume() {
        super.onResume();
        fetchUserMessagesAPI();
    }


    private void setUI() {
        lv_customer_messages = (ListView) findViewById(R.id.lv_customer_messages);
        ll_userType = (LinearLayout) findViewById(R.id.ll_userType);
        spinner_userType = (Spinner) findViewById(R.id.spinner_userType);
        no_data_available = (TextView) findViewById(R.id.no_data_available);
    }


    private void setVisibleUI(List<UserMessages> userMessages) {
        if (userMessages == null || userMessages.size() == 0) {
            lv_customer_messages.setVisibility(View.GONE);
            no_data_available.setVisibility(View.VISIBLE);
            no_data_available.setText(getString(R.string.msg_No_User_Message_Found));
        } else {
            lv_customer_messages.setVisibility(View.VISIBLE);
            no_data_available.setVisibility(View.GONE);
            setUserMessageAdapter(userMessages);
        }
    }


    private void fetchUserMessagesAPI() {

        if (!DialogUtils.isSpinnerDefaultValue(this, userType, getString(R.string.label_User_Type)) || (userType.equals(""))) {
            return;
        }

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.fetchUserMessagesAPI(userType, new AppResponseListener<UserMessageResponse>(UserMessageResponse.class, this) {
            @Override
            public void onSuccess(UserMessageResponse result) {
                hideProgressBar();
                setVisibleUI(result.getUserMessages());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setUserTypeSpinner()
    {
        final List<String> userTypeList = new ArrayList<>();
        userTypeList.add(0, getString(R.string.please_select));
        userTypeList.add(1, AppConstant.UserType.CUSTOMER_TYPE);
        userTypeList.add(2, AppConstant.UserType.SHOP_TYPE);
        userTypeList.add(3, AppConstant.UserType.DELIVERY_PERSON_TYPE);

        ArrayAdapter<String> deliveryDateDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, userTypeList);
        deliveryDateDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_userType.setAdapter(deliveryDateDataAdapter);
        spinner_userType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                userType = spinner_userType.getSelectedItem().toString();
                if (!userType.equals(getString(R.string.please_select)))
                fetchUserMessagesAPI();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void setUserMessageAdapter(List<UserMessages> productList) {
        UserMessagesAdapter deliveryAddressAdapter = new UserMessagesAdapter(this, productList);
        lv_customer_messages.setAdapter(deliveryAddressAdapter);
    }

}
