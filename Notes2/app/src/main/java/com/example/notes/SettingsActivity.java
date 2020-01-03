package com.example.notes;

/****************************************************************************************************************************************************************************************************
                                                                    THIS ACTIVITY HANDLES ALL THE SETTINGS THAT ARE AVAILABLE IN THIS APP
 ***************************************************************************************************************************************************************************************************/

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Toolbar toolbar;
    Switch aSwitch;
    SharedPreferences sharedPreferences;
    boolean switchStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        int whiteColorValue = Color.WHITE;

        toolbar = findViewById(R.id.settings_toolbar);
        toolbar.setTitleTextColor(whiteColorValue);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");


        aSwitch = findViewById(R.id.fingerprint_switch);

        sharedPreferences = getApplicationContext().getSharedPreferences("FingerprintPref",MODE_PRIVATE);
        final SharedPreferences.Editor editor =sharedPreferences.edit();

        switchStatus = sharedPreferences.getBoolean("isTrue",false);


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(aSwitch.isChecked() == true) {

                aSwitch.setChecked(true);
                editor.putBoolean("isTrue",true);
                editor.commit();
                }

                else {

                    aSwitch.setChecked(false);
                    editor.putBoolean("isTrue",false);
                    editor.commit();
                }

            }
        });

        if( switchStatus == true ) {

            aSwitch.setChecked(true);

        }
        else {
            aSwitch.setChecked(false);


        }



        Spinner spinner = findViewById(R.id.theme_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Themes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

       switch(adapterView.getSelectedItem().toString()) {
           case "Dark" :
               getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
               break;
       }
       }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}