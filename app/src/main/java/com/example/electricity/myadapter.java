package com.example.electricity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class myadapter extends FirebaseRecyclerAdapter<com.example.electricity.model,myadapter.myviewholder>{
    String rem;

    public myadapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull com.example.electricity.model model) {
        holder.nametext.setText(model.getName());
        holder.message.setText(model.getMessage());
        holder.phone.setText(model.getPhone());
        holder.time.setText(model.getTime());
        holder.person.setText(model.getPerson());
        holder.location.setText(model.getLocation());


        rem = model.getName();

        Glide.with(holder.img1.getContext()).load(model.getImage()).into(holder.img1);

        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new descfragment(model.getName(),model.getMessage(),model.getPhone(),model.getImage(),model.getTime(),model.getPerson())).addToBackStack(null).commit();


            }
        });
        holder.button.getContext();
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                ref.child("complainthistory").child("name").setValue(model.getName());
                Query applesQuery = ref.child("complaint").orderByChild("name").equalTo(model.getName());

//                Toast.makeText(activity, model.getName()+" ", Toast.LENGTH_LONG).show();

                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }

                        Toast.makeText(activity, "Removed "+model.getName()+" From Complaint's", Toast.LENGTH_LONG).show();
                    }



                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(activity, "Not found", Toast.LENGTH_LONG).show();
                    }
                });


            }
        });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);

        return new myviewholder(view);
    }



    public class myviewholder extends RecyclerView.ViewHolder{

        ImageView img1;
        TextView nametext,message,phone,time,button,person,location;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            nametext = itemView.findViewById(R.id.nametext);
            message = itemView.findViewById(R.id.message);
            phone = itemView.findViewById(R.id.phone);
            time = itemView.findViewById(R.id.time);
            img1 = itemView.findViewById(R.id.img1);
            button = itemView.findViewById(R.id.button2);
            person = itemView.findViewById(R.id.person);
            location = itemView.findViewById(R.id.location);
        }
    }
}
