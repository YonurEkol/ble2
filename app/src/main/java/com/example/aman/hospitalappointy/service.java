package com.example.aman.hospitalappointy;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.aman.hospitalappointy.activity.Sdk_lib;

public class service extends Service {
    private static final String TAG = "MyService";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onDestroy() {
        Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
//        Log.d(TAG, "onDestroy");
    }


//    @Override
//    public void onStart(Intent intent, int startid)
//    {
//        Intent intents = new Intent(getBaseContext(),hello.class);
//        intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intents);
////        Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
////        Log.d(TAG, "onStart");
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID, "My app no sound", NotificationManager.IMPORTANCE_LOW
            );
            notificationChannel.setDescription("");
            notificationChannel.setSound(null,null);
            notificationChannel.enableLights(false);
            notificationChannel.enableVibration(false);
            notificationManager.createNotificationChannel(notificationChannel);
            Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();
            startForeground(1, notification);
        }
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent intents = new Intent(getBaseContext(),hello.class);
        //intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intents);
        Toast.makeText(this, "start kısmında", Toast.LENGTH_LONG).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Sdk_lib sl = new Sdk_lib(this);
            sl.connectDevice("DC:BB:2C:A7:A1:65", "SW12","HEART_DETECT_START");
            startForegroundService(intents);
        }else{
            Toast.makeText(this, "Islem else dustu", Toast.LENGTH_LONG).show();
        }
//        handleStart(intent, startId);
        return START_STICKY;
    }
}
