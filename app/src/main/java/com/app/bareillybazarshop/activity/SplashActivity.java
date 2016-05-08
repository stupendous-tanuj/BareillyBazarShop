package com.app.bareillybazarshop.activity;

import android.os.Bundle;
import android.os.Handler;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.listner.IDialogListener;
import com.app.bareillybazarshop.network.AppRestClient;
import com.app.bareillybazarshop.notification.GcmIdGenerator;
import com.app.bareillybazarshop.utils.AppUtil;
import com.app.bareillybazarshop.utils.DialogUtils;
import com.app.bareillybazarshop.utils.PreferenceKeeper;


public class SplashActivity extends BaseActivity {

    private static final long SPLASH_TIME_OUT = 1500;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setDeviceInfo();
        setHandler();
    }

    private void setDeviceInfo() {
        PreferenceKeeper.getInstance().setDeviceInfo(AppUtil.getDeviceId(this));
    }


    private void setHandler() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (AppUtil.isNetworkAvailable(SplashActivity.this)) {
                    boolean isUserLogin = PreferenceKeeper.getInstance().getIsLogin();
                    if (isUserLogin) {
                        launchActivityMain(HomeActivity.class);
                       // verifyAppApi();
                    } else {
                        launchActivityMain(LoginActivity.class);
                    }
                    finish();
                } else {
                    launchActivity(NoInternetConnectionActivity.class);
                    /*
                    DialogUtils.showDialogNetwork(SplashActivity.this, getString(R.string.not_network), new IDialogListener() {
                        @Override
                        public void onClickOk() {
                            finish();
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                    */
                }
            }
        };
        handler.postDelayed(runnable, SPLASH_TIME_OUT);
    }



    @Override
    public void onBackPressed() {
        AppRestClient.getClient().cancelAll(TAG);
        handler.removeCallbacks(runnable);
        super.onBackPressed();
    }
/*
    private void verifyAppApi() {
        AppHttpRequest request = AppRequestBuilder.verifyApp(new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                launchActivityMain(HomeActivity.class);
            }

            @Override
            public void onError(ErrorObject error) {
                DialogUtils.showDialog(SplashActivity.this, error.getErrorMessage());
            }
        });

        AppRestClient.getClient().sendRequest(this, request, TAG);
    }*/
}
