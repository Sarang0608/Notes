package com.example.notes;

/***************************************************************************************************************************************************************************************************
                                                                THIS FRAGMENT HANDLES ADDING THE NEW NOTES INTO THE FIREBASE DATABASE
***************************************************************************************************************************************************************************************************/

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class AddNoteFragment extends Fragment {

    DocumentReference documentReference;
    Bundle bundle;
    CheckBox isImp;
    Button saveBtn;
    ImageView mic;
    EditText header,body;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view;
        view = inflater.inflate(R.layout.fragment_add_note,container,false);



        int whiteColorValue = Color.WHITE;

         header = (EditText) view.findViewById(R.id.note_header_et);
         body = (EditText) view.findViewById(R.id.note_content_et);
         mic = (ImageView) view.findViewById(R.id.mic_img);
        saveBtn = (Button) view.findViewById(R.id.save_btn);
        isImp = (CheckBox) view.findViewById(R.id.mark_imp);
        bundle = getArguments();


        //Adding the custom toolbar(different from the Main Activity)
        Toolbar addNotesToolbar =  (Toolbar) view.findViewById(R.id.add_notes_toolbar);
        //((AppCompatActivity)getActivity()).getSupportActionBar().hide();//Hide the toolbar from Main Activity. Absence of this method will result in two different toolbars being present at the same time
        ((AppCompatActivity)getActivity()).setSupportActionBar(addNotesToolbar);


       if(bundle!=null) {



           final String headText = bundle.getString("Heading");
           final String bodyText = bundle.getString("Body");
           final Boolean isImpEdit = bundle.getBoolean("IsImp");
           final String docID = bundle.getString("DocRef");

           header.setText(headText);
           body.setText(bodyText);

           if (isImpEdit == true) {
               isImp.setChecked(true);
           } else {
               isImp.setChecked(false);
           }

           saveBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   closeKeyboard();

                   String newHeading = header.getText().toString();
                   String newBody = body.getText().toString();
                   Boolean newIsImp;

                   if (isImp.isChecked() == true) {
                       newIsImp = true;
                   } else {
                       newIsImp = false;
                   }
                   String UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                   final Note note = new Note(UserID,newHeading,newBody,newIsImp,bundle.getBoolean("isCom"),new Timestamp(new Date()));


                   FirebaseFirestore.getInstance()
                           .collection("notes")
                           .add(note)
                           .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                               @Override
                               public void onSuccess(DocumentReference documentReference) {
                                   Snackbar snackbar = Snackbar.make(getView(),"Note Edited Successfully!",Snackbar.LENGTH_SHORT);
                                   snackbar.show();
                                   FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                   fragmentTransaction.replace(R.id.fragment_container,new AllNotesFragment());
                                   fragmentTransaction.commit();
                               }
                           });


               }
           });

           //Handles the Speech-to-Text conversion of the App
           mic.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   getSpeechInput(view);
               }
           });


       }
       else {
           saveBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   closeKeyboard();

                   if (isImp.isChecked() == true) {
                       boolean isImport = true;

                       addNote(header.getText().toString(), body.getText().toString(), isImport);
                   } else {
                       boolean isImport = false;
                       addNote(header.getText().toString(), body.getText().toString(), isImport);
                   }
               }
           });

           //Handles the Speech-to-Text conversion of the App
           mic.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   getSpeechInput(view);
               }
           });
       }
        return view;
    }


    //Method that recognizes the Speech given by user
    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        startActivityForResult(intent,10);

    }

    //Method that converts the recognized speech to text and stores it in the Edit Text
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EditText content = (EditText) getView().findViewById(R.id.note_content_et);

        switch (requestCode) {
            case 10:
                if(resultCode==RESULT_OK && data!=null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    content.setText(result.get(0));
                }
                break;
        }

    }

    public void addNote(String header, String body, boolean isImportant) {

        String UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Note note = new Note(UserID,header,body,isImportant,false,new Timestamp(new Date()));

        FirebaseFirestore.getInstance()
                .collection("notes")
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Snackbar snackbar = Snackbar.make(getView(),"Note Added Successfully!",Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container,new HomeFragment());
                        fragmentTransaction.commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Couldn't add the note. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void closeKeyboard() {

        View view = getActivity().getCurrentFocus();

        if(view!= null) {

            InputMethodManager imm = (InputMethodManager)((AppCompatActivity)getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);

            imm.hideSoftInputFromWindow(view.getWindowToken(),0);


        }

    }

}
