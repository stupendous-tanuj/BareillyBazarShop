package com.app.bareillybazarshop.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.adapter.OrderDetailAdapter;
import com.app.bareillybazarshop.api.output.CartDetail;
import com.app.bareillybazarshop.api.output.CommonResponse;
import com.app.bareillybazarshop.api.output.DeliveryPersonResponse;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.api.output.MyOrderDetailResponse;
import com.app.bareillybazarshop.api.output.OrderDetail;
import com.app.bareillybazarshop.api.output.Product;
import com.app.bareillybazarshop.constant.AppConstant;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppResponseListener;
import com.app.bareillybazarshop.network.AppRestClient;
import com.app.bareillybazarshop.utils.DialogUtils;
import com.app.bareillybazarshop.utils.Logger;
import com.app.bareillybazarshop.utils.PreferenceKeeper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class UpdateOrderDetailActivity extends BaseActivity {

    public TextView orderId;
    public TextView orderPlacedBy;
    public TextView orderPlacedTo;
    public TextView orderQuotedAmount;
    public TextView orderDeliveryType;
    public TextView orderPaymentMethod;
    public TextView orderStatus;
    public TextView orderDeliveryAddressIdentifier;
    public TextView tv_orderInvoiceAmount;
    public TextView tv_orderCancellationReason;
    public TextView tv_my_order_order_time;
    public TextView tv_update_order;
    public TextView tv_expectedDeliveryTime;
    public TextView tv_dispatchTime;
    public TextView tv_deliveryTime;
    public EditText et_orderCancellationReason;
    public EditText et_orderInvoiceAmount;
    public Spinner spinner_toOrderStatus;
    public LinearLayout ll_orderCancellationReason;
    public LinearLayout ll_orderInvoiceAmount;
    public LinearLayout ll_orderInvoiceAmountCollected;
    public LinearLayout ll_orderReceiptAcknowledgementCollected;
    public LinearLayout ll_orderReceiptAcknowledgement;
    public LinearLayout ll_shippedBy;
    public LinearLayout ll_beingShippedBy;
    public LinearLayout ll_editOrderInvoiceAmount;
    public LinearLayout ll_dispatchTime;
    public LinearLayout ll_deliveryTime;
    public LinearLayout ll_editOrderCancellationReason;
    public TextView tv_shippedBy;
    public Spinner spinner_beingShippedBy;
    public CheckBox cb_orderInvoiceAmountCollected;
    public CheckBox cb_orderReceiptAcknowledgementCollected;
    public CheckBox cb_orderReceiptAcknowledgement;
    String orderIdValue = "";
    String orderStatusValue = "";
    String toOrderStatusValue = "";
    String deliveryPerson = "";
    private RecyclerView recycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order_detail);
        setHeader(getString(R.string.header_update_order_detail), "");
        setUI();
        setUIListener();
        setRecycler();
        Logger.INFO("create", "create");
        if(PreferenceKeeper.getInstance().getIsLogin() == true) {
            getIntentData();
        }
        else {
            launchActivity(LoginActivity.class);
        }

    }


    private void getIntentData()
    {
        orderIdValue = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.ORDER_ID);
        orderStatusValue = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.ORDER_STATUS);
        Logger.INFO("orderIdValue", orderIdValue);
        Logger.INFO("orderStatusValue", orderStatusValue);
        fetchOrderDetailsAPI();
    }

    public void setUI() {
        orderId = (TextView) findViewById(R.id.tv_my_order_id);
        orderPlacedBy = (TextView) findViewById(R.id.tv_my_order_place_by);
        orderPlacedTo = (TextView) findViewById(R.id.tv_my_order_place_to);
        orderQuotedAmount = (TextView) findViewById(R.id.tv_my_order_quoted_ammont);
        orderDeliveryType = (TextView) findViewById(R.id.tv_my_order_delievry_type);
        orderPaymentMethod = (TextView) findViewById(R.id.tv_my_order_payment_method);
        recycleView = (RecyclerView) findViewById(R.id.rv_home_activity_my_order);
        orderStatus = (TextView) findViewById(R.id.tv_my_order_status);
        orderDeliveryAddressIdentifier = (TextView) findViewById(R.id.tv_my_order_address_id);
        tv_orderInvoiceAmount = (TextView) findViewById(R.id.tv_orderInvoiceAmount);
        tv_orderCancellationReason = (TextView) findViewById(R.id.tv_orderCancellationReason);
        tv_expectedDeliveryTime = (TextView) findViewById(R.id.tv_expectedDeliveryTime);
        tv_my_order_order_time = (TextView) findViewById(R.id.tv_my_order_order_time);
        tv_dispatchTime = (TextView) findViewById(R.id.tv_dispatchTime);
        tv_deliveryTime = (TextView) findViewById(R.id.tv_deliveryTime);
        spinner_toOrderStatus = (Spinner) findViewById(R.id.spinner_to_order_status);
        tv_update_order = (TextView) findViewById(R.id.tv_update_order);
        et_orderCancellationReason = (EditText)findViewById(R.id.et_orderCancellationReason);
        et_orderInvoiceAmount = (EditText)findViewById(R.id.et_orderInvoiceAmount);
        ll_orderInvoiceAmountCollected = (LinearLayout)findViewById(R.id.ll_orderInvoiceAmountCollected);
        ll_orderReceiptAcknowledgementCollected = (LinearLayout)findViewById(R.id.ll_orderReceiptAcknowledgementCollected);
        ll_orderReceiptAcknowledgement = (LinearLayout)findViewById(R.id.ll_orderReceiptAcknowledgement);
        ll_editOrderInvoiceAmount = (LinearLayout)findViewById(R.id.ll_editOrderInvoiceAmount);
        ll_dispatchTime = (LinearLayout)findViewById(R.id.ll_dispatchTime);
        ll_deliveryTime = (LinearLayout)findViewById(R.id.ll_deliveryTime);
        ll_editOrderCancellationReason = (LinearLayout)findViewById(R.id.ll_editOrderCancellationReason);
        cb_orderInvoiceAmountCollected = (CheckBox)findViewById(R.id.cb_orderInvoiceAmountCollected);
        cb_orderReceiptAcknowledgementCollected = (CheckBox)findViewById(R.id.cb_orderReceiptAcknowledgementCollected);
        cb_orderReceiptAcknowledgement = (CheckBox)findViewById(R.id.cb_orderReceiptAcknowledgement);
        ll_orderInvoiceAmount = (LinearLayout) findViewById(R.id.ll_orderInvoiceAmount);
        ll_orderCancellationReason = (LinearLayout)findViewById(R.id.ll_orderCancellationReason);
        ll_shippedBy = (LinearLayout)findViewById(R.id.ll_shippedBy);
        ll_beingShippedBy = (LinearLayout)findViewById(R.id.ll_beingShippedBy);
        tv_shippedBy = (TextView)findViewById(R.id.tv_shippedBy);
        spinner_beingShippedBy = (Spinner)findViewById(R.id.spinner_beingShippedBy);

        cb_orderInvoiceAmountCollected.setChecked(false);
        cb_orderReceiptAcknowledgementCollected.setChecked(false);
        cb_orderReceiptAcknowledgement.setChecked(false);
        ll_orderInvoiceAmount.setVisibility(View.GONE);
        ll_orderCancellationReason.setVisibility(View.GONE);
        ll_editOrderInvoiceAmount.setVisibility(View.GONE);
        ll_editOrderCancellationReason.setVisibility(View.GONE);
        ll_orderInvoiceAmountCollected.setVisibility(View.GONE);
        ll_orderReceiptAcknowledgementCollected.setVisibility(View.GONE);
        ll_orderReceiptAcknowledgement.setVisibility(View.GONE);
        ll_shippedBy.setVisibility(View.GONE);
        ll_beingShippedBy.setVisibility(View.GONE);
        ll_dispatchTime.setVisibility(View.GONE);
        ll_deliveryTime.setVisibility(View.GONE);
    }

    private void setRecycler() {
        recycleView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(mLayoutManager);
    }

    String quotedAmount = "";
    String shopId = "";
    public void setData(OrderDetail orderDetail) {
        orderStatusValue = orderDetail.getOrderStatus();
        orderId.setText(orderIdValue);
        orderStatus.setText(orderStatusValue);
        orderPlacedBy.setText(orderDetail.getOrderPlacedBy());
        shopId = orderDetail.getOrderPlacedTo();
        orderPlacedTo.setText(shopId);
        quotedAmount = orderDetail.getOrderQuotedAmount();
        et_orderInvoiceAmount.setText(quotedAmount);
        orderQuotedAmount.setText(quotedAmount);
        orderDeliveryType.setText(orderDetail.getOrderDeliveryType());
        orderPaymentMethod.setText(orderDetail.getOrderPaymentMethod());
        orderDeliveryAddressIdentifier.setText(orderDetail.getOrderDeliveryAddressIdentifier());
        tv_orderInvoiceAmount.setText(orderDetail.getOrderInvoiceAmount());
        tv_orderCancellationReason.setText(orderDetail.getOrderCancellationReason());
        tv_my_order_order_time.setText(orderDetail.getOrderCreationTimestamp());
        tv_expectedDeliveryTime.setText(orderDetail.getOrderExpectedDeliveryTimestamp());
        tv_shippedBy.setText(orderDetail.getOrderBeingShippedBy());
        tv_dispatchTime.setText(orderDetail.getOrderPickedUpForDeliveryTimestamp());
        tv_deliveryTime.setText(orderDetail.getOrderDeliveryTimestamp());
        if(orderStatusValue.equals(AppConstant.STATUS.STATUS_DELIVERED)) {
            cb_orderReceiptAcknowledgement.setChecked(orderDetail.getOrderReceiptAcknowledgement() == "N" ? false : true);
        }
        if(orderStatusValue.equals(AppConstant.STATUS.STATUS_CLOSED)) {
            cb_orderReceiptAcknowledgement.setChecked(orderDetail.getOrderReceiptAcknowledgement() == "N" ? false : true);
            cb_orderInvoiceAmountCollected.setChecked(orderDetail.getOrderInvoiceAmountCollected() == "N" ? false : true);
            cb_orderReceiptAcknowledgementCollected.setChecked(orderDetail.getOrderReceiptAcknowledgementCollected() == "N" ? false : true);
        }

        String toOrderValues[] = orderDetail.getToOrderStatus().split(",");
        final List<String> toOrderStatus = new ArrayList<>();
        for (int i = 0; i < toOrderValues.length; i++)
            toOrderStatus.add(toOrderValues[i]);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, toOrderStatus);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_toOrderStatus.setAdapter(dataAdapter);
        spinner_toOrderStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                toOrderStatusValue = toOrderStatus.get(pos);
                //orderStatusValue = toOrderStatus.get(pos);
                if (toOrderStatusValue.equals(AppConstant.STATUS.STATUS_PREPARED)) {
                    ll_beingShippedBy.setVisibility(View.VISIBLE);
                    fetchDeliveryPersonAPI();
                }
                else if (toOrderStatusValue.equals(AppConstant.STATUS.STATUS_DELIVERED)) {
                    ll_editOrderInvoiceAmount.setVisibility(View.VISIBLE);
                    ll_orderReceiptAcknowledgement.setVisibility(View.VISIBLE);
                }
                else if (toOrderStatusValue.equals(AppConstant.STATUS.STATUS_CANCELLED)) {
                    ll_orderCancellationReason.setVisibility(View.VISIBLE);
                }
                else if(toOrderStatusValue.equals(AppConstant.STATUS.STATUS_CLOSED)){
                    ll_orderInvoiceAmountCollected.setVisibility(View.VISIBLE);
                    ll_orderReceiptAcknowledgementCollected.setVisibility(View.VISIBLE);
                }
                else {
                    ll_beingShippedBy.setVisibility(View.GONE);
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        if(orderStatusValue.equals(AppConstant.STATUS.STATUS_CANCELLED)){
            ll_orderInvoiceAmount.setVisibility(View.VISIBLE);
            ll_orderCancellationReason.setVisibility(View.VISIBLE);
        }
        if(orderStatusValue.equals(AppConstant.STATUS.STATUS_DISPATCHED)){
            ll_shippedBy.setVisibility(View.VISIBLE);
            ll_dispatchTime.setVisibility(View.VISIBLE);
        }
        if(orderStatusValue.equals(AppConstant.STATUS.STATUS_PREPARED)){
            ll_shippedBy.setVisibility(View.VISIBLE);
        }
        if(orderStatusValue.equals(AppConstant.STATUS.STATUS_DELIVERED)){
            ll_shippedBy.setVisibility(View.VISIBLE);
            ll_orderInvoiceAmount.setVisibility(View.VISIBLE);
            ll_orderReceiptAcknowledgement.setVisibility(View.VISIBLE);
            ll_deliveryTime.setVisibility(View.VISIBLE);
            cb_orderReceiptAcknowledgement.setEnabled(false);
        }
        if(orderStatusValue.equals(AppConstant.STATUS.STATUS_CLOSED)){
            ll_editOrderInvoiceAmount.setVisibility(View.GONE);
            ll_orderInvoiceAmountCollected.setVisibility(View.VISIBLE);
            ll_orderReceiptAcknowledgementCollected.setVisibility(View.VISIBLE);
            ll_dispatchTime.setVisibility(View.VISIBLE);
            ll_deliveryTime.setVisibility(View.VISIBLE);
            ll_shippedBy.setVisibility(View.VISIBLE);
            ll_orderInvoiceAmount.setVisibility(View.VISIBLE);
            ll_orderReceiptAcknowledgement.setVisibility(View.VISIBLE);
            cb_orderInvoiceAmountCollected.setEnabled(false);
            cb_orderReceiptAcknowledgementCollected.setEnabled(false);
            cb_orderReceiptAcknowledgement.setEnabled(false);
        }

    }

    private void setUIListener() {
        tv_update_order.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_update_order:
                updateOrderStatusApi();
                break;
        }
    }


    private void fetchOrderDetailsAPI() {

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.fetchOrderDetailsAPI(orderIdValue, orderStatusValue, new AppResponseListener<MyOrderDetailResponse>(MyOrderDetailResponse.class, this) {
            @Override
            public void onSuccess(MyOrderDetailResponse result) {
                hideProgressBar();
                setData(result.getOrderDetails().get(0));
                setPlaceCartAdapter(result.getCartDetails());

            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }


    private void setPlaceCartAdapter(List<CartDetail> carts) {
        OrderDetailAdapter placeOrderlAdapter = new OrderDetailAdapter(this, carts);
        recycleView.setAdapter(placeOrderlAdapter);
    }

    private void fetchDeliveryPersonAPI() {
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.associatedDeliveryPersonAPI(shopId.split(" ")[0], "0", new AppResponseListener<DeliveryPersonResponse>(DeliveryPersonResponse.class, this) {
            @Override
            public void onSuccess(DeliveryPersonResponse result) {
                hideProgressBar();
                setDeliveryPersonDropDown(result);
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    public void setDeliveryPersonDropDown(DeliveryPersonResponse response)
    {
        final List<String> deliveryPersonList = new ArrayList<>();
        deliveryPersonList.add(getString(R.string.please_select));
        for (int i = 0; i < response.getDeliveryPerson().size(); i++)
            deliveryPersonList.add(response.getDeliveryPerson().get(i).getDeliveryPersonMobileNumber()+" "+response.getDeliveryPerson().get(i).getDeliveryPersonName());

        ArrayAdapter<String> deliveryDateDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, deliveryPersonList);
        deliveryDateDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_beingShippedBy.setAdapter(deliveryDateDataAdapter);
        spinner_beingShippedBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                deliveryPerson = deliveryPersonList.get(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }


    private void updateOrderStatusApi() {

        String additionalFieldValue = "";
        if(toOrderStatusValue.equals(AppConstant.STATUS.STATUS_DELIVERED)) {
            additionalFieldValue = et_orderInvoiceAmount.getText().toString().trim();

            if (!DialogUtils.isQuotedAmountVerification(this, quotedAmount, additionalFieldValue)) {
                return;
            }

            if(!DialogUtils.isChecked(this, getString(R.string.label_Order_Receipt_Acknowledgement), cb_orderReceiptAcknowledgement.isChecked())) {
                return;
            }
        }

        if(toOrderStatusValue.equals(AppConstant.STATUS.STATUS_CANCELLED))
            additionalFieldValue = et_orderCancellationReason.getText().toString();

        if (!DialogUtils.isUpdateOrderDetailsVerification(this, toOrderStatusValue)) {
            return;
        }

        if(toOrderStatusValue.equals(AppConstant.STATUS.STATUS_PREPARED))
        {
            additionalFieldValue = deliveryPerson.split(" ")[0];
            if (!DialogUtils.isSpinnerDefaultValue(this, deliveryPerson, getString(R.string.label_Delivery_Person)) || (deliveryPerson.equals(""))) {
                return;
            }
        }

        if(toOrderStatusValue.equals(AppConstant.STATUS.STATUS_CLOSED)) {

            if(!DialogUtils.isChecked(this, getString(R.string.label_Invoice_Amount_Collected), cb_orderInvoiceAmountCollected.isChecked())) {
                return;
            }

            if(!DialogUtils.isChecked(this, getString(R.string.label_Receipt_Acknowledgement_Collected), cb_orderReceiptAcknowledgementCollected.isChecked())) {
                return;
            }
        }


        showProgressBar(findViewById(R.id.tv_update_order));
        AppHttpRequest request = AppRequestBuilder.updateOrderStatusAPI(orderIdValue, orderStatusValue, toOrderStatusValue, additionalFieldValue, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar(findViewById(R.id.tv_update_order));
                showToast(result.getSuccessMessage());
                orderStatusValue = toOrderStatusValue;
                fetchOrderDetailsAPI();
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar(findViewById(R.id.tv_update_order));
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }
}
