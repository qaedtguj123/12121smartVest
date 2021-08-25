package com.example.smartvest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class sensorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //CollectionReference 는 파이어스토어의 컬렉션을 참조하는 객체다.
        CollectionReference productRef = db.collection("vest").document("RaspberryPi").collection("sensor");
        //get()을 통해서 해당 컬렉션의 정보를 가져온다.
        productRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //작업이 성공적으로 마쳤을때
                if (task.isSuccessful()) {
                    //컬렉션 아래에 있는 모든 정보를 가져온다.
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //document.getData() or document.getId() 등등 여러 방법으로
                        //데이터를 가져올 수 있다.
                        Map a =  document.getData();
                        String b =  document.getId();
                        Log.v("테스트",String.valueOf(a));
                        Log.v("테스트",b);
                    }
                    //그렇지 않을때
                } else {

                }
            }
        });




    }
}