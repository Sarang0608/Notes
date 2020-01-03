package com.example.notes;

/****************************************************************************************************************************************************************************************************
                                                            THIS ACTIVITY DESCRIBES THE DETAILS OF THE APP AND THE DEVELOPERS OF THE APP
 ***************************************************************************************************************************************************************************************************/

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        TextView app = findViewById(R.id.abt_app_tv);
        TextView link = findViewById(R.id.git_link);
        TextView dev = findViewById(R.id.abt_dev_tv);
        String hyperlink = "<a href ='https://github.com/Sarang0608/Notes.git'>Github</a>";

        app.setText("This App was developed by 'Runtime Terror'. This app stores the notes which are input by the user into a FirebaseFirestore database. The dependencies used in this app are: FirebaseAuthUI(provides the Login UI),FirebaseFirestore(the database which stores all the notes), Glide(downloads the user profile picture from their Google Account), CircularImageView(provides the circular image view in the profile picture)");
        link.setClickable(true);
        link.setMovementMethod(LinkMovementMethod.getInstance());
        link.setText(Html.fromHtml(hyperlink,Html.FROM_HTML_MODE_COMPACT));

        dev.setText("The Back-end of this app was coded by:Sarang Desai, while the front-end was designed by: Johnson B. Major thanks to our mentor Arjun Bajpai for providing the guidance necessary to complete this app.");


    }
}
