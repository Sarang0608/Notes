package com.example.notes;
/*****************************************************************************************************************************************************************************************************
                                                     THIS FRAGMENT CONTAINS THE DETAILS OF THE USER'S PROFILE AND ALSO HANDLES THE LOGOUT FEATURE
 ****************************************************************************************************************************************************************************************************/

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My Profile");

        Button logOut = (Button) view.findViewById(R.id.logout_btn);


        TextView profileName = (TextView) view.findViewById(R.id.profile_name);
        ImageView profilePicture = (ImageView) view.findViewById(R.id.profile_pic);

        profileName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());


        if(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()!=null) {
        Glide .with(getActivity())
                .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl())
                .into(profilePicture);}

        else {

            profilePicture.setImageResource(R.drawable.ic_account_circle_blue_24dp);

        }

        //Handles the logout feature
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance().signOut(getActivity());

            }
        });

        return view;
    }
}
