package com.example.electricity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.baoyachi.stepview.VerticalStepView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class step extends Fragment implements View.OnClickListener{

    VerticalStepView verticalStepView;
    Button feedback;
    TextView text;
    DatabaseReference myRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_step, container, false);


        feedback = view.findViewById(R.id.feedback);
        feedback.setOnClickListener(this);

        text = view.findViewById(R.id.text);

        verticalStepView = (VerticalStepView)view.findViewById(R.id.vertical);
        List<String> sources = new ArrayList<>();
        sources.add("Complaint Registered");
        sources.add("Received");
        sources.add("Reached Your Place");
        sources.add("Working on Complaint");
        sources.add("Solved");

        SharedPreferences yes = getActivity().getSharedPreferences("dataremeber", Context.MODE_PRIVATE);
        String hey=yes.getString("user","");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        myRef = myRef.child("complaint").child(hey).child("person");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value;
                try{
                    value = dataSnapshot.getValue(String.class);
                    if (value.equals(null)){
                        verticalStepView.setStepsViewIndicatorComplectingPosition(sources.size())
                                .reverseDraw(false)
                                .setStepViewTexts(sources)
                                .setLinePaddingProportion(2.85f)
                                .setStepsViewIndicatorUnCompletedLineColor(Color.parseColor("#FFFF00"))
                                .setStepViewComplectedTextColor(Color.parseColor("#FFFF00"))
                                .setStepViewUnComplectedTextColor(ContextCompat.getColor(getContext(),R.color.uncompleted_text_color))
                                .setStepsViewIndicatorCompletedLineColor(Color.parseColor("#FFFFFF"))
                                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getContext(),R.drawable.complted))
                                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getContext(),R.drawable.attention))
                                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getContext(),R.drawable.default_icon));
                    }
                    else if(value.equals("")){
                        verticalStepView.setStepsViewIndicatorComplectingPosition(sources.size()-4)
                                .reverseDraw(false)
                                .setStepViewTexts(sources)
                                .setLinePaddingProportion(2.85f)
                                .setStepsViewIndicatorUnCompletedLineColor(Color.parseColor("#FFFF00"))
                                .setStepViewComplectedTextColor(Color.parseColor("#FFFF00"))
                                .setStepViewUnComplectedTextColor(ContextCompat.getColor(getContext(),R.color.uncompleted_text_color))
                                .setStepsViewIndicatorCompletedLineColor(Color.parseColor("#FFFFFF"))
                                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getContext(),R.drawable.complted))
                                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getContext(),R.drawable.attention))
                                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getContext(),R.drawable.default_icon));
                    }

                    else{
                        verticalStepView.setStepsViewIndicatorComplectingPosition(sources.size()-2)
                                .reverseDraw(false)
                                .setStepViewTexts(sources)
                                .setLinePaddingProportion(2.85f)
                                .setStepsViewIndicatorUnCompletedLineColor(Color.parseColor("#FFFF00"))
                                .setStepViewComplectedTextColor(Color.parseColor("#FFFF00"))
                                .setStepViewUnComplectedTextColor(ContextCompat.getColor(getContext(),R.color.uncompleted_text_color))
                                .setStepsViewIndicatorCompletedLineColor(Color.parseColor("#FFFFFF"))
                                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getContext(),R.drawable.complted))
                                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getContext(),R.drawable.attention))
                                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getContext(),R.drawable.default_icon));

                        text.setText(value + " took your complaint");
                    }
                } catch (Exception e) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    myRef = database.getReference();
                    myRef = myRef.child("complainthistory").child("name");
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String value2;
                            try {
                                value2 = dataSnapshot.getValue(String.class);
                                if(value2.equals(hey)){
                                    verticalStepView.setStepsViewIndicatorComplectingPosition(sources.size())
                                            .reverseDraw(false)
                                            .setStepViewTexts(sources)
                                            .setLinePaddingProportion(2.85f)
                                            .setStepsViewIndicatorUnCompletedLineColor(Color.parseColor("#FFFF00"))
                                            .setStepViewComplectedTextColor(Color.parseColor("#FFFF00"))
                                            .setStepViewUnComplectedTextColor(ContextCompat.getColor(getContext(),R.color.uncompleted_text_color))
                                            .setStepsViewIndicatorCompletedLineColor(Color.parseColor("#FFFFFF"))
                                            .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getContext(),R.drawable.complted))
                                            .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getContext(),R.drawable.attention))
                                            .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getContext(),R.drawable.default_icon));

                                    Toast.makeText(getActivity(),"Problem Solved. Give Feedback"+hey,Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception exception) {
                                try{
                                    verticalStepView.setStepsViewIndicatorComplectingPosition(sources.size()-5)
                                            .reverseDraw(false)
                                            .setStepViewTexts(sources)
                                            .setLinePaddingProportion(2.85f)
                                            .setStepsViewIndicatorUnCompletedLineColor(Color.parseColor("#FFFF00"))
                                            .setStepViewComplectedTextColor(Color.parseColor("#FFFF00"))
                                            .setStepViewUnComplectedTextColor(ContextCompat.getColor(getContext(),R.color.uncompleted_text_color))
                                            .setStepsViewIndicatorCompletedLineColor(Color.parseColor("#FFFFFF"))
                                            .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getContext(),R.drawable.complted))
                                            .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getContext(),R.drawable.attention))
                                            .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getContext(),R.drawable.default_icon));
                                } catch (Exception ex) {

                                }


                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
            @Override
            public void onCancelled (@NonNull DatabaseError error){

            }
        });
        return view;

    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.feedback:
                Toast.makeText(getActivity(), "Give Feedback", Toast.LENGTH_LONG).show();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_containerr, new feedbackk(), "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
        }


    }
}



