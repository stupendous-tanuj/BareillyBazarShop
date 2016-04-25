package com.app.shopkeeper.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.shopkeeper.R;
import com.app.shopkeeper.adapter.HomeAdapter;
import com.app.shopkeeper.api.output.ErrorObject;
import com.app.shopkeeper.api.output.MyOrderDetailResponse;
import com.app.shopkeeper.api.output.OrderDetail;
import com.app.shopkeeper.constant.AppConstant;
import com.app.shopkeeper.network.AppHttpRequest;
import com.app.shopkeeper.network.AppRequestBuilder;
import com.app.shopkeeper.network.AppResponseListener;
import com.app.shopkeeper.network.AppRestClient;
import com.app.shopkeeper.utils.AppUtil;
import com.app.shopkeeper.utils.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends BaseActivity {

    private Toolbar toolbar;
    private DrawerLayout drawer_home_root;
    private ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout ll_home_layout;
    private LinearLayout nav_home_left_drawer;
    private TextView homeTitle;
    private LinearLayout linear_home_logout;
    private TextView tv_home_left_mobile;
    private TextView tv_home_address;
    private TextView tv_home_left_name;
    private RecyclerView recycleView;
    private TextView no_data_available;
    private TextView tv_from_dte_home;
    private TextView tv_to_dte_home;
    private static DatePickerDialog.OnDateSetListener mDateListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setHeader("Shop Keeper", "");
        initUI();// initialized component
        setRecycler();
        initUIListner();
        initDrawerToggle(); // set listener of drawer with toggle
        getCurrentTime();
    }

    private void getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String fromDate = dateFormat.format(cal.getTime());
        String toDate = dateFormat.format(cal.getTime());
        tv_from_dte_home.setText(fromDate);
        tv_to_dte_home.setText(toDate);
        fetchMyOrderDetailApi(fromDate, toDate);
    }


    private void setRecycler() {
        recycleView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(mLayoutManager);
    }


    private void fetchMyOrderDetailApi(String fromDate, String toDate) {
        String useType = "Shop";
        Logger.INFO(TAG, "From : " + fromDate);
        Logger.INFO(TAG, "To : " + toDate);

        long from = AppUtil.getMillisFromDate(fromDate);
        long to = AppUtil.getMillisFromDate(toDate);

        if (from > to) {
            showToast("From Date cannot be greater then To Date");
            return;
        }
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.fetchMyOrderDetailAPI(useType, fromDate, toDate, new AppResponseListener<MyOrderDetailResponse>(MyOrderDetailResponse.class, this) {
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
                    String from = tv_from_dte_home.getText().toString();
                    String to = tv_to_dte_home.getText().toString();
                    fetchMyOrderDetailApi(from, to);
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

    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_from_dte_home = (TextView) findViewById(R.id.tv_from_dte_home);
        tv_to_dte_home = (TextView) findViewById(R.id.tv_to_dte_home);
        no_data_available = (TextView) findViewById(R.id.no_data_available);
        recycleView = (RecyclerView) findViewById(R.id.rv_home_activity_my_order);
        // left
        tv_home_address = (TextView) findViewById(R.id.tv_home_address);
        linear_home_logout = (LinearLayout) findViewById(R.id.linear_home_logout);
        drawer_home_root = (DrawerLayout) findViewById(R.id.drawer_home_root);        // mDrawerLayout object Assigned to the view
        drawer_home_root.setScrimColor(Color.TRANSPARENT);
        ll_home_layout = (RelativeLayout) findViewById(R.id.ll_home_layout);
        nav_home_left_drawer = (LinearLayout) findViewById(R.id.nav_home_left_drawer);
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
        findViewById(R.id.linear_home_apprate).setOnClickListener(this);
        findViewById(R.id.linear_home_shareapp).setOnClickListener(this);
        findViewById(R.id.linear_home_contactus).setOnClickListener(this);
        findViewById(R.id.linear_home_logout).setOnClickListener(this);
        findViewById(R.id.linear_home_from_dte).setOnClickListener(this);
        findViewById(R.id.linear_home_to_dte).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        drawer_home_root.closeDrawer(nav_home_left_drawer);
        switch (view.getId()) {
            case R.id.linear_home_shopkeeper_profile:
                launchActivity(ShopKeeperProfileActivity.class);
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
                launchActivity(AssociateDeliveryPersonActivity.class);
                break;
            case R.id.linear_home_add_delivery_person:
                launchActivity(AddDeliveryPersonActivity.class);
                break;
            case R.id.linear_home_shop_operation_time:
                launchActivity(ShopOperationTimeActivity.class);
                break;
            case R.id.linear_home_apprate:
                rateApp();
                break;
            case R.id.linear_home_shareapp:
                shareApp();
                break;
            case R.id.linear_home_contactus:
                launchActivity(ContactUsActivity.class);
                break;
            case R.id.linear_home_logout:
                // launchActivity(ShopOperationTimeActivity.class);
                break;
            case R.id.linear_home_from_dte:
                setDte(tv_from_dte_home);
                break;
            case R.id.linear_home_to_dte:
                setDte(tv_to_dte_home);
                break;
        }
    }


    private void shareApp() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Here is the share content body";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
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
