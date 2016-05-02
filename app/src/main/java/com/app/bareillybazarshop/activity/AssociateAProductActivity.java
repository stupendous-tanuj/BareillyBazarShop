package com.app.bareillybazarshop.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.api.output.AssociatedShopId;
import com.app.bareillybazarshop.api.output.AssociatedShopIdResponse;
import com.app.bareillybazarshop.api.output.CommonResponse;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.api.output.Product;
import com.app.bareillybazarshop.api.output.ProductCategory;
import com.app.bareillybazarshop.api.output.ProductCategoryResponse;
import com.app.bareillybazarshop.api.output.ProductResponse;
import com.app.bareillybazarshop.api.output.ShopCategory;
import com.app.bareillybazarshop.api.output.ShopCategoryResponse;
import com.app.bareillybazarshop.constant.AppConstant;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppResponseListener;
import com.app.bareillybazarshop.network.AppRestClient;
import com.app.bareillybazarshop.utils.DialogUtils;
import com.app.bareillybazarshop.utils.PreferenceKeeper;

import java.util.ArrayList;
import java.util.List;

public class AssociateAProductActivity extends BaseActivity {

    private boolean isAddFromHome;
    private TextView tv_ass_product_add_sku;
    private TextView tv_ass_product_add_english_name;
    private TextView tv_ass_product_add_hindi_name;
    private TextView tv_ass_product_add_description;
    private TextView tv_ass_product_add_unit;
    private EditText et_ass_product_add_price;
    private EditText et_ass_product_add_offer_price;
    private CheckBox cbAvailable;
    private TextView tv_add_associate_product;
    private Spinner spinner_ass_shop_ctegory;
    private Spinner spinner_ass_product_ctegory;
    private Spinner spinner_ass_product;
    private LinearLayout linear_spinner_shop_ctegory;
    private LinearLayout linear_spinner_product_ctegory;
    private LinearLayout linear_spinner_shop_product;
    private LinearLayout linear_associated_product_root;
    private TextView tv_no_product_found;
    private TextView tv_serch_product;
    private ArrayAdapter<String> adapter;
    private PopupWindow popupWindowserch;
    private ScrollView scrollview_ssocite_product;
    private TextView view;
    private Spinner spinner_shopId;
    LinearLayout ll_shopId;
    String shopIdValue = "";
    String USER_TYPE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_associate_product);
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        setUI();
        setUIListner();
        getIntentData();
    }

    public PopupWindow popupWindowserch(List<String> categories) {
        PopupWindow popupWindow = new PopupWindow(this);

        LayoutInflater order_list_LayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = order_list_LayoutInflater.inflate(R.layout.list_serch_product_pop, null, true);
        final EditText tv_serch_product_name = (EditText) view.findViewById(R.id.ed_serch_product);
        final ListView listview_ssocite_product = (ListView) view.findViewById(R.id.list);

        adapter = new ArrayAdapter<String>(this, R.layout.list_serch_product, R.id.tv_serch_product_name, categories);
        listview_ssocite_product.setAdapter(adapter);
        // some other visual settings
        popupWindow.setFocusable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);

        tv_serch_product_name.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int count) {
                AssociateAProductActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        return popupWindow;
    }

    private void setUI() {
        view = (TextView) findViewById(R.id.view);
        scrollview_ssocite_product = (ScrollView) findViewById(R.id.scrollview_ssocite_product);
        tv_serch_product = (TextView) findViewById(R.id.tv_serch_product);
        tv_no_product_found = (TextView) findViewById(R.id.tv_no_product_found);
        linear_associated_product_root = (LinearLayout) findViewById(R.id.linear_associated_product_root);
        linear_spinner_shop_ctegory = (LinearLayout) findViewById(R.id.linear_spinner_shop_ctegory);
        linear_spinner_product_ctegory = (LinearLayout) findViewById(R.id.linear_spinner_product_ctegory);
        linear_spinner_shop_product = (LinearLayout) findViewById(R.id.linear_spinner_shop_product);
        spinner_ass_shop_ctegory = (Spinner) findViewById(R.id.spinner_ass_shop_ctegory);
        spinner_ass_product_ctegory = (Spinner) findViewById(R.id.spinner_ass_product_ctegory);
        spinner_ass_product = (Spinner) findViewById(R.id.spinner_ass_product);
        tv_add_associate_product = (TextView) findViewById(R.id.tv_add_associate_product);
        tv_ass_product_add_sku = (TextView) findViewById(R.id.tv_ass_product_add_sku);
        tv_ass_product_add_english_name = (TextView) findViewById(R.id.tv_ass_product_add_english_name);
        tv_ass_product_add_hindi_name = (TextView) findViewById(R.id.tv_ass_product_add_hindi_name);
        tv_ass_product_add_description = (TextView) findViewById(R.id.tv_ass_product_add_description);
        tv_ass_product_add_unit = (TextView) findViewById(R.id.tv_ass_product_add_unit);
        et_ass_product_add_price = (EditText) findViewById(R.id.et_ass_product_add_price);
        et_ass_product_add_offer_price = (EditText) findViewById(R.id.et_ass_product_add_offer_price);
        cbAvailable = (CheckBox) findViewById(R.id.cb_ass_product_add_availability);
        ll_shopId = (LinearLayout) findViewById(R.id.ll_shopId);
        spinner_shopId = (Spinner) findViewById(R.id.spinner_shopId);
        if((USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE))) {
            ll_shopId.setVisibility(View.GONE);
        }
        else {
            ll_shopId.setVisibility(View.VISIBLE);
            associateShopIdAPI();
        }

        tv_serch_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // listview_ssocite_product.setVisibility(View.VISIBLE);
                popupWindowserch.showAsDropDown(view, 0, 0);
            }
        });

    }

    private void setUIListner() {
        findViewById(R.id.tv_add_associate_product).setOnClickListener(this);
    }

    private void fetchAllShopCategoryApi() {
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


    private void setSpinnerShopCtegory(List<ShopCategory> shopC) {
        if (shopC.size() == 0) {
            linear_spinner_shop_ctegory.setVisibility(View.GONE);
            linear_spinner_product_ctegory.setVisibility(View.GONE);
            linear_spinner_shop_product.setVisibility(View.GONE);
            tv_add_associate_product.setVisibility(View.GONE);
            linear_associated_product_root.setVisibility(View.GONE);
            tv_no_product_found.setVisibility(View.VISIBLE);
            return;
        } else {
            linear_spinner_shop_ctegory.setVisibility(View.VISIBLE);
            linear_spinner_product_ctegory.setVisibility(View.VISIBLE);
            linear_spinner_shop_product.setVisibility(View.VISIBLE);
            tv_no_product_found.setVisibility(View.GONE);
        }
        final List<String> categories = new ArrayList<>();
        for (ShopCategory method : shopC) {
            categories.add(method.getShopCategoryName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ass_shop_ctegory.setAdapter(dataAdapter);
        spinner_ass_shop_ctegory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String shopCN = categories.get(pos);
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
            linear_spinner_product_ctegory.setVisibility(View.GONE);
            linear_spinner_shop_product.setVisibility(View.GONE);
            tv_add_associate_product.setVisibility(View.GONE);
            linear_associated_product_root.setVisibility(View.GONE);
            tv_no_product_found.setVisibility(View.VISIBLE);
            return;
        } else {
            linear_spinner_product_ctegory.setVisibility(View.VISIBLE);
            linear_spinner_shop_product.setVisibility(View.VISIBLE);
            tv_no_product_found.setVisibility(View.GONE);
        }
        final List<String> categories = new ArrayList<>();
        for (ProductCategory method : productC) {
            categories.add(method.getProductCategoryName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ass_product_ctegory.setAdapter(dataAdapter);
        spinner_ass_product_ctegory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String productCN = categories.get(pos);
                fetchAvailableProductApi(shopCN, productCN);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void fetchAvailableProductApi(String shopCN, String productCN) {
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.fetchAvailableProductApi(shopIdValue, shopCN, productCN, new AppResponseListener<ProductResponse>(ProductResponse.class, this) {
            @Override
            public void onSuccess(ProductResponse result) {
                hideProgressBar();
                setSpinnerProduct(result.getProducts());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private List<Product> products;

    private void setSpinnerProduct(final List<Product> products) {
        this.products = products;
        if (products.size() == 0) {
            linear_spinner_shop_product.setVisibility(View.GONE);
            tv_add_associate_product.setVisibility(View.GONE);
            linear_associated_product_root.setVisibility(View.GONE);
            tv_no_product_found.setVisibility(View.VISIBLE);
            return;
        } else {
            linear_spinner_shop_product.setVisibility(View.VISIBLE);
            tv_add_associate_product.setVisibility(View.VISIBLE);
            linear_associated_product_root.setVisibility(View.VISIBLE);
            tv_no_product_found.setVisibility(View.GONE);
        }
        final List<String> categories = new ArrayList<>();
        for (Product method : products) {
            if(method.getProductNameHindi().equals("") || method.getProductNameHindi().equals(null))
            categories.add(method.getProductNameEnglish());
            else
            categories.add(method.getProductNameEnglish() + ", " + method.getProductNameHindi());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ass_product.setAdapter(dataAdapter);
        spinner_ass_product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                setProduct(products.get(pos));
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        popupWindowserch = popupWindowserch(categories);
        /// serch product
        //adapter = new ArrayAdapter<String>(this, R.layout.list_serch_product, R.id.tv_serch_product_name, categories);
        //listview_ssocite_product.setAdapter(adapter);
    }

    private void setProduct(Product product) {
        tv_ass_product_add_sku.setText(product.getProductSKUID());
        tv_ass_product_add_english_name.setText(product.getProductNameEnglish());
        tv_ass_product_add_hindi_name.setText(product.getProductNameHindi());
        tv_ass_product_add_description.setText(product.getProductDescription());
        tv_ass_product_add_unit.setText(product.getProductPriceForUnits() + " " + product.getProductOrderUnit());
    }

    private void getIntentData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            isAddFromHome = getIntent().getExtras().getBoolean(AppConstant.BUNDLE_KEY.IS_ADD_ASSOCIATE_PRODUCT_HOME);
            if (isAddFromHome) {
                tv_add_associate_product.setVisibility(View.GONE);
                linear_associated_product_root.setVisibility(View.GONE);
                fetchAllShopCategoryApi();
                tv_add_associate_product.setText("Associate A Product");
                setHeader("Associate A Product", "");
                spinner_ass_shop_ctegory.setEnabled(true);
                spinner_ass_product_ctegory.setEnabled(true);
                spinner_ass_product.setEnabled(true);
                ll_shopId.setVisibility(View.VISIBLE);
            } else {
                linear_spinner_shop_ctegory.setVisibility(View.GONE);
                linear_spinner_product_ctegory.setVisibility(View.GONE);
                linear_spinner_shop_product.setVisibility(View.GONE);
                tv_add_associate_product.setText("Update Associated Product");
                setHeader("Update Associated Product", "");
                String SKU_ID = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.SKU_ID);
                String ENGLISH_NAME = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.ENGLISH_NAME);
                String HINDI_NAME = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.HINDI_NAME);
                String DES = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.DES);
                String UNIT = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.UNIT);
                String PRICE = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.PRICE);
                String OFFER_PRICE = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.OFFER_PRICE);
                String IMAGE = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.IMAGE);
                shopIdValue = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.SHOP_ID);
                ll_shopId.setVisibility(View.GONE);
                tv_serch_product.setVisibility(View.GONE);
                tv_ass_product_add_sku.setText(SKU_ID);
                tv_ass_product_add_english_name.setText(ENGLISH_NAME);
                tv_ass_product_add_hindi_name.setText(HINDI_NAME);
                tv_ass_product_add_description.setText(DES);
                tv_ass_product_add_unit.setText(UNIT);
                et_ass_product_add_price.setText(PRICE);
                et_ass_product_add_offer_price.setText(OFFER_PRICE);
                cbAvailable.setChecked(true);
                spinner_ass_shop_ctegory.setEnabled(false);
                spinner_ass_product_ctegory.setEnabled(false);
                spinner_ass_product.setEnabled(false);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_associate_product:
                if (isAddFromHome) {
                    addAssociatedProductAPI();
                } else {
                    updateAddAssociatedProductAPI();
                }
                break;
        }
    }

    private void addAssociatedProductAPI() {

        String sku = tv_ass_product_add_sku.getText().toString();
        String productAvailability = cbAvailable.isChecked() == true ? "1" : "0";
        String price = et_ass_product_add_price.getText().toString().trim();
        String offerPrice = et_ass_product_add_offer_price.getText().toString().trim();
        if (!DialogUtils.showDialogProduct(this, price, offerPrice)) {
            return;
        }
        showProgressBar(tv_add_associate_product);
        AppHttpRequest request = AppRequestBuilder.addAssociatedProductAPI(shopIdValue, sku, productAvailability, price, offerPrice, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar(tv_add_associate_product);
                showToast(result.getSuccessMessage());
                finish();
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar(tv_add_associate_product);
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void updateAddAssociatedProductAPI() {

        String sku = tv_ass_product_add_sku.getText().toString();
        String productAvailability = cbAvailable.isChecked() == true ? "1" : "0";
        String price = et_ass_product_add_price.getText().toString().trim();
        String offerPrice = et_ass_product_add_offer_price.getText().toString().trim();
        if (!DialogUtils.showDialogProduct(this, price, offerPrice)) {
            return;
        }
        showProgressBar(tv_add_associate_product);
        AppHttpRequest request = AppRequestBuilder.updateAssociatedProductAPI(shopIdValue, sku, productAvailability, price, offerPrice, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar(tv_add_associate_product);
                showToast(result.getSuccessMessage());
                finish();
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar(tv_add_associate_product);
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

}
