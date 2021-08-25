package com.example.smartvest;


import androidx.fragment.app.FragmentActivity;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.smartvest.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.smartvest.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ManagerActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    UserVO vo;
    List<UserVO> data;
    LatLng smart;

    //    float latitude=null;
//    double longitude=null;
//    float altitude=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //get()을 통해서 해당 문서의 정보를 가져온다.


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user_gps")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                data.add(new UserVO((double) document.getData().get("경도"), (double) document.getData().get("고도"),
                                        (double) document.getData().get("위도")));
                            }
                        } else {
                            Log.w("테스트00", "Error getting documents.", task.getException());
                        }
                        for (int i = 0; i < data.size(); i++) {
                            Log.v("수학", String.valueOf(data.get(i).getAltitude()));
                            Log.v("수학", String.valueOf(data.get(i).getLatitude()));
                            Log.v("수학", String.valueOf(data.get(i).getLongitude()));
                        }
                        for (int i = 0; i < data.size(); i++) {

                            double a = data.get(i).getLatitude();
                            double b = data.get(i).getLongitude();
                            float c = data.get(i).getAltitude();

                            smart = new LatLng(a, b);
                            Log.v("테스트", "if전");
                            if (data.get(i).getLatitude() == data.get(i).getLatitude() + i) {
                                Log.v("테스트", "if안");
                                mMap.addMarker(new MarkerOptions().position(smart).title("사용자" + i)
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            } else {
                                Log.v("테스트", "else안");
                                mMap.addMarker(new MarkerOptions().position(smart).title("사용자" + i)
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                            }
                            Log.v("테스트", "if끝");
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(smart));
                            CameraUpdate cUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(a, b), c);
                            //newlatlngzoom(latlng, 줌배율 아하 )
                            mMap.moveCamera(cUpdate);
                            mMap.setMaxZoomPreference(20);
                        }


                    }
                });





    }


}
