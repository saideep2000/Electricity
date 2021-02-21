package com.example.electricity;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;


public class zedescfragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    String name, message, phone, image;
    float rating;

    public zedescfragment(String name, String message, String phone, String image) {
        // Required empty public constructor
    }

    public zedescfragment(String name, String message, String phone, String image,float rating) {
        this.name=name;
        this.message=message;
        this.phone=phone;
        this.image=image;
        this.rating=rating;
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
        View view =  inflater.inflate(R.layout.fragment_zedescfragment, container, false);

        ImageView imageholder = view.findViewById(R.id.imageholder);
        TextView nameholder = view.findViewById(R.id.nameholder);
        TextView messageholder = view.findViewById(R.id.messageholder);
        TextView phoneholder = view.findViewById(R.id.phoneholder);
        RatingBar ratingg = view.findViewById(R.id.ratingBar);
        TextView ratingholder = view.findViewById(R.id.ratingholder);

        nameholder.setText(name);
        messageholder.setText(message);
        phoneholder.setText(phone);
        ratingg.setRating(rating);
        String sample = "";

        if(rating == 1){
            sample = "Very bad";
        }
        else if(rating == 2){
            sample = "Need some improvement";
        }
        else if(rating == 3){
            sample = "Good";
        }
        else if(rating == 4){
            sample = "Great";
        }
        else{
            sample = "Awesome. I love it";
        }

        ratingholder.setText(sample);

        Glide.with(getContext()).load(image).into(imageholder);
        return view;
    }

    public void onBackPressed(){
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new recfragment()).addToBackStack(null).commit();

    }

}