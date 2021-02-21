package com.example.electricity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;





public class bill extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private Spinner spin1, spin2 , spin3;
    private static final String[] paths1 = {"Telangana", "Andhra pradesh", "Karnataka"};
    private static final String[] paths2 = {"Northern Disturbution", "Sourthern Disturbution", "kptcl"};
    private static final String[] paths3 = {"BHIM UPI", "Phonepe", "Google Pay", "Debit Card"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        View view = inflater.inflate(R.layout.activity_bill, container, false);

        spin1 = (Spinner) view.findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, paths1);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spin1.setAdapter(adapter);

        spin2 = (Spinner) view.findViewById(R.id.spinner2);
        ArrayAdapter<String> adapterr = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, paths2);
        adapterr.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spin2.setAdapter(adapterr);

        spin3 = (Spinner) view.findViewById(R.id.spinner3);
        ArrayAdapter<String> adapterrr = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, paths3);
        adapterrr.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spin3.setAdapter(adapterrr);



        return view;
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}