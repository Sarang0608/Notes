package com.example.notes;

/*****************************************************************************************************************************************************************************************************
                                                        THIS FRAGMENT CONTAINS THE METHODS TO SEARCH FOR A GIVEN NOTE AND THE OPEN THE NOTE
 ****************************************************************************************************************************************************************************************************/

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class SearchFragment extends Fragment  {

    RecyclerView recyclerView;
    NotesRecyclerAdapter notesRecyclerAdapter;
    FirebaseAuth firebaseAuth;
    EditText searchNote;
    ArrayList<Note> filterList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.fragment_search,container,false);

        searchNote = (EditText) view.findViewById(R.id.search_note_et);

        recyclerView = (RecyclerView) view.findViewById(R.id.search_notes_rv);

        searchRecyclerView(firebaseAuth.getInstance().getCurrentUser());

        return view;
    }

    public void searchRecyclerView(FirebaseUser user) {


        Query query;
        query = FirebaseFirestore.getInstance()
                .collection("notes")
                .whereEqualTo("id",user.getUid());

        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query,Note.class)
                .build();



    }

    @Override
    public void onStart() {
        super.onStart();

        if(notesRecyclerAdapter!=null) {
            notesRecyclerAdapter.startListening();
        }

    }

    @Override
    public void onStop() {
        super.onStop();

        if(notesRecyclerAdapter!=null) {
            notesRecyclerAdapter.stopListening();
        }
    }



}
