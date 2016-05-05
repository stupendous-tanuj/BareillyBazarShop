package com.app.bareillybazarshop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.activity.BaseActivity;
import com.app.bareillybazarshop.activity.HomeActivity;
import com.app.bareillybazarshop.api.output.CommonResponse;
import com.app.bareillybazarshop.api.output.DeliveryLocation;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.listner.IDialogListener;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppResponseListener;
import com.app.bareillybazarshop.network.AppRestClient;
import com.app.bareillybazarshop.utils.DialogUtils;

import java.util.List;

public class AssociatedDeliveryLocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = HomeActivity.class.getSimpleName();
    private List<DeliveryLocation> deliveryLocation;
    private BaseActivity activity;
    private String shopId;

    public AssociatedDeliveryLocationAdapter(BaseActivity activity, List<DeliveryLocation> deliveryLocation, String shopId) {

        this.deliveryLocation = deliveryLocation;
        this.shopId = shopId;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View homeAdapter = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_associated_delivery_locations, parent, false);
        System.out.println("Test");
        return new DeliveryLocationHolder(homeAdapter);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        System.out.println("Test");
        final DeliveryLocation data = getItem(position);
        DeliveryLocationHolder holder = (DeliveryLocationHolder) viewHolder;
        setData(holder, data, position);

    }

    private void setData(DeliveryLocationHolder holder, final DeliveryLocation data, final int pos) {
        holder.deliveryLocation.setText(data.getDeliveryLocation());
        holder.city.setText(data.getCity());

        holder.tv_ass_delivery_location_deassociate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogUtils.showDialogYesNo(activity, activity.getString(R.string.deassociated_location), activity.getString(R.string.yes), activity.getString(R.string.no), new IDialogListener() {
                    @Override
                    public void onClickOk() {
                        deassociatedDeliveryLocationAPI(data.getDeliveryLocation(), pos);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });
    }

    private void deassociatedDeliveryLocationAPI(String deliveryLocation, final int pos) {
        activity.showProgressBar();
        AppHttpRequest request = AppRequestBuilder.deAssociateDeliveryLocationAPI(shopId, deliveryLocation, new AppResponseListener<CommonResponse>(CommonResponse.class, activity) {
            @Override
            public void onSuccess(CommonResponse result) {
                activity.hideProgressBar();
                activity.showToast(result.getSuccessMessage());
                AssociatedDeliveryLocationAdapter.this.deliveryLocation.remove(pos);
                notifyDataSetChanged();
            }

            @Override
            public void onError(ErrorObject error) {
                activity.hideProgressBar();

            }
        });
        AppRestClient.getClient().sendRequest((BaseActivity) activity, request, activity.TAG);
    }

    private DeliveryLocation getItem(int position) {
        return deliveryLocation.get(position);
    }

    @Override
    public int getItemCount() {
        return deliveryLocation != null ? deliveryLocation.size() : 0;
    }

    public class DeliveryLocationHolder extends RecyclerView.ViewHolder {

        private final TextView deliveryLocation;
        private final TextView city;
        private final TextView tv_ass_delivery_location_deassociate;

        public DeliveryLocationHolder(View view) {
            super(view);
            deliveryLocation = (TextView) view.findViewById(R.id.tv_delivery_location_name);
            city = (TextView) view.findViewById(R.id.tv_delivery_location_city);
            tv_ass_delivery_location_deassociate = (TextView) view.findViewById(R.id.tv_ass_delivery_location_deassociate);
        }
    }
}
