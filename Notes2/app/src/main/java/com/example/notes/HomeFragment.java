package com.example.notes;
/****************************************************************************************************************************************************************************************************-
                                                 THIS FRAGMENT IS THE HOME SCREEN FOR THE APP. IT SHOWS RECENT NOTES AND THE IMPORTANT NOTES SEPARATELY.
 ****************************************************************************************************************************************************************************************************/

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.HomeRecyclerAdapter.HomeNote;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HomeFragment extends Fragment implements HomeNote {

    RecyclerView recentNotes,impNotes;
   HomeRecyclerAdapter notesRecyclerAdapter1,notesRecyclerAdapter2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);

        recentNotes = (RecyclerView) view.findViewById(R.id.recent_notes_rv);
        impNotes = (RecyclerView) view.findViewById(R.id.imp_notes_rv);


        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");

        setRecentRecyclerView(FirebaseAuth.getInstance().getCurrentUser());
        setImptRecyclerView(FirebaseAuth.getInstance().getCurrentUser());

        FloatingActionButton addFab = (FloatingActionButton) view.findViewById(R.id.add_note_fab);

        //Handles the Floating Action Button that takes the user to AddNotesFragment
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddNoteFragment fragment = new AddNoteFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,fragment);
                fragmentTransaction.addToBackStack(null).commit();


            }
        });

        return view;
    }
    private void setRecentRecyclerView(FirebaseUser firebaseUser) {

        Query query = FirebaseFirestore.getInstance()
                .collection("notes")
                .whereEqualTo("id",firebaseUser.getUid())
                .orderBy("creationTime", Query.Direction.DESCENDING)
                .limit(5);

        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query,Note.class)
                .build();

        notesRecyclerAdapter1 = new HomeRecyclerAdapter(options,this);
        recentNotes.setAdapter(notesRecyclerAdapter1);


    }


    private void setImptRecyclerView(FirebaseUser firebaseUser) {

        Query query = FirebaseFirestore.getInstance()
                .collection("notes")
                .whereEqualTo("id",firebaseUser.getUid())
                .whereEqualTo("isImportant",true)
                .orderBy("creationTime", Query.Direction.DESCENDING)
                .limit(5);

        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query,Note.class)
                .build();

        notesRecyclerAdapter2 = new HomeRecyclerAdapter(options,this);
        impNotes.setAdapter(notesRecyclerAdapter2);


    }

    @Override
    public void onStart() {
        super.onStart();

        if(notesRecyclerAdapter1!=null) {
            notesRecyclerAdapter1.startListening();

        }

        if(notesRecyclerAdapter2!=null) {
            notesRecyclerAdapter2.startListening();
        }

    }

    @Override
    public void onStop() {
        super.onStop();

        if(notesRecyclerAdapter1!=null) {
            notesRecyclerAdapter1.stopListening();
        }

        if(notesRecyclerAdapter2!=null) {

            notesRecyclerAdapter2.stopListening();

        }



    }

}
