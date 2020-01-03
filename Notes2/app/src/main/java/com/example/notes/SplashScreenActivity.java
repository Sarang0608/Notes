package com.example.notes;

/*****************************************************************************************************************************************************************************************************
                                                THIS ACTIVITY HANDLES THE SPLASH SCREEN (THE SCREEN THAT APPEARS FOR 3 SECONDS BEFORE THE APP STARTS)
 ****************************************************************************************************************************************************************************************************/

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.WindowManager;

import java.util.prefs.Preferences;

public class SplashScreenActivity extends AppCompatActivity {

    boolean getFingerSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreenActivity.this,FingerPrintActivty.class);
                    startActivity(intent);
                    finish();
                }
            },3000);


        }
}
