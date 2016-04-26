package com.app.bareillybazarshop.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.activity.BaseActivity;
import com.app.bareillybazarshop.activity.HomeActivity;
import com.app.bareillybazarshop.activity.ViewShopProfileActivity;
import com.app.bareillybazarshop.api.output.AssociatedShop;
import com.app.bareillybazarshop.constant.AppConstant;

import java.util.List;

public class AssociatedShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = HomeActivity.class.getSimpleName();
    private List<AssociatedShop> associatedShop;
    private BaseActivity activity;
    private String shopId;

    public AssociatedShopAdapter(BaseActivity activity, List<AssociatedShop> associatedShop, String shopId) {

        this.associatedShop = associatedShop;
        this.shopId = shopId;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View homeAdapter = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_associated_shops, parent, false);
        return new AssociatedShopHolder(homeAdapter);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final AssociatedShop data = getItem(position);
        AssociatedShopHolder holder = (AssociatedShopHolder) viewHolder;
        setData(holder, data, position);

    }

    private void setData(AssociatedShopHolder holder, final AssociatedShop data, final int pos) {
        holder.tv_shopId.setText(data.getShopID());
        holder.tv_shopName.setText(data.getShopName());
        holder.tv_shopDescription.setText(data.getShopDescription());
        holder.tv_shopAddress.setText(data.getShopAddressAreaSector());
        holder.tv_shopLandmark.setText(data.getShopAddressLandmark());


        holder.ll_shops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(AppConstant.BUNDLE_KEY.SHOP_ID, data.getShopID());
                activity.launchActivity(ViewShopProfileActivity.class, bundle);
            }
        });


    }


    private AssociatedShop getItem(int position) {
        return associatedShop.get(position);
    }

    @Override
    public int getItemCount() {
        return associatedShop != null ? associatedShop.size() : 0;
    }

    public class AssociatedShopHolder extends RecyclerView.ViewHolder {

        private final TextView tv_shopId;
        private final TextView tv_shopName;
        private final TextView tv_shopDescription;
        private final TextView tv_shopAddress;
        private final TextView tv_shopLandmark;
        private final LinearLayout ll_shops;

        public AssociatedShopHolder(View view) {
            super(view);
            tv_shopId = (TextView) view.findViewById(R.id.tv_shopId);
            tv_shopName = (TextView) view.findViewById(R.id.tv_shopName);
            tv_shopDescription = (TextView) view.findViewById(R.id.tv_shopDescription);
            tv_shopAddress = (TextView) view.findViewById(R.id.tv_shopAddress);
            tv_shopLandmark = (TextView) view.findViewById(R.id.tv_shopLandmark);
            ll_shops = (LinearLayout) view.findViewById(R.id.ll_shops);
        }
    }
}
