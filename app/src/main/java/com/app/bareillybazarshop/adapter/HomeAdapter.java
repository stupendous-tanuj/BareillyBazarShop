package com.app.bareillybazarshop.adapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.activity.BaseActivity;
import com.app.bareillybazarshop.activity.HomeActivity;
import com.app.bareillybazarshop.activity.UpdateOrderDetailActivity;
import com.app.bareillybazarshop.api.output.CommonResponse;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.api.output.OrderDetail;
import com.app.bareillybazarshop.constant.AppConstant;
import com.app.bareillybazarshop.listner.IDialogListener;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppResponseListener;
import com.app.bareillybazarshop.network.AppRestClient;
import com.app.bareillybazarshop.utils.DialogUtils;

import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = HomeActivity.class.getSimpleName();
    private List<OrderDetail> carts;
    private HomeActivity activity;

    public HomeAdapter(HomeActivity activity, List<OrderDetail> carts) {
        this.carts = carts;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Log.i("Test2", "Test2");
        View homeAdapter = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_home, parent, false);
        return new OrderDetailHolder(homeAdapter);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        Log.i("Test2", "Test2");
        final OrderDetail data = getItem(position);
        OrderDetailHolder holder = (OrderDetailHolder) viewHolder;
        setData(holder, data, position);

    }

    private void setData(final OrderDetailHolder holder, final OrderDetail orderDetail, final int pos) {
        holder.orderId.setText(orderDetail.getOrderId());
        holder.orderPlacedBy.setText(orderDetail.getOrderPlacedBy());
        holder.orderPlacedTo.setText(orderDetail.getOrderPlacedTo());
        holder.orderQuotedAmount.setText(orderDetail.getOrderQuotedAmount());
        holder.orderDeliveryType.setText(orderDetail.getOrderDeliveryType());
        holder.orderPaymentMethod.setText(orderDetail.getOrderPaymentMethod());
        holder.orderStatus.setText(orderDetail.getOrderStatus());
        holder.tv_my_order_order_time.setText(orderDetail.getOrderCreationTimestamp());
        holder.tv_my_order_ex_time.setText(orderDetail.getOrderExpectedDeliveryTimestamp());
        holder.orderDeliveryAddressIdentifier.setText(orderDetail.getOrderDeliveryAddressIdentifier());
        final String orderStatus = orderDetail.getOrderStatus();
        final String orderId = orderDetail.getOrderId();

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1 = new Bundle();
                bundle1.putString(AppConstant.BUNDLE_KEY.ORDER_ID, orderDetail.getOrderId());
                bundle1.putString(AppConstant.BUNDLE_KEY.ORDER_STATUS, orderDetail.getOrderStatus());
                activity.launchActivity(UpdateOrderDetailActivity.class, bundle1);
            }
        });

        holder.tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderStatus.equals(AppConstant.STATUS.STATUS_ORDERED)) {
                    DialogUtils.showDialogYesNo(activity, activity.getString(R.string.order_confirmation), activity.getString(R.string.yes), activity.getString(R.string.no), new IDialogListener() {
                        @Override
                        public void onClickOk() {
                            updateOrderStatusApi(holder, orderId, AppConstant.STATUS.STATUS_ORDERED, AppConstant.STATUS.STATUS_CONFIRMED);
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                }
                else
                {
                    DialogUtils.showDialog(activity, activity.getString(R.string.msg_already_confirmed));
                }
            }
        });

        holder.orderPlacedBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activity.callPhone(orderDetail.getOrderPlacedBy().split(" ")[0]);

            }
        });

        holder.orderPlacedTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activity.callPhone(orderDetail.getShopSupportContactNumber());

            }
        });


    }


    private void updateOrderStatusApi(final OrderDetailHolder holder, String orderIdValue, String orderStatusValue,String toOrderStatusValue) {

        String additionalFieldValue = "";

        AppHttpRequest request = AppRequestBuilder.updateOrderStatusAPI(orderIdValue, orderStatusValue, toOrderStatusValue, additionalFieldValue, new AppResponseListener<CommonResponse>(CommonResponse.class, activity) {
            @Override
            public void onSuccess(CommonResponse result) {
                activity.hideProgressBar();
                activity.showToast(result.getSuccessMessage());
                notifyDataSetChanged();
                activity.onResume();
                holder.orderStatus.setText(AppConstant.STATUS.STATUS_CONFIRMED);
            }

            @Override
            public void onError(ErrorObject error) {

            }
        });
        AppRestClient.getClient().sendRequest(activity, request, activity.TAG);
    }

    private OrderDetail getItem(int position) {
        return carts.get(position);
    }

    @Override
    public int getItemCount() {
        return carts != null ? carts.size() : 0;
    }

    class OrderDetailHolder extends RecyclerView.ViewHolder {

        private final LinearLayout linearLayout;
        public TextView orderId;
        public TextView orderPlacedBy;
        public TextView orderPlacedTo;
        public TextView orderQuotedAmount;
        public TextView orderDeliveryType;
        public TextView orderPaymentMethod;
        public TextView orderStatus;
        private TextView tv_my_order_ex_time;
        public TextView orderDeliveryAddressIdentifier;
        public TextView tv_my_order_order_time;
        public TextView tv_confirm;

        public OrderDetailHolder(View convertView) {
            super(convertView);
            orderId = (TextView) convertView.findViewById(R.id.tv_my_order_id);
            orderPlacedBy = (TextView) convertView.findViewById(R.id.tv_my_order_place_by);
            orderPlacedTo = (TextView) convertView.findViewById(R.id.tv_my_order_place_to);
            orderQuotedAmount = (TextView) convertView.findViewById(R.id.tv_my_order_quoted_ammont);
            orderDeliveryType = (TextView) convertView.findViewById(R.id.tv_my_order_delievry_type);
            orderPaymentMethod = (TextView) convertView.findViewById(R.id.tv_my_order_payment_method);
            orderStatus = (TextView) convertView.findViewById(R.id.tv_my_order_status);
            orderDeliveryAddressIdentifier = (TextView) convertView.findViewById(R.id.tv_my_order_address_id);
            tv_my_order_order_time = (TextView) convertView.findViewById(R.id.tv_my_order_order_time);
            tv_my_order_ex_time = (TextView) convertView.findViewById(R.id.tv_my_order_ex_time);
            tv_confirm = (TextView) convertView.findViewById(R.id.tv_confirm);
            linearLayout = (LinearLayout) convertView.findViewById(R.id.linearLayout);
        }
    }
}
