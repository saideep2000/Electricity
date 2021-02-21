package com.example.electricity;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class descfragment extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    DatabaseReference myRef;

    String name, message, phone, image, time , button, person;

    public descfragment() {
        // Required empty public constructor
    }

    public descfragment(String name, String message, String phone, String image, String time, String person) {
        this.name=name;
        this.message=message;
        this.phone=phone;
        this.image=image;
        this.time=time;
        this.person=person;
//        this.button=button;
    }


    public static descfragment newInstance(String param1, String param2) {
        descfragment fragment = new descfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_descfragment, container, false);

        ImageView imageholder = view.findViewById(R.id.imageholder);
        TextView nameholder = view.findViewById(R.id.nameholder);
        TextView messageholder = view.findViewById(R.id.messageholder);
        TextView phoneholder = view.findViewById(R.id.phoneholder);
        TextView timeholder = view.findViewById(R.id.timeholder);
        TextView personholder = view.findViewById(R.id.person);
        Button button = view.findViewById(R.id.button);
        button.setOnClickListener((View.OnClickListener) this);

        nameholder.setText(name);
        messageholder.setText(message);
        phoneholder.setText(phone);
        timeholder.setText(time);
        if(!person.equals("")){
            personholder.setVisibility(view.VISIBLE);
            personholder.setText(person+" has taken this complaint");
        }
        else{
            personholder.setVisibility(view.INVISIBLE);
        }

//        button.setText((CharSequence) button);

        Glide.with(getContext()).load(image).into(imageholder);
        return view;
    }

    public void onBackPressed(){
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new recfragment()).addToBackStack(null).commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                SharedPreferences yes = getActivity().getSharedPreferences("dataremeber", Context.MODE_PRIVATE);
                String hey=yes.getString("user","");
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                myRef = database.getReference();
                myRef.child("complaint").child(name).child("person").setValue(hey);
                Toast.makeText(getActivity(), "Complaint taken", Toast.LENGTH_LONG).show();
                break;
        }
    }

}