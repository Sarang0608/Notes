package com.example.notes;

/*****************************************************************************************************************************************************************************************************
                                                                THIS FRAGMENT WILL DISPLAY ALL THE NOTES STORED BY THE USER IN A RECYCLER VIEW
 ****************************************************************************************************************************************************************************************************/

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class AllNotesFragment extends Fragment implements NotesRecyclerAdapter.NoteListener {


    RecyclerView recyclerView;
   NotesRecyclerAdapter notesRecyclerAdapter;
   FirebaseAuth firebaseAuth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_all_notes,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("All Notes");

        recyclerView = (RecyclerView) view.findViewById(R.id.all_notes_rv);


        setRecyclerView(firebaseAuth.getInstance().getCurrentUser());

        return view;
    }

    private void setRecyclerView(FirebaseUser firebaseUser) {

        Query query = FirebaseFirestore.getInstance()
                .collection("notes")
                .whereEqualTo("id",firebaseUser.getUid())
                .orderBy("creationTime", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query,Note.class)
                .build();

        notesRecyclerAdapter = new NotesRecyclerAdapter(options,this);
        recyclerView.setAdapter(notesRecyclerAdapter);


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

    @Override
    public void handleCheckComplete(DocumentSnapshot documentSnapshot) {

      Note note = documentSnapshot.toObject(Note.class);

      documentSnapshot.getReference().update("isCompleted",true);


    }

    @Override
    public void handleEditNotes(DocumentSnapshot snapshot) {


        Note note = snapshot.toObject(Note.class);



        AddNoteFragment fragment = new AddNoteFragment();
        Bundle bundle = new Bundle();

        bundle.putString("Heading",note.getHeading());
        bundle.putString("Body",note.getBody());
        bundle.putBoolean("IsImp",note.getIsImportant());
        bundle.putBoolean("isCom",note.getIsCompleted());
        fragment.setArguments(bundle);
        handleDeleteItem(snapshot);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment,"Edit");
        fragmentTransaction.addToBackStack(null).commit();
    }

    @Override
    public void handleDeleteItem(DocumentSnapshot snapshot) {

        snapshot.getReference().delete();

    }


}
