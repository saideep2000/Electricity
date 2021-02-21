package com.example.electricity;


import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;


public class complaintt extends Fragment implements View.OnClickListener {


    FusedLocationProviderClient client;


    DatabaseReference myRef;
    StorageReference storageReference;
    LocationManager locationManager;
    double lati;
    double longi;
    List<Address> addressList;
    Location location;

    private static final int PERMISSION_CODE = 1000;
    private static final Object IMAGE_CAPTURE_CODE = 1001;
    ImageView img;
    Intent myFile;
    Uri image_uri;
    Uri imageUri;
    String samplerem = "other";


    private Spinner spin1;
    private static final String[] paths1 = {"Other Complaint's","Fire", "Vehicle Accident", "Pole Damage", "Power Cut","Sound","Wire breakage"};

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_complaintt, container, false);

//        SupportMapFragment supportMapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.google_map);

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(getActivity(),new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_CODE);
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE);

        } else {
            Log.e("DB", "PERMISSION GRANTED");
        }

        client = LocationServices.getFusedLocationProviderClient(getActivity());


        storageReference = FirebaseStorage.getInstance().getReference();

        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        spin1 = (Spinner) view.findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, paths1);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spin1.setAdapter(adapter);

        img = (ImageView) view.findViewById(R.id.img);
        img.setOnClickListener(this);

        Button clr = view.findViewById(R.id.clrr);
        clr.setOnClickListener(this);

        ImageView btn = (ImageView) view.findViewById(R.id.btnSpk);
        btn.setOnClickListener(this);

        ImageView cam = (ImageView) view.findViewById(R.id.cam);
        cam.setOnClickListener(this);

        Button sub = view.findViewById(R.id.submit);
        sub.setOnClickListener(this);


        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img:
                Toast.makeText(getActivity(), "Filemanager is opened...!", Toast.LENGTH_LONG).show();

                TextView txt = complaintt.this.getView().findViewById(R.id.imgtext);
                txt.setVisibility(View.INVISIBLE);

                myFile = new Intent(Intent.ACTION_GET_CONTENT);
                myFile.setType("*/*");
                startActivityForResult(myFile, 10);
                break;
            case R.id.clrr:
                Toast.makeText(getActivity(), "Image cleared..!", Toast.LENGTH_LONG).show();
                img.setImageResource(android.R.color.transparent);

                TextView txxxt = complaintt.this.getView().findViewById(R.id.imgtext);
                txxxt.setVisibility(View.VISIBLE);

//                txvResult.setText("");
                break;
            case R.id.btnSpk:
                Toast.makeText(getActivity(), "Speaking..!", Toast.LENGTH_LONG).show();
//                openDialog();
                break;
            case R.id.cam:
                Toast.makeText(getActivity(), "Opening Camera..!", Toast.LENGTH_LONG).show();

                TextView txxt = complaintt.this.getView().findViewById(R.id.imgtext);
                txxt.setVisibility(View.INVISIBLE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                            ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    } else {
                        openCamera();
                    }
                } else {
                    openCamera();
                }
                break;
            case R.id.submit:

                EditText txtt = complaintt.this.getView().findViewById(R.id.complaint);
                ImageView img = complaintt.this.getView().findViewById(R.id.img);
                TextView txxxtt = complaintt.this.getView().findViewById(R.id.imgtext);
                TextView ph = complaintt.this.getView().findViewById(R.id.phone);
                String c = txtt.getText().toString().trim();
                String p = ph.getText().toString().trim();
                if(ph.equals("") || txtt.equals("")){
                    Toast.makeText(getContext(), "Give all details", Toast.LENGTH_LONG).show();
                }
                else {


                    SharedPreferences yes = getActivity().getSharedPreferences("dataremeber", Context.MODE_PRIVATE);
                    String hey = yes.getString("user", "");

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    myRef = database.getReference();
                    try {

                        myRef.child("complaint").child(hey).child("name").setValue(hey);
                        myRef.child("complaint").child(hey).child("message").setValue(c);
                        myRef.child("complaint").child(hey).child("phone").setValue(p);
                        myRef.child("complaint").child(hey).child("person").setValue("");

                        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss a", Locale.getDefault());
                        String currentDateandTime = sdf.format(new Date());

                        myRef.child("complaint").child(hey).child("time").setValue(currentDateandTime);

                        String text = spin1.getSelectedItem().toString();

                        if (text == "Other Complaint's") {
                            samplerem = "other";

                        } else if (text == "Fire") {
                            samplerem = "fire";
                        } else if (text == "Vehicle Accident") {
                            samplerem = "accident";
                        } else if (text == "Pole Damage") {
                            samplerem = "pole";
                        } else if (text == "Power Cut") {
                            samplerem = "power";
                        } else if (text == "Sound") {
                            samplerem = "sound";
                        } else {
                            samplerem = "wire";
                        }

                        myRef.child("complaint").child(hey).child("catergory").setValue(samplerem);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "fgf", Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(getActivity(), "Complaint Registered..! ", Toast.LENGTH_LONG).show();
                    txtt.setText("");
                    ph.setText("");
                    img.setImageResource(android.R.color.transparent);
                    txxxtt.setVisibility(View.VISIBLE);

                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                        client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                location = task.getResult();
                                if (location != null) {
                                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                                    try {
                                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                        myRef.child("complaint").child(hey).child("location").setValue(String.valueOf(addresses.get(0).getAddressLine(0)));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    myRef.child("complaint").child(hey).child("lat").setValue(String.valueOf(location.getLatitude()));
                                    myRef.child("complaint").child(hey).child("lng").setValue(String.valueOf(location.getLongitude()));

                                } else {
                                    Toast.makeText(getActivity(), "Location Null..! ", Toast.LENGTH_LONG).show();
                                    LocationRequest locationRequest = new LocationRequest()
                                            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                            .setInterval(10000)
                                            .setFastestInterval(1000)
                                            .setNumUpdates(1);

                                    LocationCallback locationCallback = new LocationCallback() {
                                        @Override
                                        public void onLocationResult(LocationResult locationResult) {
                                            Location location1 = locationResult.getLastLocation();
                                        }
                                    };

                                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                        // TODO: Consider calling
                                        //    ActivityCompat#requestPermissions
                                        return;
                                    }
                                    client.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                                }
                            }

                        });
                    } else {
                        Toast.makeText(getActivity(), "Permissions not given yet..! ", Toast.LENGTH_LONG).show();
                    }

                    Toast.makeText(getActivity(), "Give Feedback", Toast.LENGTH_LONG).show();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_containerr, new step(), "findThisFragment")
                            .addToBackStack(null)
                            .commit();

                }
                break;
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera");

        ContentResolver resolver = Objects.requireNonNull(getActivity()).getContentResolver();

        image_uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(cameraIntent, (Integer) IMAGE_CAPTURE_CODE);

    }

//    private void openDialog() {
//        Dialog dialog = new Dialog();
//        dialog.show(getFragmentManager(),"dialog");
//    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] ==  PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }
                else {
                    Toast.makeText(getActivity(), "Permissions denied..!", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri){
        SharedPreferences yes = getActivity().getSharedPreferences("dataremeber", Context.MODE_PRIVATE);
        String hey=yes.getString("user","");

        String text = spin1.getSelectedItem().toString();
        String sss;

        if(text=="Other Complaint's"){
            sss = "other";
        }
        else if(text=="Fire"){
            sss = "fire";
        }
        else if(text=="Vehicle Accident"){
            sss = "accident";
        }
        else if(text=="Pole Damage"){
            sss = "pole";
        }
        else if(text=="Power Cut"){
            sss = "power";
        }
        else if(text=="Sound"){
            sss = "sound";
        }
        else{
            sss = "wire";
        }

        StorageReference fileRef = storageReference.child(hey).child("complaint.jpg");
        String finalSss = sss;
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        myRef = database.getReference();
                        myRef.child("complaint").child(hey).child("image").setValue(String.valueOf(uri));
                    }
                });

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"It failed",Toast.LENGTH_LONG).show();
                    }
                });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {
//                    String path = data.getData().getPath();
                    img.setImageURI(data.getData());
                    imageUri = data.getData();
                    uploadImageToFirebase(imageUri);
                }
                break;
            case 1001:
                if (resultCode == RESULT_OK) {
                    img.setImageURI(image_uri);
                    uploadImageToFirebase(image_uri);
                }
        }
    }
}
