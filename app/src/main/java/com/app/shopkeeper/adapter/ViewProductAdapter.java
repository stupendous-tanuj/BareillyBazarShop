package com.app.shopkeeper.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.shopkeeper.R;
import com.app.shopkeeper.activity.AssociateAProductActivity;
import com.app.shopkeeper.activity.BaseActivity;
import com.app.shopkeeper.api.output.Product;
import com.app.shopkeeper.constant.AppConstant;

import java.util.List;

public class ViewProductAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private String productCN;
    private String shopCN;
    private BaseActivity activity;
    private List<Product> product;

    public ViewProductAdapter(BaseActivity activity, String shopCN, String productCN, List<Product> product) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.product = product;
        this.shopCN = shopCN;
        this.productCN = productCN;
    }


    @Override
    public int getCount() {
        return product != null ? product.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return product != null ? product.get(position) : null;
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_view_product, viewGroup, false);
            holder = new ViewHolder();
            holder.linear_view_product_root = (LinearLayout) convertView.findViewById(R.id.linear_view_product_root);
            holder.tv_view_product_sku = (TextView) convertView.findViewById(R.id.tv_view_product_sku);
            holder.tv_view_product_name = (TextView) convertView.findViewById(R.id.tv_view_product_name);
            holder.tv_view_product_unit = (TextView) convertView.findViewById(R.id.tv_view_product_unit);
            holder.tv_view_product_des = (TextView) convertView.findViewById(R.id.tv_view_product_des);
            holder.tv_view_product_product_category = (TextView) convertView.findViewById(R.id.tv_view_product_product_category);
            holder.tv_view_product_shop_category = (TextView) convertView.findViewById(R.id.tv_view_product_shop_category);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setData(product.get(pos), holder, pos);

        return convertView;
    }

    private void setData(final Product product, final ViewHolder holder, final int pos) {
        holder.tv_view_product_sku.setText(product.getProductSKUID());
        holder.tv_view_product_name.setText(product.getProductNameEnglish());
        holder.tv_view_product_unit.setText(product.getProductPriceForUnits() + " " + product.getProductOrderUnit());
        holder.tv_view_product_des.setText(product.getProductDescription());
        holder.tv_view_product_shop_category.setText(shopCN);
        holder.tv_view_product_product_category.setText(productCN);
        holder.linear_view_product_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean(AppConstant.BUNDLE_KEY.IS_ADD_ASSOCIATE_PRODUCT_HOME, false);
                bundle.putString(AppConstant.BUNDLE_KEY.SKU_ID, product.getProductSKUID());
                bundle.putString(AppConstant.BUNDLE_KEY.ENGLISH_NAME, product.getProductNameEnglish());
                bundle.putString(AppConstant.BUNDLE_KEY.HINDI_NAME, product.getProductNameHindi());
                bundle.putString(AppConstant.BUNDLE_KEY.DES, product.getProductDescription());
                bundle.putString(AppConstant.BUNDLE_KEY.UNIT, product.getProductPriceForUnits() + " " + product.getProductOrderUnit());
                bundle.putString(AppConstant.BUNDLE_KEY.PRICE, product.getProductPrice());
                bundle.putString(AppConstant.BUNDLE_KEY.OFFER_PRICE, product.getProductOfferedPrice());
                bundle.putString(AppConstant.BUNDLE_KEY.IMAGE, product.getProductImageName());
                activity.launchActivity(AssociateAProductActivity.class, bundle);
            }
        });
    }


    public class ViewHolder {
        private TextView tv_view_product_sku;
        private TextView tv_view_product_name;
        private TextView tv_view_product_unit;
        private TextView tv_view_product_des;
        private TextView tv_view_product_product_category;
        private TextView tv_view_product_shop_category;
        public LinearLayout linear_view_product_root;
    }
}
