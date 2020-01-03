package com.example.notes;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class NotesRecyclerAdapter extends FirestoreRecyclerAdapter<Note, NotesRecyclerAdapter.NoteViewHolder> {

    NoteListener noteListener;


    public NotesRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Note> options, NoteListener noteListener) {
        super(options);
        this.noteListener = noteListener;
    }


    @Override
    protected void onBindViewHolder(@NonNull final NoteViewHolder holder, final int position, @NonNull final Note note) {

        Timestamp creationTime = note.getCreationTime();

        holder.heading.setText(note.getHeading());
        holder.body.setText(note.getBody());
        CharSequence dateCharSeq = DateFormat.format("EEEE, MMM d, yyyy h:mm:ss a",creationTime.toDate());
        holder.creationDate.setText(dateCharSeq);

        holder.menuOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                PopupMenu popupMenu = new PopupMenu(view.getContext(),holder.menuOpen);
                popupMenu.inflate(R.menu.more_menu);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {

                            case R.id.edit_note:
                                Toast.makeText(view.getContext(), "Edit a Note", Toast.LENGTH_SHORT).show();
                                DocumentSnapshot snapshot = getSnapshots().getSnapshot(position);
                                noteListener.handleEditNotes(snapshot);
                                return true;

                            case R.id.delete_note:
                                snapshot = getSnapshots().getSnapshot(position);
                                final DocumentReference documentReference = snapshot.getReference();
                                noteListener.handleDeleteItem(snapshot);
                                Snackbar snackbar = Snackbar.make(view,"Note Deleted Successfully!",Snackbar.LENGTH_SHORT);
                                snackbar.setAction("UNDO", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        documentReference.set(note);

                                    }
                                });
                                snackbar.show();
                                return true;

                            case R.id.mark_as_complete:
                                snapshot = getSnapshots().getSnapshot(position);
                                noteListener.handleCheckComplete(snapshot);
                                return true;

                            default: return false;
                        }


                    }
                });

                popupMenu.show();
            }
        });

        if(note.getIsImportant()==true) {
            holder.isImp.setImageResource(R.drawable.ic_star_golden_24dp);
        }
        else {
            holder.isImp.setImageResource(R.drawable.ic_star_border_golden_24dp);
        }

        if(note.getIsCompleted() == true) {

            holder.isCom.setVisibility(View.VISIBLE);

        }
        else {

            holder.isCom.setVisibility(View.INVISIBLE);

        }



    }


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.note_display_layout,parent,false);
        return new NoteViewHolder(view);

    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView heading,body,creationDate;
        ImageView isImp,isCom;
        Button menuOpen;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            heading = itemView.findViewById(R.id.note_header_tv);
            body = itemView.findViewById(R.id.note_body_tv);
            creationDate = itemView.findViewById(R.id.note_creation_tv);
            isImp = itemView.findViewById(R.id.note_imp_iv);
            isCom = itemView.findViewById(R.id.note_com_iv);
            menuOpen = itemView.findViewById(R.id.menu_btn);

        }


    }

    interface NoteListener {

        public void handleCheckComplete(DocumentSnapshot documentSnapshot);

        public void handleEditNotes(DocumentSnapshot snapshot);

        public void handleDeleteItem(DocumentSnapshot snapshot);

    }

}
