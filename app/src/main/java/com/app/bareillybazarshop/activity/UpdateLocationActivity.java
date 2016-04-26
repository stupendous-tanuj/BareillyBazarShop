package com.app.bareillybazarshop.activity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.api.output.ErrorObject;
import com.app.bareillybazarshop.network.AppHttpRequest;
import com.app.bareillybazarshop.network.AppRequestBuilder;
import com.app.bareillybazarshop.network.AppRestClient;
import com.app.bareillybazarshop.network.LocationResponseListener;
import com.app.bareillybazarshop.utils.GPSTracker;
import com.app.bareillybazarshop.utils.PreferenceKeeper;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UpdateLocationActivity extends BaseActivity {

    private TextView tv_current_location;
    private ListView listSearch;
    private AutoCompleteTextView myAutoComplete;
    private String finalSearchAddress;
    private TextView tv_location_update_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_location);
        setHeader("Add Shop Location", "");
        setUI();
        setUIListener();
        setCurrentLocation("");
    }

    private void setUI() {
        tv_location_update_click = (TextView) findViewById(R.id.tv_location_update_click);
        tv_current_location = (TextView) findViewById(R.id.tv_current_location);
        myAutoComplete = (AutoCompleteTextView) findViewById(R.id.auto_complete_locaion);
        listSearch = (ListView) findViewById(R.id.list_location_search);
        setData();
        //view.findViewById(R.id.linearlayout_location).setOnClickListener(this);
    }

    private void setUIListener() {
        findViewById(R.id.tv_use_current_location).setOnClickListener(this);
        findViewById(R.id.tv_location_update_click).setOnClickListener(this);
    }

    private void setCurrentLocation(String finalSearchAddress) {
        if ("".equals(finalSearchAddress)) {
            String flatNumber = PreferenceKeeper.getInstance().getflatNumber();
            String area = PreferenceKeeper.getInstance().getArea();
            String locality = PreferenceKeeper.getInstance().getLocality();
            String city = PreferenceKeeper.getInstance().getCity();
            String state = PreferenceKeeper.getInstance().getState();
            String pincode = PreferenceKeeper.getInstance().getPincode();
            tv_current_location.setText(flatNumber + " " + area + " " + locality + " " + city + " " + state + " " + pincode);
        } else {
            tv_current_location.setText(finalSearchAddress);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_location_update_click:
                getLocationFromAddress(UpdateLocationActivity.this, finalSearchAddress);
                break;
            case R.id.tv_use_current_location:
                updateCurrentLocation();
                break;
        }
    }

    ////////////////////////


    private void setData() {
        myAutoComplete.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Logger.INFO("TAG", "LOCATION DATA : " + charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Logger.INFO("TAG", "LOCATION DATA : " + editable.toString());
                getLocationsBasedOnString(editable.toString());
            }
        });

    }

    private void getLocationsBasedOnString(String writeDate) {

        String key = "key=AIzaSyAyNs0KdHVAhgdtLzl7yEzFmBGH846RqPU";   // Obtain browser key from https://code.google.com/apis/console
        String input = "input=" + URLEncoder.encode(writeDate);
        String types = "types=geocode";    // place type to be searched
        String sensor = "sensor=false";    // Sensor enabled
        String parameters = input + "&" + types + "&" + sensor + "&" + key;   // Building the parameters to the web service
        String output = "json";           // Output format
        String url = output + "?" + parameters;     // Building the url to the web service

        AppHttpRequest request = AppRequestBuilder.getLocationsBasedOnString(url, new LocationResponseListener<ArrayList>(ArrayList.class, this) {
            @Override
            public void onSuccess(final ArrayList list) {
                LocationSearchAdapter locationSearchAdapter = new LocationSearchAdapter(UpdateLocationActivity.this, list);
                listSearch.setAdapter(locationSearchAdapter);
                locationSearchAdapter.notifyDataSetChanged();
                listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        myAutoComplete.setText(list.get(position).toString());
                        finalSearchAddress = list.get(position).toString();
                        setCurrentLocation(finalSearchAddress);
                        hideSoftKeyboard();
                    }
                });
            }

            @Override
            public void onError(ErrorObject error) {
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }


    public class LocationSearchAdapter extends BaseAdapter {

        private final LayoutInflater inflater;
        private Context context;
        private ViewHolder viewHolder;
        private List<String> data;

        public LocationSearchAdapter(Context ctx, List<String> data) {
            context = ctx;
            this.data = data;
            inflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return data != null ? data.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return data != null ? data.get(position) : null;
        }


        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup viewGroup) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.adapter_location_search, viewGroup, false);
                viewHolder = new ViewHolder();
                viewHolder.tv_location_search = (TextView) convertView.findViewById(R.id.tv_location_search);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            setData(viewHolder, (String) getItem(pos));
            return convertView;
        }

        private void setData(ViewHolder viewHolder, String item) {
            viewHolder.tv_location_search.setText(item);
        }

        public class ViewHolder {
            TextView tv_location_search;
        }
    }

    private class UpdateLocationAsy extends AsyncTask<Void, Void, Address> {

        private BaseActivity activity;
        private GPSTracker gps;

        public UpdateLocationAsy(BaseActivity activity, GPSTracker gps) {
            this.activity = activity;
            this.gps = gps;
            showProgressBar();
        }

        @Override
        protected Address doInBackground(Void... params) {
            if (gps != null) {
                double latitude = gps.getLatitude();
                double longit = gps.getLongitude();
                return getAddressFromLocation(latitude, longit);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Address returnedAddress) {
            hideProgressBar();
            if (returnedAddress != null) {
                setUserAddress(returnedAddress);
                hideProgressBar();
                UpdateLocationActivity.this.finish();
            } else {
                showToast("Address is not found");
            }
        }
    }

    private void setUserAddress(Address returnedAddress) {
        String flatNumber = returnedAddress.getFeatureName();
        String area = returnedAddress.getSubAdminArea();
        String locality = returnedAddress.getSubLocality();
        String city = returnedAddress.getLocality();
        String state = returnedAddress.getAdminArea();
        String pincode = returnedAddress.getPostalCode();

        PreferenceKeeper.getInstance().setflatNumber(flatNumber);
        PreferenceKeeper.getInstance().setArea(area);
        PreferenceKeeper.getInstance().setLocality(locality);
        PreferenceKeeper.getInstance().setCity(city);
        PreferenceKeeper.getInstance().setState(state);
        PreferenceKeeper.getInstance().setPincode(pincode);
    }

    private void updateCurrentLocation() {
        GPSTracker gps = new GPSTracker(this);
        if (gps.canGetLocation()) {
            UpdateLocationAsy runner = new UpdateLocationAsy(this, gps);
            runner.execute();
        } else {
            gps.showSettingsAlert();
        }
    }

    public Address getAddressFromLocation(double lat, double lon) {
        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
            if (addresses != null) {
                if (addresses.size() > 0) {
                    Address returnedAddress = addresses.get(0);
                    return returnedAddress;
                }
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return;
            }
            if (address.size() > 0) {
                Address location = address.get(0);
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                SearchLocationAsy runner = new SearchLocationAsy(this, latitude, longitude);
                runner.execute();
            } else {
                showToast("Address is not found");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private class SearchLocationAsy extends AsyncTask<Void, Void, Address> {

        private double latitude;
        private double longitute;

        public SearchLocationAsy(BaseActivity activity, double latitude, double longitute) {
            this.latitude = latitude;
            this.longitute = longitute;
            showProgressBar(findViewById(R.id.tv_location_update_click));
        }

        @Override
        protected Address doInBackground(Void... params) {
            return getAddressFromLocation(latitude, longitute);
        }

        @Override
        protected void onPostExecute(Address returnedAddress) {
            hideProgressBar(findViewById(R.id.tv_location_update_click));
            if (returnedAddress != null) {
                setUserAddress(returnedAddress);
                UpdateLocationActivity.this.finish();
            } else {
                showToast("Address is not found");
            }
        }
    }
}
