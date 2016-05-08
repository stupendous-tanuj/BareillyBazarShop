package com.app.bareillybazarshop.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.api.output.AssociatedShopId;
import com.app.bareillybazarshop.api.output.AssociatedShopIdResponse;
import com.app.bareillybazarshop.api.output.CommonResponse;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.constant.AppConstant;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppResponseListener;
import com.app.bareillybazarshop.network.AppRestClient;
import com.app.bareillybazarshop.utils.DialogUtils;
import com.app.bareillybazarshop.utils.PreferenceKeeper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ShopOperationalTimeActivity extends BaseActivity {

    private EditText et_shop_time_closing_date;
    private CheckBox cb_shop_time_all;
    private EditText et_shop_time_opening_time;
    private EditText et_shop_time_closing_time;
    private LinearLayout linear_opening_time;
    private LinearLayout linear_closing_time;
    private Spinner spinner_shopId;
    LinearLayout ll_shopId;
    String shopIdValue = "";
    String USER_TYPE = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_operational_time);
        setHeader(getString(R.string.header_shop_operational_time), "");
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        setUI();
    }

    private void setUI() {
        et_shop_time_closing_date = (EditText) findViewById(R.id.et_shop_time_closing_date);
        cb_shop_time_all = (CheckBox) findViewById(R.id.cb_shop_time_all);
        et_shop_time_opening_time = (EditText) findViewById(R.id.et_shop_time_opening_time);
        et_shop_time_closing_time = (EditText) findViewById(R.id.et_shop_time_closing_time);
        linear_opening_time = (LinearLayout) findViewById(R.id.linear_opening_time);
        linear_closing_time = (LinearLayout) findViewById(R.id.linear_closing_time);

        ll_shopId = (LinearLayout) findViewById(R.id.ll_shopId);
        spinner_shopId = (Spinner) findViewById(R.id.spinner_shopId);
        if((USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE))) {
            ll_shopId.setVisibility(View.GONE);
        }
        else {
            ll_shopId.setVisibility(View.VISIBLE);
            associateShopIdAPI();
        }

        findViewById(R.id.et_shop_time_opening_time).setOnClickListener(this);
        findViewById(R.id.et_shop_time_closing_time).setOnClickListener(this);
        findViewById(R.id.tv_shop_time_add).setOnClickListener(this);
        findViewById(R.id.et_shop_time_closing_date).setOnClickListener(this);

        cb_shop_time_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linear_opening_time.setVisibility(View.GONE);
                    linear_closing_time.setVisibility(View.GONE);
                } else {
                    linear_opening_time.setVisibility(View.VISIBLE);
                    linear_closing_time.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setShopIdSpinner(List<AssociatedShopId> associatedShopId)
    {
        final List<String> shopId = new ArrayList<>();
        shopId.add(getString(R.string.please_select));
        for (int i = 0; i < associatedShopId.size(); i++)
            shopId.add(associatedShopId.get(i).getShopID());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, shopId);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_shopId.setAdapter(dataAdapter);
        spinner_shopId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                shopIdValue = shopId.get(pos);
                if (!shopIdValue.equals(getString(R.string.please_select))) {

                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void associateShopIdAPI() {

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.associatedShopId(new AppResponseListener<AssociatedShopIdResponse>(AssociatedShopIdResponse.class, this) {
            @Override
            public void onSuccess(AssociatedShopIdResponse result) {
                hideProgressBar();
                setShopIdSpinner(result.getAssociatedShops());
                //PreferenceKeeper.getInstance().setAssociatedShopId(result.getAssociatedShops());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_shop_time_closing_date:
                setDte(et_shop_time_closing_date);
                break;
            case R.id.tv_shop_time_add:
                addShopTimeAPI();
                break;
            case R.id.et_shop_time_opening_time:
                setTime(et_shop_time_opening_time);
                break;
            case R.id.et_shop_time_closing_time:
                setTime(et_shop_time_closing_time);
                break;
        }
    }

    private void setTime(final EditText et) {
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

    private void setDte(final EditText tv) {
        showDatePickerDialog();
        mDateListner = new DatePickerDialog.OnDateSetListener() {
            //         "fromDate": "2016-02-14",
            @Override
            public void onDateSet(final DatePicker datePicker, int year, int month, int day) {
                if (datePicker.isShown()) {
                    tv.setText(year + "-" + getData(++month) + "-" + getData(day));
                }
            }
        };
    }

    private static DatePickerDialog.OnDateSetListener mDateListner;

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), mDateListner, year, month, day);
        }
    }

    private void addShopTimeAPI() {

        if(USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE)) {
            shopIdValue = PreferenceKeeper.getInstance().getUserId();
        }

        String closingDate = et_shop_time_closing_date.getText().toString().trim();
        String openingTime = et_shop_time_opening_time.getText().toString().trim();
        String closingTime = et_shop_time_closing_time.getText().toString().trim();
        if (cb_shop_time_all.isChecked()) {
            openingTime = "";
            closingTime = "";
        } else {
            if (!DialogUtils.showDialogShopOpertion(this, closingDate, openingTime, closingTime)) {
                return;
            }
        }

        showProgressBar(findViewById(R.id.tv_shop_time_add));
        AppHttpRequest request = AppRequestBuilder.addShopTimeAPI(shopIdValue, closingDate, openingTime, closingTime, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar(findViewById(R.id.tv_shop_time_add));
                showToast(result.getSuccessMessage());
                finish();
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar(findViewById(R.id.tv_shop_time_add));
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }
}
