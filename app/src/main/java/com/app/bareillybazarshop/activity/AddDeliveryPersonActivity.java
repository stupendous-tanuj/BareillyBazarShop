package com.app.bareillybazarshop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.api.output.CommonResponse;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.api.output.SupportedIDType;
import com.app.bareillybazarshop.api.output.SupportedIdTypeResponse;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppResponseListener;
import com.app.bareillybazarshop.network.AppRestClient;
import com.app.bareillybazarshop.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

public class AddDeliveryPersonActivity extends BaseActivity {


    private EditText ed_add_delievry_person_name;
    private EditText ed_add_delievry_person_mobile_number;
    private EditText ed_add_delievry_person_address;
    private EditText ed_add_delievry_person_id_number;
    private EditText et_emailId;
    private Spinner spinner_add_delievry_person_id_type;
    private String personIdType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delivery_person);
        setHeader(getString(R.string.header_add_a_delivery_person), "");
        setUI();
        supportedPersonIdTypeApi();
    }

    private void setUI() {
        ed_add_delievry_person_name = (EditText) findViewById(R.id.ed_add_delievry_person_name);
        ed_add_delievry_person_mobile_number = (EditText) findViewById(R.id.ed_add_delievry_person_mobile_number);
        ed_add_delievry_person_address = (EditText) findViewById(R.id.ed_add_delievry_person_address);
        ed_add_delievry_person_id_number = (EditText) findViewById(R.id.ed_add_delievry_person_id_number);
        et_emailId = (EditText) findViewById(R.id.et_emailId);
        spinner_add_delievry_person_id_type = (Spinner) findViewById(R.id.spinner_add_delievry_person_id_type);
        findViewById(R.id.tv_add_delievry_person_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAssociateDeliveryPersonAPI();
            }
        });
    }

    private void supportedPersonIdTypeApi() {
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.supportedPersonIdTypeApi(new AppResponseListener<SupportedIdTypeResponse>(SupportedIdTypeResponse.class, this) {
            @Override
            public void onSuccess(SupportedIdTypeResponse result) {
                hideProgressBar();
                setSpinnerPersonIdType(result.getSupportedIDType());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }


    private void setSpinnerPersonIdType(List<SupportedIDType> orderUnits) {
        final List<String> categories = new ArrayList<>();
        for (SupportedIDType method : orderUnits) {
            categories.add(method.getIdType());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_add_delievry_person_id_type.setAdapter(dataAdapter);
        spinner_add_delievry_person_id_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                personIdType = categories.get(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void addAssociateDeliveryPersonAPI() {
        String name = ed_add_delievry_person_name.getText().toString().trim();
        String mobileNumber = ed_add_delievry_person_mobile_number.getText().toString().trim();
        String address = ed_add_delievry_person_address.getText().toString().trim();
        String idType = personIdType;
        String idNumber = ed_add_delievry_person_id_number.getText().toString().trim();
        String emailId = et_emailId.getText().toString();
        if (!DialogUtils.showDialogDeliveryPerson(this, name, mobileNumber, address, idType, idNumber, emailId)) {
            return;
        }
        showProgressBar(findViewById(R.id.tv_add_delievry_person_add));
        AppHttpRequest request = AppRequestBuilder.addAssociateDeliveryPersonAPI(name, mobileNumber, address, idType, idNumber,emailId, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar(findViewById(R.id.tv_add_delievry_person_add));
                showToast(result.getSuccessMessage());
                finish();
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar(findViewById(R.id.tv_add_delievry_person_add));
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

}
