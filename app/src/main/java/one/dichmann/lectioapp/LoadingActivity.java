package one.dichmann.lectioapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;

import downloadLectio.GetSchedule;

public class LoadingActivity extends AppCompatActivity {

    public static String finalIntent = "one.dichmann.LectioApp.Intent";
    private Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Intent intent = getIntent();
        String intention = intent.getStringExtra(finalIntent);

        switch (intention) {
            case "Schedule": {
                String gymID = intent.getStringExtra(LoginActivity.finalGymID);
                String nameID = intent.getStringExtra(LoginActivity.finalNameID);
                c.setTimeInMillis(intent.getLongExtra(ScheduleActivity.finalLong, 1L));

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
