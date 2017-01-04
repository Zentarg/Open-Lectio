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
    private String todayDay;
    private String todayDate;
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

        todayDay = Weekday.Weekday();

        todayDate = date[6]+date[7]+"/"+date[9]+date[10]+"-"+date[1]+date[2]+date[3]+date[4];

        View schedule = findViewById(R.id.schedule_DayAndDate);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView dayDay = new TextView(this);
        dayDay.setTextSize(25);
        dayDay.setGravity(Gravity.CENTER);
        dayDay.setTextColor(getResources().getColor(R.color.schedule_TextColor));
        dayDay.setLayoutParams(layoutParams);
        dayDay.setText(todayDay);

        TextView dayDate = new TextView(this);
        dayDate.setTextSize(25);
        dayDate.setTextColor(getResources().getColor(R.color.schedule_TextColor));
        dayDate.setLayoutParams(layoutParams);
        dayDate.setGravity(Gravity.CENTER);
        dayDate.setText(todayDate);

        ((LinearLayout) schedule).addView(dayDay);
        ((LinearLayout) schedule).addView(dayDate);


        for (int i = 0; i < modules.length; i++) {
            String[] module = modules[i].split("---");
            Pattern noteRegex = Pattern.compile(".*?" + todayDate + ".*?");
            Matcher noteMatcher = noteRegex.matcher(module[0]);
            boolean found = noteMatcher.find();
            if(found){
                this.CreateModule("time", "team", "teacher", "room");
            }
        }
    }

    public void CreateModule(String time, String team, String teacher, String room) {
        LinearLayout mainLinearLayout = (LinearLayout) findViewById(R.id.activity_schedule);

        LinearLayout.LayoutParams moduleLLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        moduleLLParams.setMargins(0, 20, 0, 0);

        LinearLayout moduleLL = new LinearLayout(this);
        moduleLL.setOrientation(LinearLayout.VERTICAL);
        moduleLL.setLayoutParams(moduleLLParams);
        moduleLL.setGravity(Gravity.CENTER);
        moduleLL.setBackgroundColor(getResources().getColor(R.color.schedule_Regular));

        TextView moduleTeam = new TextView(this);
        moduleTeam.setText(team);
        moduleTeam.setTextSize(25);
        moduleTeam.setGravity(Gravity.CENTER);
        moduleTeam.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        moduleLL.addView(moduleTeam);

        TextView moduleTeacher = new TextView(this);
        moduleTeacher.setText(teacher);
        moduleTeacher.setTextSize(25);
        moduleTeacher.setGravity(Gravity.CENTER);
        moduleTeacher.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        moduleLL.addView(moduleTeacher);

        TextView moduleRoom = new TextView(this);
        moduleRoom.setText(room);
        moduleRoom.setTextSize(25);
        moduleRoom.setGravity(Gravity.CENTER);
        moduleRoom.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        moduleLL.addView(moduleRoom);


        mainLinearLayout.addView(moduleLL);
    }
}
