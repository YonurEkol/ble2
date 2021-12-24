package com.example.aman.hospitalappointy;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.example.aman.hospitalappointy.activity.Sdk_lib;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.logger.Logger;



public class Sil_StartReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent arg1) {
        Intent intent = new Intent(context,MainActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
        Logger.i("Autostart", "started");
//        context.startService(i);
//        if (intent.action == Intent.ACTION_BOOT_COMPLETED && getServiceState(context) == ServiceState.STARTED) {
//            Intent(context, EndlessService::class.java).also {
//                this.action = Actions.START.name;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    context.startForegroundService(it)
//                }
//                context.startService(it)
//            }
//        }
    }
}


class servicex extends Service
{
    //Firebase Auth
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference();

    private static final String TAG = "MyService";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onDestroy() {

    }

    @Override
    public void onStart(Intent intent, int startid)
    {
        String currnetUID = mAuth.getCurrentUser().getUid().toString();
        mUserDatabase.child("Patient_Details").child(currnetUID).child("status2").setValue("New");
        Sdk_lib sl = new Sdk_lib(getBaseContext());
        sl.connectDevice("DC:BB:2C:A7:A1:65", "SW12","HEART_DETECT_START");

//        Intent intents = new Intent(getBaseContext(),hello.class);
//        intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intents);

    }
}


