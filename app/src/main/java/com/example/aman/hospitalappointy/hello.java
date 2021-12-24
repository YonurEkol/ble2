package com.example.aman.hospitalappointy;

import android.app.Activity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class hello extends Activity {
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FirebaseDatabase.getInstance().getReference().child("Patient_Details").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("status3").setValue("İlk baslama basarili");
        setContentView(R.layout.activity_main);
//        Toast.makeText(getBaseContext(), "Hello........", Toast.LENGTH_LONG).show();
    }
}
