package one.dichmann.lectioapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import downloadLectio.AsyncResponse;
import downloadLectio.GetGyms;
import downloadLectio.GetSchedule;
import schedule.Schedule;

public class ScheduleActivity extends AppCompatActivity implements AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        String gymID;
        String nameID;

        Intent intent = getIntent();
        gymID = intent.getStringExtra(LoginActivity.finalGymID);
        nameID = intent.getStringExtra(LoginActivity.finalNameID);
        System.out.println(gymID+"-"+nameID);

        getIntent();

        GetSchedule asyncTaskSchedule = new GetSchedule();
        asyncTaskSchedule.delegate = this;
        asyncTaskSchedule.gymID = gymID;
        asyncTaskSchedule.nameID = nameID;
        asyncTaskSchedule.execute();

    }

    @Override
    public void processFinish(String output) {

    }
}
