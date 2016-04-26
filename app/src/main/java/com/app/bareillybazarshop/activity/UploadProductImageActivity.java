package com.app.bareillybazarshop.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bareillybazarshop.R;


public class UploadProductImageActivity extends BaseActivity {

    TextView tv_uploadImage;
    TextView tv_camera;
    TextView tv_gallery;
    ImageView iv_productImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_product_image);
        setUI();
        setHeader(getString(R.string.header_upload_product_image), "");
        setUIListener();
        //TODO Upload product image activity

    }

    public void setUI() {
        tv_uploadImage = (TextView) findViewById(R.id.tv_uploadImage);
        tv_camera = (TextView) findViewById(R.id.tv_camera);
        tv_gallery = (TextView) findViewById(R.id.tv_gallery);
        iv_productImage = (ImageView) findViewById(R.id.iv_productImage);
    }

    private void setUIListener() {
        tv_uploadImage.setOnClickListener(this);
    }

}
