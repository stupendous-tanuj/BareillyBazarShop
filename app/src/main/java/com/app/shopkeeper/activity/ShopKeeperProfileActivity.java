package com.app.shopkeeper.activity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.app.shopkeeper.R;
import com.app.shopkeeper.api.output.CommonResponse;
import com.app.shopkeeper.api.output.DeliveryMethod;
import com.app.shopkeeper.api.output.ErrorObject;
import com.app.shopkeeper.api.output.MyOrderDetailResponse;
import com.app.shopkeeper.api.output.OrderUnit;
import com.app.shopkeeper.api.output.PaymentMethod;
import com.app.shopkeeper.api.output.ShopProfile;
import com.app.shopkeeper.api.output.ShopProfileResponse;
import com.app.shopkeeper.network.AppHttpRequest;
import com.app.shopkeeper.network.AppRequestBuilder;
import com.app.shopkeeper.network.AppResponseListener;
import com.app.shopkeeper.network.AppRestClient;
import com.app.shopkeeper.utils.AppUtil;
import com.app.shopkeeper.utils.PreferenceKeeper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ShopKeeperProfileActivity extends BaseActivity {

    private TextView tv_profile_shop_id;
    private EditText et_profile_shop_name;
    private TextView tv_profile_registartion_status;
    private TextView tv_profile_owner_name;
    private EditText et_profile_shop_address;
    private EditText et_profile_area_sector;
    private TextView tv_profile_city;
    private EditText et_profile_pincode;
    private EditText et_profile_land_mark;
    private EditText et_profile_closing_time;
    private EditText et_profile_opening_time;
    private EditText et_profile_owner_contact;
    private EditText et_profile_support_contact;
    private EditText et_profile_order_processing_contact;
    private EditText et_profile_email_id;
    private EditText et_profile_minimum_acc_order;
    private EditText et_profile_delivery_charge;
    private TextView tv_profile_owner_id_type;
    private TextView tv_profile_owner_id_number;
    private TextView tv_profile_owner_tin;
    private Spinner spinner_profile_delivery_type;
    private Spinner spinner_profile_payment_method;
    private EditText et_profile_shop_rating;
    private TextView tv_profile_state;
    private boolean isLocClick;
    private String deliveryType;
    private String paymentMethod;
    private ScrollView scrollview_shop_keeper;
    private TextView tv_profile_ssocite_to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_keeper_profile);
        setHeader("Shop Keeper Profile", "");
        setUI();
        setUIListner();
        fetchShopKeeperProfileApi();
        isLocClick = false;
    }

    private void setUIListner() {
        findViewById(R.id.tv_profile_location_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLocClick = true;
                launchActivity(UpdateLocationActivity.class);
            }
        });
        findViewById(R.id.tv_update_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfileApi();
            }
        });
    }

    private void setUI() {
        scrollview_shop_keeper = (ScrollView) findViewById(R.id.scrollview_shop_keeper);
        scrollview_shop_keeper.setVisibility(View.INVISIBLE);
        tv_profile_shop_id = (TextView) findViewById(R.id.tv_profile_shop_id);
        et_profile_shop_name = (EditText) findViewById(R.id.et_profile_shop_name);
        tv_profile_registartion_status = (TextView) findViewById(R.id.tv_profile_registartion_status);
        tv_profile_owner_name = (TextView) findViewById(R.id.tv_profile_owner_name);
        et_profile_shop_address = (EditText) findViewById(R.id.et_profile_shop_address);
        et_profile_area_sector = (EditText) findViewById(R.id.et_profile_area_sector);
        tv_profile_city = (TextView) findViewById(R.id.tv_profile_city);
        et_profile_pincode = (EditText) findViewById(R.id.et_profile_pincode);
        tv_profile_state = (TextView) findViewById(R.id.tv_profile_state);
        et_profile_land_mark = (EditText) findViewById(R.id.et_profile_land_mark);
        et_profile_closing_time = (EditText) findViewById(R.id.et_profile_closing_time);
        et_profile_opening_time = (EditText) findViewById(R.id.et_profile_opening_time);
        et_profile_owner_contact = (EditText) findViewById(R.id.et_profile_owner_contact);
        et_profile_support_contact = (EditText) findViewById(R.id.et_profile_support_contact);
        et_profile_order_processing_contact = (EditText) findViewById(R.id.et_profile_order_processing_contact);
        et_profile_email_id = (EditText) findViewById(R.id.et_profile_email_id);
        et_profile_minimum_acc_order = (EditText) findViewById(R.id.et_profile_minimum_acc_order);
        et_profile_delivery_charge = (EditText) findViewById(R.id.et_profile_delivery_charge);
        tv_profile_owner_id_type = (TextView) findViewById(R.id.tv_profile_owner_id_type);
        tv_profile_owner_id_number = (TextView) findViewById(R.id.tv_profile_owner_id_number);
        tv_profile_owner_tin = (TextView) findViewById(R.id.tv_profile_owner_tin);
        tv_profile_ssocite_to = (TextView) findViewById(R.id.tv_profile_ssocite_to);
        spinner_profile_delivery_type = (Spinner) findViewById(R.id.spinner_profile_delivery_type);
        spinner_profile_payment_method = (Spinner) findViewById(R.id.spinner_profile_payment_method);
        et_profile_shop_rating = (EditText) findViewById(R.id.et_profile_shop_rating);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isLocClick) {
            String flatNumber = PreferenceKeeper.getInstance().getflatNumber();
            String area = PreferenceKeeper.getInstance().getArea();
            String locality = PreferenceKeeper.getInstance().getLocality();
            String city = PreferenceKeeper.getInstance().getCity();
            String state = PreferenceKeeper.getInstance().getState();
            String pincode = PreferenceKeeper.getInstance().getPincode();

            et_profile_shop_address.setText(flatNumber + " " + area);
            et_profile_area_sector.setText(area);
            tv_profile_city.setText(city);
            et_profile_pincode.setText(pincode);
            tv_profile_state.setText(state);
            et_profile_land_mark.setText(locality);

            isLocClick = false;
        }
    }

    private void fetchShopKeeperProfileApi() {
        String shopID = "SP1001";

        showProgressBar(findViewById(R.id.tv_update_profile));
        AppHttpRequest request = AppRequestBuilder.fetchShopKeeperProfileApi(shopID, new AppResponseListener<ShopProfileResponse>(ShopProfileResponse.class, this) {
            @Override
            public void onSuccess(ShopProfileResponse result) {
                scrollview_shop_keeper.setVisibility(View.VISIBLE);
                hideProgressBar(findViewById(R.id.tv_update_profile));
                setProfileData(result.getShopProfile().get(0));
                setSpinnerDeliveryMethod(result.getDeliveryMethods());
                setSpinnerPaymentMethod(result.getPaymentMethods());
            }

            @Override
            public void onError(ErrorObject error) {
                scrollview_shop_keeper.setVisibility(View.VISIBLE);
                hideProgressBar(findViewById(R.id.tv_update_profile));
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setProfileData(ShopProfile profile) {
        tv_profile_shop_id.setText(profile.getShopID());
        et_profile_shop_name.setText(profile.getShopName());
        tv_profile_registartion_status.setText(profile.getShopRegistrationStatus());
        tv_profile_owner_name.setText(profile.getShopOwnerName());
        et_profile_shop_address.setText(profile.getShopAddress());
        et_profile_area_sector.setText(profile.getShopAddressAreaSector());
        tv_profile_city.setText(profile.getShopAddressCity());
        et_profile_pincode.setText(profile.getShopAddressPincode());
        tv_profile_state.setText(profile.getShopAddressState());
        et_profile_land_mark.setText(profile.getShopAddressLandmark());
        et_profile_closing_time.setText(profile.getShopClosingTime());
        et_profile_opening_time.setText(profile.getShopOpeningTime());
        et_profile_owner_contact.setText(profile.getShopOwnerContactNumber());
        et_profile_support_contact.setText(profile.getShopSupportContactNumber());
        et_profile_order_processing_contact.setText(profile.getShopOrderProcessingContactNumber());
        et_profile_email_id.setText(profile.getShopEmailId());
        et_profile_minimum_acc_order.setText(profile.getShopMinimumAcceptedOrder());
        et_profile_delivery_charge.setText(profile.getShopDeliveryCharges());
        tv_profile_owner_id_type.setText(profile.getShopOwnerIDType());
        tv_profile_owner_id_number.setText(profile.getShopOwnerIDNumber());
        tv_profile_owner_tin.setText(profile.getShopTANNumber());
        tv_profile_ssocite_to.setText(profile.getShopCreatedFor());
        et_profile_shop_rating.setText(profile.getShopRating());
        et_profile_closing_time.setOnClickListener(this);
        et_profile_opening_time.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_profile_closing_time:
                setTime(et_profile_closing_time);
                break;
            case R.id.et_profile_opening_time:
                setTime(et_profile_opening_time);
                break;
        }
    }

    private void setTime(final TextView et) {
        showTimePickerDialog(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                if (timePicker.isShown()) {
                    String time = getData(hourOfDay) + ":" + getData(minute);
                    et.setText(time);
                }
            }
        });
    }

    private void setSpinnerDeliveryMethod(List<DeliveryMethod> deliveryType1) {
        final List<String> categories = new ArrayList<>();
        for (DeliveryMethod method : deliveryType1) {
            categories.add(method.getDeliveryMethod());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_profile_delivery_type.setAdapter(dataAdapter);
        spinner_profile_delivery_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        spinner_profile_payment_method.setAdapter(dataAdapter);
        spinner_profile_payment_method.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                paymentMethod = categories.get(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updateProfileApi() {
        String closingTime = et_profile_closing_time.getText().toString();
        String openingTime = et_profile_opening_time.getText().toString();

        String shopID = tv_profile_shop_id.getText().toString();
        String shopName = et_profile_shop_name.getText().toString().trim();
        String shopAddress = et_profile_shop_address.getText().toString().trim();
        String city = tv_profile_city.getText().toString();
        String pincode = et_profile_pincode.getText().toString().trim();
        String state = tv_profile_state.getText().toString();
        String landmark = et_profile_land_mark.getText().toString().trim();
        String ownerNumber = et_profile_owner_contact.getText().toString().trim();
        String supportNumber = et_profile_support_contact.getText().toString().trim();
        String procesingNumber = et_profile_support_contact.getText().toString().trim();
        String emailId = et_profile_email_id.getText().toString().trim();
        String minimumAccOrder = et_profile_minimum_acc_order.getText().toString().trim();
        String deliveryCharge = et_profile_delivery_charge.getText().toString().trim();
        int open = AppUtil.getTotlTime(openingTime);
        int close = AppUtil.getTotlTime(closingTime);
        if (open > close) {
            showToast("Opening time cannot be greater than Closing time");
            return;
        }
        showProgressBar(findViewById(R.id.tv_update_profile));
        AppHttpRequest request = AppRequestBuilder.updateShopKeeperProfileApi(shopID, shopName, shopAddress, city, pincode, state, landmark, closingTime, openingTime, ownerNumber, supportNumber
                , procesingNumber, emailId, minimumAccOrder, deliveryCharge, deliveryType, paymentMethod, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
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
}
