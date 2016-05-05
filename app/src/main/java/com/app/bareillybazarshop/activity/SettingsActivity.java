package com.app.bareillybazarshop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.constant.AppConstant;
import com.app.bareillybazarshop.utils.PreferenceKeeper;

import java.util.List;

public class SettingsActivity extends BaseActivity {

    private Spinner spinner_selectLanguage = null;
    String language = "";
    private TextView tv_applySettings = null;
    private TextView tv_currentLanguage = null;

    @Override
    protected void onCreate(Bundle savedInstanceLanguage) {
        super.onCreate(savedInstanceLanguage);
        setContentView(R.layout.activity_settings);
        setHeader(getString(R.string.header_App_Settings), "");
        setUI();
        setLanguageSpinner();
        setCurrentLanguage();
    }

    public void setUI() {
        spinner_selectLanguage = (Spinner) findViewById(R.id.spinner_selectLanguage);
        tv_applySettings = (TextView) findViewById(R.id.tv_applySettings);
        tv_currentLanguage = (TextView) findViewById(R.id.tv_currentLanguage);
        tv_applySettings.setOnClickListener(this);
    }


    private void setCurrentLanguage() {
        String locale = PreferenceKeeper.getInstance().getLocale();
        String language = "";
        if(locale.equals("en"))
            language = AppConstant.LANGUAGE.LANGUAGE_ENGLISH;
        else if(locale.equals("hi"))
            language = AppConstant.LANGUAGE.LANGUAGE_HINDI;
        else if(locale.equals("mr"))
            language = AppConstant.LANGUAGE.LANGUAGE_MARATHI;

        tv_currentLanguage.setText(language);
    }

        private void setLanguageSpinner()
    {
        final List<String> LanguageList = AppConstant.fetchLanguages();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, LanguageList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_selectLanguage.setAdapter(dataAdapter);
        spinner_selectLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                language = spinner_selectLanguage.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_applySettings:
                applyLanguage();
                break;
        }
    }

    private void applyLanguage()
    {
        String locale = "";
        if(language.equals(AppConstant.LANGUAGE.LANGUAGE_ENGLISH))
            locale = "en";
        else if(language.equals(AppConstant.LANGUAGE.LANGUAGE_HINDI))
            locale = "hi";
        else if(language.equals(AppConstant.LANGUAGE.LANGUAGE_MARATHI))
            locale = "mr";
        PreferenceKeeper.getInstance().setLocale(locale);
        showToast(getString(R.string.msg_settings_update_success));
        setCurrentLanguage();
    }



}
