package com.example.aman.hospitalappointy;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.widget.Toast;

public class Sil_MyJobService extends JobService {
    @Override
    public boolean onStopJob(JobParameters params) {
        Toast.makeText(this, "İslem bitti", Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Toast.makeText(this, "İslem basladı", Toast.LENGTH_LONG).show();
        return true;
    }
}
