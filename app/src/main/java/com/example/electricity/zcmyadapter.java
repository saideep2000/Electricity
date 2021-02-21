package com.example.electricity;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class zcmyadapter extends FirebaseRecyclerAdapter<zbmodel,zcmyadapter.myviewholder> {

    public zcmyadapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zdsinglerowdesgin,parent,false);
        return new myviewholder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull zbmodel model) {
        holder.nametext.setText(model.getName());
        holder.message.setText(model.getMessage());
        holder.phone.setText(model.getPhone());
        holder.ratingg.setRating(model.getRating());


        Glide.with(holder.img1.getContext()).load(model.getImage()).into(holder.img1);

        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.zwrapper,new zedescfragment(model.getName(),model.getMessage(),model.getPhone(),model.getImage(),model.getRating())).addToBackStack(null).commit();

            }
        });
    }

    public class myviewholder extends RecyclerView.ViewHolder{

        ImageView img1;
        TextView nametext,message,phone;
        RatingBar ratingg;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            nametext = itemView.findViewById(R.id.nametext);
            message = itemView.findViewById(R.id.message);
            phone = itemView.findViewById(R.id.phone);
            img1 = itemView.findViewById(R.id.img1);
            ratingg = itemView.findViewById(R.id.ratingBar);
        }
    }
}
