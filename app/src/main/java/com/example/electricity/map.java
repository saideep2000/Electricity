package com.example.electricity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;
import static com.example.electricity.R.id.accident;
import static com.example.electricity.R.id.icon;
import static com.example.electricity.R.id.message;

public class map extends Fragment implements View.OnClickListener , OnMapReadyCallback{

    FusedLocationProviderClient client;
    LocationManager locationManager;
    double lati;
    double longi;
    List<Address> addressList;
    DatabaseReference myRef;
    Location location1,location,location2;

    private static final int PERMISSION_CODE = 1000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        View view = inflater.inflate(R.layout.map, container, false);


        SupportMapFragment mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        mMapFragment.getMapAsync(this);



        return view;
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng khammam = new LatLng(17.2473,80.1514);

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(khammam,13));

        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            myRef = database.getReference();
            myRef = myRef.child("complaint");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                    while(items.hasNext())
                    {
                        MarkerOptions markerOptions = new MarkerOptions();
                        DataSnapshot item = items.next();
                        String name , phone , latii , longii , catergory,message;
                        name = item.child("name").getValue().toString();
                        phone = item.child("phone").getValue().toString();
                        catergory = item.child("catergory").getValue().toString();
                        message = item.child("message").getValue().toString();

                        latii = item.child("lat").getValue().toString();
                        longii = item.child("lng").getValue().toString();

                        Toast.makeText(getContext(), "Getting complaint's addresses", Toast.LENGTH_SHORT).show();


                        LatLng sample = new LatLng(Double.parseDouble(latii),Double.parseDouble(longii));
                        markerOptions.position(sample);
                        markerOptions.title(name+ " : "+ phone+ " : "+ catergory);
                        markerOptions.snippet(message);

                        int height = 100;
                        int width = 100;
                        BitmapDrawable bitmapdraw;
                        Bitmap b;

                        if(catergory.equals("fire")){

                            bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.fire);
                        }
                        else if(catergory.equals("accident")){
                            bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.accident);

                        }
                        else if(catergory.equals("pole")){
                            bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.pole);

                        }
                        else if(catergory.equals("power")){
                            bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.power);

                        }
                        else if(catergory.equals("sound")){
                            bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.sound);

                        }
                        else if(catergory.equals("wire")){
                            bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.wirecut);

                        }
                        else{
                            bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.block);
                        }
                        b = bitmapdraw.getBitmap();
                        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker)).anchor(0.5f, 1);
//                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sample,10));
                        googleMap.addMarker(markerOptions);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(getContext(), "Failed try again...sorry", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e){
            Toast.makeText(getContext(), "Wrong way", Toast.LENGTH_LONG).show();
        }



        //officer location


        try
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE);

        } else {
            Log.e("DB", "PERMISSION GRANTED");
        }

        client = LocationServices.getFusedLocationProviderClient(getActivity());

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    location = task.getResult();
                    if (location != null) {

                        LatLng loc = new LatLng(location.getLatitude(),location.getLongitude());
                        MarkerOptions markerOptio = new MarkerOptions();
                        markerOptio.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                        markerOptio.position(loc);
                        markerOptio.title("My Location");
                        googleMap.addMarker(markerOptio);

//                        Toast.makeText(getContext(), loc.latitude+" - "+loc.longitude, Toast.LENGTH_SHORT).show();

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
                                location2 = locationResult.getLastLocation();

                                LatLng loc = new LatLng(location2.getLatitude(),location2.getLongitude());
                                MarkerOptions markerOptionss = new MarkerOptions();
                                markerOptionss.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                                markerOptionss.position(loc);
                                markerOptionss.title("My Location");
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc,8));
                                googleMap.addMarker(markerOptionss);

//                                Toast.makeText(getContext(), location2.getLatitude()+" - "+location2.getLongitude(), Toast.LENGTH_LONG).show();

                            }
                        };

                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        client.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                }

            });
        }
        else{
            Toast.makeText(getActivity(), "Permissions not given yet..! ", Toast.LENGTH_LONG).show();
        }




        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOption = new MarkerOptions();
                markerOption.position(latLng);

                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    markerOption.title(String.valueOf(addresses.get(0).getAddressLine(0)));
                } catch (IOException e) {
                    e.printStackTrace();
                }



                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,14));
                googleMap.addMarker(markerOption);
            }
        });

    }
}