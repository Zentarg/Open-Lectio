package one.dichmann.lectioapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import downloadLectio.AsyncResponse;
import downloadLectio.GetGyms;
import downloadLectio.GetSchedule;
import schedule.Schedule;
import schedule.Weekday;

public class ScheduleActivity extends AppCompatActivity implements AsyncResponse {
    private String today;

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

    public void CreateModule(String date, String team, String teacher, String room) {

        String lessonDate;
        String lessonTime;

        today = Weekday.Today();

        View schedule = findViewById(R.id.schedule_DayAndDate);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView day = new TextView(this);
        day.setTextSize(25);
        day.setGravity(View.TEXT_ALIGNMENT_CENTER);
        day.setTextColor(getResources().getColor(R.color.schedule_Regular));
        day.setLayoutParams(layoutParams);
        day.setText(today);

        ((LinearLayout) schedule).addView(day);

    }

}
