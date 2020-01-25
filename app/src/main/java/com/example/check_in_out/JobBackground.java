package com.example.check_in_out;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static com.example.check_in_out.CheckIn.EXTRA;
import static com.example.check_in_out.CheckIn.EXTRA_ID;


public class JobBackground extends JobService {

    public static final String TAG = "JobBackgroung: ";
    List<Visitors> overStayed;
    List<Visitors> visitor;
    LiveData<List<Visitors>> lala;
    Date allTime;
    Date dateOnly;
    Date current;
    SimpleDateFormat ft;
    VisitorsRepository visitorsRepository;
    NotificationManagerCompat notificationManagerCompat;
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        visitor = new ArrayList<>();
        overStayed =new ArrayList<>();
        visitorsRepository = new VisitorsRepository(getApplication());
        lala = visitorsRepository.getInVisitors();
        lala.observeForever(new Observer<List<Visitors>>() {
            @Override
            public void onChanged(List<Visitors> visitors) {
                visitor = visitors;
            }
        });



        ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date())+" "+"17:00:00";
        notificationManagerCompat = NotificationManagerCompat.from(this);


        try {
            allTime = ft.parse(currentDateandTime);
            dateOnly = ft.parse(date);
        } catch (ParseException e) {
            Log.e(TAG, "onStartJob: "+"problem with t or a" );
        }

        doInTheBacground(jobParameters);
        return true;
    }

    private void doInTheBacground(final JobParameters jobParameters) {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                if(visitor ==null)
                   // return;
                for(int i = 0; i< visitor.size(); i++){
                    Visitors visit = visitor.get(i);
                    String dt = visit.getDateIn()+" "+visit.getTimeIn();
                    try {
                        current = ft.parse(dt);
                    }catch (ParseException p){
                        Log.d(TAG, "run: ex");
                    }
                    if((current.getTime()+  (5*60*1000))< allTime.getTime() || current.getTime() < dateOnly.getTime() ){
                        overStayed.add(visit);
                    }

                }
                if(overStayed==null)
                    return;
                for(int i=0; i<overStayed.size(); i++){
                    Visitors visit = visitor.get(i);
                    Intent notificationIntent = new Intent(getApplicationContext(), CheckOut.class);
                    passData(notificationIntent,visit);
                    PendingIntent notificationPendingIntent = PendingIntent.getActivity(getApplicationContext(),
                            2, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    Notification notification = new NotificationCompat.Builder(JobBackground.this,  app.channel)
                            .setSmallIcon(R.drawable.in_black)
                            .setContentTitle("This is person is still in")
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentText(visit.getName() +" of id "+visit.getIdNumber())
                            .setContentIntent(notificationPendingIntent)
                            .setAutoCancel(true)
                            .build();
                    notificationManagerCompat.notify(i, notification);
                }

            jobFinished(jobParameters, false);
            }
        }).start();
    }

    void passData(Intent data, Visitors visit){
        data.putExtra(EXTRA_ID,visit.getId());
        data.putExtra(EXTRA + "name", visit.getName());
        data.putExtra(EXTRA + "secondname", visit.getSecondName());
        data.putExtra(EXTRA + "idnumber",visit.getIdNumber());
        data.putExtra(EXTRA + "plate", visit.getPlate());
        data.putExtra(EXTRA + "phone",visit.getPhone());
        data.putExtra(EXTRA + "purpose", visit.getPurpose());
        data.putExtra(EXTRA + "date",visit.getDateIn());
        data.putExtra(EXTRA + "time", visit.getTimeIn());
        data.putExtra(EXTRA + "flag", visit.isFlagIn());
        data.putExtra(EXTRA+"flagReason", visit.getFlagReason());
        data.putExtra(EXTRA + "status", visit.isStatus());
        data.putExtra(EXTRA + "timeOut", visit.getTimeOut());
        data.putExtra(EXTRA + "dateOut", visit.getDateOut());

    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
