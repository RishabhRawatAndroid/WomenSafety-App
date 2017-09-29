package com.myapp.rishabhrawat.womensafety;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Iterator;

public class MapTrackingActivity extends FragmentActivity implements OnMapReadyCallback {

    static  String id;
    private GoogleMap mMap;
    private DatabaseReference locationroot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_tracking);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        stopService(new Intent(MapTrackingActivity.this,NotificationService.class));
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        SharedPreferenceClass sharedPreferenceClass=new SharedPreferenceClass(MapTrackingActivity.this);

        locationroot = FirebaseDatabase.getInstance().getReference().child("LOCATION").child(sharedPreferenceClass.locationid());

        Toast.makeText(this, "the child is "+sharedPreferenceClass.locationid(), Toast.LENGTH_SHORT).show();

        DatabaseReference delroot = FirebaseDatabase.getInstance().getReference().child("NOTIFICATION").child(sharedPreferenceClass.locationid());
        delroot.setValue(null);
        stopService(new Intent(MapTrackingActivity.this,NotificationService.class));
        startService(new Intent(MapTrackingActivity.this,NotificationService.class));


        locationroot.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Update_Location(dataSnapshot, googleMap);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //Update_Location(dataSnapshot,googleMap);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void Update_Location(DataSnapshot dataSnapshot, GoogleMap myMap) {

        SharedPreferenceClass sharedPreferenceClass=new SharedPreferenceClass(MapTrackingActivity.this);
        double latitude;
        double longitude;

        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()) {

                if (dataSnapshot.getValue() != null) {

                    latitude = Double.parseDouble(String.valueOf(((DataSnapshot) i.next()).getValue()));
                    longitude = Double.parseDouble(String.valueOf(((DataSnapshot) i.next()).getValue()));

                    Log.e("RISHABHMAP", String.format("Latitude :" + latitude + "\n" + "Longitude : " + longitude + " " + id));
                    CameraPosition googlePlex = CameraPosition.builder()
                            .target(new LatLng(latitude, longitude))
                            .zoom(16)
                            .bearing(0)
                            .tilt(45)
                            .build();

                    myMap.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
                    myMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 1000, null);
                    myMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(sharedPreferenceClass.getuserpresentlocation()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));


                }

        }

    }
}
