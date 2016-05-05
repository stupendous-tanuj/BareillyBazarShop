package com.app.bareillybazarshop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.adapter.AssociatedProductAdapter;
import com.app.bareillybazarshop.api.output.AssociatedProductResponse;
import com.app.bareillybazarshop.api.output.AssociatedShopId;
import com.app.bareillybazarshop.api.output.AssociatedShopIdResponse;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.api.output.Product;
import com.app.bareillybazarshop.api.output.UserMessages;
import com.app.bareillybazarshop.constant.AppConstant;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppResponseListener;
import com.app.bareillybazarshop.network.AppRestClient;
import com.app.bareillybazarshop.utils.DialogUtils;
import com.app.bareillybazarshop.utils.PreferenceKeeper;

import java.util.ArrayList;
import java.util.List;

public class AssociatedProductActivity extends BaseActivity {

    String shopIdValue = "";
    private Spinner spinner_shopId;
    LinearLayout ll_shopId;
    String USER_TYPE = "";
    private TextView no_data_available;
    private ListView lv_associated_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associated_product);
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        setHeader(getString(R.string.header_Associated_Product), "");
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
        ll_shopId = (LinearLayout) findViewById(R.id.ll_shopId);
        spinner_shopId = (Spinner) findViewById(R.id.spinner_shopId);
        no_data_available = (TextView) findViewById(R.id.no_data_available);
        if((USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE))) {
            ll_shopId.setVisibility(View.GONE);
        }
        else {
            ll_shopId.setVisibility(View.VISIBLE);
            associateShopIdAPI();
        }

        findViewById(R.id.iv_add_associated_product).setOnClickListener(this);
    }

    private void setVisibleUI(List<Product> associatedProduct) {
        if (associatedProduct == null || associatedProduct.size() == 0) {
            lv_associated_product.setVisibility(View.GONE);
            no_data_available.setVisibility(View.VISIBLE);
            no_data_available.setText(getString(R.string.msg_No_Product_Found));
        } else {
            lv_associated_product.setVisibility(View.VISIBLE);
            no_data_available.setVisibility(View.GONE);
            setAssciateProductAdapter(associatedProduct);
        }
    }


    private void fetchAssociatedProductListApi() {

        if (!DialogUtils.isSpinnerDefaultValue(this, shopIdValue, getString(R.string.label_Shop_ID)) || (shopIdValue.equals(""))) {
            return;
        }

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.fetchAssociatedProductListAPI(shopIdValue, new AppResponseListener<AssociatedProductResponse>(AssociatedProductResponse.class, this) {
            @Override
            public void onSuccess(AssociatedProductResponse result) {
                hideProgressBar();
                setVisibleUI(result.getProducts());

            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
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
                    fetchAssociatedProductListApi();
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


    private void setAssciateProductAdapter(List<Product> productList) {
        AssociatedProductAdapter deliveryAddressAdapter = new AssociatedProductAdapter(this, productList, shopIdValue);
        lv_associated_product.setAdapter(deliveryAddressAdapter);
    }

}
