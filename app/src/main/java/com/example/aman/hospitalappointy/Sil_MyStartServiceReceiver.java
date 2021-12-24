package com.example.aman.hospitalappointy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Sil_MyStartServiceReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Sil_Util.scheduleJob(context);
    }
}
