package schedule;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import one.dichmann.lectioapp.R;
import one.dichmann.lectioapp.ScheduleActivity;

public class Display {

    public ArrayList<Object> vertical(String lessons, Context context) {
        String[] lesson = lessons.split("---");

        ArrayList<Object> textViews = new ArrayList<Object>();
        for (int i=0;i<3;i++) {
            TextView moduleVertical = new TextView(context);
            moduleVertical.setText(lesson[i]);
            moduleVertical.setTextSize(25);
            moduleVertical.setPadding(10, 10, 10, 10);
            moduleVertical.setGravity(Gravity.CENTER);
            moduleVertical.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            TextView moduleHorizontal = new TextView(context);
            moduleHorizontal.setText(lesson[i]);
            moduleHorizontal.setTextSize(15);
            moduleHorizontal.setPadding(5, 5, 5, 5);
            moduleHorizontal.setGravity(Gravity.CENTER);
            moduleHorizontal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            textViews.add((Object)moduleVertical);
            textViews.add((Object)moduleHorizontal);
        }
        return textViews;
    }

    public TextView[] horizontal(String lessons, Context context) {
        return null;
    }

    public TextView[] DayAndDate(String date, String day, Context context) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView dayDay = new TextView(context);
        dayDay.setTextSize(25);
        dayDay.setGravity(Gravity.CENTER);
        dayDay.setTextColor(context.getResources().getColor(R.color.schedule_TextColor));
        dayDay.setLayoutParams(layoutParams);
        dayDay.setText(day);

        TextView dayDate = new TextView(context);
        dayDate.setTextSize(25);
        dayDate.setTextColor(context.getResources().getColor(R.color.schedule_TextColor));
        dayDate.setLayoutParams(layoutParams);
        dayDate.setGravity(Gravity.CENTER);
        dayDate.setText(date);

        TextView[] views = {dayDay,dayDate};
        return views;
    }
}
