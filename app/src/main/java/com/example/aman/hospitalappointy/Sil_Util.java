package com.example.aman.hospitalappointy;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.widget.Toast;

public class Sil_Util {


    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, Sil_MyJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(30 * 1000); // Wait at least 30s
        builder.setOverrideDeadline(60 * 1000); // Maximum delay 60s
        Toast.makeText(context, "Util basladi", Toast.LENGTH_LONG).show();

        JobScheduler jobScheduler = (JobScheduler)context.getSystemService(context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
    }


}
