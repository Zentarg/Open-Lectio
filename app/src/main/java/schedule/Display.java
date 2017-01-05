package schedule;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import one.dichmann.lectioapp.R;
import one.dichmann.lectioapp.ScheduleActivity;

public class Display {

    public TextView[] vertical(String lessons, Context context, LinearLayout mainLinearLayout) {
        String[] lesson = lessons.split("---");

        TextView[] textViews = new TextView[3];
        for (int i=0;i<3;i++) {
            TextView module = new TextView(context);
            module.setText(lesson[i]);
            module.setTextSize(25);
            module.setGravity(Gravity.CENTER);
            module.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textViews[i] = module;
        }
        return textViews;
    }

    public void horizontal(String lessons, Context context) {

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
