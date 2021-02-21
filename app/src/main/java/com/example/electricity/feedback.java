package com.example.electricity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class feedback extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        View view = inflater.inflate(R.layout.feedback, container, false);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.zwrapper, new zarecfragment(), "findThisFragment")
                .addToBackStack(null)
                .commit();


        return view;
    }


    @Override
    public void onClick(View view) {

    }
}