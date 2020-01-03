package com.example.notes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class TestFragment extends Fragment {

    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        bundle = getArguments();

        if(bundle!=null) {

            String text = bundle.getString("Heading");

            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();

        }

        return inflater.inflate(R.layout.fragment_test, container, false);
    }


}
