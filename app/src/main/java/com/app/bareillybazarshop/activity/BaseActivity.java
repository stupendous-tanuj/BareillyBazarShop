package com.app.bareillybazarshop.activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.listner.IRightListner;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Calendar;

public class BaseActivity extends FragmentActivity implements View.OnClickListener {


    public final String TAG = BaseActivity.class.getSimpleName();

    private ProgressBar progressBar;
    protected Toolbar toolbar;
    private ImageView ivBack;
    private TextView tvCenter;
    private TextView tvRight;

    public void showTimePickerDialog(TimePickerDialog.OnTimeSetListener mTimeListner) {
        DialogFragment newFragment = new TimePickerFragment(mTimeListner);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    /*

      time picker
    */
    public static class TimePickerFragment extends DialogFragment
           /* implements TimePickerDialog.OnTimeSetListener */ {
        private TimePickerDialog.OnTimeSetListener mTimeListner;

        public TimePickerFragment(TimePickerDialog.OnTimeSetListener mTimeListner) {
            this.mTimeListner = mTimeListner;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), mTimeListner, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }
    }


    public void callPhone(String phoneNumber)
    {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + this.getString(R.string.label_Country_Code) + phoneNumber));
        this.startActivity(intent);
    }

    public ImageLoader imageLoader = ImageLoader.getInstance();
    public DisplayImageOptions imageOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
            .cacheOnDisk(true)
            .resetViewBeforeLoading(true).showImageOnFail(R.drawable.index).showImageOnLoading(R.drawable.index).showImageForEmptyUri(R.drawable.index)
            .considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565)
            .build();


    public String getData(int i) {
        return String.valueOf((i > 9 ? i : "0" + i));
    }

    public void setHeader(String center, String right) {
        ivBack = (ImageView) findViewById(R.id.iv_main_back);
        tvCenter = (TextView) findViewById(R.id.tv_main_center);
        tvRight = (TextView) findViewById(R.id.tv_main_right);

        tvCenter.setText(center);
        if (right.equalsIgnoreCase("")) {
            tvRight.setVisibility(View.GONE);
        } else {
            tvRight.setText(right);
        }

        if (ivBack != null) {
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    public void setHeader(String center, String right, final IRightListner listner) {
        setHeader(center, right);
        if (listner != null) {
            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.onRightClick();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void launchActivity(Class<? extends BaseActivity> activityClass) {
        startActivity(new Intent(this, activityClass));
    }

    public void launchActivity(Class<? extends BaseActivity> classType, Bundle bundle) {
        Intent intent = new Intent(this, classType);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void launchActivityMain(Class<? extends BaseActivity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View view) {

    }

    public void showProgressBar(View view) {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
        if (view != null) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public void hideProgressBar(View view) {
        if (progressBar != null && progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void showProgressBar() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        if (progressBar != null && progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
