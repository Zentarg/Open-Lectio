package one.dichmann.lectioapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import downloadLectio.AsyncResponse;
import downloadLectio.GetGyms;

public class ScheduleActivity extends AppCompatActivity implements AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Bundle receive = new Bundle();
        String gymID;
        String nameID;

        receive = getIntent().getExtras();
        gymID = receive.getString("gym");
        nameID = receive.getString("name");

        getIntent();
        GetGyms asyncTaskSchedule = new GetGyms();
        asyncTaskSchedule.delegate = this;
        asyncTaskSchedule.execute();

    }

    @Override
    public void processFinish(String output) {

    }
}
