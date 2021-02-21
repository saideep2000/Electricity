package com.example.electricity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;

import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class home extends Fragment implements View.OnClickListener {
    VideoView videoView;
    TextView textView,running,name,writename;
    Dialog myDialog;
    Button thankyou;
    DatabaseReference myReff;
    ImageView imageView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        }
        catch (NullPointerException e){}



        View view = inflater.inflate(R.layout.activity_home, container, false);



        videoView = (VideoView) view.findViewById(R.id.videoplayer);
        videoView.setVideoPath("android.resource://"+ getActivity().getPackageName()+ "/raw/world");
        MediaController mediaController = new MediaController(getContext());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();

        myDialog = new Dialog(getContext());

        myDialog.setContentView(R.layout.custompopup);
        thankyou = (Button) myDialog.findViewById(R.id.thankyou);
        name = (TextView) myDialog.findViewById(R.id.name);
        TextView txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("X");

        SharedPreferences yes = getActivity().getSharedPreferences("dataremeber", Context.MODE_PRIVATE);
        String hey=yes.getString("user","");

        writename = (TextView) view.findViewById(R.id.writename);
        writename.setText("Welcome !!  "+hey);

        name.setText(hey);

        Button btnFollow = (Button) myDialog.findViewById(R.id.thankyou);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        thankyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();


        running = (TextView) view.findViewById(R.id.run);
        running.setSelected(true);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myReff = database.getReference();


        try
        {
            myReff = myReff.child("messages").child("customer");
            myReff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    SpannableString temp = new SpannableString("");

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            SpannableString ss1=  new SpannableString(snap.getKey());
                            ss1.setSpan(new ForegroundColorSpan(Color.YELLOW), 0, ss1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            ss1 = new SpannableString(TextUtils.concat(temp,ss1));

                            SpannableString ss2=  new SpannableString(" : ");
                            ss2.setSpan(new ForegroundColorSpan(Color.RED), 0, ss2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            SpannableString ss3=  new SpannableString((CharSequence) snap.getValue());
                            ss3.setSpan(new ForegroundColorSpan(Color.CYAN), 0, ss3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            temp = new SpannableString(TextUtils.concat(ss1,ss2,ss3," ;   "));


                        }
                    }
                    running.setText(temp);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "No announcement's ", Toast.LENGTH_LONG).show();
                    running.setText(" ");
                }
            });
        } catch (Exception e) {
            Toast.makeText(getActivity(), " ", Toast.LENGTH_LONG).show();
            running.setText(" ");
        }



        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }
}