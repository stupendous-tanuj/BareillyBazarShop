package com.app.shopkeeper.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.app.shopkeeper.R;
import com.app.shopkeeper.adapter.AssociatedProductAdapter;
import com.app.shopkeeper.api.output.AssociatedProductResponse;
import com.app.shopkeeper.api.output.ErrorObject;
import com.app.shopkeeper.api.output.Product;
import com.app.shopkeeper.constant.AppConstant;
import com.app.shopkeeper.network.AppHttpRequest;
import com.app.shopkeeper.network.AppRequestBuilder;
import com.app.shopkeeper.network.AppResponseListener;
import com.app.shopkeeper.network.AppRestClient;

import java.util.List;

public class AssociatedProductActivity extends BaseActivity {

    private ListView lv_associated_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associated_product);
        setHeader("Associated Product", "");
        setUI();

    }

    // update product
    // AssociateProduct api for addition and update 7
    @Override
    protected void onResume() {
        super.onResume();
        fetchAssociatedProductListApi();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_associated_product:
                Bundle bundle = new Bundle();
                bundle.putBoolean(AppConstant.BUNDLE_KEY.IS_ADD_ASSOCIATE_PRODUCT_HOME, true);
                launchActivity(AssociateAProductActivity.class, bundle);
                break;
        }
    }

    private void setUI() {
        lv_associated_product = (ListView) findViewById(R.id.lv_associated_product);
        findViewById(R.id.iv_add_associated_product).setOnClickListener(this);
    }


    private void fetchAssociatedProductListApi() {
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.fetchAssociatedProductListAPI("SP1001", new AppResponseListener<AssociatedProductResponse>(AssociatedProductResponse.class, this) {
            @Override
            public void onSuccess(AssociatedProductResponse result) {
                hideProgressBar();
                setAssciateProductAdapter(result.getProducts());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setAssciateProductAdapter(List<Product> customerAddresses) {
        AssociatedProductAdapter deliveryAddressAdapter = new AssociatedProductAdapter(this, customerAddresses);
        lv_associated_product.setAdapter(deliveryAddressAdapter);
    }

}
