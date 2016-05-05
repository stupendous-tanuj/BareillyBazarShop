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
import com.app.bareillybazarshop.adapter.ViewProductAdapter;
import com.app.bareillybazarshop.api.output.AssociatedShopId;
import com.app.bareillybazarshop.api.output.AssociatedShopIdResponse;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.api.output.Product;
import com.app.bareillybazarshop.api.output.ProductCategory;
import com.app.bareillybazarshop.api.output.ProductCategoryResponse;
import com.app.bareillybazarshop.api.output.ShopCategory;
import com.app.bareillybazarshop.api.output.ShopCategoryResponse;
import com.app.bareillybazarshop.api.output.ViewAvailableProductResponse;
import com.app.bareillybazarshop.constant.AppConstant;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppResponseListener;
import com.app.bareillybazarshop.network.AppRestClient;
import com.app.bareillybazarshop.utils.DialogUtils;
import com.app.bareillybazarshop.utils.PreferenceKeeper;

import java.util.ArrayList;
import java.util.List;

public class ViewAvailableProductActivity extends BaseActivity {

    private ListView lv_associated_product;
    private Spinner spinner_add_product_category;
    private Spinner spinner_add_product_shop_category;
    private String shopCN;
    private String productCN;
    private TextView tv_no_product_found;
    private Spinner spinner_shopId;
    LinearLayout ll_shopId;
    String shopIdValue = "";
    String USER_TYPE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_available_product);
        setHeader(getString(R.string.header_View_Available_Product), "");
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        setUI();
        fetchAllShopCategoryApi();
    }

    private void fetchAllShopCategoryApi() {
        showProgressBar();
        lv_associated_product.setVisibility(View.GONE);
        AppHttpRequest request = AppRequestBuilder.fetchAllShopCategoryApi(new AppResponseListener<ShopCategoryResponse>(ShopCategoryResponse.class, this) {
            @Override
            public void onSuccess(ShopCategoryResponse result) {
                hideProgressBar();
                setSpinnerShopCtegory(result.getShopCategories());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setSpinnerShopCtegory(List<ShopCategory> shopC) {
        final List<String> categories = new ArrayList<>();
        for (ShopCategory method : shopC) {
            categories.add(method.getShopCategoryName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_add_product_shop_category.setAdapter(dataAdapter);
        spinner_add_product_shop_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                shopCN = categories.get(pos);
                fetchAllProductCategoryApi(shopCN);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void fetchAllProductCategoryApi(final String shopCN) {
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.fetchAllProductCategoryApi(shopCN, new AppResponseListener<ProductCategoryResponse>(ProductCategoryResponse.class, this) {
            @Override
            public void onSuccess(ProductCategoryResponse result) {
                hideProgressBar();
                setSpinnerProductCtegory(shopCN, result.getProductCategories());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }


    private void setSpinnerProductCtegory(final String shopCN, List<ProductCategory> productC) {
        if (productC.size() == 0) {
            tv_no_product_found.setVisibility(View.VISIBLE);
            lv_associated_product.setVisibility(View.GONE);
        } else {
            tv_no_product_found.setVisibility(View.GONE);
        }
        final List<String> categories = new ArrayList<>();
        for (ProductCategory method : productC) {
            categories.add(method.getProductCategoryName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_add_product_category.setAdapter(dataAdapter);
        spinner_add_product_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                productCN = categories.get(pos);
                if (!shopIdValue.equals(getString(R.string.please_select)))
                viewAvailableProductAPI();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        tv_no_product_found = (TextView) findViewById(R.id.tv_no_product_found);
        spinner_add_product_category = (Spinner) findViewById(R.id.spinner_add_product_category);
        spinner_add_product_shop_category = (Spinner) findViewById(R.id.spinner_add_product_shop_category);

        lv_associated_product = (ListView) findViewById(R.id.lv_associated_product);
        findViewById(R.id.iv_add_associated_product).setOnClickListener(this);

        ll_shopId = (LinearLayout) findViewById(R.id.ll_shopId);
        spinner_shopId = (Spinner) findViewById(R.id.spinner_shopId);
        if((USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE))) {
            ll_shopId.setVisibility(View.GONE);
        }
        else {
            ll_shopId.setVisibility(View.VISIBLE);
            associateShopIdAPI();
        }
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
                if (!shopIdValue.equals(getString(R.string.please_select)))
                {
                    viewAvailableProductAPI();
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


    private void viewAvailableProductAPI() {

        if (!DialogUtils.isSpinnerDefaultValue(this, shopIdValue, getString(R.string.label_Shop_ID)) || (shopIdValue.equals(""))) {
            return;
        }

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.viewAvailableProductAPI(shopIdValue, shopCN, productCN, new AppResponseListener<ViewAvailableProductResponse>(ViewAvailableProductResponse.class, this) {
            @Override
            public void onSuccess(ViewAvailableProductResponse result) {
                hideProgressBar();
                setAvailableProductAdapter(shopCN, productCN, result.getProducts());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setAvailableProductAdapter(String shopCN, String productCN, List<Product> customerAddresses) {
        if (customerAddresses.size() == 0) {
            tv_no_product_found.setVisibility(View.VISIBLE);
            lv_associated_product.setVisibility(View.GONE);
        } else {
            tv_no_product_found.setVisibility(View.GONE);
            lv_associated_product.setVisibility(View.VISIBLE);
            ViewProductAdapter deliveryAddressAdapter = new ViewProductAdapter(this, shopCN, productCN, customerAddresses);
            lv_associated_product.setAdapter(deliveryAddressAdapter);
        }

    }

}
