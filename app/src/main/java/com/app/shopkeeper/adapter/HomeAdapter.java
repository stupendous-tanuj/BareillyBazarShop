package com.app.shopkeeper.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.shopkeeper.R;
import com.app.shopkeeper.activity.BaseActivity;
import com.app.shopkeeper.activity.HomeActivity;
import com.app.shopkeeper.activity.UpdateOrderDetailActivity;
import com.app.shopkeeper.api.output.OrderDetail;
import com.app.shopkeeper.constant.AppConstant;

import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = HomeActivity.class.getSimpleName();
    private List<OrderDetail> carts;
    private BaseActivity activity;

    public HomeAdapter(BaseActivity activity, List<OrderDetail> carts) {
        this.carts = carts;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View homeAdapter = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_home, parent, false);
        return new OrderDetailHolder(homeAdapter);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        final OrderDetail data = getItem(position);
        OrderDetailHolder holder = (OrderDetailHolder) viewHolder;
        setData(holder, data, position);

    }

    private void setData(OrderDetailHolder holder, final OrderDetail orderDetail, final int pos) {
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

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1 = new Bundle();
                bundle1.putString(AppConstant.BUNDLE_KEY.ORDER_ID, orderDetail.getOrderId());
                 bundle1.putString(AppConstant.BUNDLE_KEY.ORDER_STATUS, orderDetail.getOrderStatus());
                activity.launchActivity(UpdateOrderDetailActivity.class, bundle1);
            }
        });
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
            linearLayout = (LinearLayout) convertView.findViewById(R.id.linearLayout);
        }
    }
}
