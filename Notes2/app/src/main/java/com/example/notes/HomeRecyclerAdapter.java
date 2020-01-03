package com.example.notes;

import android.text.format.DateFormat;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;

public class HomeRecyclerAdapter extends FirestoreRecyclerAdapter<Note, HomeRecyclerAdapter.HomeViewHolder> {

    HomeNote homeNote;

    public HomeRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Note> options, HomeNote homeNote) {
        super(options);
        this.homeNote = homeNote;
    }

    @Override
    protected void onBindViewHolder(@NonNull HomeViewHolder holder, int position, @NonNull Note note) {

        Timestamp creationTime = note.getCreationTime();

        holder.header.setText(note.getHeading());
        holder.body.setText(note.getBody());
        holder.body.setMovementMethod(new ScrollingMovementMethod());
        CharSequence dateCharSeq = DateFormat.format("dd-MM yyyy",creationTime.toDate());
        holder.time.setText(dateCharSeq);

        if(note.getIsImportant()==true) {
            holder.imp.setImageResource(R.drawable.ic_star_golden_24dp);
        }
        else {
            holder.imp.setImageResource(R.drawable.ic_star_border_golden_24dp);
        }

        if(note.getIsCompleted() == true) {

            holder.com.setVisibility(View.VISIBLE);

        }
        else {

            holder.com.setVisibility(View.INVISIBLE);

        }



    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.home_display_layout,parent,false);

        return new HomeViewHolder(view);

    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {

        TextView header,body,time;
        ImageView com,imp;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);


            header = itemView.findViewById(R.id.home_note_header_tv);
            body = itemView.findViewById(R.id.home_note_body_tv);
            time = itemView.findViewById(R.id.home_note_creation_tv);
            com = itemView.findViewById(R.id.home_note_com_iv);
            imp = itemView.findViewById(R.id.home_note_imp_iv);
        }
    }

    interface HomeNote {

    }

}
