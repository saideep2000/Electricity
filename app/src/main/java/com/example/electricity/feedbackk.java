

package com.example.electricity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class feedbackk extends Fragment implements View.OnClickListener, RatingBar.OnRatingBarChangeListener {
    DatabaseReference myRef;
    StorageReference storageReference;
    int temp=5;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_feedbackk, container, false);

        try
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        }
        catch (NullPointerException e){}



        Button clr = view.findViewById(R.id.clrr);
        clr.setOnClickListener(this);

        Button sub = view.findViewById(R.id.submit);
        sub.setOnClickListener(this);

        TextView how = view.findViewById(R.id.how);

        RatingBar mRatingBar = view.findViewById(R.id.ratingBar);

        mRatingBar.setOnRatingBarChangeListener(this);




        return view;
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

        TextView mRatingScale = getActivity().findViewById(R.id.how);
        switch ((int) ratingBar.getRating()) {
            case 1:
                mRatingScale.setText("Very bad");
                temp = 1;
                break;
            case 2:
                mRatingScale.setText("Need some improvement");
                temp = 2;
                break;
            case 3:
                mRatingScale.setText("Good");
                temp = 3;
                break;
            case 4:
                mRatingScale.setText("Great");
                temp = 4;
                break;
            case 5:
                mRatingScale.setText("Awesome. I love it");
                temp = 5;
                break;
            default:
                mRatingScale.setText("");
        }

    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clrr:
                Toast.makeText(getActivity(), "Text cleared..!", Toast.LENGTH_LONG).show();

                EditText txt = feedbackk.this.getView().findViewById(R.id.feedback);
                txt.setText("");
                break;
            case R.id.submit:
                EditText txtt = feedbackk.this.getView().findViewById(R.id.feedback);

                if(txtt.equals("")){
                    Toast.makeText(getActivity(), "Fill All ...!!", Toast.LENGTH_LONG).show();
                }
                else {

                    String c = txtt.getText().toString().trim();

                    SharedPreferences yes = getActivity().getSharedPreferences("dataremeber", Context.MODE_PRIVATE);
                    String hey = yes.getString("user", "");

                    EditText txttt = feedbackk.this.getView().findViewById(R.id.phone);
                    String d = txttt.getText().toString().trim();

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    myRef = database.getReference();
                    try {
                        myRef.child("feedback").child(hey).child("name").setValue(hey);
                        myRef.child("feedback").child(hey).child("message").setValue(c);
                        myRef.child("feedback").child(hey).child("phone").setValue(d);
                        myRef.child("feedback").child(hey).child("rating").setValue(temp);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(getActivity(), "Feedback submitted..! ", Toast.LENGTH_LONG).show();
                    txtt.setText("");
                    txttt.setText("");
                    RatingBar mRatingBar = view.findViewById(R.id.ratingBar);
                    mRatingBar.setRating(0);

                    storageReference = FirebaseStorage.getInstance().getReference();
                    StorageReference fileRef = storageReference.child(hey).child("profile.jpg");
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            myRef = database.getReference();

                            myRef.child("feedback").child(hey).child("image").setValue(String.valueOf(uri));
                        }
                    });
                }

                break;

            case R.id.ratingBar:

                TextView mRatingScale = view.findViewById(R.id.how);

                mRatingScale.setText(String.valueOf(view));
                RatingBar ratingBar = view.findViewById(R.id.ratingBar);
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        mRatingScale.setText("Very bad");
                        break;
                    case 2:
                        mRatingScale.setText("Need some improvement");
                        break;
                    case 3:
                        mRatingScale.setText("Good");
                        break;
                    case 4:
                        mRatingScale.setText("Great");
                        break;
                    case 5:
                        mRatingScale.setText("Awesome. I love it");
                        break;
                    default:
                        mRatingScale.setText("");
                }

                break;

        }

    }


}
