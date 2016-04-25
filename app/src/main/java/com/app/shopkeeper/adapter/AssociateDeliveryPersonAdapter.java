package com.app.shopkeeper.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.app.shopkeeper.R;
import com.app.shopkeeper.activity.BaseActivity;
import com.app.shopkeeper.activity.HomeActivity;
import com.app.shopkeeper.api.output.CommonResponse;
import com.app.shopkeeper.api.output.DeliveryPerson;
import com.app.shopkeeper.api.output.ErrorObject;
import com.app.shopkeeper.listner.IDialogListener;
import com.app.shopkeeper.network.AppHttpRequest;
import com.app.shopkeeper.network.AppRequestBuilder;
import com.app.shopkeeper.network.AppResponseListener;
import com.app.shopkeeper.network.AppRestClient;
import com.app.shopkeeper.utils.DialogUtils;

import java.util.List;

public class AssociateDeliveryPersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = HomeActivity.class.getSimpleName();
    private List<DeliveryPerson> deliveryPerson;
    private BaseActivity activity;
    private String shopIdORSellerHubId;

    public AssociateDeliveryPersonAdapter(BaseActivity activity, List<DeliveryPerson> deliveryPerson, String shopIdORSellerHubId) {
        this.deliveryPerson = deliveryPerson;
        this.shopIdORSellerHubId = shopIdORSellerHubId;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View homeAdapter = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_associate_delivery_person, parent, false);
        return new DeliverPersonHolder(homeAdapter);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        final DeliveryPerson data = getItem(position);
        DeliverPersonHolder holder = (DeliverPersonHolder) viewHolder;
        setData(holder, data, position);

    }

    private void setData(DeliverPersonHolder holder, final DeliveryPerson data, final int pos) {
        holder.name.setText(data.getDeliveryPersonName());
        holder.mobileNumber.setText(data.getDeliveryPersonMobileNumber());

        holder.tv_ass_delivery_person_deassociate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogUtils.showDialogYesNo(activity, activity.getString(R.string.deassociated_product), activity.getString(R.string.yes), activity.getString(R.string.no), new IDialogListener() {
                    @Override
                    public void onClickOk() {
                        deassociatedDeliveryPersoneAPI(data.getDeliveryPersonMobileNumber(), pos);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });
    }

    private void deassociatedDeliveryPersoneAPI(String deliveryPersonId, final int pos) {
        activity.showProgressBar();
        AppHttpRequest request = AppRequestBuilder.deAssociateDeliveryPersonAPI(shopIdORSellerHubId, deliveryPersonId, new AppResponseListener<CommonResponse>(CommonResponse.class, activity) {
            @Override
            public void onSuccess(CommonResponse result) {
                activity.hideProgressBar();
                activity.showToast(result.getSuccessMessage());
                deliveryPerson.remove(pos);
                notifyDataSetChanged();
            }

            @Override
            public void onError(ErrorObject error) {
                activity.hideProgressBar();

            }
        });
        AppRestClient.getClient().sendRequest((BaseActivity) activity, request, activity.TAG);
    }

    private DeliveryPerson getItem(int position) {
        return deliveryPerson.get(position);
    }

    @Override
    public int getItemCount() {
        return deliveryPerson != null ? deliveryPerson.size() : 0;
    }

    public class DeliverPersonHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView mobileNumber;
        private final TextView tv_ass_delivery_person_deassociate;

        public DeliverPersonHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_ass_delivery_mobile_number);
            mobileNumber = (TextView) view.findViewById(R.id.tv_ass_delivery_mobile_number);
            tv_ass_delivery_person_deassociate = (TextView) view.findViewById(R.id.tv_ass_delivery_person_deassociate);
        }
    }
}
