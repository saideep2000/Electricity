package com.example.electricity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class timereport extends Fragment implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_timereport, container, false);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.time_wrapper, new office(), "findThisFragment")
                .addToBackStack("first frag")
                .commit();

        return view;
    }


    @Override
    public void onClick(View view) {

    }
}