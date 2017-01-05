package one.dichmann.lectioapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import downloadLectio.AsyncResponse;
import downloadLectio.GetGyms;
import downloadLectio.GetSchedule;
import schedule.Schedule;
import schedule.Weekday;

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

        Schedule asyncTaskSchedule = new Schedule();
        asyncTaskSchedule.delegate = this;
        asyncTaskSchedule.gymID = gymID;
        asyncTaskSchedule.nameID = nameID;
        asyncTaskSchedule.context = this;
        asyncTaskSchedule.mainLinearLayout = (LinearLayout) findViewById(R.id.activity_schedule);
        asyncTaskSchedule.dayAndDate = findViewById(R.id.schedule_DayAndDate);
        asyncTaskSchedule.execute();
    }

    @Override
    public void processFinish(String output) {
    }

    @Override
    public void processViews(TextView[] textView) {
        ((LinearLayout) findViewById(R.id.schedule_DayAndDate)).addView(textView[0]);
        ((LinearLayout) findViewById(R.id.schedule_DayAndDate)).addView(textView[1]);

        LinearLayout.LayoutParams moduleLLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        moduleLLParams.setMargins(0, 20, 0, 0);

        LinearLayout moduleLL = new LinearLayout(this);
        moduleLL.setOrientation(LinearLayout.VERTICAL);
        moduleLL.setLayoutParams(moduleLLParams);
        moduleLL.setGravity(Gravity.CENTER);
        moduleLL.setBackgroundColor(this.getResources().getColor(R.color.schedule_Regular));

        for (int i=2;i<textView.length;i++) {
            moduleLL.addView(textView[i]);
        }
    }
}
