package one.dichmann.lectioapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;

import downloadLectio.GetSchedule;

public class LoadingActivity extends AppCompatActivity {

    public static String finalIntent = "one.dichmann.LectioApp.Intent";
    public static String finalLong = "one.dichmann.LetioApp.Long";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Calendar c = Calendar.getInstance();


        Intent intent = getIntent();
        long timeInMillis = intent.getLongExtra(finalLong, 1L);
        System.out.print(timeInMillis);
        System.out.println("lonng");
        String intention = intent.getStringExtra(finalIntent);
        c.setTimeInMillis(timeInMillis);
        System.out.print(c.getTimeInMillis());
        if ((int) c.get(Calendar.YEAR)==1970) {
            c.setTime(new Date());
        }

        switch (intention) {
            case "Schedule": {
                String gymID = intent.getStringExtra(LoginActivity.finalGymID);
                String nameID = intent.getStringExtra(LoginActivity.finalNameID);

                GetSchedule asyncTaskSchedule = new GetSchedule();
                asyncTaskSchedule.gymID = gymID;
                asyncTaskSchedule.nameID = nameID;
                asyncTaskSchedule.c = c;
                asyncTaskSchedule.context = this;
                asyncTaskSchedule.execute();
            }
            case "Extra": {
                String gymID = intent.getStringExtra(LoginActivity.finalGymID);
                String nameID = intent.getStringExtra(LoginActivity.finalNameID);

                GetSchedule asyncTaskSchedule = new GetSchedule();
                asyncTaskSchedule.gymID = gymID;
                asyncTaskSchedule.nameID = nameID;
                asyncTaskSchedule.c = c;
                asyncTaskSchedule.context = this;
                asyncTaskSchedule.execute();
            }
        }
    }
}
