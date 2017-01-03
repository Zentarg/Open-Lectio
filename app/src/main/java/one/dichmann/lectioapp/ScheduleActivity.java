package one.dichmann.lectioapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import downloadLectio.AsyncResponse;
import downloadLectio.GetGyms;
import downloadLectio.GetSchedule;

public class ScheduleActivity extends AppCompatActivity implements AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        String gymID;
        String nameID;

        gymID = getIntent().getStringExtra(LoginActivity.finalGymID);
        nameID = getIntent().getStringExtra(LoginActivity.finalNameID);
        System.out.println(gymID+"-"+nameID);

        getIntent();
        GetSchedule asyncTaskSchedule = new GetSchedule();
        asyncTaskSchedule.gymID = gymID;
        asyncTaskSchedule.nameID = nameID;
        asyncTaskSchedule.execute();

    }

    @Override
    public void processFinish(String output) {

    }
}
