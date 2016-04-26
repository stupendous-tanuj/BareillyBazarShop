package com.app.bareillybazarshop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.api.output.CommonResponse;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.api.output.OrderUnit;
import com.app.bareillybazarshop.api.output.OrderUnitResponse;
import com.app.bareillybazarshop.api.output.ProductCategory;
import com.app.bareillybazarshop.api.output.ProductCategoryResponse;
import com.app.bareillybazarshop.api.output.ShopCategory;
import com.app.bareillybazarshop.api.output.ShopCategoryResponse;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppResponseListener;
import com.app.bareillybazarshop.network.AppRestClient;
import com.app.bareillybazarshop.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends BaseActivity {


    private EditText et_add_product_eng_name;
    private EditText et_add_product_hindi_name;
    private EditText et_add_product_des;
    private Spinner spinner_add_product_category;
    private Spinner spinner_add_product_shop_category;
    private Spinner spinner_add_product_order_unit;
    private EditText et_add_product_no_unit;
    private String shopCN;
    private String productCN;
    private String orderUnitC;
    private TextView tv_add_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        setHeader("Add Product", "");
        setUI();
        fetchAllShopCategoryApi();
        fetchOrderUnitApi();
    }

    private void fetchAllShopCategoryApi() {
        tv_add_product.setVisibility(View.GONE);
        showProgressBar();
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
        tv_add_product.setVisibility(View.VISIBLE);
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
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void fetchOrderUnitApi() {
        AppHttpRequest request = AppRequestBuilder.fetchOrderUnitApi(new AppResponseListener<OrderUnitResponse>(OrderUnitResponse.class, this) {
            @Override
            public void onSuccess(OrderUnitResponse result) {

                setSpinnerOrderUnit(result.getOrderUnits());
            }

            @Override
            public void onError(ErrorObject error) {

            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }


    private void setSpinnerOrderUnit(List<OrderUnit> orderUnits) {
        final List<String> categories = new ArrayList<>();
        for (OrderUnit method : orderUnits) {
            categories.add(method.getOrderUnit());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_add_product_order_unit.setAdapter(dataAdapter);
        spinner_add_product_order_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                orderUnitC = categories.get(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void setUI() {
        tv_add_product = (TextView) findViewById(R.id.tv_add_product);
        et_add_product_eng_name = (EditText) findViewById(R.id.et_add_product_eng_name);
        et_add_product_hindi_name = (EditText) findViewById(R.id.et_add_product_hindi_name);
        et_add_product_des = (EditText) findViewById(R.id.et_add_product_des);
        spinner_add_product_category = (Spinner) findViewById(R.id.spinner_add_product_category);
        spinner_add_product_shop_category = (Spinner) findViewById(R.id.spinner_add_product_shop_category);
        spinner_add_product_order_unit = (Spinner) findViewById(R.id.spinner_add_product_order_unit);
        et_add_product_no_unit = (EditText) findViewById(R.id.et_add_product_no_unit);

        findViewById(R.id.tv_add_product).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_product:
                addAProductAPI();
                break;
        }
    }

    private void addAProductAPI() {
        String eName = et_add_product_eng_name.getText().toString().trim();
        String hName = et_add_product_hindi_name.getText().toString().trim();
        String des = et_add_product_des.getText().toString().trim();
        String shopCategory = shopCN;
        String pCategory = productCN;
        String orderUnit = orderUnitC;
        String noOfUnit = et_add_product_no_unit.getText().toString().trim();
        if (!DialogUtils.showDialogddProduct(this, eName, hName, des, noOfUnit)) {
            return;
        }

        showProgressBar(findViewById(R.id.tv_add_product));
        AppHttpRequest request = AppRequestBuilder.addAProductAPI(eName, hName, des, pCategory, shopCategory, orderUnit, noOfUnit, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar(findViewById(R.id.tv_add_product));
                showToast(result.getSuccessMessage());
                finish();
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar(findViewById(R.id.tv_add_product));
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

}
