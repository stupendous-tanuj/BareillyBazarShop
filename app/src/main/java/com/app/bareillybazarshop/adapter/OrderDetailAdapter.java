package com.app.bareillybazarshop.adapter;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.activity.BaseActivity;
import com.app.bareillybazarshop.activity.HomeActivity;
import com.app.bareillybazarshop.activity.UpdateOrderDetailActivity;
import com.app.bareillybazarshop.api.output.CartDetail;
import com.app.bareillybazarshop.api.output.OrderDetail;
import com.app.bareillybazarshop.api.output.Product;
import com.app.bareillybazarshop.constant.AppConstant;
import com.app.bareillybazarshop.utils.AppUtil;
import com.app.bareillybazarshop.utils.Logger;

import java.util.List;


public class OrderDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = UpdateOrderDetailActivity.class.getSimpleName();
    private List<CartDetail> carts;
    private BaseActivity activity;

    public OrderDetailAdapter(BaseActivity activity, List<CartDetail> carts) {
        Log.i("Test1", "Test1");
        this.carts = carts;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Log.i("Test3", "Test3");
        View homeAdapter = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_product_catalog, parent, false);
        return new OrderDetailHolder(homeAdapter);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final CartDetail data = getItem(position);
        OrderDetailHolder holder = (OrderDetailHolder) viewHolder;
        setData(holder, data, position);
        Log.i("Test2", "Test2");

    }

    private void setData(OrderDetailHolder holder, final CartDetail product, final int pos) {
        Log.i("Cart Size", product.getProductNameEnglish());
        ((BaseActivity) activity).imageLoader.displayImage(product.getProductImageName()
                , holder.iv_product_ctlog, ((BaseActivity) activity).imageOptions);

        holder.tv_pcatelog_name.setText(product.getProductNameEnglish());
        holder.tv_pcatelog_unit.setText(product.getProductPriceForUnits() + " " + product.getProductOrderUnit());
        holder.tv_pcatelog_prize.setText("Price : " + product.getCartProductPrice());

    }

    private CartDetail getItem(int position) {
        return carts.get(position);
    }

    @Override
    public int getItemCount() {
        return carts != null ? carts.size() : 0;
    }

    class OrderDetailHolder extends RecyclerView.ViewHolder {

        private TextView tvValue;
        private TextView tv_pcatelog_des;
        private TextView tv_pcatelog_name;
        private TextView tv_pcatelog_prize;
        private TextView tv_pcatelog_offer_prize;
        private TextView tvIncrementPrize;
        public TextView tv_pcatelog_unit;
        public ImageView iv_product_ctlog;

        public OrderDetailHolder(View convertView) {
            super(convertView);
            iv_product_ctlog = (ImageView) convertView.findViewById(R.id.iv_product_ctlog);
            tv_pcatelog_name = (TextView) convertView.findViewById(R.id.tv_pcatelog_name);
            tv_pcatelog_des = (TextView) convertView.findViewById(R.id.tv_pcatelog_des);
            tv_pcatelog_unit = (TextView) convertView.findViewById(R.id.tv_pcatelog_unit);
            tv_pcatelog_prize = (TextView) convertView.findViewById(R.id.tv_pcatelog_prize);
            tvIncrementPrize = (TextView) convertView.findViewById(R.id.tv_pcatelog_increment_prize);
            tv_pcatelog_offer_prize = (TextView) convertView.findViewById(R.id.tv_pcatelog_offer_prize);
            tvValue = (TextView) convertView.findViewById(R.id.tv_catalog_detail_value);
        }
    }
}
