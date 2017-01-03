package one.dichmann.lectioapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private String today;
    private String[] date;

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
        CreateDay(output);
    }

    public void CreateDay(String Schedule) {

        String[] modules = Schedule.split("££");
        String lessonDate;
        String lessonTime;

        date = Weekday.Today().split("");
            if (date[6].equals("0")) {
                date[6] = "";
            }
            if (date[9].equals("0")) {
                date[9] = "";
        }
        today = date[6]+date[7]+"/"+date[9]+date[10]+"-"+date[1]+date[2]+date[3]+date[4];

        View schedule = findViewById(R.id.schedule_DayAndDate);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView day = new TextView(this);
        day.setTextSize(25);
        day.setGravity(View.TEXT_ALIGNMENT_CENTER);
        day.setTextColor(getResources().getColor(R.color.schedule_Regular));
        day.setLayoutParams(layoutParams);
        day.setText(today);

        System.out.println(today);

        ((LinearLayout) schedule).addView(day);
        for (int i = 0; i < modules.length; i++) {
            String[] module = modules[i].split("---");
            Pattern noteRegex = Pattern.compile(".*?" + today + ".*?");
            Matcher noteMatcher = noteRegex.matcher(module[0]);
            boolean found = noteMatcher.find();
            if(found){
                this.CreateModule(modules[i]);
            }
        }
    }
    public void CreateModule(String date) {
        System.out.println(date);
    }
}
