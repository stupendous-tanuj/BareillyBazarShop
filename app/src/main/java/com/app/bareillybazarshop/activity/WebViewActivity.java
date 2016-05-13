package com.app.bareillybazarshop.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.constant.AppConstant;

public class WebViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        if (getIntent() != null && getIntent().getExtras() != null) {
            String searchKey = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.LINK);
            Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
            search.putExtra(SearchManager.QUERY, searchKey);
            startActivity(search);
        }
        finish();

    }

}
