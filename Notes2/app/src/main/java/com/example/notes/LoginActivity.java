package com.example.notes;

/****************************************************************************************************************************************************************************************************
                            THIS IS THE LOGIN SCREEN OF THE APP. IT CONTAINS THE CODE FOR THE FIREBASE AUTHENTICATION UI THAT CONTAINS THE VARIOUS LOGIN METHODS
 ***************************************************************************************************************************************************************************************************/

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.Nullable;

public class LoginActivity extends AppCompatActivity {


    int AUTHUI_REQUEST_CODE = 1001;
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
    Date currentLocalTime = calendar.getTime();

    DateFormat dateFormat = new SimpleDateFormat("HH");

    int timeHour;

    LinearLayout layout;
    TextView head;
    //TextView subtitle = findViewById(R.id.textView_subtitle);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        String localTime = dateFormat.format(currentLocalTime);

        layout = findViewById(R.id.login_layout);
        head = findViewById(R.id.textView_heading);

        timeHour = Integer.parseInt(localTime);
        if(timeHour>=5&&timeHour<=12) {

            layout.setBackgroundResource(R.drawable.background_morning);
            head.setText("Good Morning!");
        }

        else if(timeHour>12&&timeHour<=17) {

            layout.setBackgroundResource(R.drawable.background_afternoon);
            head.setText("Good Afternoon!");
        }

        else if(timeHour>17&&timeHour<=20) {

            layout.setBackgroundResource(R.drawable.background_evening);
            head.setText("Good Evening!");
        }

        else if(timeHour>20&&timeHour<=24) {

            layout.setBackgroundResource(R.drawable.background_night);
            head.setText("Good Evening!");
        }

        else if(timeHour>=0&&timeHour<5) {

            layout.setBackgroundResource(R.drawable.background_night);
            head.setText("Good Evening!");
        }

        //Checks if the user is already logged on or not. If he is, it takes him to the MainActivity.
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(this,MainActivity.class));
            this.finish();
        }



    }
    //Method that handles the Login processes.
    public void handleLoginRegister(View view) {

        //This list contains the various ways a user can login.
        List<AuthUI.IdpConfig> provider = Arrays.asList(
          new AuthUI.IdpConfig.EmailBuilder().build(),
                  new AuthUI.IdpConfig.GoogleBuilder().build()

        );

        //Builds the Firebase Authentication (LOGIN) UI
        Intent intent = AuthUI.getInstance()
                .createSignInIntentBuilder()//Creates the Sign in builders that build the sign in methods
                .setAvailableProviders(provider)//Sets the available login methods to the ones mentioned in the above list
                .setTosAndPrivacyPolicyUrls("https://firebase@notes.example.com","https://firebase@notes.example.com")//Handle the PRIVACY POLICY and TOS hyperlinks
                .setLogo(R.drawable.app_icon)//Sets the logo that appears on the top
                .setAlwaysShowSignInMethodScreen(true)//Method to always show the login screen, whenever the user is logged out.
                .build();

        startActivityForResult(intent,AUTHUI_REQUEST_CODE);
    }


    //Handles the login processes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        //Check if the sign in is successful.
        if(requestCode == AUTHUI_REQUEST_CODE) {
            //Check if the user is a new user or a returning one.
            if(resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user.getMetadata().getCreationTimestamp()==user.getMetadata().getLastSignInTimestamp()) {
                    Toast.makeText(this,"Welcome New User!!",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Welcome back Returning User!!", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                this.finish();
            }
            else {
                Toast.makeText(this, "Login/Registration Failed", Toast.LENGTH_SHORT).show();

            }
        }
    }


}
