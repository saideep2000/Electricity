package com.example.electricity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class message extends Fragment implements View.OnClickListener {

    Button send,clear;
    EditText typemesg;
    TextView message;
    Switch aSwitch;
    DatabaseReference myRef,myReff;
    SpannableString already;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_message, container, false);

        send = view.findViewById(R.id.send);
        clear = view.findViewById(R.id.clear);

        message = view.findViewById(R.id.textt);
        typemesg = view.findViewById(R.id.typemesg);

        aSwitch = view.findViewById(R.id.switch1);
        aSwitch.setChecked(true);

        send.setOnClickListener(this);
        clear.setOnClickListener(this);

        message.isVerticalScrollBarEnabled();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myReff = database.getReference();



        try
        {
            myReff = myReff.child("messages").child("customer");
            myReff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    SpannableString temp=  new SpannableString("");

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        SpannableString ss1=  new SpannableString(snapshot.getKey());
                        ss1.setSpan(new RelativeSizeSpan(0.8f), 0,ss1.length(), 0);
                        ss1.setSpan(new ForegroundColorSpan(Color.YELLOW), 0, ss1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        ss1 = new SpannableString(TextUtils.concat(temp,ss1,"\n","    "));

                        for (DataSnapshot snap : snapshot.getChildren()) {

                            SpannableString ss2=  new SpannableString(snap.getKey());
                            UnderlineSpan underlinespan = new UnderlineSpan();
                            ss2.setSpan(underlinespan,0,ss2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            StyleSpan boldspan = new StyleSpan(Typeface.BOLD);
                            ss2.setSpan(boldspan,0,ss2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            ss2.setSpan(new ForegroundColorSpan(Color.CYAN), 0, ss2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            ss2.setSpan(new RelativeSizeSpan(1.3f), 0,ss2.length(), 0);

                            SpannableString ss3=  new SpannableString((CharSequence) snap.getValue());
                            ss3.setSpan(new ForegroundColorSpan(Color.WHITE), 0, ss3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            SpannableString ss4=  new SpannableString(" : ");
                            ss4.setSpan(new ForegroundColorSpan(Color.RED), 0, ss4.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            temp = new SpannableString(TextUtils.concat(ss1,ss2,ss4,ss3,"\n"));
                        }
                        temp = new SpannableString(TextUtils.concat(temp,"\n"));
                    }
                    message.setText(temp);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            Toast.makeText(getActivity(), "No messages", Toast.LENGTH_LONG).show();
        }


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getActivity(), "Messages From Office", Toast.LENGTH_SHORT).show();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.time_wrapper, new office(), "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });



        return view;
    }


    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.send:

                Toast.makeText(getActivity(), "Message sended to your colleagues", Toast.LENGTH_LONG).show();
                EditText txt = message.this.getView().findViewById(R.id.typemesg);

                if(!txt.equals("")){
                    SharedPreferences yes = getActivity().getSharedPreferences("dataremeber", Context.MODE_PRIVATE);
                    String hey = yes.getString("user", "");

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    myRef = database.getReference();
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss a", Locale.getDefault());
                        String currentDateandTime = sdf.format(new Date());

                        myRef.child("messages").child("customer").child(currentDateandTime).child(hey).setValue(typemesg.getText().toString());
                        txt.setText("");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Write a message", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.clear:
                SharedPreferences yes = getActivity().getSharedPreferences("dataremeber", Context.MODE_PRIVATE);
                String hey = yes.getString("user", "");

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                myReff = database.getReference();

                myReff = myReff.child("messages").child("customer");
                myReff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                if(snap.getKey().equals(hey)){
                                    snapshot.getRef().removeValue();
                                }
                            }
                        }

                        Toast.makeText(getActivity(), "Your messages are removed", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        }

    }
}