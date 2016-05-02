package com.app.bareillybazarshop.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.adapter.HomeAdapter;
import com.app.bareillybazarshop.api.output.AssociatedShopId;
import com.app.bareillybazarshop.api.output.AssociatedShopIdResponse;
import com.app.bareillybazarshop.api.output.CommonResponse;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.api.output.MyOrderDetailResponse;
import com.app.bareillybazarshop.api.output.OrderDetail;
import com.app.bareillybazarshop.constant.AppConstant;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppResponseListener;
import com.app.bareillybazarshop.network.AppRestClient;
import com.app.bareillybazarshop.utils.AppUtil;
import com.app.bareillybazarshop.utils.DialogUtils;
import com.app.bareillybazarshop.utils.Logger;
import com.app.bareillybazarshop.utils.PreferenceKeeper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends BaseActivity {

    private Toolbar toolbar;
    private DrawerLayout drawer_home_root;
    private ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout ll_home_layout;
    private LinearLayout nav_home_left_drawer;
    private TextView homeTitle;
    private TextView tv_home_left_mobile;
    private TextView tv_userId;
    private TextView tv_userType;
    private RecyclerView recycleView;
    private TextView no_data_available;
    private TextView tv_from_dte_home;
    private TextView tv_to_dte_home;
    LinearLayout linear_home_shopkeeper_profile = null;
    LinearLayout linear_home_seller_hub_profile = null;
    LinearLayout linear_home_associated_product = null;
    LinearLayout linear_home_add_a_product = null;
    LinearLayout linear_home_associated_delivery_person = null;
    LinearLayout linear_home_add_delivery_person = null;
    LinearLayout linear_home_shop_operation_time = null;
    LinearLayout linear_home_contactus = null;
    LinearLayout linear_home_logout = null;
    LinearLayout linear_home_from_dte = null;
    LinearLayout linear_home_to_dte = null;

    LinearLayout linear_home_my_orders = null;
    LinearLayout linear_home_associated_shops = null;
    LinearLayout linear_change_password = null;
    LinearLayout linear_home_settings = null;
    LinearLayout linear_bar_deliveryLocation = null;
    LinearLayout linear_bar_deliveryPerson = null;
    LinearLayout linear_bar_product = null;
    LinearLayout linear_home_addAShop = null;
    LinearLayout linear_home_associatedProductCategory = null;
    LinearLayout linear_home_all_delivery_person = null;
    LinearLayout linear_home_delivery_location = null;
    LinearLayout linear_home_addADeliveryLocation = null;


    private static DatePickerDialog.OnDateSetListener mDateListner;
    private Spinner spinner_orderStatus;
    private Spinner spinner_shopId;
    LinearLayout ll_shopId;
    LinearLayout ll_orderStatus;
    String shopIdValue = "";
    String orderStatusValue = "ALL";
    String USER_TYPE = "";
    String from = "";
    String to = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        setHeader(getString(R.string.header_my_orders) + " - " + USER_TYPE, "");
        initUI();// initialized component
        setRecycler();
        initUIListner();
        initDrawerToggle(); // set listener of drawer with toggle
        getCurrentTime();
        verifyApplicationIDAPI();
        fetchMyOrderDetailApi();
    }

    private void getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        from = dateFormat.format(cal.getTime());
        to = dateFormat.format(cal.getTime());
        tv_from_dte_home.setText(Html.fromHtml("<u>" + from + "</u>"));
        tv_to_dte_home.setText(Html.fromHtml("<u>" + to + "</u>"));
        if(!shopIdValue.equals(getString(R.string.please_select)))
            fetchMyOrderDetailApi();
    }


    private void setRecycler() {
        recycleView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(mLayoutManager);
    }


    private void fetchMyOrderDetailApi() {


        long longFrom = AppUtil.getMillisFromDate(from);
        long longTo = AppUtil.getMillisFromDate(to);

        if(USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE)) {
            shopIdValue = PreferenceKeeper.getInstance().getUserId();
        }

        if (longFrom > longTo) {
            showToast(getString(R.string.from_date_greater));
            return;
        }

        if (!DialogUtils.isSpinnerDefaultValue(this, shopIdValue, getString(R.string.label_Shop_ID)) || (shopIdValue.equals(""))) {
            return;
        }

        showProgressBar();

        AppHttpRequest request = AppRequestBuilder.fetchMyOrderDetailAPI(shopIdValue, orderStatusValue ,from, to, new AppResponseListener<MyOrderDetailResponse>(MyOrderDetailResponse.class, this) {
            @Override
            public void onSuccess(MyOrderDetailResponse result) {
                hideProgressBar();
                setVisibleUI(result.getOrderDetails());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
                setVisibleUI(null);
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setDte(final TextView tv) {
        showDatePickerDialog();
        mDateListner = new DatePickerDialog.OnDateSetListener() {
            //         "fromDate": "2016-02-14",
            @Override
            public void onDateSet(final DatePicker datePicker, int year, int month, int day) {
                if (datePicker.isShown()) {
                    Logger.INFO(TAG, "listner ");
                    tv.setText(year + "-" + getData(++month) + "-" + getData(day));
                    from = tv_from_dte_home.getText().toString();
                    to = tv_to_dte_home.getText().toString();
                    if(!shopIdValue.equals(getString(R.string.please_select)))
                    fetchMyOrderDetailApi();
                }
            }
        };
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), mDateListner, year, month, day);
        }
    }

    private void setVisibleUI(List<OrderDetail> carts) {
        if (carts == null || carts.size() == 0) {
            recycleView.setVisibility(View.GONE);
            no_data_available.setVisibility(View.VISIBLE);
            no_data_available.setText("My order is not available.");
        } else {
            recycleView.setVisibility(View.VISIBLE);
            no_data_available.setVisibility(View.GONE);
            setMyOrderAdapterData(carts);
        }
    }

    private void setMyOrderAdapterData(List<OrderDetail> carts) {
        HomeAdapter placeOrderlAdapter = new HomeAdapter(this, carts);
        recycleView.setAdapter(placeOrderlAdapter);
    }

    private void setOrderStatusSpinner()
    {
        final List<String> orderStatus = new ArrayList<>();
        orderStatus.add(AppConstant.STATUS.STATUS_ALL);
        orderStatus.add(AppConstant.STATUS.STATUS_ORDERED);
        orderStatus.add(AppConstant.STATUS.STATUS_CONFIRMED);
        orderStatus.add(AppConstant.STATUS.STATUS_SUBSCRIBED);
        orderStatus.add(AppConstant.STATUS.STATUS_DELIVERED);
        orderStatus.add(AppConstant.STATUS.STATUS_CLOSED);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, orderStatus);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_orderStatus.setAdapter(dataAdapter);
        spinner_orderStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                orderStatusValue = orderStatus.get(pos);
                if (!shopIdValue.equals(getString(R.string.please_select)))
                    fetchMyOrderDetailApi();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                if (!shopIdValue.equals(getString(R.string.please_select)))
                    fetchMyOrderDetailApi();
            }
        });
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
                    fetchMyOrderDetailApi();
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


    private void verifyApplicationIDAPI() {
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.VerifyApplicationIDAPI(new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar();
                setOrderStatusSpinner();
                if (USER_TYPE.equals(AppConstant.UserType.SELLER_HUB_TYPE) || USER_TYPE.equals(AppConstant.UserType.DELIVERY_PERSON_TYPE)) {
                    associateShopIdAPI();
                }
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
                showToast("Application is Blocked.");
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }


    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_from_dte_home = (TextView) findViewById(R.id.tv_from_dte_home);
        tv_to_dte_home = (TextView) findViewById(R.id.tv_to_dte_home);
        no_data_available = (TextView) findViewById(R.id.no_data_available);
        recycleView = (RecyclerView) findViewById(R.id.rv_home_activity_my_order);
        // left
        tv_userId = (TextView) findViewById(R.id.tv_userId);
        tv_userType = (TextView) findViewById(R.id.tv_userType);
        linear_home_logout = (LinearLayout) findViewById(R.id.linear_home_logout);
        drawer_home_root = (DrawerLayout) findViewById(R.id.drawer_home_root);        // mDrawerLayout object Assigned to the view
        drawer_home_root.setScrimColor(Color.TRANSPARENT);
        ll_home_layout = (RelativeLayout) findViewById(R.id.ll_home_layout);
        nav_home_left_drawer = (LinearLayout) findViewById(R.id.nav_home_left_drawer);
        linear_home_shopkeeper_profile = (LinearLayout) findViewById(R.id.linear_home_shopkeeper_profile);
        linear_home_seller_hub_profile = (LinearLayout) findViewById(R.id.linear_home_seller_hub_profile);
        linear_home_associated_product = (LinearLayout) findViewById(R.id.linear_home_associated_product);
        linear_home_delivery_location  = (LinearLayout) findViewById(R.id.linear_home_delivery_location);
        linear_home_add_a_product = (LinearLayout) findViewById(R.id.linear_home_add_a_product);
        linear_home_associated_delivery_person = (LinearLayout) findViewById(R.id.linear_home_associated_delivery_person);
        linear_home_add_delivery_person = (LinearLayout) findViewById(R.id.linear_home_add_delivery_person);
        linear_home_shop_operation_time = (LinearLayout) findViewById(R.id.linear_home_shop_operation_time);
        linear_home_settings = (LinearLayout) findViewById(R.id.linear_home_settings);
        linear_home_contactus = (LinearLayout) findViewById(R.id.linear_home_contactus);
        linear_home_logout = (LinearLayout) findViewById(R.id.linear_home_logout);
        linear_home_from_dte = (LinearLayout) findViewById(R.id.linear_home_from_dte);
        linear_home_to_dte = (LinearLayout) findViewById(R.id.linear_home_to_dte);
        linear_change_password = (LinearLayout) findViewById(R.id.linear_change_password);
        linear_home_my_orders = (LinearLayout) findViewById(R.id.linear_home_my_orders);
        linear_home_associated_shops  = (LinearLayout) findViewById(R.id.linear_home_associated_shops);
        linear_home_addAShop = (LinearLayout) findViewById(R.id.linear_home_addAShop);
        linear_home_addADeliveryLocation = (LinearLayout) findViewById(R.id.linear_home_addADeliveryLocation);
        linear_home_all_delivery_person = (LinearLayout) findViewById(R.id.linear_home_all_delivery_person);




        //linear_home_associateAProductCategory = (LinearLayout) findViewById(R.id.linear_home_associateAProductCategory);
        linear_home_associatedProductCategory = (LinearLayout) findViewById(R.id.linear_home_associatedProductCategory);
        spinner_orderStatus = (Spinner) findViewById(R.id.spinner_orderStatus);
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        spinner_shopId = (Spinner) findViewById(R.id.spinner_shopId);
        ll_shopId = (LinearLayout) findViewById(R.id.ll_shopId);
        ll_orderStatus = (LinearLayout) findViewById(R.id.ll_orderStatus);
        if((USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE))) {
            ll_shopId.setVisibility(View.GONE);
        }
        else {
            ll_shopId.setVisibility(View.VISIBLE);
        }



        linear_bar_deliveryLocation = (LinearLayout) findViewById(R.id.linear_bar_deliveryLocation);
        linear_bar_deliveryPerson = (LinearLayout) findViewById(R.id.linear_bar_deliveryPerson);
        linear_bar_product = (LinearLayout) findViewById(R.id.linear_bar_product);

        if(USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE)) {
            linear_home_associated_shops.setVisibility(View.GONE);
            linear_home_addAShop.setVisibility(View.GONE);
            linear_home_addADeliveryLocation.setVisibility(View.GONE);
            linear_home_all_delivery_person.setVisibility(View.GONE);
        }
        else if(USER_TYPE.equals(AppConstant.UserType.SELLER_HUB_TYPE)) {
            linear_home_seller_hub_profile.setVisibility(View.GONE);
        }
        else if(USER_TYPE.equals(AppConstant.UserType.DELIVERY_PERSON_TYPE)) {
            //Menu
            linear_home_shop_operation_time.setVisibility(View.GONE);
            linear_home_associated_product.setVisibility(View.GONE);
            linear_home_add_a_product.setVisibility(View.GONE);
            linear_home_associated_delivery_person.setVisibility(View.GONE);
            linear_home_add_delivery_person.setVisibility(View.GONE);
            linear_home_delivery_location.setVisibility(View.GONE);
            linear_home_addAShop.setVisibility(View.GONE);
            linear_home_addADeliveryLocation.setVisibility(View.GONE);
            linear_home_all_delivery_person.setVisibility(View.GONE);
            //Bars
            linear_bar_deliveryLocation.setVisibility(View.GONE);
            linear_bar_deliveryPerson.setVisibility(View.GONE);
            linear_bar_product.setVisibility(View.GONE);
            linear_home_associatedProductCategory.setVisibility(View.GONE);
        }


        tv_userId.setText(PreferenceKeeper.getInstance().getUserId());
        tv_userType.setText(PreferenceKeeper.getInstance().getUserType());
    }

    private void initDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer_home_root, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float moveFactor = (linear_home_logout.getWidth() * slideOffset);
                ll_home_layout.setTranslationX(moveFactor);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }
        }; // mDrawerLayout Toggle Object Made
        drawer_home_root.setDrawerListener(mDrawerToggle); // mDrawerLayout Listener set to the mDrawerLayout toggle
    }

    private void initUIListner() {
        findViewById(R.id.linear_home_shopkeeper_profile).setOnClickListener(this);
        findViewById(R.id.linear_home_seller_hub_profile).setOnClickListener(this);
        findViewById(R.id.linear_home_associated_product).setOnClickListener(this);
        findViewById(R.id.linear_home_associate_a_product).setOnClickListener(this);
        findViewById(R.id.linear_home_view_available_product).setOnClickListener(this);
        findViewById(R.id.linear_home_add_a_product).setOnClickListener(this);
        findViewById(R.id.linear_home_associated_delivery_person).setOnClickListener(this);
        findViewById(R.id.linear_home_add_delivery_person).setOnClickListener(this);
        findViewById(R.id.linear_home_shop_operation_time).setOnClickListener(this);
        findViewById(R.id.linear_home_contactus).setOnClickListener(this);
        findViewById(R.id.linear_home_logout).setOnClickListener(this);
        findViewById(R.id.linear_home_from_dte).setOnClickListener(this);
        findViewById(R.id.linear_home_to_dte).setOnClickListener(this);
        linear_home_settings.setOnClickListener(this);
        linear_change_password.setOnClickListener(this);
        linear_home_associated_shops.setOnClickListener(this);
        linear_home_addAShop.setOnClickListener(this);
        linear_home_my_orders.setOnClickListener(this);
        linear_home_all_delivery_person.setOnClickListener(this);
        linear_home_delivery_location.setOnClickListener(this);
        linear_home_addADeliveryLocation.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        drawer_home_root.closeDrawer(nav_home_left_drawer);
        switch (view.getId()) {
            case R.id.linear_home_shopkeeper_profile:
                if(USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE))
                    launchActivity(ShopKeeperProfileActivity.class);
                else if(USER_TYPE.equals(AppConstant.UserType.DELIVERY_PERSON_TYPE))
                    launchActivity(DeliveryPersonProfileActivity.class);
                else if(USER_TYPE.equals(AppConstant.UserType.SELLER_HUB_TYPE))
                    launchActivity(SellerHUbProfileActivity.class);
                break;
            case R.id.linear_home_seller_hub_profile:
                launchActivity(SellerHUbProfileActivity.class);
                break;
            case R.id.linear_home_associated_product:
                launchActivity(AssociatedProductActivity.class);
                break;
            case R.id.linear_home_associate_a_product:
                Bundle bundle = new Bundle();
                bundle.putBoolean(AppConstant.BUNDLE_KEY.IS_ADD_ASSOCIATE_PRODUCT_HOME, true);
                launchActivity(AssociateAProductActivity.class, bundle);
                break;
            case R.id.linear_home_view_available_product:
                launchActivity(ViewAvailableProductActivity.class);
                break;
            case R.id.linear_home_add_a_product:
                launchActivity(AddProductActivity.class);
                break;
            case R.id.linear_home_associated_delivery_person:
                launchActivity(AssociatedDeliveryPersonActivity.class);
                break;
            case R.id.linear_home_add_delivery_person:
                launchActivity(AddDeliveryPersonActivity.class);
                break;
            case R.id.linear_home_shop_operation_time:
                launchActivity(ViewShopOperationalTimeActivity.class);
                break;
            case R.id.linear_home_contactus:
                launchActivity(ContactUsActivity.class);
                break;
            case R.id.linear_home_logout:
                PreferenceKeeper.getInstance().clearData();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
                break;
            case R.id.linear_home_from_dte:
                setDte(tv_from_dte_home);
                break;
            case R.id.linear_home_to_dte:
                setDte(tv_to_dte_home);
                break;
            case R.id.linear_change_password:
                launchActivity(ChangePasswordActivity.class);
                break;
            case R.id.linear_home_associated_shops:
                launchActivity(AssociatedShopsActivity.class);
                break;
            case R.id.linear_home_settings:
                launchActivity(SettingsActivity.class);
                break;
            case R.id.linear_home_addAShop:
                launchActivity(AddAShopActivity.class);
                break;
            case R.id.linear_home_my_orders:
                launchActivity(HomeActivity.class);
                break;
            case R.id.linear_home_all_delivery_person:
                launchActivity(AllDeliveryPersonsActivity.class);
                break;
            case R.id.linear_home_delivery_location:
                launchActivity(AssociatedDeliveryLocationsActivity.class);
                break;
            case R.id.linear_home_addADeliveryLocation:
                launchActivity(AddADeliveryLocationActivity.class);
                break;



        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


}
